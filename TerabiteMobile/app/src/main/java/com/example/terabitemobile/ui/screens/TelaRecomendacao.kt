package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun TelaRecomendacao() {
    val fundoCinza = Color(0xFFD1D1D1)
    val tomVinho = Color(0xFFA73E2B)
    val tomBranco = Color.White

    Scaffold(
        bottomBar = {
            BottomNavigationBarRecomendacao()
        },
        containerColor = fundoCinza
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ParteSuperiorRecomendacao()
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca()
            Spacer(modifier = Modifier.height(14.dp))
            Text("Recomendados", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))
            Spacer(modifier = Modifier.weight(1f))
            ListaProdutosRecomendacao(tomBranco, tomVinho)
        }
    }
}

@Composable
fun ParteSuperiorRecomendacao() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
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

@Composable
fun CampoBusca() {
    var searchText by remember { mutableStateOf("") }
    // Campo de busca
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Buscar...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}

@Composable
fun ListaProdutosRecomendacao(tomBranco: Color, tomVinho: Color) {
    val produtos = listOf(
        Pair("Produto", "Marca"),
        Pair("Produto", "Marca")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        produtos.forEach { (nome, marca) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = tomBranco),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = nome, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = marca, color = Color.Gray, fontSize = 16.sp)
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Remover",
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .background(tomVinho, shape = RoundedCornerShape(12.dp))
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("+ Adicionar outro Produto", color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
//        Image(
//            painter = painterResource(id = R.drawable.onda_imagem),
//            contentDescription = "Onda decorativa vinho",
//            modifier = Modifier
//                .fillMaxWidth()
//                .zIndex(1f)
//                .height(220.dp),
//            contentScale = ContentScale.FillBounds
//        )
    }
}

@Composable
fun BottomNavigationBarRecomendacao() {
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

@Preview(showBackground = true)
@Composable
fun TelaRecomendacaoPreview() {
    TelaRecomendacao()
}