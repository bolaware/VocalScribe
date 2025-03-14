package com.bolaware.vocalscribe

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechConcatenator @Inject constructor() {

    private val list = mutableListOf<Pair<String, Boolean>>()

    fun add(words: Pair<String, Boolean>) {

    }
}