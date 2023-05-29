package ru.qa_service_helper.utills

import ru.some_project.qa.auto.core.invocation.invoke
import ru.qa_service_helper.api.client.apiClientServiceIspdn
import ru.qa_service_helper.api.models.request.CreateDocumentPack
import ru.qa_service_helper.api.models.request.Product
import ru.qa_service_helper.api.models.response.CrmDocumentPackResponse
import ru.qa_service_helper.api.requests.docs.MasterDocsRequests.createDocumentPack
import java.util.*

fun getRandomUuid() = UUID.randomUUID().toString()

fun getRandomUkdNumber(): String {
    val part1 = (1000..9999).shuffled().first()
    val part2 = (1000..9999).shuffled().first()
    val part3 = (1000..9999).shuffled().first()
    val part4 = (1000..9999).shuffled().first()
    val part5 = (1000..9999).shuffled().first()
    return "$part1-$part2-$part3-$part4-$part5"
}

fun createNewDocumentPack(
    bitrixId: Int,
    email: String,
    documentSetId: Int,
    ukdNumber: String,
    products: List<Product> = listOf(Product(ukd = ukdNumber))
): CrmDocumentPackResponse {

    return "Выполнить запрос на создание пакета документов" {
        apiClientServiceIspdn.send(
            createDocumentPack(
                CreateDocumentPack(
                    bitrixId = bitrixId,
                    email = email,
                    documentSetId = documentSetId,
                    products = products
                )
            )
        ).deserializeAs()
    }
}

fun isCorrectUKDFormat(ukdNumber: String): Boolean {
    val regex = """(\d\d\d\d)-(\d\d\d\d)-(\d\d\d\d)-(\d\d\d\d)-(\d\d\d\d)""".toRegex()
    return regex.matches(ukdNumber)
}