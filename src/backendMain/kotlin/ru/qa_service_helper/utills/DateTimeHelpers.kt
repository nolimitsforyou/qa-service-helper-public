package ru.qa_service_helper.utills

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

fun getCurrentDate() = LocalDateTime.now().toString().substringBeforeLast(".")

fun getDateWithPeriodAfterNextMonthLastDate(periodInDays: Int = 14) =
    TemporalAdjusters.lastDayOfMonth().adjustInto(getRawLastDateOfNextMonth())
        .plus(Period.of(0, 0, periodInDays)).toString().substringBeforeLast(".")

private fun getRawLastDateOfNextMonth() = getRawCurrentDateMaxDateTime().plusMonths(1)

private fun getRawCurrentDateMaxDateTime() =
    LocalDateTime.now().withHour(23).withMinute(59).withSecond(59)

fun formatToValidDateTime(date: String) =
    LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")), LocalTime.MAX)
        .toString().substringBeforeLast(".")