package com.pmartinb.clickbaitresolver.newsdetail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor() : ViewModel() {

    val newsUrl = mutableStateOf("https://www.google.com")


}
