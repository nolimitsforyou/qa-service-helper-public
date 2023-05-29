package ru.qa_service_helper

object Model {

    val qaHelperService = HelperQAService()

    suspend fun createNewUser(
        user: CreateUserModel
    ): CreatedUserModel {
        return qaHelperService.createNewUser(user)
    }

    suspend fun createNewAccess(
        access: CreateUpdateAccessModel
    ): CreatedAccessModel {
        return qaHelperService.createNewAccess(access)
    }

    suspend fun addSlaveToAccess(
        accessId: String, slaveBitrix: String, selectEnvironment: String
    ): String {
       return qaHelperService.addSlaveToAccess(accessId, slaveBitrix, selectEnvironment)
    }

    suspend fun removeSlaveFromAccess(
        accessId: String, slaveBitrix: String, selectEnvironment: String
    ): String {
        return qaHelperService.removeSlaveFromAccess(accessId, slaveBitrix, selectEnvironment)
    }
}