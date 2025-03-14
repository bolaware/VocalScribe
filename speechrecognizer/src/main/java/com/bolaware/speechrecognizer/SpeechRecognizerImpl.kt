package com.bolaware.speechrecognizer

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
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

    override fun listen(listener: SpeechListener) {
        try {
            val rec = Recognizer(model, 16000.0f)
            speechService = SpeechService(rec, 16000.0f)
            speechService.startListening(object : RecognitionListener {
                override fun onPartialResult(hypothesis: String?) {
                    if (hypothesis != null) {
                        jsonTextExtractor.extractText(hypothesis)?.let {
                            listener.onHypothesis(it)
                        }
                    }
                }

                override fun onResult(hypothesis: String?) {
                    if (hypothesis != null) {
                        jsonTextExtractor.extractText(hypothesis)?.let {
                            listener.onFinal(it)
                        }
                    }
                }

                override fun onFinalResult(hypothesis: String?) {
                    if (hypothesis != null) {
                        jsonTextExtractor.extractText(hypothesis)?.let {
                            listener.onFinal(it)
                        }
                    }
                }

                override fun onError(exception: Exception?) {
                    exception?.let { listener.onError(it) }
                }

                override fun onTimeout() {
                    listener.onError(TimeoutException())
                }
            })
        } catch (e: IOException) {
            Timber.e(e, "Failed to set listener")
        }
    }

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