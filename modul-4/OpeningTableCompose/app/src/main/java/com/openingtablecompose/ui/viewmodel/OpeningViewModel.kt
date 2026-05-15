package com.openingtablecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.openingtablecompose.data.ChessOpening
import com.openingtablecompose.data.OpeningRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

sealed class OpeningEvent {
    data class NavigateToWeb(val url: String) : OpeningEvent()
    data class NavigateToDetail(val id: String) : OpeningEvent()
}

class OpeningViewModel(private val logPrefix: String) : ViewModel() {

    private val _openings = MutableStateFlow<List<ChessOpening>>(emptyList())
    val openings: StateFlow<List<ChessOpening>> = _openings

    private val _navigationEvent = MutableStateFlow<OpeningEvent?>(null)
    val navigationEvent: StateFlow<OpeningEvent?> = _navigationEvent

    init {
        loadOpenings()
    }

    private fun loadOpenings() {
        val data = OpeningRepository.getOpenings()
        _openings.value = data
        data.forEach { opening ->
            Timber.d("[$logPrefix] Data item masuk ke list: ${opening.name}")
        }
    }

    fun onWebClicked(url: String) {
        Timber.i("[$logPrefix] Tombol Explicit Intent ditekan untuk URL: $url")
        _navigationEvent.value = OpeningEvent.NavigateToWeb(url)
    }

    fun onDetailClicked(opening: ChessOpening) {
        Timber.i("[$logPrefix] Tombol Detail ditekan untuk item: ${opening.name}")
        Timber.d("[$logPrefix] Mengirim ID data ke halaman Detail: ${opening.id}")
        _navigationEvent.value = OpeningEvent.NavigateToDetail(opening.id)
    }

    fun onEventHandled() {
        _navigationEvent.value = null
    }
}