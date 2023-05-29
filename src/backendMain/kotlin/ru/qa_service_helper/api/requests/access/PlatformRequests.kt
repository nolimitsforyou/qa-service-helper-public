package ru.qa_service_helper.api.requests.access

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.some_project.qa.auto.core.api.Request
import ru.qa_service_helper.api.models.request.CreateAccessRequest
import ru.qa_service_helper.api.models.request.UpdateAccess

object PlatformRequests {

    private const val ACCESS_CREATE = "/api/v1/access_create"
    private const val POST_ACCESS_UPDATE = "/api/v1/access_update"
    private const val ACCESS_SLAVE_ADD = "/api/v1/access-slave_add"
    private const val ACCESS_SLAVE_REMOVE = "/api/v1/access-slave_remove"
    private const val ACCESS_DEACTIVATE = "/api/v1/access_deactivate"

    fun accessCreate(access: CreateAccessRequest) = Request(
        desc = "Запрос на создание доступа через API",
        spec = {
            setContentType("application/json")
            setBody(jacksonObjectMapper().writeValueAsString(access))
        },
        send = { post(ACCESS_CREATE) }
    )

    fun accessUpdate(access: UpdateAccess) = Request(
        desc = "Редактирование доступа",
        spec = {
            setContentType("application/json")
            setBody(jacksonObjectMapper().writeValueAsString(access))
        },
        send = { post(POST_ACCESS_UPDATE) }
    )

    fun accessSlaveAdd(accessId: String, slaveId: Int) = Request(
        desc = "Добавление слейва к доступу",
        spec = {
            setContentType("application/json")
            setBody("""{"accessId": "$accessId","slaveId": $slaveId}""".trimIndent())
        },
        send = { post(ACCESS_SLAVE_ADD) }
    )

    fun accessSlaveRemove(accessId: String, slaveId: Int) = Request(
        desc = "Удаление слейва из доступа",
        spec = {
            setContentType("application/json")
            setBody("""{"accessId": "$accessId","slaveId": $slaveId}""".trimIndent())
        },
        send = { post(ACCESS_SLAVE_REMOVE) }
    )

    fun accessDeactivate(commonId: String) = Request(
        desc = "Запрос на деактивацию доступа через API",
        spec = {
            setContentType("application/json")
            setBody("""{ "accessId": "$commonId"}""".trimIndent())
        },
        send = { post(ACCESS_DEACTIVATE) }
    )
}