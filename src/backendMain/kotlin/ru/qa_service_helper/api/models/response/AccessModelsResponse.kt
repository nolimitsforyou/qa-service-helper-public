package ru.qa_service_helper.api.models.response

data class AccessV2GetResponse(
    val access: Access
)

data class Access(
    val commonId: String,
    val userId: Int?,
    val productVersion: Int,
    val simpleProduct: String?,
    val baseProduct: String?,
    val codeValue: String,
    val accessType: String?,
    val dateStart: String,
    val dateEnd: String,
    val isActive: Boolean,
    val isSlave: Boolean,
    val slaveIds: List<Int>?,
    val createDate: String,
    val version: Version,
    val productVersionId: String?,
    val paidAccessStatusId: Int?,
    val attributes: Attributes,
    val activation: Activation
)

data class Version(
    val version: Int,
    val timestamp: String
)

data class Attributes(
    val activateDate: String?,
    val userCount: Int,
    val dsgNumber: Int,
    val supportDealer: String?,
    val salesDealer: String?,
    val salesDate: String?,
    val attributeList: List<Attribute>?,
    val source: String?,
    val structureType: String?,
    val parentId: String?,
)

data class Attribute(
    val key: String?,
    val value: String?
)

data class Activation(
    val activationType: String,
    val activationInterval: String?,
    val interval: Int?
)