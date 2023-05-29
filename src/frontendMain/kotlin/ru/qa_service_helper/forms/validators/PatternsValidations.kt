package ru.qa_service_helper.forms.validators

object PatternsValidations {

    val PATTERN_EXCEPT_DIGITS = Regex("""[^0-9]""")
    val PATTERN_EXCEPT_SIMPLE_PRODUCT = Regex("""[^0-9А-Яа-яЁёa-zA-Z\-]""")
    val PATTER_EXCEPT_ACCESS_ATTR = Regex("""[^0-9a-zA-Z\-]""")
    val PATTERN_MATCH_UKD = Regex("""^\d{4}-\d{4}-\d{4}-\d{4}-\d{4}""")
    val PATTERN_PHONE_NUMBER_11 = Regex("""^7\d{10}""")
    val PATTERN_EXCEPT_PHONE_NUMBER = Regex("""^[^7]+""")
    val PATTERN_MATCH_EMAIL =
        Regex("""^(([\wа-яА-Я0-9-+!#&${'$'}%*/=^`'{|}~.]+)|(".+"))@([a-zA-Zа-яА-Я0-9-]+.)+((xn--[a-zA-Z0-9]+)|([a-zA-Zа-яА-Я]{2,}))${'$'}""")

    val PATTERN_ZERO_DAY = Regex("""^[0][0]""")
    val PATTERN_ZERO_DAY_PLUS = Regex("""^[0][0].+""")
    val PATTERN_DAY_BIGGER_40 = Regex("""^[4-9][0-9]""")
    val PATTERN_DAY_BIGGER_40_PLUS = Regex("""^[4-9][0-9].+""")
    val PATTERN_DAY_BIGGER_32 = Regex("""^[3][2-9]""")
    val PATTERN_DAY_BIGGER_32_PLUS = Regex("""^[3][2-9].+""")
    val PATTERN_ZERO_MONTH = Regex("""^\d{2}\.[0][0]""")
    val PATTERN_ZERO_MONTH_PLUS = Regex("""^\d{2}\.[0][0].+""")
    val PATTERN_MONTH_BIGGER_12 = Regex("""^\d{2}\.[1][3-9]""")
    val PATTERN_MONTH_BIGGER_12_PLUS = Regex("""^\d{2}\.[1][3-9].+""")
    val PATTERN_MONTH_BIGGER_20 = Regex("""^\d{2}\.[2-9][0-9]""")
    val PATTERN_MONTH_BIGGER_20_PLUS = Regex("""^\d{2}\.[2-9][0-9].+""")
    val PATTERN_MATCH_DATE = Regex("""^\d{2}\.\d{2}\.\d{4}""")
}

