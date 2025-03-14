package com.bolaware.speechrecognizer

interface SpeechRecognizer {
    suspend fun initialize(languageModel: String): Result<Unit>

    fun listen(listener: SpeechListener)

    fun stopListener()

    fun cleanUp()
}