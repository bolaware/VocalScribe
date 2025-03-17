package com.bolaware.speechrecognizer

import kotlinx.coroutines.flow.Flow

interface SpeechRecognizer {
    suspend fun initialize(languageModel: String): Result<Unit>

    fun listen(): Flow<SpeechResult>

    fun stopListener()

    fun cleanUp()
}

data class SpeechResult(
    val hypothesis: String? = null,
    val finalResult: String? = null
)