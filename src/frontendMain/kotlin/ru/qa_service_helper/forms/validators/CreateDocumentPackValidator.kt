package ru.qa_service_helper.forms.validators

import io.kvision.form.ValidationStatus
import io.kvision.form.text.Text
import io.kvision.html.Button
import ru.qa_service_helper.forms.validators.CommonValidators.bitrixIdInputValidator
import ru.qa_service_helper.forms.validators.CommonValidators.emailInputValidator
import ru.qa_service_helper.forms.validators.CommonValidators.ukdInputIsCorrect

object CreateDocumentPackValidator {

    fun documentPackFieldsValidator(
        fieldBitrix: Text,
        fieldEmail: Text,
        fieldUkd: Text,
        button: Button
    ) {

        bitrixIdInputValidator(fieldBitrix)
        emailInputValidator(fieldEmail = fieldEmail, isRequired = false)

        if (fieldUkd.value.isNullOrEmpty()) {
            fieldUkd.validatorError = null
        } else if (!ukdInputIsCorrect(fieldUkd.value.toString())) {
            fieldUkd.apply {
                validatorError = "некорректный УКД"
                value = ukdFormatter(fieldUkd.value.toString())
            }
        } else {
            fieldUkd.apply {
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        button.disabled = !(!fieldBitrix.value.isNullOrEmpty()
                && !fieldEmail.value.isNullOrEmpty()
                && fieldEmail.validationStatus == ValidationStatus.VALID
                && fieldUkd.validatorError == null)
    }

    private fun ukdFormatter(ukd: String): String {

        lateinit var result: String

        if (ukd.matches(Regex("""^\d{4}"""))) {
            result = "$ukd-"
        }
        if (ukd.matches(Regex("""^\d{4}-\d{4}"""))) {
            result = "$ukd-"
        }
        if (ukd.matches(Regex("""^\d{4}-\d{4}-\d{4}"""))) {
            result = "$ukd-"
        }
        if (ukd.matches(Regex("""^\d{4}-\d{4}-\d{4}-\d{4}"""))) {
            result = "$ukd-"
        }
        return result
    }
}