package com.pmartinb.clickbaitresolver.categories.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
) : ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(listOf("Apple", "Space"))
    val categories: StateFlow<List<String>>
        get() = _categories


}