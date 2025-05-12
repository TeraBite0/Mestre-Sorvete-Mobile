package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.terabitemobile.R
import com.example.terabitemobile.navigateIfDifferent
import com.example.terabitemobile.ui.theme.tomVinho

data class NavItem(
    val label: String,
    val icon: Painter,
    val route: String
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavItem("Início", painterResource(R.drawable.house), "inicio"),
        NavItem("Cardápio", painterResource(R.drawable.scroll), "cardapio"),
        NavItem("Estoque", painterResource(R.drawable.box), "estoque"),
        NavItem("Destaque", painterResource(R.drawable.star), "destaques")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route

    Box {
        if (currentRoute == "inicio" ||
            currentRoute == "destaques") {
            Image(
                painter = painterResource(id = R.drawable.onda),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            navItems.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigateIfDifferent(item.route) },
                    icon = { Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = Modifier
                            .size(21.dp)
                    ) },
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = tomVinho,
                        selectedIconColor = Color(0xFFFFFFFF),
                        selectedTextColor = Color(0xFF343434),
                        unselectedIconColor = Color(0xFF343434),
                        unselectedTextColor = Color(0xFF343434),
                        disabledIconColor = Color(0xFF343434),
                        disabledTextColor = Color(0xFF343434)
                    ),
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