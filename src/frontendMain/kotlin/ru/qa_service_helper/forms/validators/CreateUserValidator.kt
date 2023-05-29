package ru.qa_service_helper.forms.validators

import io.kvision.form.ValidationStatus
import io.kvision.form.text.Text
import io.kvision.html.Button
import ru.qa_service_helper.forms.validators.CommonValidators.emailInputValidator
import ru.qa_service_helper.forms.validators.PatternsValidations.PATTERN_EXCEPT_PHONE_NUMBER
import ru.qa_service_helper.forms.validators.PatternsValidations.PATTERN_PHONE_NUMBER_11

object CreateUserValidator {

    fun userFieldsValidator(
        fieldEmail: Text,
        fieldPhone: Text,
        button: Button
    ) {

        emailInputValidator(fieldEmail = fieldEmail, isRequired = false)

        if (fieldPhone.value.isNullOrEmpty()) {
            fieldPhone.validatorError = null
        }
        fieldPhone.value?.let {

            fieldPhone.value = it.replace(PatternsValidations.PATTERN_EXCEPT_DIGITS, "")

            if (it.matches(PATTERN_EXCEPT_PHONE_NUMBER)) {
                fieldPhone.validatorError = "номер должен начинаться с 7"
            } else if (!it.matches(PATTERN_PHONE_NUMBER_11)) {
                fieldPhone.validatorError = "номер должен быть 11 цифр"
            } else {
                fieldPhone.apply {
                    validatorError = null
                    validationStatus = ValidationStatus.VALID
                }
            }
        }

        button.disabled = !(
                fieldEmail.validatorError == null && fieldPhone.validatorError == null
                )
    }
}