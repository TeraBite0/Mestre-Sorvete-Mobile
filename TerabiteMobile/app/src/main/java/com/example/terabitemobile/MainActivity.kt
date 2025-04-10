package com.example.terabitemobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.ui.models.CardapioModel
import com.example.terabitemobile.ui.models.MarcaModel
import com.example.terabitemobile.ui.models.RecomendacaoModel
import com.example.terabitemobile.ui.screens.BottomNavigationBar
import com.example.terabitemobile.ui.screens.TelaLogin
import com.example.terabitemobile.ui.theme.background

class MainActivity : ComponentActivity() {
    private val cardapioViewModel: CardapioModel by viewModels()
    private val marcaViewModel: MarcaModel by viewModels()
    private val recomendacaoViewModel: RecomendacaoModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val focusManager = LocalFocusManager.current


            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            MaterialTheme {
                Scaffold(containerColor = background, bottomBar = {
                    if (currentRoute != "login" && currentRoute != "ferramentas") BottomNavigationBar(
                        navController
                    )
                }, modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }) { paddingValues ->
                    AppNavigation(
                        paddingValues,
                        navController,
                        cardapioViewModel = cardapioViewModel,
                        marcaViewModel = marcaViewModel,
                        recomendacaoViewModel = recomendacaoViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    TelaLogin(rememberNavController())
}