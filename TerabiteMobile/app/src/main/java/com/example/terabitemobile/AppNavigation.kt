package com.example.terabitemobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.terabitemobile.ui.screens.TelaInicio
import TelaFerramentas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.terabitemobile.data.classes.LoginResponse
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.DestaqueModel
import com.example.terabitemobile.data.models.MarcaModel
import com.example.terabitemobile.data.models.BaixaModel
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.models.EstoqueModel
import com.example.terabitemobile.data.models.RecomendacaoModel
import com.example.terabitemobile.ui.screens.TelaCardapio
import com.example.terabitemobile.ui.screens.TelaDestaque
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

fun getStartRoute(): String {
    lateinit var startRoute: String
    var loginResponse = LoginResponse()

    if (loginResponse.token == "") {
        startRoute = "login"
    } else {
        startRoute = "inicio"
    }

    return startRoute
}

@Composable
fun AppNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    cardapioViewModel: CardapioModel,
    marcaViewModel: MarcaModel,
    recomendacaoViewModel: RecomendacaoModel,
    destaqueViewModel: DestaqueModel,
    baixaViewModel: BaixaModel,
    estoqueViewModel: EstoqueModel
) {
    NavHost(
        navController = navController, startDestination = getStartRoute(), modifier = Modifier
    ) {
        // Login
        composable(route = "login") {
            TelaLogin(navController)
        }

        // Inicio
        composable(route = "inicio") {
            TelaInicio(navController)
        }

        // Ferramentas
        composable(route = "ferramentas") {
            TelaFerramentas(navController)
        }

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
            TelaDestaque(
                paddingValues,
                destaqueViewModel = destaqueViewModel,
                produtosViewModel = cardapioViewModel
            )
        }

        // Saída estoque
        composable(route = "baixas") {
            val produtos by cardapioViewModel.produtos.observeAsState(emptyList())
            TelaBaixas(paddingValues, viewModel = baixaViewModel, produtos = produtos)
        }

        // Estoque
        composable(route = "estoque") {
            TelaEstoque(paddingValues, viewModel = estoqueViewModel)
        }

        // Genérico (SEMPRE DEIXE ESTA ROTA NO FINAL)
        composable(route = "generica") {
            TelaGenerica(navController)
        }
    }
}