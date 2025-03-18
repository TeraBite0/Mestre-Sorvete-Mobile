package com.example.terabitemobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.terabitemobile.ui.screens.TelaLogin
import com.example.terabitemobile.ui.screens.TelaInicio
import TelaFerramentas
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = "login", modifier = Modifier
    ) {
        composable(route = "login", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }, popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaLogin(navController)
        }

        composable(route = "inicio", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }, popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaInicio(navController)
        }

        composable(route = "ferramentas", enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }, popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }, popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
            TelaFerramentas(navController)
        }
    }
}