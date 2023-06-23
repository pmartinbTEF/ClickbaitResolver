package com.pmartinb.clickbaitresolver.newslist.ui

sealed class NewsListScreenState {
    object Loading : NewsListScreenState()
    object Ready : NewsListScreenState()
    object Error : NewsListScreenState()
}