package com.bolaware.feature_home.ui

import com.bolaware.data.languages.Language

data class HomeState(
    val text: String = "",
    val recordState: RecordState = RecordState.Idle(isLoading = false),
    val alertDialogState: AlertDialogState? = null,
    val languages: List<LanguageUi> = emptyList()
) {
    val showTextField
        get() = recordState !is RecordState.Idle

    val showMicButton
        get() = recordState !is RecordState.Saving

    val showClearSaveButton
        get() = recordState is RecordState.Saving

    val isListening
        get() = recordState is RecordState.Listening

    val isMicLoading
        get() = recordState is RecordState.Idle && recordState.isLoading

    val selectedLanguage: LanguageUi?
        get() = languages.find { it.isSelected }
}

data class AlertDialogState(
    val title: String,
    val text: String,
    val negative: AlertDialogAction,
    val positive: AlertDialogAction,
    val isDismissable: Boolean = true
)

data class AlertDialogAction(val text: String, val onAction: () -> Unit)

data class LanguageUi(val language: Language, val isSelected: Boolean)

sealed interface RecordState {
    data class Idle(val isLoading: Boolean = false) : RecordState
    data object Listening : RecordState
    data object Saving : RecordState
}
