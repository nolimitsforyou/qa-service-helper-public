package ru.qa_service_helper.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserForm(
    val email: String?,
    val password: String?,
    val phoneNumber: String?,
    val isVerifiedNumber: Boolean,
    val selectEnvironment: String
)

@Serializable
data class CreateAccessForm(
    val bitrixId: String,
    val simpleProduct: String,
    val version: String,
    val dateStart: String?,
    val dateEnd: String?,
    val previousAccessId: String?,
    val commonId: String?,
    val selectAccessType: String,
    val selectEnvironment: String
)