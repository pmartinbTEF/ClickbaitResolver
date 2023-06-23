package com.pmartinb.clickbaitresolver.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmartinb.clickbaitresolver.prompt.data.Result.Success
import com.pmartinb.clickbaitresolver.prompt.domain.usecase.GetSummarizerModelUseCase
import com.pmartinb.clickbaitresolver.prompt.domain.usecase.SaveSummarizerModelUseCase
import com.pmartinb.clickbaitresolver.settings.domain.GetNewsKeyUseCase
import com.pmartinb.clickbaitresolver.settings.domain.GetSummarizerKeyUseCase
import com.pmartinb.clickbaitresolver.settings.domain.SaveNewsKeyUseCase
import com.pmartinb.clickbaitresolver.settings.domain.SaveSummarizerKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val saveNewsKeyUseCase: SaveNewsKeyUseCase,
    val saveSummarizerKeyUseCase: SaveSummarizerKeyUseCase,
    val saveSummarizerModelUseCase: SaveSummarizerModelUseCase,
    val getNewsKeyUseCase: GetNewsKeyUseCase,
    val getSummarizerKeyUseCase: GetSummarizerKeyUseCase,
    val getSummarizerModelUseCase: GetSummarizerModelUseCase,
) : ViewModel() {

    private var _newsKey = MutableStateFlow<String>("")
    val newsKey: StateFlow<String>
        get() = _newsKey
    private var _summarizerKey = MutableStateFlow<String>("")
    val summarizerKey: StateFlow<String>
        get() = _summarizerKey
    private var _summarizerModel = MutableStateFlow<String>("")
    val summarizerModel: StateFlow<String>
        get() = _summarizerModel

    val stateNews: StateFlow<SettingsScreenState> = channelFlow {
        getNewsKeyUseCase().collect { _result ->
            when (_result) {
                is Success -> {
                    _newsKey.value = _result.value
                    send(SettingsScreenState.ReadyNews)
                }

                else -> {
                    _newsKey.value = "error"
                    send(SettingsScreenState.ErrorNews)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsScreenState.Loading
    )

    val stateSummarizer: StateFlow<SettingsScreenState> = channelFlow {
        getSummarizerKeyUseCase().collect { _result ->
            when (_result) {
                is Success -> {
                    _summarizerKey.value = _result.value
                    send(SettingsScreenState.ReadySummarizer)
                }

                else -> {
                    send(SettingsScreenState.ErrorSummarizer)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsScreenState.Loading
    )

    val stateSummarizerModel: StateFlow<SettingsScreenState> = channelFlow {
        getSummarizerModelUseCase().collect { _result ->
            when (_result) {
                is Success -> {
                    _summarizerModel.value = _result.value
                    send(SettingsScreenState.ReadySummarizerModel)
                }

                else -> {
                    send(SettingsScreenState.ErrorSummarizerModel)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsScreenState.Loading
    )

    fun onSavePressed(fieldNews: String, fieldSummarizer: String, fieldSummarizerModel: String) {
        viewModelScope.launch {
            saveNewsKeyUseCase(fieldNews)
            saveSummarizerKeyUseCase(fieldSummarizer)
            saveSummarizerModelUseCase(fieldSummarizerModel)
        }
    }
}
