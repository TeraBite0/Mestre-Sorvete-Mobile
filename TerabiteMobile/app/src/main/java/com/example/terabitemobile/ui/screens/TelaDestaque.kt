package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.zIndex
import com.example.terabitemobile.R
import com.example.terabitemobile.ui.theme.tomVinho

@Composable
fun TelaDestaque() {
    val fundoCinza = Color(0xFFD1D1D1)
    val tomVinho = Color(0xFFA73E2B)
    val tomBranco = Color.White

    Scaffold(
        bottomBar = {
            BottomNavigationBarDestaque()
        },
        containerColor = fundoCinza
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ParteSuperiorDestaque()
            Spacer(modifier = Modifier.height(16.dp))
            CampoBuscaDestaque()
            Spacer(modifier = Modifier.height(14.dp))
            Text("Destaque", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(28.dp))
            Spacer(modifier = Modifier.weight(1f))
            ListaProdutosDestaque(tomBranco, tomVinho, fundoCinza)
        }
    }
}

@Composable
fun ParteSuperiorDestaque() {
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
                Text("Josué", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text("Administrador", fontSize = 16.sp, color = Color.Gray)
            }
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C3829)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Adicionar +", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoBuscaDestaque() {
    var searchText by remember { mutableStateOf("") }
    // Campo de busca
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Buscar...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        )
    )
}


@Composable
fun ListaProdutosDestaque(tomBranco: Color, tomVinho: Color, fundoCinza: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // altura aumentada
            colors = CardDefaults.cardColors(containerColor = tomBranco),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(2.dp, tomVinho, RoundedCornerShape(12.dp)) // borda vermelha
                            .background(Color.LightGray, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("470 × 470", color = Color.Gray, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier
                            .background(fundoCinza, RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        InfoItemComFundo("Nome:", "Nescolak", fundoCinza)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoItemComFundo("Marca:", "Senhor Sorvete", fundoCinza)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoItemComFundo("Categoria:", "Lorem", fundoCinza)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Descrição....",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* ação */ },
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text("Alterar Destaque", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
fun BottomNavigationBarDestaque() {
    var itemSelecionado by remember { mutableStateOf(0) }
    val items = listOf(
        "Início" to Icons.Filled.Home,
        "Cardápio" to Icons.Filled.List,
        "Estoque" to Icons.Filled.ShoppingCart,
        "Conta" to Icons.Filled.Person
    )

    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = itemSelecionado == index,
                onClick = { itemSelecionado = index },
                icon = { Icon(item.second, contentDescription = item.first) },
                label = { Text(item.first) }
            )
        }
    }
}

@Composable
fun InfoItemComFundo(label: String, value: String, fundoCinza: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(fundoCinza, RoundedCornerShape(16.dp))
            .padding(6.dp)
    ) {
        Row {
            Text(
                label,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                value,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TelaDestaquePreview() {
    TelaDestaque()
}