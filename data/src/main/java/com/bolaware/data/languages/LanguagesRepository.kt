package com.bolaware.data.languages

import com.bolaware.data.Language
import javax.inject.Inject

class LanguagesRepository @Inject constructor() {

    fun getSupportedLanguages(): List<Language> {
        return SupportedLanguages.entries.map { it.language }
    }
}

internal enum class SupportedLanguages(val language: Language) {
    English(Language(key = "en-us", fullName = "English(en-us)", modelName = "model-en-us")),
    German(Language(key = "de", fullName = "German(de)", modelName = "model-small-de"))
}