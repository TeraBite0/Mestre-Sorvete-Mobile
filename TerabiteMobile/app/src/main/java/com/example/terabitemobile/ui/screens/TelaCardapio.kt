package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.terabitemobile.R
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomVinho
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.terabitemobile.ui.models.CardapioModel
import com.example.terabitemobile.ui.models.cardapioItem

@Composable
fun TelaCardapio(viewModel: CardapioModel = viewModel()) {
    val fundoCinza = Color(0xFFD1D1D1)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)
    var searchText by remember { mutableStateOf("") }

    val produtos by viewModel.produtos.observeAsState(emptyList())

    Scaffold(
        bottomBar = {
            /*
                Não faço ideia porque mas tirar essa bottom bar aqui desativa o scroll os
                itens do cardapio (macabro)
             */
            BottomNavigationBarCardapio()
        },
        containerColor = background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ProfileHeader(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text("Cardápio", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))
            ListaProdutosCardapio(
                tomBege = tomBege,
                tomVinho = tomVinho,
                fundoCinza = fundoCinza,
                searchText = searchText,
                produtos = produtos,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ProfileHeader(viewModel: CardapioModel) {
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

        Button(
            onClick = {
                viewModel.adicionarProduto(
                    cardapioItem(
                        id = (viewModel.produtos.value?.size ?: 0) + 1,
                        nome = "Novo Sorvete",
                        marca = "Nova Marca",
                        tipo = "Sorvete",
                        subtipo = "Cremoso",
                        preco = "R$19,99",
                        qtdCaixa = 10,
                        qtdPorCaixa = 20,
                        ativo = true,
                        temLactose = false,
                        temGluten = false
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            modifier = Modifier
                .width(145.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )

            Text(
                text = "Adicionar",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun CampoBusca(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text("Buscar...", style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = tomVinho,
            unfocusedBorderColor = Color.Gray,
            cursorColor = tomVinho,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

data class Produto(
    val id: Int,
    val nome: String,
    val preco: String,
    val ativo: Boolean
)

@Composable
fun ListaProdutosCardapio(
    tomBege: Color,
    tomVinho: Color,
    fundoCinza: Color,
    searchText: String,
    produtos: List<cardapioItem>,
    viewModel: CardapioModel
) {

    val produtosFiltrados = produtos.filter {
        it.nome.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (produtosFiltrados.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isEmpty()) {
                            "Nenhum produto cadastrado"
                        } else {
                            "Nenhum produto encontrado para \"$searchText\""
                        },
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(
                items = produtosFiltrados,
                key = { produto -> produto.id }
            ) { produto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = tomVinho),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(fundoCinza, shape = RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = produto.nome,
                                color = Color.White,
                                fontSize = 16.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = produto.preco, color = Color.White, fontSize = 15.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = tomBege),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(90.dp)
                            ) {
                                Text("Editar", color = tomVinho)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BottomNavigationBarCardapio() {
    var itemSelecionado by remember { mutableIntStateOf(0) }
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
fun TelaCardapioPreview() {
    TelaCardapio()
}
