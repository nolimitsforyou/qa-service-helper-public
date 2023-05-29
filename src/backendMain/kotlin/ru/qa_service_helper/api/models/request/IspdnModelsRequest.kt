package ru.qa_service_helper.api.models.request

import ru.qa_service_helper.utills.getRandomUuid
import java.time.LocalDateTime

data class CreateDocumentPack(
    val orderId: String = getRandomUuid(),
    val bitrixId: Int,
    val documentSetId: Int,
    val packType: String = "Paid",
    val email: String,
    val fio: String = "Автотестовый Пользователь",
    val products: List<Product>,
    val directorLinkInfo: DirectorLinkInfo? = null,
    val linkedDocumentIds: List<Any> = listOf(),
    val documents: List<Any> = listOf()
)

data class DirectorLinkInfo(
    val packId: Int,
    val documentTypeIds: List<Int> = listOf(9, 10)

)

data class Product(
    val crmVersionId: Int = 1111,
    val dateStart: String = LocalDateTime.now().toString().substringBeforeLast("."),
    val dateEnd: String = LocalDateTime.now().plusDays(30).toString().substringBeforeLast("."),
    val ukd: String,
    val userCount: Int = 1,
    val productType: String = "Paid",
    val program: String = "Название программы",
    val programId: String = getRandomUuid(),
    val duration: Int = 120,
    val licId: String? = getRandomUuid()
)