package com.example.terabitemobile.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TelaProduto() {
    val fundoCinza = Color(0xFFD1D1D1)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)

    Scaffold(
        bottomBar = { BottomNavigationBar() },
        containerColor = fundoCinza
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ParteSuperior()
            Spacer(modifier = Modifier.height(16.dp))
            CamposData()
            Spacer(modifier = Modifier.height(14.dp))
            Text("Baixas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))
            Spacer(modifier = Modifier.weight(1f))
            ListaProdutos(tomBege, tomVinho)
        }
    }
}

@Composable
fun ParteSuperior() {
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

@Composable
fun CamposData() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(2) {
            Card(
                modifier = Modifier.weight(1f).padding(8.dp),
//                backgroundColor = Color.White,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Calendário",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("DD/MM/AAAA", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ListaProdutos(tomBege: Color, tomVinho: Color) {
    Column {
        repeat(3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(2) {
                    Card(
                        modifier = Modifier.size(170.dp),
                        //backgroundColor = tomBege,
                        colors = CardDefaults.cardColors(containerColor = tomBege),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        Card(
                                modifier = Modifier.width(90.dp),
//                                backgroundColor = tomVinho,
                            colors = CardDefaults.cardColors(containerColor = tomVinho),
                                shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                "DD/MM",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
                            )
                        }
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = tomVinho),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Info, contentDescription = "Quantidade", tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("-5", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = tomVinho),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Star, contentDescription = "Produto", tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Produto", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BottomNavigationBar() {
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
fun TelaProdutoPreview() {
    TelaProduto()
}
