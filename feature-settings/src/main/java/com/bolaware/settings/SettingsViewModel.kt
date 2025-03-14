package com.bolaware.settings
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    private val _text = MutableStateFlow("This is Settings Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}