package ru.qa_service_helper.css

import io.kvision.core.*
import io.kvision.utils.auto

val appStyle = Style(selector = ".qa_app") {
    background = Background(
        Color.rgb(255, 255, 255)
    )
    flexDirection = FlexDirection.COLUMN
}

val mainPageStyle = Style(selector = ".qa_main_page") {
    position = Position.RELATIVE
    marginLeft = auto
    marginRight = auto
    paddingTop = CssSize(24, UNIT.px)
    paddingRight = CssSize(16, UNIT.px)
    paddingLeft = CssSize(16, UNIT.px)
    width = CssSize(1800, UNIT.px)
    maxWidth = CssSize(100, UNIT.perc)
    display = Display.BLOCK
}

val containerFormsStyle = Style(selector = ".qa_container_forms") {
    display = Display.GRID
    gridTemplateColumns = "repeat(2, 1fr)"
    gridRowGap = 50
    alignItems = AlignItems.START
    paddingTop = CssSize(24, UNIT.px)
}