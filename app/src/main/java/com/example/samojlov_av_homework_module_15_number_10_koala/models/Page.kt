package com.example.samojlov_av_homework_module_15_number_10_koala.models

data class Page(val name: String, val url: String) {
    companion object {
        val pageList = mutableListOf(
            Page("Поиск", "https://ya.ru/"),
            Page("Новости", "https://dzen.ru/news"),
            Page("Спорт", "https://dzen.ru/news/rubric/sport"),
            Page("Культура", "https://dzen.ru/news/rubric/culture"),
            Page("Кино", "https://www.afisha.ru/msk/cinema/"),
            Page("Музыка", "https://zaycev.net/genres/index.html")
        )
    }
}