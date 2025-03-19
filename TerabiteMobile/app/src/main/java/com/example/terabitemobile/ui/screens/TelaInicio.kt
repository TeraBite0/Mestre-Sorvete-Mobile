package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.R
import com.example.terabitemobile.ui.theme.TerabiteMobileTheme

@Composable
fun TelaInicio(navController: NavHostController) {
    val colors = TelaInicioColors()

    Scaffold(
        containerColor = colors.background,
        bottomBar = { BottomNavigationBar(colors) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(16.dp))
            SearchField()
            Spacer(modifier = Modifier.height(16.dp))
            EstoqueCard(colors)
            Spacer(modifier = Modifier.height(16.dp))
            FerramentasSection(colors, navController)
        }
    }
}

@Composable
private fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Usuário",
                tint = Color(0xFF8C3829),
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    "Josué",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Administrador",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun SearchField() {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Buscar...", style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun EstoqueCard(colors: TelaInicioColors) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.cardBege),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Gerenciar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                )
                Text(
                    text = "Estoque",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color(0xFF343434)
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        "Acessar",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.box),
                contentDescription = "ícone de caixa",
                modifier = Modifier
                    .padding(16.dp)
                    .size(65.dp),
                colorFilter = ColorFilter.tint(Color(0xFF343434))
            )
        }
    }
}

@Composable
private fun FerramentasSection(colors: TelaInicioColors, navController: NavHostController) {
    Text(
        text = "Ferramentas",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 20.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FerramentaItem(
            titulo = "Estoque",
            corFundo = colors.primary,
            corTexto = Color.White,
            iconRes = R.drawable.box,
            onClick = { navController.navigate("estoque") }
        )

        FerramentaItem(
            titulo = "Cardápio",
            corFundo = colors.cardBege,
            corTexto = Color(0xFF343434),
            iconRes = R.drawable.scroll,
            onClick = { navController.navigate("cardapio") }
        )

        FerramentaItem(
            titulo = "Baixas",
            corFundo = colors.primary,
            corTexto = Color.White,
            iconRes = R.drawable.store,
            onClick = { navController.navigate("baixas") }
        )
    }

    VerMaisButton(colors, navController)
}

@Composable
private fun VerMaisButton(colors: TelaInicioColors, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, end = 14.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Ver mais",
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(25.dp))

        IconButton(
            onClick = { navController.navigate("ferramentas") },
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(colors.primary)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = "Ver mais",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FerramentaItem(
    titulo: String,
    corFundo: Color,
    corTexto: Color,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(100.dp),
        colors = CardDefaults.cardColors(containerColor = corFundo),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = titulo,
                tint = corTexto,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,
                color = corTexto,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(colors: TelaInicioColors) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.onda_imagem),
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
            val navItems = listOf(
                NavItem("Início", Icons.Default.Home, true),
                NavItem("Cardápio", Icons.Default.Menu, false),
                NavItem("Estoque", Icons.Default.Search, false),
                NavItem("Conta", Icons.Default.Person, false)
            )

            navItems.forEach { item ->
                NavigationBarItem(
                    selected = item.selected,
                    onClick = { },
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = {
                        Text(
                            item.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (item.selected) FontWeight.SemiBold else FontWeight.Bold
                        )
                    }
                )
            }
        }
    }
}

private data class NavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selected: Boolean
)

private class TelaInicioColors {
    val background = Color(0xFFF9FBFF)
    val primary = Color(0xFF8C3829)
    val cardBege = Color(0xFFE9DEB0)
}

@Preview(showBackground = true)
@Composable
fun TelaInicioPreview() {
    TerabiteMobileTheme {
        TelaInicio(rememberNavController())
    }
}