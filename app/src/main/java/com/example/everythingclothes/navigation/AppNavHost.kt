package com.example.everythingclothes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.everythingclothes.data.AuthViewModel

import com.example.everythingclothes.ui.theme.Screens.Home.HomeScreen
import com.example.everythingclothes.ui.theme.Screens.Login.LoginScreen

import com.example.everythingclothes.ui.theme.Screens.Products.ViewProductsScreen
import com.example.everythingclothes.ui.theme.Screens.Signup.SignupScreen
import com.example.everythingclothes.ui.theme.Screens.Welcome.WelcomeScreen
import com.example.everythingclothes.ui.theme.screens.products.AddProductsScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = WELCOME_URL
) {
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navController = navController, context = context)

    NavHost(navController = navController, modifier = modifier, startDestination = startDestination) {
        composable(WELCOME_URL) {
            WelcomeScreen(navController = navController)
        }
        composable(LOGIN_URL) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(ADD_PRODUCTS_URL) {
            AddProductsScreen(navController = navController)
        }
        composable(VIEW_PRODUCTS_URL) {
            ViewProductsScreen(navController = navController)
        }
        composable(SIGNUP_URL) {
            SignupScreen(navController = navController)
        }
        composable(HOME_URL) {
            HomeScreen(navController = navController)
        }
    }
}
