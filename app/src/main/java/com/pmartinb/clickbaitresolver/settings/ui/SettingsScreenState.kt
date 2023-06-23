package com.pmartinb.clickbaitresolver.settings.ui


sealed class SettingsScreenState {
    object Loading : SettingsScreenState()
    object ReadyNews : SettingsScreenState()
    object ReadySummarizer : SettingsScreenState()
    object ReadySummarizerModel : SettingsScreenState()
    object ErrorNews : SettingsScreenState()
    object ErrorSummarizer : SettingsScreenState()
    object ErrorSummarizerModel : SettingsScreenState()
}