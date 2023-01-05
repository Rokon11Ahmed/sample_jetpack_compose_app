package com.example.currentnews.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.currentnews.R
import com.example.currentnews.core.utils.Helper
import com.example.currentnews.ui.components.NewsCard
import com.example.currentnews.ui.navigation.Screen
import com.example.currentnews.ui.viewmodel.HomeScreenViewModel
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    homeScreenViewModel.getNewsHeadlines()
    val newsList = homeScreenViewModel.newsList
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        var isLoading = homeScreenViewModel.isLoading.collectAsState()
        ProgressBar(isLoading.value)

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        //SearchBar()
        LazyColumn() {
            item {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(top = 30.dp, bottom = 20.dp),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            item {
                val query = homeScreenViewModel.queryText.value
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .padding(start = 8.dp, end = 8.dp, bottom = 15.dp),
                            value = query,
                            onValueChange = {
                                homeScreenViewModel.onQueryChange(it)
                            },
                            textStyle = TextStyle(color = colorScheme.onSurface),
                            label = { Text(text = stringResource(id = R.string.IDS_SEARCH)) },
                            placeholder = { Text(text = stringResource(id = R.string.IDS_SEARCH_HERE)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search,
                            ),
                            maxLines = 1,
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "emailIcon") },
                            keyboardActions = KeyboardActions(onSearch = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                homeScreenViewModel.getNewsHeadlines()
                            })
                        )
                    }
                }
            }
            itemsIndexed(
                items = newsList.value
            ) { index, news ->
                NewsCard(news = news, onClick = {
                    navController.navigate(Screen.NewsDetails.route + "/${news.id}")
                })
            }
        }
    }
}

@Composable
fun ProgressBar(isLoading: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = Color.Blue,
                strokeWidth = Dp(value = 4F)
            )
        }
    }
}