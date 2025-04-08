package com.bolaware.speechrecognizer

import org.json.JSONObject

class JsonTextExtractor {

    fun extractText(jsonString: String): String? {
        return try {
            val jsonObject = JSONObject(jsonString)
            jsonObject.optString("text").takeIf { it.isNotBlank() } ?:
            jsonObject.optString("partial").takeIf { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }
}