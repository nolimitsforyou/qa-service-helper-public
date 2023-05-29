package ru.qa_service_helper.utills

import com.fasterxml.jackson.module.kotlin.readValue
import io.restassured.response.Response
import org.junit.jupiter.api.fail
import ru.some_project.qa.auto.core.serialization.Mapper

inline fun <reified T> Response.deserializeAs(): T {
    this.body().asString().ifBlank { fail("Ответ на запрос не содержит данных для десериализации") }
    return Mapper.json(strict = false).readValue(body().asString())
}