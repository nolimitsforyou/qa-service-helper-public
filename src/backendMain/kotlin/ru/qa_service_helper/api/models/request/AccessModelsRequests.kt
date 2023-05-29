package ru.qa_service_helper.api.models.request

import com.fasterxml.jackson.annotation.JsonInclude
import ru.qa_service_helper.utills.getMaxActivationInterval
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateAccessRequest(
    val userId: Int?,
    val productVersion: Int,
    val simpleProduct: String,
    val accessType: String = "PaidAccess",
    val dateStart: String = LocalDateTime.now().toString().substringBeforeLast("."),
    val dateEnd: String = LocalDateTime.now().plusDays(30).toString().substringBeforeLast("."),
    val attributes: Attributes = Attributes(),
    val activation: Activation = Activation(),
    val productVersionId: String? = null,
    val code: String? = null,
    val paidAccessStatusId: Int? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Attributes(
    val activateDate: String? = null,
    val userCount: Int = 10,
    val dsgNumber: Int? = null,
    val supportDealer: String? = null,
    val salesDealer: String? = null,
    val salesDate: String? = null,
    val attributeList: List<Attribute>? = null,
    val source: String = "qa",
    var structureType: String? = null,
    var parentId: String? = null
)

data class Attribute(
    val key: String,
    val value: String
)

data class Activation(
    val activationType: String = "fixedStartDateAfterActivation",
    val activationInterval: String = "day",
    val interval: Int = getMaxActivationInterval(activationInterval)
)

data class UpdateAccess(
    val commonId: String,
    val userId: Int?,
    val productVersion: Int,
    val simpleProduct: String?,
    val accessType: String? = "PaidAccess",
    val dateStart: String,
    val dateEnd: String,
    val attributes: AttributesUpdate = AttributesUpdate(),
    val productVersionId: String? = null,
    val paidAccessStatusId: Int? = null
)

data class AttributesUpdate(
    val activateDate: String? = null,
    val userCount: Int = 9999,
    val dsgNumber: Int = 0,
    val supportDealer: String? = null,
    val salesDealer: String? = null,
    val salesDate: String? = null,
    val attributeList: List<AttributeUpdate>? = null,
    val source: String? = null,
    var structureType: String? = null,
    var parentId: String? = null
)

class AttributeUpdate