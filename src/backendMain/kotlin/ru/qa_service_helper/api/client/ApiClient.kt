package ru.qa_service_helper.api.client

import ru.some_project.qa.auto.core.api.ApiClient
import ru.qa_service_helper.external.services.SERVICE_ISPDN
import ru.qa_service_helper.external.services.SERVICE_MOCK

val apiClientServiceIspdn get() = ApiClient(SERVICE_ISPDN)

val apiClientMockService get() = ApiClient(SERVICE_MOCK)