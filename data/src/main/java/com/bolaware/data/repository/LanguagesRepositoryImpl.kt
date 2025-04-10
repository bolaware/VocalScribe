package com.bolaware.data.repository

import com.bolaware.data.local.languages.Language
import com.bolaware.feature_home_domain.LanguagesRepository

class LanguagesRepositoryImpl : LanguagesRepository {

    override fun getSupportedLanguages(): List<com.bolaware.feature_home_domain.data.LanguageDomain> {
        return SupportedLanguages.entries.map { it.language.toDomain() }
    }
}

internal enum class SupportedLanguages(val language: Language) {
    English(Language(key = "en-us", fullName = "English(en-us)", modelName = "model-en-us")),
    German(Language(key = "de", fullName = "German(de)", modelName = "model-small-de"))
}

private fun Language.toDomain(): com.bolaware.feature_home_domain.data.LanguageDomain {
    return com.bolaware.feature_home_domain.data.LanguageDomain(
        key = this.key,
        fullName = this.fullName,
        modelName = this.modelName
    )
}