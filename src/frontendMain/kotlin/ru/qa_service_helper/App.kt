package ru.qa_service_helper

import io.kvision.*
import io.kvision.html.div
import io.kvision.panel.root
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import ru.qa_service_helper.css.appStyle
import ru.qa_service_helper.css.containerFormsStyle
import ru.qa_service_helper.css.mainPageStyle
import ru.qa_service_helper.forms.*

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        root("qa_helper") {

            div {
                addCssStyle(appStyle)

                add(HeaderElement())

                div {
                    addCssStyle(mainPageStyle)
                    div {
                        addCssStyle(containerFormsStyle)
                        add(CreateNewUserPanel())
                        add(CreateNewAccessPanel())
                        add(SlavesPanel())
                        add(PreviousAccessPanel())
                        add(DeactivateAccessPanel())
                        add(CreateNewDocPackPanel())
                    }
                }
            }
            add(FooterElement())
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}


