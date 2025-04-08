package com.bolaware.feature_history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolaware.core_domain.TranscriptInteractor
import com.bolaware.feature_history.timestamp.TimestampFormatter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TranscriptHistoryViewModel(
    private val transcriptInteractor: TranscriptInteractor,
    private val timestampFormatter: TimestampFormatter
) : ViewModel() {

    val transcripts: StateFlow<List<TranscriptUi>> = transcriptInteractor.getTranscripts()
        .map {
            it.map {
                TranscriptUi(
                    id = it.id,
                    text = it.text,
                    time = timestampFormatter.formatTimestamp(it.timestamp)
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteTranscript(transcript: TranscriptUi) {
        viewModelScope.launch {
            transcriptInteractor.deleteTranscript(transcript.id)
        }
    }
}