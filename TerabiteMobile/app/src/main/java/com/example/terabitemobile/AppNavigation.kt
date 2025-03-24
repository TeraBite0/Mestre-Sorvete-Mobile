package com.example.terabitemobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.terabitemobile.ui.screens.TelaInicio
import TelaFerramentas
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.example.terabitemobile.ui.screens.TelaGenerica
import com.example.terabitemobile.ui.screens.TelaLogin

@Composable
fun AppNavigation(navController: NavHostController) {
    // Adicione rotas novas aqui
    val validRoutes = setOf("login", "inicio", "ferramentas")

    NavHost(
        navController = navController, startDestination = "login", modifier = Modifier
    ) {
        // Login
        composable(route = "login", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(100)
            ) + fadeIn(animationSpec = tween(100))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(100))
        }) {
            TelaLogin(navController)
        }

        // Inicio
        composable(route = "inicio", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(100)
            ) + fadeIn(animationSpec = tween(100))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(100)
            ) + fadeOut(animationSpec = tween(100))
        }) {
            TelaInicio(navController)
        }

        // Ferramentas
        composable(route = "ferramentas", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(100)
            ) + fadeIn(animationSpec = tween(100))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(100)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaFerramentas(navController)
        }

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (currentRoute != null && currentRoute !in validRoutes) {
            navController.navigate("generica") {
                popUpTo("login") { inclusive = true }
            }
        }

        // Genérico (SEMPRE DEIXE ESTA ROTA NO FINAL)
        composable(route = "generica") {
            TelaGenerica(navController)
        }

    }
}