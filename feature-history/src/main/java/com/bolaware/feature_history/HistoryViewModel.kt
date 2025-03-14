package com.bolaware.feature_history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel : ViewModel() {
    private val _text = MutableStateFlow("This is History Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}