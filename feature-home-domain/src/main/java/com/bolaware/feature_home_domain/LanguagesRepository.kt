package com.bolaware.feature_home_domain

import com.bolaware.feature_home_domain.data.LanguageDomain

interface LanguagesRepository {
    fun getSupportedLanguages(): List<LanguageDomain>
}