package ru.qa_service_helper.forms

import io.kvision.html.*
import ru.qa_service_helper.css.footerStyle
import ru.qa_service_helper.css.headerStyle
import ru.qa_service_helper.css.logoStyle

class HeaderElement : Header() {
    init {
        addCssStyle(headerStyle)
        align = Align.CENTER
        span {
            addCssStyle(logoStyle)
            content = "QA Service Helper"
        }
    }
}

class FooterElement : Footer() {
    init {
        addCssStyle(footerStyle)
    }
}