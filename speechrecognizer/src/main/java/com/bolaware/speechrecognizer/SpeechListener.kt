package com.bolaware.speechrecognizer

import java.lang.Exception

interface SpeechListener {
    fun onHypothesis(text: String)

    fun onFinal(text: String)

    fun onError(exception: Exception)
}