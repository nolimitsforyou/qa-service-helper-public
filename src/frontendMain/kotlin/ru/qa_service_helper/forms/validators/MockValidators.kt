package ru.qa_service_helper.forms.validators

import io.kvision.form.ValidationStatus
import io.kvision.form.text.Text
import io.kvision.html.Button

object MockValidators {

    private const val FILL_THE_FIELD = "заполните поле"

    fun crmPreviousAccessValidator(
        fieldBitrix: Text,
        fieldPreviousAccess: Text,
        button: Button
    ) {
        CommonValidators.bitrixIdInputFormatter(fieldBitrix.value.toString())

        if (fieldPreviousAccess.value.isNullOrEmpty()) {
            fieldPreviousAccess.validatorError = FILL_THE_FIELD
        } else {
            fieldPreviousAccess.apply {
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        if (fieldBitrix.value.isNullOrEmpty()) {
            fieldBitrix.validatorError = FILL_THE_FIELD
        } else {
            fieldBitrix.apply {
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        button.disabled = !(
                !fieldPreviousAccess.value.isNullOrEmpty()
                        && !fieldBitrix.value.isNullOrEmpty()
                        && fieldPreviousAccess.validatorError == null
                        && fieldBitrix.validatorError == null
                )
    }
}