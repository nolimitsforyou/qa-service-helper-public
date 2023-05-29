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
import ru.qa_service_helper.Model.qaHelperService
import ru.qa_service_helper.css.*
import ru.qa_service_helper.forms.validators.AccessValidator.addSlaveToAccessValidator

class SlavesPanel : SimplePanel() {

    private lateinit var accessId: Text
    private lateinit var bitrixId: Text
    private lateinit var selectEnv: SimpleSelect
    private lateinit var infoText: Span
    private lateinit var buttonSubmit: Button
    private lateinit var buttonDelete: Button
    private lateinit var iconRefresh: Image
    private val listFields by lazy { listOf(accessId, bitrixId) }

    init {

        addCssStyle(formCreateStyle)

        div {
            addCssStyle(blockPanelHeaderStyle)
            div {
                addCssStyle(headerCreateFormStyle)
                span {
                    addCssStyle(titleTextStyle)
                    content = "Доступ. Добавление/удаление слейва"
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
                    //блок эмейла
                    addCssStyle(inputBlockStyle)

                    label(content = "AccessId*") {
                        addCssStyle(inputLabelStyle)
                    }
                    accessId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
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
                    //блок селектов с кнопкой
                    addCssStyle(buttonsGroupStyle)
                    selectEnv = simpleSelect {
                        addCssStyle(selectEnvStyle)
                        value = RC_ENV.first
                        options = listOf(RC_ENV, DEV_ENV)
                    }

                    buttonSubmit = button("Add") {
                        disabled = true
                    }
                    buttonDelete = button("Delete") {
                        addCssStyle(buttonDangerStyle)
                        disabled = true
                    }
                }
            }

            val infoWindow = div {
                //блок информ-окна
                addCssStyle(informWindowStyle)
                infoText = span {

                    addCssStyle(infoTextStyle)

                    content = TEXT_INFO_SLAVES_ADD
                }
            }

            fun postAdd() {
                AppScope.launch {
                    try {
                        qaHelperService.addSlaveToAccess(
                            accessId = accessId.value!!,
                            slaveBitrix = bitrixId.value!!,
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
                        buttonDelete.disabled = false
                    } catch (e: ServiceException) {
                        infoWindow.add(
                            span {
                                addCssStyle(infoTextStyle)
                                p { content = e.message }
                            }
                        )
                        buttonSubmit.disabled = false
                        buttonDelete.disabled = false
                    }
                }
            }

            fun postDelete() {
                AppScope.launch {
                    try {
                        qaHelperService.removeSlaveFromAccess(
                            accessId = accessId.value!!,
                            slaveBitrix = bitrixId.value!!,
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
                        buttonDelete.disabled = false
                    } catch (e: ServiceException) {
                        infoWindow.add(
                            span {
                                addCssStyle(infoTextStyle)
                                p { content = e.message }
                            }
                        )
                        buttonSubmit.disabled = false
                        buttonDelete.disabled = false
                    }
                }
            }

            listFields.forEach {
                it.onEvent {
                    keyup = {
                        addSlaveToAccessValidator(
                            fieldAccess = accessId,
                            fieldBitrix = bitrixId,
                            buttonAdd = buttonSubmit,
                            buttonDelete = buttonDelete
                        )
                    }
                    keydown = {
                        addSlaveToAccessValidator(
                            fieldAccess = accessId,
                            fieldBitrix = bitrixId,
                            buttonAdd = buttonSubmit,
                            buttonDelete = buttonDelete
                        )
                    }
                }
            }

            buttonSubmit.onClick {
                //TODO отображать спиннер а потом инфо
                infoWindow.removeAll()
                buttonDelete.disabled = true
                disabled = true
                postAdd()
            }

            buttonDelete.onClick {
                //TODO отображать спиннер а потом инфо
                infoWindow.removeAll()
                buttonSubmit.disabled = true
                disabled = true
                postDelete()
            }

            iconRefresh.onClick {
                infoWindow.apply {
                    removeAll()
                    add(infoText)
                }
                selectEnv.value = RC_ENV.first
                buttonSubmit.disabled = true
                buttonDelete.disabled = true
                listFields.forEach {
                    it.value = ""
                    it.validationStatus = null
                }
            }
        }
    }
}