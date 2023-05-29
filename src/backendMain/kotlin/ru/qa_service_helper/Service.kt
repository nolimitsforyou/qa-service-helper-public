package ru.qa_service_helper

import io.kvision.remote.ServiceException
import ru.some_project.qa.auto.core.commons.generator.entity.user.*
import ru.some_project.qa.auto.core.commons.generator.generate
import ru.some_project.qa.auto.core.commons.generator.utils.randomEmail
import ru.some_project.qa.auto.core.commons.generator.utils.randomWorkPosition
import ru.some_project.qa.auto.core.commons.properties.ENV
import ru.some_project.qa.auto.core.commons.properties.PROJECT_NAME
import ru.qa_service_helper.api.models.request.Attributes
import ru.qa_service_helper.utills.*
import ru.qa_service_helper.utills.DefaultParameters.Companion.DEFAULT_PASSWORD

actual class HelperQAService : IHelperQAService {

    private val exceptionText = "Что-то пошло не так, проверьте параметры запроса"

    override suspend fun createNewUser(
        user: CreateUserModel
    ): CreatedUserModel {

        user.apply {
            ENV = selectEnvironment.lowercase()
            PROJECT_NAME = "qa-helper"

            try {
                generate(
                    User(
                        _email = email ?: randomEmail,
                        password = password ?: DEFAULT_PASSWORD,
                        phone = if (phoneNumber == null) {
                            getRandomPhone()
                        } else {
                            Phone(
                                countryCode = "7",
                                code = phoneNumber.substring(1..3),
                                number = phoneNumber.substring(4..10)
                            )
                        },
                        phoneSubmittedFlag = phoneIsVerified,
                        organization = null,
                        position = post ?: randomWorkPosition
                    )
                ).apply {

                    holdDeleteUserById(id)
                    holdUserById(userId = id, holdingTimeInSeconds = 604800)

                    return CreatedUserModel(
                        email = this.email,
                        password = this.password,
                        bitrixId = this.id,
                        post = this.position,
                        phoneNumber = this.phone.toString(),
                        isVerifiedPhone = this.phoneSubmittedFlag
                    )
                }
            } catch (exc: Exception) {
                throw ServiceException(exc.message ?: exceptionText)
            }
        }
    }

    override suspend fun createNewAccess(access: CreateUpdateAccessModel): CreatedAccessModel {

        lateinit var result: CreatedAccessModel

        access.apply {

            val formattedDateStart = dateStart?.let {
                formatToValidDateTime(it)
            }
            val formattedDateEnd = dateEnd?.let {
                formatToValidDateTime(it)
            }

            ENV = selectEnvironment.lowercase()

            if (commonId.isNullOrEmpty()) {
                // если commonId нет, то создаём новый доступ
                try {
                    val attributeCustom = Attributes()
                    parentId?.let {
                        attributeCustom.parentId = parentId
                    }
                    structureType?.let {
                        attributeCustom.structureType = structureType
                    }
                    createUserAccess(
                        userId = bitrixId?.toInt(),
                        simpleProduct = simpleProduct,
                        productVersion = version.toInt(),
                        dateStart = formattedDateStart ?: getCurrentDate(),
                        dateEnd = formattedDateEnd ?: getDateWithPeriodAfterNextMonthLastDate(),
                        accessType = when (accessType) {
                            AccessType.PAID.id -> AccessType.PAID
                            AccessType.DEMO.id -> AccessType.DEMO
                            else -> throw IllegalArgumentException("AccessType is incorrect")
                        },
                        attributes = attributeCustom
                    ).access.apply {
                        result = CreatedAccessModel(
                            userId = userId,
                            simpleProduct = this.simpleProduct,
                            version = this.productVersion,
                            ukd = this.codeValue,
                            commonId = this.commonId,
                            dateStart = this.dateStart,
                            dateEnd = this.dateEnd,
                            accessType = this.accessType,
                            structureType = this.attributes.structureType,
                            parentId = this.attributes.parentId,
                            infoText = "Сгенерирован новый доступ:"
                        )
                    }
                } catch (exc: Exception) {
                    throw ServiceException(exc.message ?: exceptionText)
                }

            } else {
                try {
                    updateAccess(
                        commonId = commonId,
                        userId = bitrixId!!.toInt(),
                        productVersion = version.toInt(),
                        simpleProduct = simpleProduct,
                        dateStart = formattedDateStart ?: getCurrentDate(),
                        dateEnd = formattedDateEnd ?: getDateWithPeriodAfterNextMonthLastDate(),
                        accessType = when (accessType) {
                            AccessType.PAID.id -> AccessType.PAID
                            AccessType.DEMO.id -> AccessType.DEMO
                            else -> throw IllegalArgumentException("AccessType is incorrect")
                        },
                        structureType = structureType,
                        parentId = parentId
                    ).access.apply {
                        result = CreatedAccessModel(
                            userId = userId,
                            simpleProduct = this.simpleProduct,
                            version = this.productVersion,
                            ukd = this.codeValue,
                            commonId = this.commonId,
                            dateStart = this.dateStart,
                            dateEnd = this.dateEnd,
                            accessType = this.accessType,
                            structureType = this.attributes.structureType,
                            parentId = this.attributes.parentId,
                            infoText = "Обновлён существующий доступ:"
                        )
                    }
                } catch (exc: Exception) {
                    throw ServiceException(exc.message ?: exceptionText)
                }
            }
        }
        return result
    }

    override suspend fun createNewDocPack(docPack: CreateDocPackModel): CreatedDocumentPackModel {
        var result: CreatedDocumentPackModel

        docPack.apply {

            ENV = selectEnvironment.lowercase()

            try {
                createNewDocumentPack(
                    bitrixId = bitrixId.toInt(),
                    email = email,
                    documentSetId = documentSet.toInt(),
                    ukdNumber = ukd ?: getRandomUkdNumber()
                ).apply {
                    result = CreatedDocumentPackModel(packId = packId)
                }
            } catch (exc: Exception) {
                throw ServiceException(exc.message ?: exceptionText)
            }
        }
        return result
    }

    override suspend fun addSlaveToAccess(
        accessId: String,
        slaveBitrix: String,
        selectEnvironment: String
    ): String {
        return try {
            ENV = selectEnvironment.lowercase()
            addSlaveToAccess(accessId, slaveBitrix.toInt())
        } catch (exc: Exception) {
            throw ServiceException(exc.message ?: exceptionText)
        }
    }

    override suspend fun removeSlaveFromAccess(
        accessId: String,
        slaveBitrix: String,
        selectEnvironment: String
    ): String {
        return try {
            ENV = selectEnvironment.lowercase()
            removeSlaveFromAccess(accessId, slaveBitrix.toInt())
        } catch (exc: Exception) {
            throw ServiceException(exc.message ?: exceptionText)
        }
    }

    override suspend fun createPreviousAccessCrm(
        bitrixId: String,
        previousAccessId: String?,
        accessId: String?,
        selectEnvironment: String
    ): String {
        return try {
            ENV = selectEnvironment.lowercase()
            createCrmPreviousAccess(bitrixId.toInt(), previousAccessId, accessId)
        } catch (exc: Exception) {
            throw ServiceException(exc.message ?: exceptionText)
        }
    }

    override suspend fun deactivateAccess(commonId: String, selectEnvironment: String): String {
        return try {
            ENV = selectEnvironment.lowercase()
            deactivateAccess(commonId)
        } catch (exc: Exception) {
            throw ServiceException(exc.message ?: exceptionText)
        }
    }
}