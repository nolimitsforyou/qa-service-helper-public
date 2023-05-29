package ru.qa_service_helper.external.services

import ru.some_project.qa.auto.core.commons.properties.*

interface SchoolServiceBuilder {
    val serviceName: String
    val productName: String
    val config: String get() = "$serviceName-$productName"
    val protocol: String
    val domain: String

    val url
        get() = when (ENV.lowercase()) {
            "dev" -> "$protocol://$branchPrefix-$serviceName-$productName.$ENV.$productName.$domain"
            "rc" -> "$protocol://$branchPrefix-$serviceName-$productName.$ENV.$productName.$domain"
            "prod" -> "$protocol://$serviceName-$productName.$ENV.$productName.$domain"
            else -> throw IllegalArgumentException("Unknown environment: $ENV")
        }.lowercase()


    private val branchPrefix
        get() = if (config !in CONFIGS) {
            when (ENV.lowercase()) {
                "dev" -> if (DEV_BRANCH != null) {
                    DEV_BRANCH?.substringAfterLast("/")
                } else {
                    "master"
                }
                "rc" -> "release"
                else -> throw IllegalArgumentException("Unknown environment: $ENV")
            }
        } else {

            if (JIRA_ISSUE == null && DEV_BRANCH_PREFIX.isNullOrEmpty()) {
                throw IllegalArgumentException("JIRA_ISSUE and DEV_BRANCH_PREFIX can't be null")
            }

            when (DEV_BRANCH_PREFIX) {
                "master" -> if (ENV.lowercase() == "rc") {
                    "stable"
                } else {
                    DEV_BRANCH_PREFIX
                }
                "release" -> DEV_BRANCH_PREFIX
                else -> JIRA_ISSUE!!.lowercase()
            }
        }
}

internal enum class SchoolServiceUrlBuilder(override val serviceName: String, override val productName: String) :
    SchoolServiceBuilder {

    MOCK("service-mock", "school"),
    ISPDN("documents-backend", "ad");

    override val protocol = "http"
    override val domain = "aservices.tech"
}