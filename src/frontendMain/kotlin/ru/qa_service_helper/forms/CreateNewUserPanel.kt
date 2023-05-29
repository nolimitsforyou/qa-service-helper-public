package ru.qa_service_helper.forms

import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.core.onClick
import io.kvision.core.onEvent
import io.kvision.form.check.CheckBox
import io.kvision.form.check.checkBox
import io.kvision.form.select.SimpleSelect
import io.kvision.form.select.simpleSelect
import io.kvision.form.text.Text
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.remote.ServiceException
import io.kvision.require
import kotlinx.coroutines.launch
import ru.qa_service_helper.AppScope
import ru.qa_service_helper.CreateUserModel
import ru.qa_service_helper.Environment.DEV_ENV
import ru.qa_service_helper.Environment.RC_ENV
import ru.qa_service_helper.Model.qaHelperService
import ru.qa_service_helper.css.*
import ru.qa_service_helper.forms.validators.CreateUserValidator.userFieldsValidator

class CreateNewUserPanel : SimplePanel() {

    private lateinit var emailAddress: Text
    private lateinit var password: Text
    private lateinit var post: Text
    private lateinit var phone: Text
    private lateinit var infoText: Span
    private lateinit var selectEnv: SimpleSelect
    private lateinit var buttonSubmit: Button
    private lateinit var checkBoxPhoneNumber: CheckBox
    private lateinit var iconRefresh: Image
    private val listFields by lazy { listOf(emailAddress, password, phone) }

    init {
        addCssStyle(formCreateStyle)

        div {
            addCssStyle(blockPanelHeaderStyle)
            div {
                addCssStyle(headerCreateFormStyle)
                span {
                    addCssStyle(titleTextStyle)
                    content = "Генерация нового пользователя"
                }
            }

            div {
                addCssStyle(blockIconRefreshStyle)
                iconRefresh = image(
                    require("img/refresh-icon.svg") as? String,
                    alt = "refresh",
                    responsive = true, shape = ImageShape.ROUNDED
                ) {
                    height = CssSize(20, UNIT.px)
                }
            }
        }

        div {
            //БЛОК с формами
            addCssStyle(blockInFormCreateStyle)

            div {
                //БЛОК с полями
                addCssStyle(blockFieldsStyle)

                div {
                    //блок эмейла
                    addCssStyle(inputBlockStyle)

                    label(content = "Email") {
                        addCssStyle(inputLabelStyle)
                    }
                    emailAddress = text(type = TextInputType.EMAIL) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок пароля
                    addCssStyle(inputBlockStyle)

                    label(content = "Password") {
                        addCssStyle(inputLabelStyle)
                    }
                    password = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок пароля
                    addCssStyle(inputBlockStyle)

                    label(content = "Post") {
                        addCssStyle(inputLabelStyle)
                    }
                    post = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок номера телефона
                    addCssStyle(inputBlockStyle)

                    label(content = "Phone number") {
                        addCssStyle(inputLabelStyle)
                    }
                    phone = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                        maxlength = 11
                        placeholder = "79998887766"
                    }
                }

                div {
                    //блок чекбокс
                    addCssStyle(buttonsGroupStyle)

                    checkBoxPhoneNumber = checkBox(label = "Номер телефона подтверждён") {
                        addCssStyle(checkBoxPhoneStyle)
                    }
                }

                div {
                    //блок селектов с кнопкой
                    addCssStyle(buttonsGroupStyle)
                    selectEnv = simpleSelect {
                        addCssStyle(selectEnvStyle)
                        value = RC_ENV.first
                        options = listOf(RC_ENV, DEV_ENV)
                    }

                    buttonSubmit = button("Submit")
                }
            }

            val infoWindow = div {
                //блок информ-окна
                addCssStyle(informWindowStyle)
                infoText = span {
                    addCssStyle(infoTextStyle)
                    content = TEXT_INFO_WINDOW_CREATE_USER
                }
            }

            fun post() {
                AppScope.launch {
                    try {
                        qaHelperService.createNewUser(
                            CreateUserModel(
                                email = emailAddress.value,
                                password = password.value,
                                post = post.value,
                                phoneNumber = phone.value,
                                phoneIsVerified = checkBoxPhoneNumber.value,
                                selectEnvironment = selectEnv.value!!
                            )
                        ).apply {
                            infoWindow.add(
                                span {
                                    addCssStyle(infoTextStyle)
                                    p { content = "Сгенерирован новый пользователь:" }
                                    p { content = "Email: $email" }
                                    p { content = "Password: $password" }
                                    p { content = "BitrixId: $bitrixId" }
                                    p { content = "Phone number: $phoneNumber" }
                                    p { content = "Post: $post" }
                                    p { content = "Phone is verified: $isVerifiedPhone" }
                                }
                            )
                        }
                        buttonSubmit.disabled = false
                    } catch (e: ServiceException) {
                        //TODO 500ку не обрабатывает
                        infoWindow.add(
                            span {
                                addCssStyle(infoTextStyle)
                                p { content = e.message }
                            }
                        )
                        buttonSubmit.disabled = false
                    }
                }
            }

            listFields.forEach {
                it.onEvent {
                    keyup = {
                        userFieldsValidator(
                            fieldEmail = emailAddress,
                            fieldPhone = phone,
                            button = buttonSubmit
                        )
                    }
                    keydown = {
                        userFieldsValidator(
                            fieldEmail = emailAddress,
                            fieldPhone = phone,
                            button = buttonSubmit
                        )
                    }
                }
            }

            buttonSubmit.onClick {
                //TODO отображать спиннер а потом инфо
                infoWindow.removeAll()
                disabled = true
                post()
            }

            iconRefresh.onClick {
                post.value = ""
                infoWindow.apply {
                    removeAll()
                    add(infoText)
                }
                selectEnv.value = RC_ENV.first
                buttonSubmit.disabled = false
                checkBoxPhoneNumber.value = false
                listFields.forEach {
                    it.value = ""
                    it.validationStatus = null
                }
            }
        }
    }
}