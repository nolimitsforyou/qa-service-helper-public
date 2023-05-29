package ru.qa_service_helper.css

import io.kvision.core.*
import io.kvision.form.text.Text

val titleTextStyle = Style(selector = ".qa_title_panel") {
    color = Color.rgb(0, 0, 0)

    position = Position.RELATIVE

    fontFamily = "Helvetica"
    fontStyle = FontStyle.NORMAL
    fontWeight = FontWeight.NORMAL
    fontSize = CssSize(20, UNIT.px)
    lineHeight = CssSize(23, UNIT.px)

    width = CssSize(420, UNIT.px)
    height = CssSize(22, UNIT.px)
}

val formCreateStyle = Style(selector = ".qa_create_panel") {
    position = Position.RELATIVE
    width = CssSize(800, UNIT.px)
    background = Background(
        Color.rgb(255, 255, 255)
    )
    border = Border(width = CssSize(1, UNIT.px), style = BorderStyle.SOLID, Color.rgb(232, 235, 244))
    boxShadow = BoxShadow(
        hOffset = CssSize(5, UNIT.px),
        vOffset = CssSize(5, UNIT.px),
        blurRadius = CssSize(10, UNIT.px),
        color = Color.rgb(215, 214, 214)
    )
    borderRadius = CssSize(10, UNIT.px)
    marginLeft = CssSize(10, UNIT.px)
    marginTop = CssSize(10, UNIT.px)
}

val blockInFormCreateStyle = Style(selector = ".qa_create_block") {
    position = Position.RELATIVE
    display = Display.FLEX
    justifyContent = JustifyContent.SPACEBETWEEN
    marginTop = CssSize(10, UNIT.px)
    marginBottom = CssSize(10, UNIT.px)
    marginRight = CssSize(10, UNIT.px)
    marginLeft = CssSize(9, UNIT.px)
}

val blockPanelHeaderStyle = Style(selector = ".qa_panel_header_style") {
    position = Position.RELATIVE
    justifyContent = JustifyContent.SPACEBETWEEN
    display = Display.FLEX
}

val blockIconRefreshStyle = Style(selector = ".qa_icon_refresh_style") {
    position = Position.RELATIVE
    marginRight =  CssSize(20, UNIT.px)
    marginLeft =  CssSize(20, UNIT.px)
    marginTop = CssSize(15, UNIT.px)
    cursor = Cursor.POINTER
}

val headerCreateFormStyle = Style(selector = ".qa_create_header") {
    position = Position.RELATIVE
    marginTop = CssSize(15, UNIT.px)
    marginBottom = CssSize(5, UNIT.px)
    marginLeft = CssSize(20, UNIT.px)
    marginRight = CssSize(20, UNIT.px)
}

val blockFieldsStyle = Style(selector = ".qa_fields_block") {
    position = Position.RELATIVE
    marginTop = CssSize(10, UNIT.px)
    marginBottom = CssSize(10, UNIT.px)
    marginRight = CssSize(10, UNIT.px)
    marginLeft = CssSize(9, UNIT.px)
    width = CssSize(320, UNIT.px)
}

val inputBlockStyle = Style(selector = ".qa_field_item") {
    position = Position.RELATIVE
    marginTop = CssSize(10, UNIT.px)
    width = CssSize(300, UNIT.px)
    height = CssSize(65, UNIT.px)
}

val buttonsGroupStyle = Style(selector = ".qa_buttons_group") {
    position = Position.RELATIVE
    marginTop = CssSize(10, UNIT.px)
    width = CssSize(300, UNIT.px)
    height = CssSize(42, UNIT.px)
    display = Display.INLINE
}

val inputLabelStyle = Style(selector = ".qa_input_label") {
    color = Color.rgb(0, 0, 0)
    position = Position.ABSOLUTE
    width = CssSize(150, UNIT.px)

    left = CssSize(0, UNIT.perc)
    right = CssSize(50, UNIT.perc)
    top = CssSize(0, UNIT.perc)
    bottom = CssSize(75.38, UNIT.perc)

    fontFamily = "Helvetica"
    fontStyle = FontStyle.ITALIC
    fontWeight = FontWeight.LIGHTER
    fontSize = CssSize(16, UNIT.px)
    lineHeight = CssSize(20, UNIT.px)
}

val inputTextStyle = Style(selector = ".qa_input_text") {
    position = Position.ABSOLUTE

    width = CssSize(300, UNIT.px)
    height = CssSize(30, UNIT.px)

    left = CssSize(0, UNIT.perc)
    right = CssSize(0, UNIT.perc)
    top = CssSize(32.31, UNIT.perc)
    bottom = CssSize(21.54, UNIT.perc)

    Text().invalidFeedback.apply {
        marginTop = CssSize(-1, UNIT.px)
        fontSize = CssSize(13, UNIT.px)
    }
}

val informWindowStyle = Style(selector = ".qa_inform_block") {
    position = Position.RELATIVE
    width = CssSize(400, UNIT.px)
    padding = CssSize(10, UNIT.px)
    marginBottom = CssSize(10, UNIT.px)

    background = Background(
        Color.rgb(255, 255, 255)
    )
    border = Border(width = CssSize(1, UNIT.px), style = BorderStyle.SOLID, Color.rgb(208, 211, 219))
    borderRadius = CssSize(10, UNIT.px)
}

val infoTextStyle = Style(selector = ".qa_inform_text") {
    fontFamily = "Helvetica"
    fontStyle = FontStyle.NORMAL
    fontWeight = FontWeight.LIGHTER
    fontSize = CssSize(14, UNIT.px)
}