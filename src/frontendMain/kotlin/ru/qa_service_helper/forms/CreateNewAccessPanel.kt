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
import ru.qa_service_helper.*
import ru.qa_service_helper.Environment.DEV_ENV
import ru.qa_service_helper.Environment.RC_ENV
import ru.qa_service_helper.Model.qaHelperService
import ru.qa_service_helper.css.*
import ru.qa_service_helper.forms.validators.AccessValidator.createAccessFieldsValidator

class CreateNewAccessPanel : SimplePanel() {

    private lateinit var bitrixId: Text
    private lateinit var simpleProduct: Text
    private lateinit var version: Text
    private lateinit var dateStart: Text
    private lateinit var dateEnd: Text
    private lateinit var structureType: SimpleSelect
    private lateinit var parentId: Text
    private lateinit var commonId: Text
    private lateinit var infoText: Span
    private lateinit var selectAccessType: SimpleSelect
    private lateinit var selectEnv: SimpleSelect
    private lateinit var buttonSubmit: Button
    private lateinit var iconRefresh: Image
    private val listFields by lazy {
        listOf(bitrixId, simpleProduct, version, dateStart, dateEnd, parentId, commonId)
    }

    init {
        addCssStyle(formCreateStyle)

        div {
            addCssStyle(blockPanelHeaderStyle)
            div {
                addCssStyle(headerCreateFormStyle)
                span {
                    addCssStyle(titleTextStyle)
                    content = "Генерация/Обновление доступа"
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
                    //блок Битрикса
                    addCssStyle(inputBlockStyle)

                    label(content = "BitrixId") {
                        addCssStyle(inputLabelStyle)
                    }
                    bitrixId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }

                div {
                    //блок Simple Product
                    addCssStyle(inputBlockStyle)

                    label(content = "Simple Product *") {
                        addCssStyle(inputLabelStyle)
                    }
                    simpleProduct = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }

                div {
                    //блок version
                    addCssStyle(inputBlockStyle)

                    label(content = "Version *") {
                        addCssStyle(inputLabelStyle)
                    }
                    version = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }

                div {
                    //блок Date Start
                    addCssStyle(inputBlockStyle)

                    label(content = "Date Start") {
                        addCssStyle(inputLabelStyle)
                    }
                    dateStart = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                        placeholder = "01.12.2022"
                        maxlength = 10
                    }
                }

                div {
                    //блок Date End
                    addCssStyle(inputBlockStyle)

                    label(content = "Date End") {
                        addCssStyle(inputLabelStyle)
                    }
                    dateEnd = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                        placeholder = "31.12.2024"
                        maxlength = 10
                    }
                }

                div {
                    //блок Structure Type
                    addCssStyle(inputBlockStyle)

                    structureType = simpleSelect(label = "StructureType", emptyOption = true) {
                        addCssStyle(selectLargeStyle)
                        addCssStyle(inputLabelStyle)
                        value = null
                        options = listOf(
                            Pair(StructureType.PODPISKA.type, StructureType.PODPISKA.title),
                            Pair(StructureType.BLOCK.type, StructureType.BLOCK.title)
                        )
                    }
                }

                div {
                    //блок Parent Id
                    addCssStyle(inputBlockStyle)

                    label(content = "ParentId") {
                        addCssStyle(inputLabelStyle)
                    }
                    parentId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }

                div {
                    //блок CommonId
                    addCssStyle(inputBlockStyle)

                    label(content = "CommonId") {
                        addCssStyle(inputLabelStyle)
                    }
                    commonId = text(type = TextInputType.TEXT) {
                        addCssStyle(inputTextStyle)
                    }
                }
                div {
                    //блок селектов с кнопкой
                    addCssStyle(buttonsGroupStyle)
                    selectAccessType = simpleSelect {
                        addCssStyle(selectAccessStyle)
                        value = "PaidAccess"
                        options = listOf(Pair("PaidAccess", "Paid"), Pair("DemoAccess", "Demo"))
                    }

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
                    content = TEXT_INFO_CREATE_ACCESS
                }
            }

            listFields.forEach {
                it.onEvent {
                    keyup = {
                        createAccessFieldsValidator(
                            fieldBitrix = bitrixId,
                            fieldProduct = simpleProduct,
                            fieldVersion = version,
                            fieldDateStart = dateStart,
                            fieldDateEnd = dateEnd,
                            fieldCommonId = commonId,
                            button = buttonSubmit
                        )
                    }
                    keydown = {
                        createAccessFieldsValidator(
                            fieldBitrix = bitrixId,
                            fieldProduct = simpleProduct,
                            fieldVersion = version,
                            fieldDateStart = dateStart,
                            fieldDateEnd = dateEnd,
                            fieldCommonId = commonId,
                            button = buttonSubmit
                        )
                    }
                }
            }

            fun post() {
                AppScope.launch {
                    try {
                        qaHelperService.createNewAccess(
                            CreateUpdateAccessModel(
                                bitrixId = bitrixId.value,
                                simpleProduct = simpleProduct.value!!,
                                version = version.value!!,
                                dateStart = dateStart.value,
                                dateEnd = dateEnd.value,
                                parentId = parentId.value,
                                structureType = structureType.value,
                                commonId = commonId.value,
                                accessType = selectAccessType.value!!,
                                selectEnvironment = selectEnv.value!!
                            )
                        ).apply {
                            infoWindow.add(
                                span {
                                    addCssStyle(infoTextStyle)
                                    p { content = infoText }
                                    p { content = "UserId: $userId" }
                                    p { content = "SimpleProduct: $simpleProduct" }
                                    p { content = "Version: $version" }
                                    p { content = "UKD: $ukd" }
                                    p { content = "CommonId: $commonId" }
                                    p { content = "DateStart: $dateStart" }
                                    p { content = "DateEnd: $dateEnd" }
                                    p { content = "AccessType: $accessType" }
                                    p { content = "StructureType: $structureType" }
                                    p { content = "ParentId: $parentId" }
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
                structureType.value = ""
                selectAccessType.value = "PaidAccess"
                buttonSubmit.disabled = true
                listFields.forEach {
                    it.value = ""
                    it.validationStatus = null
                }
            }
        }
    }
}