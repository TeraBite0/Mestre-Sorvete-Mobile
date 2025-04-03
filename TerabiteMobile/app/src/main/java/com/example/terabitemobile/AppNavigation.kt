package com.example.terabitemobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
import com.example.terabitemobile.ui.models.CardapioModel
import com.example.terabitemobile.ui.screens.TelaCardapio
import com.example.terabitemobile.ui.screens.TelaGenerica
import com.example.terabitemobile.ui.screens.TelaLogin


fun NavController.navigateIfDifferent(route: String) {
    if (currentDestination?.route != route) {
        navigate(route)
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    cardapioViewModel: CardapioModel
) {
    NavHost(
        navController = navController, startDestination = "login", modifier = Modifier
    ) {
        // Login
        composable(route = "login", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 2 }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaLogin(navController)
        }
        // Inicio
        composable(route = "inicio", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it  / 2}, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaInicio(navController)
        }
        // Ferramentas
        composable(route = "ferramentas", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 2 }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaFerramentas(navController)
        }
        //Cardapio
        composable(route = "cardapio", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 2 }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaCardapio(viewModel = cardapioViewModel)
        }
        // Genérico (SEMPRE DEIXE ESTA ROTA NO FINAL)
        composable(route = "generica") {
            TelaGenerica(navController)
        }
    }
}