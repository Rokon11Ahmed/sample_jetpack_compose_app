package com.example.currentnews.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currentnews.ui.screen.WelcomeScreen
import com.example.currentnews.ui.screen.HomeScreen
import com.example.currentnews.ui.screen.NewsDetailsScreen

import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.NewsDetails.route + "/{id}") {navBackStack ->
            val id = navBackStack.arguments?.getString("id")
            NewsDetailsScreen(newsId = id)
        }
    }
}