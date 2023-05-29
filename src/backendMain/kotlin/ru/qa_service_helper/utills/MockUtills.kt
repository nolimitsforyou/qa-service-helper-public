package ru.qa_service_helper.utills

import ru.qa_service_helper.api.client.apiClientMockService
import ru.qa_service_helper.api.requests.mock.MockServiceRequests.createMockAccess
import java.net.HttpURLConnection

fun createCrmPreviousAccess(bitrixId: Int, previousAccessId: String?, accessId: String? = null): String {
    val response =
        apiClientMockService.send(createMockAccess(bitrixId, accessId, previousAccessId).apply { verify = { } })
    if (response.statusCode != HttpURLConnection.HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return "В мок-сервис добавлено инфо о previousAccessId: $previousAccessId для bitrixId: $bitrixId"
    }
}