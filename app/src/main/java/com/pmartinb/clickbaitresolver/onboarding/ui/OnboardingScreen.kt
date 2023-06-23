package com.pmartinb.clickbaitresolver.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pmartinb.clickbaitresolver.onboarding.viewmodel.OnboardingViewModel
import com.pmartinb.composecodelab.ui.theme.ClickbaitResolverTheme

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onOpenMainScreen: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the News summarizer")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = { onOpenMainScreen() }
        ) {
            Text("Continue")
        }
    }

}

@Preview(widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ClickbaitResolverTheme {
        OnboardingScreen(
            viewModel = hiltViewModel(),
            {},
        )
    }
}
