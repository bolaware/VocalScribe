package com.bolaware.speechrecognizer

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "SpeechRecognizer"

internal class SpeechRecognizerImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val jsonTextExtractor: JsonTextExtractor
) : SpeechRecognizer {

    private lateinit var model: Model
    private lateinit var speechService: SpeechService

    override suspend fun initialize(languageModel: String): Result<Unit> {
        return suspendCoroutine { continuation ->
            StorageService.unpack(
                context,
                languageModel,
                "model",
                { model: Model ->
                    this.model = model
                    continuation.resume(Result.success(Unit))
                    Timber.tag(TAG).d("READY")
                },
                { exception: IOException ->
                    continuation.resume(Result.failure(exception))
                    Timber.e(exception, "Failed to unpack the model")
                }
            )
        }
    }

    override fun listen(): Flow<SpeechResult> = callbackFlow {
        try {
            val rec = Recognizer(model, 16000.0f)
            speechService = SpeechService(rec, 16000.0f)

            val listener = object : RecognitionListener {
                override fun onPartialResult(hypothesis: String?) {
                    hypothesis?.let { jsonTextExtractor.extractText(it) }?.let {
                        trySend(SpeechResult(hypothesis = it))
                    }
                }

                override fun onResult(hypothesis: String?) {
                    hypothesis?.let { jsonTextExtractor.extractText(it) }?.let {
                        trySend(SpeechResult(finalResult = it))
                    }
                }

                override fun onFinalResult(hypothesis: String?) {
                    hypothesis?.let { jsonTextExtractor.extractText(it) }?.let {
                        trySend(SpeechResult(finalResult = it))
                    }
                }

                override fun onError(exception: Exception?) {
                    close(exception ?: IOException("Unknown speech recognition error"))
                }

                override fun onTimeout() {
                    close(TimeoutException("Speech recognition timeout"))
                }
            }

            speechService.startListening(listener)

            awaitClose { speechService.stop() }
        } catch (e: IOException) {
            Timber.e(e, "Failed to start listening")
            close(e)  // Close Flow on failure
        }
    }
        .catch { e -> Timber.e(e, "Speech recognition error: ${e.message}") }
        .buffer()

    override fun stopListener() {
        speechService.stop()
    }

    override fun cleanUp() {
        if (::speechService.isInitialized) {
            speechService.stop()
            speechService.shutdown()
        }
    }
}