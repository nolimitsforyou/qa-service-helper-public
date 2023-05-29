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
import ru.qa_service_helper.CreateDocPackModel
import ru.qa_service_helper.Environment.DEV_ENV
import ru.qa_service_helper.Environment.RC_ENV
import ru.qa_service_helper.Model.qaHelperService
import ru.qa_service_helper.css.*
import ru.qa_service_helper.forms.validators.CreateDocumentPackValidator.documentPackFieldsValidator

class CreateNewDocPackPanel : SimplePanel() {

    private lateinit var bitrixId: Text
    private lateinit var emailAddress: Text
    private lateinit var ukd: Text
    private lateinit var selectDocumentSet: SimpleSelect
    private lateinit var selectEnv: SimpleSelect
    private lateinit var infoText: Span
    private lateinit var buttonSubmit: Button
    private lateinit var iconRefresh: Image
    private val listFields by lazy { listOf(bitrixId, emailAddress, ukd) }

    init {
        addCssStyle(formCreateStyle)

        div {
            addCssStyle(blockPanelHeaderStyle)
            div {
                addCssStyle(headerCreateFormStyle)
                span {
                    addCssStyle(titleTextStyle)
                    content = "Генерация пакета документов"
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
                    //блок битрикса
                    addCssStyle(inputBlockStyle)

                    label(content = "BitrixId *") {
                        addCssStyle(inputLabelStyle)
                    }
                    bitrixId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок эмейла
                    addCssStyle(inputBlockStyle)

                    label(content = "Email *") {
                        addCssStyle(inputLabelStyle)
                    }
                    emailAddress = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок УКД
                    addCssStyle(inputBlockStyle)

                    label(content = "UKD") {
                        addCssStyle(inputLabelStyle)
                    }
                    ukd = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                        maxlength = 24
                        placeholder = "1111-2222-3333-4444-5555"
                    }
                }
                div {
                    addCssStyle(inputBlockStyle)

                    selectDocumentSet = simpleSelect(label = "Document pack set") {
                        addCssStyle(selectLargeStyle)
                        addCssStyle(inputLabelStyle)
                        value = DocumentPack.HIGH_SCHOOL_DEFAULT.type
                        options = listOf(
                            Pair(DocumentPack.HIGH_SCHOOL_DEFAULT.type, DocumentPack.HIGH_SCHOOL_DEFAULT.title),
                            Pair(DocumentPack.HIGH_SCHOOL_VIP.type, DocumentPack.HIGH_SCHOOL_VIP.title),
                            Pair(DocumentPack.DIRECTOR_ACADEMY.type, DocumentPack.DIRECTOR_ACADEMY.title),
                            Pair(DocumentPack.PUPIL_ACADEMY.type, DocumentPack.PUPIL_ACADEMY.title),
                            Pair(DocumentPack.DIRECTOR_ACADEMY_360.type, DocumentPack.DIRECTOR_ACADEMY_360.title),
                            Pair(DocumentPack.PUPIL_ACADEMY_360.type, DocumentPack.PUPIL_ACADEMY_360.title)
                        )
                    }
                }
                div {
                    addCssStyle(buttonsGroupStyle)

                    selectEnv = simpleSelect {
                        value = RC_ENV.first
                        options = listOf(RC_ENV, DEV_ENV)
                        addCssStyle(selectEnvStyle)
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

                    content = TEXT_INFO_CREATE_DOC_PACK
                }
            }

            fun post() {
                AppScope.launch {
                    try {
                        qaHelperService.createNewDocPack(
                            CreateDocPackModel(
                                bitrixId = bitrixId.value!!,
                                email = emailAddress.value!!,
                                ukd = ukd.value,
                                documentSet = selectDocumentSet.value!!,
                                selectEnvironment = selectEnv.value!!
                            )
                        ).apply {
                            infoWindow.add(
                                span {
                                    addCssStyle(infoTextStyle)
                                    p { content = "Сгенерирован новый пакет документов:" }
                                    p { content = "PackId: $packId" }
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
                        documentPackFieldsValidator(
                            fieldBitrix = bitrixId,
                            fieldEmail = emailAddress,
                            fieldUkd = ukd,
                            button = buttonSubmit
                        )
                    }
                    keydown = {
                        documentPackFieldsValidator(
                            fieldBitrix = bitrixId,
                            fieldEmail = emailAddress,
                            fieldUkd = ukd,
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
                selectDocumentSet.value = DocumentPack.HIGH_SCHOOL_DEFAULT.type
                buttonSubmit.disabled = true
                listFields.forEach {
                    it.value = ""
                    it.validationStatus = null
                }
            }
        }
    }
}