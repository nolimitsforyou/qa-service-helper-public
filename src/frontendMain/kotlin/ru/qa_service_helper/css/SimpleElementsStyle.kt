package ru.qa_service_helper.css

import io.kvision.core.*

val buttonDefaultStyle = Style(selector = ".btn-primary") {

    position = Position.RELATIVE
    width = CssSize(80, UNIT.px)
    height = CssSize(38, UNIT.px)
    marginBottom = CssSize(3, UNIT.px)
    marginRight = CssSize(10, UNIT.px)
    borderRadius = CssSize(5, UNIT.px)
    background = Background(
        Color.rgb(71, 77, 94)
    )
    border = Border(
        width = CssSize(1, UNIT.px),
        style = BorderStyle.SOLID, Color.rgb(71, 77, 94)
    )
    fontFamily = "Helvetica"
    fontStyle = FontStyle.NORMAL
    fontWeight = FontWeight.NORMAL
    fontSize = CssSize(16, UNIT.px)
    lineHeight = CssSize(18, UNIT.px)
}

val buttonDangerStyle = Style(selector = ".qa_button_danger") {
    background = Background(
        Color.rgb(185, 17, 63)
    )
}

val buttonDisabled = Style(selector = ".btn-primary:disabled") {
    color = Color.rgb(255, 249, 249)
    background = Background(
        Color.rgb(135, 138, 142)
    )
    border = Border(
        width = CssSize(1, UNIT.px),
        style = BorderStyle.SOLID, Color.rgb(86, 90, 90)
    )
}

val selectEnvStyle = Style(selector = ".qa_select_env") {
    position = Position.RELATIVE
    height = CssSize(30, UNIT.px)
    marginRight = CssSize(10, UNIT.px)
    display = Display.INLINEBLOCK
}

val selectAccessStyle = Style(selector = ".qa_select_access") {
    position = Position.RELATIVE
    height = CssSize(30, UNIT.px)
    marginRight = CssSize(10, UNIT.px)
    display = Display.INLINEBLOCK
}

val selectLargeStyle = Style(selector = ".qa_select_large") {
    width = CssSize(300, UNIT.px)
    height = CssSize(30, UNIT.px)
    display = Display.INLINEBLOCK
}

val checkBoxPhoneStyle = Style(selector = ".qa_checkboxPhone") {
    width = CssSize(300, UNIT.px)
    height = CssSize(30, UNIT.px)
    paddingLeft = CssSize(4, UNIT.px)
}