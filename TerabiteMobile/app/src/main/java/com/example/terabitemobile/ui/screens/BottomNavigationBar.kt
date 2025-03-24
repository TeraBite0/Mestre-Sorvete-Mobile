package com.example.terabitemobile.ui.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.terabitemobile.R

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavItem("Início", Icons.Filled.Home, "inicio"),
        NavItem("Cardápio", Icons.Filled.Menu, "generica"),
        NavItem("Estoque", Icons.Filled.Search, "generica"),
        NavItem("Conta", Icons.Filled.Person, "generica")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var renderizarBarra: Boolean = true

    for (item in navItems) {
        if (item.route == "login" || item.route == "ferramentas") {
            renderizarBarra = false
        }
    }

    if (renderizarBarra) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.onda),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.FillBounds
            )

            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = { navController.navigate(item.route) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = {
                            Text(
                                item.label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (currentRoute == item.route) FontWeight.SemiBold else FontWeight.Bold
                            )
                        }
                    )
                }
            }
        }
    }
}