package com.example.terabitemobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.terabitemobile.R

@Composable
fun NavBar(
    currentRoute: String = "inicio",
    onNavigate: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        // Barra de navegação
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NavigationBarItem(
                selected = currentRoute == "inicio",
                onClick = { onNavigate("inicio") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
                label = { Text("Início") }
            )
            NavigationBarItem(
                selected = currentRoute == "cardapio",
                onClick = { onNavigate("cardapio") },
                icon = { Icon(Icons.Default.Menu, contentDescription = "Cardápio") },
                label = { Text("Cardápio") }
            )
            NavigationBarItem(
                selected = currentRoute == "estoque",
                onClick = { onNavigate("estoque") },
                icon = { Icon(Icons.Default.Search, contentDescription = "Estoque") },
                label = { Text("Estoque") }
            )
            NavigationBarItem(
                selected = currentRoute == "conta",
                onClick = { onNavigate("conta") },
                icon = { Icon(Icons.Default.Person, contentDescription = "Conta") },
                label = { Text("Conta") }
            )
        }
    }
}