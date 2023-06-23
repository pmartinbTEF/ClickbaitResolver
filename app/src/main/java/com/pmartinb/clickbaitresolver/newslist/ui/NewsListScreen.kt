package com.pmartinb.clickbaitresolver.newslist.ui

import android.content.res.Configuration
import android.util.Base64
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pmartinb.clickbaitresolver.navigation.Screen
import com.pmartinb.clickbaitresolver.newslist.domain.model.News
import com.pmartinb.clickbaitresolver.newslist.viewmodel.NewsListViewModel
import com.pmartinb.composecodelab.ui.theme.ClickbaitResolverTheme


@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    viewModel: NewsListViewModel,
    topic: String,
    onCardClick: (String) -> Unit,
) {
    viewModel.setTopic(topic)
    val news by remember { viewModel.news }.collectAsState()


    val state by remember { viewModel.state }.collectAsState()

    when (state) {
        is NewsListScreenState.Loading -> {
            NewsListSkeleton()
        }
        is NewsListScreenState.Ready -> {
            News(
                news = news,
                onCardClick = onCardClick,
                modifier = modifier,
            )
        }
        is NewsListScreenState.Error -> {
            ErrorLoadingNews(
                modifier = modifier,
                message = "TBD",
                onRetryClick = { viewModel.onErrorRetryPressed() }
            )
        }
    }
}

@Composable
fun News(
    news: List<News>,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = news) {
            val urlEncoded = Base64.encodeToString(it.url?.toByteArray(), Base64.DEFAULT)
            NewsCard(
                newsHeadline = it.headline, newsDescription = it.description, url = urlEncoded, onCardClick)
        }
    }
}

@Composable
fun ErrorLoadingNews(
    modifier: Modifier = Modifier,
    message: String = "Error loading news",
    onRetryClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = message)
        IconButton(onClick = { onRetryClick() }) {
            Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "Retry")
        }
    }
}

@Composable
fun NewsListSkeleton() {
    Card(
        modifier = Modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(text = "Loading...")
    }
}



@Preview(
    showBackground = true,
    widthDp = 320,
)
@Composable
private fun NewsListPreview2() {
    ClickbaitResolverTheme {
        News(List(10) {
            News(headline = "15 outdated details you should delete from your resume", description = "$it")
        }, {})
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
private fun NewsListDarkPreview() {
    ClickbaitResolverTheme {
        News(List(10) {
            News(headline = "15 outdated details you should delete from your resume", description = "$it")
        }, {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsCard(newsHeadline: String, newsDescription: String, url: String, onCardClick: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val padd by animateDpAsState(
        targetValue = if (expanded.value) {
            64.dp
        } else {
            0.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = {onCardClick(url)},
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )

                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = newsHeadline, style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded.value)
                    Text(
                        text = newsDescription,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 16.dp)
                    )
            }
            IconButton(
                onClick = {
                    Log.i("TAG", "Greeting: ")
                    expanded.value = !expanded.value
                },
                enabled = true,
                content = {
                    Icon(
                        imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        null
                    )
                }
            )

        }
    }
}

