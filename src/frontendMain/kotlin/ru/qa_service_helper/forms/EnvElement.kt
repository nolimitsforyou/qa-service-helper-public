package ru.qa_service_helper.forms

import io.kvision.form.select.SimpleSelect
import ru.qa_service_helper.Environment.DEV_ENV
import ru.qa_service_helper.Environment.RC_ENV

class SelectEnv : SimpleSelect() {
    init {
        label = "ENV"
        value = "RC"
        options = listOf(RC_ENV, DEV_ENV)
    }
}