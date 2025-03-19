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
import androidx.compose.foundation.layout.paddingFromBaseline
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
import androidx.compose.material.icons.outlined.ArrowForward
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInicio(navController: NavHostController) {
    val fundoCinza = Color(0xFFF9FBFF)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = fundoCinza, // Cor de fundo para toda a tela
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onda_imagem),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.FillBounds
                )

                // Barra de navegação
                NavigationBar(
                    containerColor = Color.White,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
                        label = {
                            Text(
                                "Início",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Default.Menu, contentDescription = "Cardápio") },
                        label = {
                            Text(
                                "Cardápio",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Estoque") },
                        label = {
                            Text(
                                "Estoque",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Conta") },
                        label = {
                            Text(
                                "Conta",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Parte superior (cabeçalho)
            ParteSuperiorInicio()

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de busca
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        "Buscar...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card de Gerenciar Estoque
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = tomBege),
                shape = RoundedCornerShape(16.dp)
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
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text(
                            "Acessar",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Seção Ferramentas
            Text(
                text = "Ferramentas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
            )

            // Grade de ferramentas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botão Estoque
                FerramentaItem(
                    titulo = "Estoque",
                    corFundo = tomVinho,
                    corTexto = Color.White,
                    onClick = {navController.navigate("estoque")}
                )

                // Botão Cardápio
                FerramentaItem(
                    titulo = "Cardápio",
                    corFundo = tomBege,
                    corTexto = Color(0xFF343434),
                    onClick = {navController.navigate("cardapio")}
                )

                // Botão Baixas
                FerramentaItem(
                    titulo = "Baixas",
                    corFundo = tomVinho,
                    corTexto = Color.White,
                    onClick = {navController.navigate("baixas")}
                )
            }

            // Botão Ver mais
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
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
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(tomVinho)
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
    }
}

@Composable
fun ParteSuperiorInicio() {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FerramentaItem(
    titulo: String,
    corFundo: Color,
    corTexto: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(100.dp),
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
            // Ícone específico para cada ferramenta
            when (titulo) {
                "Estoque" -> Icon(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = titulo,
                    tint = corTexto,
                    modifier = Modifier.size(24.dp)
                )
                "Cardápio" -> Icon(
                    painter = painterResource(id = R.drawable.scroll),
                    contentDescription = titulo,
                    tint = corTexto,
                    modifier = Modifier.size(24.dp)
                )
                "Baixas" -> Icon(
                    painter = painterResource(id = R.drawable.store),
                    contentDescription = titulo,
                    tint = corTexto,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,
                color = corTexto,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaInicioPreview() {
    TelaInicio(rememberNavController())
}