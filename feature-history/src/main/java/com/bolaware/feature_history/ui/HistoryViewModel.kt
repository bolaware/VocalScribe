package com.bolaware.feature_history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolaware.data.transcript.Transcript
import com.bolaware.feature_history.domain.TranscriptInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transcriptInteractor: TranscriptInteractor
) : ViewModel() {

    val transcripts: StateFlow<List<Transcript>> = transcriptInteractor.getTranscripts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteTranscript(transcript: Transcript) {
        viewModelScope.launch {
            transcriptInteractor.deleteTranscript(transcript)
        }
    }
}