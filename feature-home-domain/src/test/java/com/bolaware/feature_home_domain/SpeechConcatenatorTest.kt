package com.bolaware.feature_home_domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpeechConcatenatorTest {

    private lateinit var speechConcatenator: SpeechConcatenator

    @Before
    fun setup() {
        speechConcatenator = SpeechConcatenator()
    }

    @Test
    fun `addString should add temporary text without affecting permanent list`() {
        speechConcatenator.addString("Hello", true) // Temporary
        assertEquals("Hello", speechConcatenator.getConcatenatedText())

        speechConcatenator.addString("World", true) // Overwrites temporary
        assertEquals("World", speechConcatenator.getConcatenatedText())

        speechConcatenator.addString("Permanent", false) // Adds to permanent list
        assertEquals("Permanent", speechConcatenator.getConcatenatedText())
    }

    @Test
    fun `getConcatenatedText should return combined temporary and permanent texts`() {
        speechConcatenator.addString("Hello", false)
        speechConcatenator.addString("World", false)
        speechConcatenator.addString("!", true) // Temporary
        
        assertEquals("Hello World !", speechConcatenator.getConcatenatedText())
    }

    @Test
    fun `temporary text should be removed when a new permanent text is added`() {
        speechConcatenator.addString("Temp", true) // Temporary
        assertEquals("Temp", speechConcatenator.getConcatenatedText())

        speechConcatenator.addString("Final", false) // Permanent, clears temp
        assertEquals("Final", speechConcatenator.getConcatenatedText())
    }

    @Test
    fun `clear should remove all stored strings`() {
        speechConcatenator.addString("One", false)
        speechConcatenator.addString("Two", false)
        speechConcatenator.addString("Three", true) // Temporary

        speechConcatenator.clear()
        assertEquals("", speechConcatenator.getConcatenatedText()) // Everything is removed
    }
}
