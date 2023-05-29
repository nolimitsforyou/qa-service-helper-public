package ru.qa_service_helper

import io.kvision.annotations.KVBindingRoute
import io.kvision.annotations.KVService

@KVService
interface IHelperQAService {

    @KVBindingRoute("user_create")
    suspend fun createNewUser(
        user: CreateUserModel
    ): CreatedUserModel

    @KVBindingRoute("user_access")
    suspend fun createNewAccess(
        access: CreateUpdateAccessModel
    ): CreatedAccessModel

    @KVBindingRoute("user_doc_pack")
    suspend fun createNewDocPack(
        docPack: CreateDocPackModel
    ): CreatedDocumentPackModel

    @KVBindingRoute("slave_add")
    suspend fun addSlaveToAccess(
        accessId: String, slaveBitrix: String, selectEnvironment: String
    ): String

    @KVBindingRoute("slave_remove")
    suspend fun removeSlaveFromAccess(
        accessId: String, slaveBitrix: String, selectEnvironment: String
    ): String

    @KVBindingRoute("crm_previous_access")
    suspend fun createPreviousAccessCrm(
        bitrixId: String, previousAccessId: String?, accessId: String?, selectEnvironment: String
    ): String

    @KVBindingRoute("deactivate_access")
    suspend fun deactivateAccess(commonId: String, selectEnvironment: String): String
}