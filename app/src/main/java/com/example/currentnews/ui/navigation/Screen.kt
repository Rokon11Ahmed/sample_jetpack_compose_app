package com.example.currentnews.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Home : Screen(route = "home_screen")
    object NewsDetails : Screen(route = "news_details_screen")
}