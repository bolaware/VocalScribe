package com.bolaware.feature_history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolaware.feature_history.domain.TranscriptInteractor
import com.bolaware.feature_history.timestamp.TimestampFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
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