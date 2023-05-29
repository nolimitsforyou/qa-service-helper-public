package ru.qa_service_helper.forms

import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.core.onClick
import io.kvision.core.onEvent
import io.kvision.form.select.SimpleSelect
import io.kvision.form.select.simpleSelect
import io.kvision.form.text.Text
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.remote.ServiceException
import kotlinx.coroutines.launch
import ru.qa_service_helper.AppScope
import ru.qa_service_helper.Environment.DEV_ENV
import ru.qa_service_helper.Environment.RC_ENV
import ru.qa_service_helper.Model
import ru.qa_service_helper.css.*
import ru.qa_service_helper.forms.validators.MockValidators.crmPreviousAccessValidator

class PreviousAccessPanel : SimplePanel() {

    private lateinit var bitrixId: Text
    private lateinit var previousAccessId: Text
    private lateinit var selectEnv: SimpleSelect
    private lateinit var infoText: Span
    private lateinit var buttonSubmit: Button
    private lateinit var iconRefresh: Image
    private val listFields by lazy { listOf(bitrixId, previousAccessId) }

    init {
        addCssStyle(formCreateStyle)

        div {
            addCssStyle(blockPanelHeaderStyle)
            div {
                addCssStyle(headerCreateFormStyle)
                span {
                    addCssStyle(titleTextStyle)
                    content = "Добавление признака PreviousAccessId в мок-сервис"
                }
            }

            div {
                addCssStyle(blockIconRefreshStyle)
                iconRefresh = image(
                    io.kvision.require("img/refresh-icon.svg") as? String,
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
                    //блок пароля
                    addCssStyle(inputBlockStyle)

                    label(content = "BitrixId*") {
                        addCssStyle(inputLabelStyle)
                    }
                    bitrixId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }

                div {
                    //блок эмейла
                    addCssStyle(inputBlockStyle)

                    label(content = "PreviousAccessId*") {
                        addCssStyle(inputLabelStyle)
                    }
                    previousAccessId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
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

                    buttonSubmit = button("Submit") {
                        disabled = true
                    }
                }
            }

            val infoWindow = div {
                //блок информ-окна
                addCssStyle(informWindowStyle)
                infoText = span {

                    addCssStyle(infoTextStyle)

                    content = TEXT_INFO_MOCK_ACCESS
                }
            }

            fun post() {
                AppScope.launch {
                    try {
                        Model.qaHelperService.createPreviousAccessCrm(
                            bitrixId = bitrixId.value!!,
                            previousAccessId = previousAccessId.value!!,
                            accessId = null,
                            selectEnvironment = selectEnv.value!!
                        ).apply {
                            infoWindow.add(
                                span {
                                    addCssStyle(infoTextStyle)
                                    p { content = this@apply }
                                }
                            )
                        }
                        buttonSubmit.disabled = false
                    } catch (e: ServiceException) {
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
                        crmPreviousAccessValidator(
                            fieldBitrix = bitrixId,
                            fieldPreviousAccess = previousAccessId,
                            button = buttonSubmit
                        )
                    }
                    keydown = {
                        crmPreviousAccessValidator(
                            fieldBitrix = bitrixId,
                            fieldPreviousAccess = previousAccessId,
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
                infoWindow.apply {
                    removeAll()
                    add(infoText)
                }
                selectEnv.value = RC_ENV.first
                buttonSubmit.disabled = true
                listFields.forEach {
                    it.value = ""
                    it.validationStatus = null
                }
            }
        }
    }
}