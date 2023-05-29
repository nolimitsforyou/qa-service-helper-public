package ru.qa_service_helper.api.models.response

data class CrmDocumentPackResponse(
    val orderId: String?,
    val packId: Int,
    val documentIds: List<DocumentId>?,
    val validation: List<Validation>?
)

data class DocumentId(
    val type: Int?,
    val id: Int,
    val docDate: String?,
    val docNr: String?
)

data class Validation(
    val status: Int,
    val errorCode: Int,
    val message: String?,
    val info: String?,
    val wrongObjects: List<WrongObject>?
)

data class WrongObject(
    val objectId: Int,
    val errorCode: Int
)