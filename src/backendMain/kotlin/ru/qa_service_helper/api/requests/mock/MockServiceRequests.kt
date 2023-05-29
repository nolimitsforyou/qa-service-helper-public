package ru.qa_service_helper.api.requests.mock

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.http.ContentType
import ru.some_project.qa.auto.core.api.Request
import ru.qa_service_helper.api.models.request.PreviousAccessMockSetupRequest

object MockServiceRequests {

    private const val PREVIOUS_ACCESS_SETUP = "/api/v1/previous-access-mock_setup"

    fun createMockAccess(bitrixId: Int?, accessId: String?, previousAccessId: String?) = Request(
        desc = "Настройка мока CRM для рассчёта previousAccessId",
        spec = {
            setContentType(ContentType.JSON)
            setBody(
                jacksonObjectMapper().writeValueAsString(
                    PreviousAccessMockSetupRequest(
                        accessId = accessId,
                        bitrixId = bitrixId,
                        previousAccessId = previousAccessId
                    )
                )
            )
        },
        send = { post(PREVIOUS_ACCESS_SETUP) }
    )
}