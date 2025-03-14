package com.bolaware.vocalscribe

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConcatenationManager @Inject constructor() {

    private val permanentStrings = mutableListOf<String>()
    private var temporaryString: String? = null

    /**
     * Adds a string to the concatenation list.
     * @param text The string to add.
     * @param isTemporary If true, the string is temporary. If false, it's permanent.
     */
    fun addString(text: String, isTemporary: Boolean) {
        if (isTemporary) {
            temporaryString = text
        } else {
            temporaryString = null
            permanentStrings.add(text)
        }
    }

    /**
     * Retrieves the final concatenated result.
     * @return The concatenated string.
     */
    fun getConcatenatedText(): String {
        return (permanentStrings + listOfNotNull(temporaryString)).joinToString(" ")
    }

    /**
     * Clears all stored strings.
     */
    fun clear() {
        permanentStrings.clear()
        temporaryString = null
    }
}
