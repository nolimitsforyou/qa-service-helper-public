package ru.qa_service_helper.external.services

import ru.some_project.qa.auto.core.properties.TProperty

var SERVICE_ISPDN by TProperty.notNullable { SchoolServiceUrlBuilder.ISPDN.url }
var SERVICE_MOCK by TProperty.notNullable { SchoolServiceUrlBuilder.MOCK.url }