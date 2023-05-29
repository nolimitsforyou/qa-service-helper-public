package ru.qa_service_helper.api.requests.docs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.some_project.qa.auto.core.api.Request
import ru.qa_service_helper.api.models.request.CreateDocumentPack
import java.net.HttpURLConnection.HTTP_OK

object MasterDocsRequests {

    private const val API_V1 = "1"
    private const val POST_CREATE_DOCUMENT_PACK = "/api/$API_V1/crm/createdocumentpack"
    private const val DEFAULT_POSITIVE_RESPONSE_STATUS = HTTP_OK

    fun createDocumentPack(documentPack: CreateDocumentPack) = Request(
        desc = "Создание пакета документов для пользователя",
        spec = {
            setContentType("application/json")
            setBody(jacksonObjectMapper().writeValueAsString(documentPack))
        },
        send = { post(POST_CREATE_DOCUMENT_PACK) },
        verify = { statusCode(DEFAULT_POSITIVE_RESPONSE_STATUS) }
    )
}