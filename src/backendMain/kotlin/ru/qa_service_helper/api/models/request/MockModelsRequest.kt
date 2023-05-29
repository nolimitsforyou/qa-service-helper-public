package ru.qa_service_helper.api.models.request

data class PreviousAccessMockSetupRequest(
    val accessId: String?,
    val bitrixId: Int?,
    val previousAccessId: String?
)