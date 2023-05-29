package ru.qa_service_helper.forms

enum class DocumentPack(val title: String, val type: String) {
    HIGH_SCHOOL_DEFAULT("Пользователь ВШ", "1"),
    HIGH_SCHOOL_VIP("VIP пользователь ВШ", "7"),
    DIRECTOR_ACADEMY("Директор академии", "2"),
    PUPIL_ACADEMY("Ученик академии", "3"),
    DIRECTOR_ACADEMY_360("Директор А360", "9"),
    PUPIL_ACADEMY_360("Ученик А360", "10")
}

enum class StructureType(val title: String, val type: String) {
    PODPISKA("Подписка", "subscription"),
    BLOCK("Блок", "block")
}