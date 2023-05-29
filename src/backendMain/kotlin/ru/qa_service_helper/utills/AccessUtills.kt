package ru.qa_service_helper.utills

import org.apache.commons.lang3.RandomUtils
import ru.some_project.qa.auto.core.commons.api.access_backend.qaApiClientAccessBackendV2
import ru.qa_service_helper.api.models.request.*
import ru.qa_service_helper.api.models.response.AccessV2GetResponse
import ru.qa_service_helper.api.requests.access.PlatformRequests.accessCreate
import ru.qa_service_helper.api.requests.access.PlatformRequests.accessDeactivate
import ru.qa_service_helper.api.requests.access.PlatformRequests.accessSlaveAdd
import ru.qa_service_helper.api.requests.access.PlatformRequests.accessSlaveRemove
import ru.qa_service_helper.api.requests.access.PlatformRequests.accessUpdate
import java.net.HttpURLConnection.HTTP_OK

fun createUserAccess(
    userId: Int?,
    productVersion: Int,
    simpleProduct: String,
    accessType: AccessType = AccessType.PAID,
    dateStart: String = getCurrentDate(),
    dateEnd: String = getDateWithPeriodAfterNextMonthLastDate(),
    attributes: Attributes = Attributes(),
    activation: Activation = Activation(),
    productVersionId: String? = null,
    code: String? = null,
    paidAccessStatusId: Int? = null
): AccessV2GetResponse {

    val response = qaApiClientAccessBackendV2.send(
        accessCreate(
            CreateAccessRequest(
                userId = userId,
                productVersion = productVersion,
                simpleProduct = simpleProduct,
                accessType = accessType.id,
                dateStart = dateStart,
                dateEnd = dateEnd,
                attributes = attributes,
                activation = activation,
                productVersionId = productVersionId,
                code = code,
                paidAccessStatusId = paidAccessStatusId
            )
        ).apply { verify = { } }
    )

    if (response.statusCode != HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return response.deserializeAs()
    }
}

fun updateAccess(
    commonId: String,
    userId: Int,
    productVersion: Int,
    simpleProduct: String,
    dateStart: String,
    dateEnd: String,
    accessType: AccessType,
    attributes: AttributesUpdate = AttributesUpdate(),
    structureType: String? = null,
    parentId: String? = null,
): AccessV2GetResponse {

    structureType?.let {
        attributes.structureType = it
    }
    parentId?.let {
        attributes.parentId = it
    }

    val response = qaApiClientAccessBackendV2.send(
        accessUpdate(
            UpdateAccess(
                commonId = commonId,
                userId = userId,
                productVersion = productVersion,
                simpleProduct = simpleProduct,
                dateStart = dateStart,
                dateEnd = dateEnd,
                accessType = accessType.id,
                attributes = attributes
            )
        ).apply { verify = { } }
    )

    if (response.statusCode != HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return response.deserializeAs()
    }
}

enum class AccessType(val id: String) {
    PAID("PaidAccess"),
    DEMO("DemoAccess")
}

fun getMaxActivationInterval(activationInterval: String) =
    when (activationInterval.lowercase()) {
        "day" -> RandomUtils.nextInt(1, 365) * 10
        "week" -> RandomUtils.nextInt(1, 52) * 100
        "month" -> RandomUtils.nextInt(1, 12) * 100
        "year" -> RandomUtils.nextInt(1, 100)
        else -> RandomUtils.nextInt(1, 100)
    }

fun addSlaveToAccess(accessId: String, slaveId: Int): String {
    val response = qaApiClientAccessBackendV2.send(accessSlaveAdd(accessId, slaveId).apply { verify = { } })
    if (response.statusCode != HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return "Slave $slaveId успешно добавлен к доступу $accessId"
    }
}

fun removeSlaveFromAccess(accessId: String, slaveId: Int): String {
    val response = qaApiClientAccessBackendV2.send(accessSlaveRemove(accessId, slaveId).apply { verify = { } })
    if (response.statusCode != HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return "Slave $slaveId успешно удалён из доступа $accessId"
    }
}

fun deactivateAccess(commonId: String) : String {
    val response = qaApiClientAccessBackendV2.send(accessDeactivate(commonId))
    if (response.statusCode != HTTP_OK) {
        throw Exception("Exception: ${response.body.asString()}")
    } else {
        return "Доступ $commonId успешно деактивирован"
    }
}