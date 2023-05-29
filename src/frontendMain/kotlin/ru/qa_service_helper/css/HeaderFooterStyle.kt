package ru.qa_service_helper.css

import io.kvision.core.*

val headerStyle = Style(selector = ".qa_header") {
    background = Background(
        color = Color.rgb(71, 77, 94)
    )
    height = CssSize(60, UNIT.px)
}

val logoStyle = Style(selector = ".qa_header_logo") {
    color = Color.rgb(255, 255, 255)
    fontFamily = "Monomaniac One"
    fontStyle = FontStyle.NORMAL
    fontWeight = FontWeight.NORMAL
    fontSize = CssSize(24, UNIT.px)
    lineHeight = CssSize(35, UNIT.px)
    position = Position.ABSOLUTE
    width = CssSize(190, UNIT.px)
    height = CssSize(25, UNIT.px)
    left = CssSize(35, UNIT.px)
    top = CssSize(12, UNIT.px)
}

val footerStyle = Style(selector = ".qa_footer") {
    background = Background(
        color = Color.rgb(255, 255, 255)
    )
    height = CssSize(60, UNIT.px)
}