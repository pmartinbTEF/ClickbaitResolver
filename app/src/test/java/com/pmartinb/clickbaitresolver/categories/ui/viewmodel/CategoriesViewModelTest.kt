package com.pmartinb.clickbaitresolver.categories.ui.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.firstOrNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoriesViewModelTest {

    private lateinit var sut: CategoriesViewModel

    @Before
    fun init() {
        sut = CategoriesViewModel()
    }

    @Test
    fun `check correct initial categories`() = runTest {
        assertEquals(
            sut.categories.firstOrNull(),
            INITIAL_CATEGORIES
        )
    }
    private companion object {
        val INITIAL_CATEGORIES = listOf<String>("Apples", "Space")
    }
}