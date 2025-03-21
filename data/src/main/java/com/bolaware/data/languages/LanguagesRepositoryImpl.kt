package com.bolaware.data.languages

import javax.inject.Inject

internal class LanguagesRepositoryImpl @Inject constructor(): LanguagesRepository {

    override fun getSupportedLanguages(): List<Language> {
        return SupportedLanguages.entries.map { it.language }
    }
}

internal enum class SupportedLanguages(val language: Language) {
    English(Language(key = "en-us", fullName = "English(en-us)", modelName = "model-en-us")),
    German(Language(key = "de", fullName = "German(de)", modelName = "model-small-de"))
}