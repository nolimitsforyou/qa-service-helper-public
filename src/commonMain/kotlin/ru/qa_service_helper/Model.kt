package ru.qa_service_helper

import kotlinx.serialization.Serializable

@Serializable
data class CreatedUserModel(
    val email: String,
    val password: String,
    val bitrixId: Int,
    val post: String?,
    val phoneNumber: String?,
    val isVerifiedPhone: Boolean
)

@Serializable
data class CreatedAccessModel(
    val userId: Int?,
    val simpleProduct: String?,
    val version: Int,
    val ukd: String,
    val commonId: String,
    val dateStart: String,
    val dateEnd: String,
    val accessType: String?,
    val structureType: String?,
    val parentId: String?,
    val infoText: String
)

@Serializable
data class CreateUpdateAccessModel(
    val bitrixId: String?,
    val simpleProduct: String,
    val version: String,
    val dateStart: String?,
    val dateEnd: String?,
    val parentId: String?,
    val structureType: String?,
    val commonId: String?,
    val accessType: String,
    val selectEnvironment: String
)

@Serializable
data class CreateDocPackModel(
    val bitrixId: String,
    val email: String,
    val ukd: String?,
    val documentSet: String,
    val selectEnvironment: String
)

@Serializable
data class CreatedDocumentPackModel(
    val packId: Int
)

@Serializable
data class CreateUserModel(
    val email: String?,
    val password: String?,
    val phoneNumber: String?,
    val phoneIsVerified: Boolean,
    val post: String?,
    val selectEnvironment: String
)