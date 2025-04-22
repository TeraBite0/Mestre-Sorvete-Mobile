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
import androidx.compose.foundation.layout.PaddingValues
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.DestaqueModel
import com.example.terabitemobile.data.models.MarcaModel
import com.example.terabitemobile.data.models.BaixasModel
import com.example.terabitemobile.data.models.EstoqueModel
import com.example.terabitemobile.data.models.RecomendacaoModel
import com.example.terabitemobile.ui.screens.TelaCardapio
import com.example.terabitemobile.ui.screens.TelaDestaque
import com.example.terabitemobile.ui.screens.TelaEsqueceuSenha
import com.example.terabitemobile.ui.screens.TelaEstoque
import com.example.terabitemobile.ui.screens.TelaGenerica
import com.example.terabitemobile.ui.screens.TelaLogin
import com.example.terabitemobile.ui.screens.TelaMarcas
import com.example.terabitemobile.ui.screens.TelaRecomendacao
import com.example.terabitemobile.ui.screens.TelaBaixas


fun NavController.navigateIfDifferent(route: String) {
    if (currentDestination?.route != route) {
        navigate(route)
    }
}

@Composable
fun AppNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    cardapioViewModel: CardapioModel,
    marcaViewModel: MarcaModel,
    recomendacaoViewModel: RecomendacaoModel,
    destaqueViewModel: DestaqueModel,
    baixaViewModel: BaixasModel,
    estoqueViewModel: EstoqueModel
) {
    NavHost(
        navController = navController, startDestination = "login", modifier = Modifier
    ) {
        // Login
        composable(route = "login") {
            TelaLogin(navController)
        }

        // Esqueceu senha
        composable(route = "esqueceu senha") {
            TelaEsqueceuSenha(navController)
        }

        // Inicio
        composable(route = "inicio") {
            TelaInicio(navController)
        }

        // Ferramentas
        composable(route = "ferramentas") {
            TelaFerramentas(navController)
        }

        // Telas abaixo PRECISAM do parâmetro padding value, já que o scaffold no Main
        // irá aplicar o padding correto para a bottom bar.

        // Cardapio
        composable(route = "cardapio") {
            TelaCardapio(paddingValues, viewModel = cardapioViewModel)
        }

        // Marcas
        composable(route = "marcas") {
            TelaMarcas(paddingValues, viewModel = marcaViewModel)
        }

        // Recomendação
        composable(route = "recomendados") {
            TelaRecomendacao(paddingValues, recomendacaoViewModel, cardapioViewModel)
        }

        // Destaque
        composable(route = "destaques") {
            TelaDestaque(paddingValues, destaqueViewModel = destaqueViewModel, produtosViewModel = cardapioViewModel)
        }

        // Saída estoque
        composable(route = "baixas") {
            TelaBaixas(paddingValues, viewModel = baixaViewModel)
        }

        // Estoque
        composable(route = "estoque") {
            TelaEstoque(paddingValues, viewModel = estoqueViewModel)
        }

        // Essa aqui não precisa do parâmetro, ela já está configurada
        // Genérico (SEMPRE DEIXE ESTA ROTA NO FINAL)
        composable(route = "generica") {
            TelaGenerica(navController)
        }
    }
}