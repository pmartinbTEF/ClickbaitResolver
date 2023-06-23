package com.pmartinb.clickbaitresolver.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pmartinb.clickbaitresolver.R

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val newsKey by remember { settingsViewModel.newsKey }.collectAsState()
    var newsStatusOk by remember { mutableStateOf(false) }
    val hintNews = stringResource(R.string.settings_input_hint_news)
    val summarizerKey by remember { settingsViewModel.summarizerKey }.collectAsState()
    var summarizerStatusOk by remember { mutableStateOf(false) }
    val hintSummarizer = stringResource(R.string.settings_input_hint_summarizer)
    val summarizerModel by remember { settingsViewModel.summarizerModel }.collectAsState()
    var summarizerModelStatusOk by remember { mutableStateOf(false) }
    val hintSummarizerModel = stringResource(R.string.settings_input_hint_summarizer_model)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Current keys:", Modifier.padding(top = 16.dp, bottom = 16.dp))

        val stateNews by remember { settingsViewModel.stateNews }.collectAsState()
        var editableText1 = ""
        val stateSummarizer by remember { settingsViewModel.stateSummarizer }.collectAsState()
        var editableText2 = ""
        val stateSummarizerModel by remember { settingsViewModel.stateSummarizerModel }.collectAsState()
        var editableText3 = ""

        val pair = settingsInput(stateNews, SettingsScreenState.ReadyNews, hintNews, newsKey, editableText1)
        editableText1 = pair.first
        newsStatusOk = pair.second

        val pair2 = settingsInput(
            stateSummarizer,
            SettingsScreenState.ReadySummarizer,
            hintSummarizer,
            summarizerKey,
            editableText2,
            modifier = Modifier.padding(top = 16.dp)
        )
        editableText2 = pair2.first
        summarizerStatusOk = pair2.second

        val pair3 = settingsInput(
            stateSummarizerModel,
            SettingsScreenState.ReadySummarizerModel,
            hintSummarizerModel,
            summarizerModel,
            editableText3,
            modifier = Modifier.padding(top = 16.dp)
        )
        editableText3 = pair3.first
        summarizerModelStatusOk = pair3.second


        Button(
            onClick = {
                settingsViewModel.onSavePressed(editableText1, editableText2, editableText3)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            enabled = newsStatusOk && summarizerStatusOk
        ) {
            Text(text = "Save")
        }
    }

}


@Composable
private fun settingsInput(
    currentState: SettingsScreenState,
    okState: SettingsScreenState,
    hint: String,
    key: String,
    editableText1: String,
    modifier: Modifier = Modifier,
): Pair<String, Boolean> {
    var editableText11 = editableText1
    var newsStatusOk1 by remember { mutableStateOf(false) }
    if (currentState.equals(okState)) {
        var text1 by remember { mutableStateOf(TextFieldValue(key)) }
        editableText11 = text1.text
        newsStatusOk1 = true
        TextField(
            value = text1,
            onValueChange = { newText ->
                text1 = newText
            },
            label = { Text(hint) },
            enabled = newsStatusOk1,
            modifier = modifier.fillMaxWidth()
        )
    } else {
        TextField(
            value = if (currentState is SettingsScreenState.Loading) {
                stringResource(R.string.loading_key)
            } else {
                stringResource(R.string.error_loading_key)
            },
            onValueChange = {},
            label = { Text(hint) },
            enabled = false,
            modifier = modifier.fillMaxWidth()
        )
    }
    return Pair(editableText11, newsStatusOk1)
}
