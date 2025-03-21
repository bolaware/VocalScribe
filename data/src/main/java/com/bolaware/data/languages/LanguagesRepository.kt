package com.bolaware.data.languages

interface LanguagesRepository {
    fun getSupportedLanguages(): List<Language>
}