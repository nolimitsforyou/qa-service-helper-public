package ru.qa_service_helper.forms.validators

import io.kvision.form.ValidationStatus
import io.kvision.form.text.Text
import ru.qa_service_helper.forms.validators.PatternsValidations.PATTERN_MATCH_DATE

object CommonValidators {

    fun emailInputIsCorrect(email: String): Boolean {
        return email.matches(PatternsValidations.PATTERN_MATCH_EMAIL)
    }

    fun ukdInputIsCorrect(ukd: String): Boolean {
        return ukd.matches(PatternsValidations.PATTERN_MATCH_UKD)
    }

    fun bitrixIdInputFormatter(bitrixId: String): String {
        return bitrixId.replace(PatternsValidations.PATTERN_EXCEPT_DIGITS, "")
    }

    fun bitrixIdInputValidator(fieldBitrix: Text) {
        if (fieldBitrix.value.isNullOrEmpty()) {
            fieldBitrix.validatorError = "заполните поле"
        } else {
            fieldBitrix.apply {
                value = bitrixIdInputFormatter(value.toString())
                if (value.isNullOrEmpty()) {
                    fieldBitrix.validatorError = "заполните поле"
                } else {
                    validatorError = null
                    validationStatus = ValidationStatus.VALID
                }
            }
        }
    }

    fun emailInputValidator(fieldEmail: Text, isRequired: Boolean) {
        if (isRequired) {
            if (fieldEmail.value.isNullOrEmpty()) {
                fieldEmail.validatorError = "заполните поле"
            } else if (!emailInputIsCorrect(fieldEmail.value.toString())) {
                fieldEmail.validatorError = "некорректный email"
            } else {
                fieldEmail.validatorError = null
                fieldEmail.validationStatus = ValidationStatus.VALID
            }
        } else if (fieldEmail.value.isNullOrEmpty()) {
            fieldEmail.validatorError = null
        } else if (!emailInputIsCorrect(fieldEmail.value.toString())) {
            fieldEmail.validatorError = "некорректный email"
        } else {
            fieldEmail.validatorError = null
            fieldEmail.validationStatus = ValidationStatus.VALID
        }
    }

    fun dateInputValidator(field: Text) {

        if (field.value.isNullOrEmpty()) {
            field.validatorError = null
            field.disabled = false
        }

        field.value?.let {
            field.value = it.replace(Regex("""[^0-9.]"""), "")

            if (it.matches(PatternsValidations.PATTERN_ZERO_DAY) || it.matches(PatternsValidations.PATTERN_ZERO_DAY_PLUS)) {
                field.validatorError = "дата не может быть 00"
            } else if (it.matches(PatternsValidations.PATTERN_DAY_BIGGER_40) || it.matches(PatternsValidations.PATTERN_DAY_BIGGER_40_PLUS)
                || it.matches(PatternsValidations.PATTERN_DAY_BIGGER_32) || it.matches(PatternsValidations.PATTERN_DAY_BIGGER_32_PLUS)
            ) {
                field.validatorError = "дата не может быть больше 31"
            } else {
                field.validatorError = null
            }
            if (it.matches(Regex("""^\d{3}"""))) {
                field.value = "${it.substring(0..1)}.${it.substring(2)}"
            }
            if (it.matches(Regex("""^\d{2}\.\d{3}"""))) {
                field.value = "${it.substring(0..4)}.${it.substring(5)}"
            }
            if (it.matches(PatternsValidations.PATTERN_ZERO_MONTH) || it.matches(PatternsValidations.PATTERN_ZERO_MONTH_PLUS)) {
                field.validatorError = "месяц не может быть 00"
            } else if (it.matches(PatternsValidations.PATTERN_MONTH_BIGGER_12) || it.matches(PatternsValidations.PATTERN_MONTH_BIGGER_12_PLUS)
                || it.matches(PatternsValidations.PATTERN_MONTH_BIGGER_20) || it.matches(PatternsValidations.PATTERN_MONTH_BIGGER_20_PLUS)
            ) {
                field.validatorError = "месяц не может быть больше 12"
            }
            if (!it.matches(PATTERN_MATCH_DATE)) {
                field.validatorError = "дата не соотв. формату дд.мм.гггг"
            }
            //TODO валидация на вставку "31122020"
        }
    }
}