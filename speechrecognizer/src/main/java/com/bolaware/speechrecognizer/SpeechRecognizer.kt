package com.bolaware.speechrecognizer

import kotlinx.coroutines.flow.Flow

/**
 * Interface for handling speech recognition.
 * Provides methods to initialize, listen, stop, and clean up speech recognition processes.
 */
interface SpeechRecognizer {
    /**
     * Initializes the speech recognizer with a given language model.
     *
     * @param languageModel The name or path of the language model to be used.
     * @return A [Result] indicating success or failure.
     */
    suspend fun initialize(languageModel: String): Result<Unit>

    /**
     * Starts listening for speech input and returns a stream of recognition results.
     *
     * @return A [Flow] emitting [SpeechResult] objects containing recognition hypotheses and final results.
     */
    fun listen(): Flow<SpeechResult>

    /**
     * Stops the ongoing speech recognition process.
     */
    fun stopListener()

    /**
     * Cleans up resources used by the speech recognizer.
     */
    fun cleanUp()
}

/**
 * Data class representing the result of speech recognition.
 *
 * @property hypothesis A possible interpretation of the spoken input, usually intermediate.
 * @property finalResult The final transcribed text after processing.
 */
data class SpeechResult(
    val hypothesis: String? = null,
    val finalResult: String? = null
)