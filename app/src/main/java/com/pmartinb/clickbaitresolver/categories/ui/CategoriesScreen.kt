package com.pmartinb.clickbaitresolver.categories.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmartinb.clickbaitresolver.categories.ui.viewmodel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    onCategoryClick: (String) -> Unit,
) {
    val categoriesList by remember { viewModel.categories }.collectAsState(listOf())
    CategoriesList(categories = categoriesList, onCategoryClick = onCategoryClick)
}

@Composable
fun CategoriesList(
    categories: List<String>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        categories.forEach() { _category ->
            Button(
                onClick = { onCategoryClick(_category) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(text = _category, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun CategoriesScreenPreview() {
    CategoriesList(
        categories = listOf("Apple", "Space"),
        onCategoryClick = {}
    )
}