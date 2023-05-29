package ru.qa_service_helper.forms.validators

import io.kvision.form.ValidationStatus
import io.kvision.form.text.Text
import io.kvision.html.Button
import ru.qa_service_helper.forms.validators.CommonValidators.dateInputValidator

object AccessValidator {

    private const val FILL_THE_FIELD = "заполните поле"

    private fun accessAttrInputsValidator(field: Text, fieldObservable: Text) {
        //TODO доработать валидацию на формат UUID
        if (!field.value.isNullOrEmpty() && fieldObservable.value.isNullOrEmpty()) {
            fieldObservable.validatorError = FILL_THE_FIELD
        } else {
            fieldObservable.apply {
                validatorError = null
            }
        }
    }

    private fun simpleProductFormatter(product: String): String {
        return product.replace(PatternsValidations.PATTERN_EXCEPT_SIMPLE_PRODUCT, "")
    }

    private fun versionProductFormatter(version: String): String {
        return version.replace(PatternsValidations.PATTERN_EXCEPT_DIGITS, "")
    }

    fun createAccessFieldsValidator(
        fieldBitrix: Text,
        fieldProduct: Text,
        fieldVersion: Text,
        fieldDateStart: Text,
        fieldDateEnd: Text,
        fieldCommonId: Text,
        button: Button
    ) {

        CommonValidators.bitrixIdInputFormatter(fieldBitrix.value.toString())

        if (fieldProduct.value.isNullOrEmpty()) {
            fieldProduct.validatorError = FILL_THE_FIELD
        } else {
            fieldProduct.apply {
                value = simpleProductFormatter(value.toString())
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        if (fieldVersion.value.isNullOrEmpty()) {
            fieldVersion.validatorError = FILL_THE_FIELD
        } else {
            fieldVersion.apply {
                value = versionProductFormatter(value.toString())
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        dateInputValidator(fieldDateStart)

        dateInputValidator(fieldDateEnd)

        accessAttrInputsValidator(
            field = fieldCommonId, fieldObservable = fieldBitrix
        )

        button.disabled = !(
                !fieldProduct.value.isNullOrEmpty()
                        && !fieldVersion.value.isNullOrEmpty()
                        && fieldDateStart.validatorError == null
                        && fieldDateEnd.validatorError == null
                        && fieldBitrix.validatorError == null
                )
    }

    fun addSlaveToAccessValidator(
        fieldAccess: Text,
        fieldBitrix: Text,
        buttonAdd: Button,
        buttonDelete: Button,
    ) {
        CommonValidators.bitrixIdInputFormatter(fieldBitrix.value.toString())

        if (fieldAccess.value.isNullOrEmpty()) {
            fieldAccess.validatorError = FILL_THE_FIELD
        } else {
            fieldAccess.apply {
                value = simpleProductFormatter(value.toString())
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
        buttonAdd.disabled = !(
                !fieldAccess.value.isNullOrEmpty()
                        && !fieldBitrix.value.isNullOrEmpty()
                        && fieldAccess.validatorError == null
                        && fieldBitrix.validatorError == null
                )
        buttonDelete.disabled = !(
                !fieldAccess.value.isNullOrEmpty()
                        && !fieldBitrix.value.isNullOrEmpty()
                        && fieldAccess.validatorError == null
                        && fieldBitrix.validatorError == null
                )
    }

    fun deactivateAccessValidator(
        fieldCommonId: Text,
        button: Button
    ) {
        if (fieldCommonId.value.isNullOrEmpty()) {
            fieldCommonId.validatorError = FILL_THE_FIELD
        } else {
            fieldCommonId.apply {
                validatorError = null
                validationStatus = ValidationStatus.VALID
            }
        }
        button.disabled = !(
                !fieldCommonId.value.isNullOrEmpty()
                        && fieldCommonId.validatorError == null
                )
    }
}