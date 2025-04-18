package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.terabitemobile.ui.theme.tomVinho
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.CardapioItem
import com.example.terabitemobile.data.models.RecomendacaoItem
import com.example.terabitemobile.data.models.RecomendacaoModel
import com.example.terabitemobile.ui.theme.background
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaRecomendacao(paddingValores: PaddingValues, RecomendacaoViewModel: RecomendacaoModel, ProdutosViewModel: CardapioModel) {

    var searchText by remember { mutableStateOf("") }
    val recomendacoes by RecomendacaoViewModel.recomendacoes.observeAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedRecomendacao by remember { mutableStateOf<RecomendacaoItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val produtos by ProdutosViewModel.produtos.observeAsState()

    val filteredRecomendacoes = remember(recomendacoes, searchText) {
        recomendacoes?.filter { it.produto.nome.contains(searchText, ignoreCase = true) } ?: emptyList()
    }

    Scaffold(
        containerColor = background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileRecomendacoes()
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Recomendados", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .height(5.dp)
                    .weight(1f)
            ) {
                items(filteredRecomendacoes) { recomendacao ->
                    RecomendacaoListItem(
                        recomendacao = recomendacao,
                        onEditClick = { recomendacao ->
                            selectedRecomendacao = recomendacao
                            showBottomSheet = true
                        }
                    )
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    recomendacao = selectedRecomendacao,
                    produtos = produtos ?: emptyList(),
                    onClose = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showBottomSheet = false
                        }
                    },
                    tomVinho = tomVinho,
                    viewModel = RecomendacaoViewModel
                )
            }
        }
    }
}

@Composable
private fun ProfileRecomendacoes() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Usuário",
                tint = tomVinho,
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.width(8.dp))
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
private fun CampoBusca(
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
            .background(Color.Transparent, RoundedCornerShape(16.dp)),
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


@Composable
private fun RecomendacaoListItem(
    recomendacao: RecomendacaoItem, onEditClick: (RecomendacaoItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                    .border(1.5.dp, tomVinho, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
//                Text("470×470", fontSize = 10.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = recomendacao.produto.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = recomendacao.produto.nomeMarca, color = Color.Gray, fontSize = 12.sp)
            }

            IconButton(
                onClick = { onEditClick(recomendacao) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar    ",
                    tint = Color.White,
                    modifier = Modifier
                        .background(tomVinho, shape = RoundedCornerShape(12.dp))
                        .padding(5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    recomendacao: RecomendacaoItem?,
    produtos: List<CardapioItem>,
    onClose: () -> Unit,
    tomVinho: Color,
    viewModel: RecomendacaoModel
) {
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedProduct by remember(recomendacao?.id) {
        mutableStateOf(recomendacao?.produto)
    }
    var searchQuery by remember { mutableStateOf("") }

    val filteredProducts = remember(searchQuery, produtos) {
        if (searchQuery.isEmpty()) {
            produtos
        } else {
            produtos.filter { it.nome.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Editar Recomendação",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = tomVinho
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Produto selecionado
        selectedProduct?.let { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                        .border(1.5.dp, tomVinho, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Imagem do produto
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = product.nome,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = product.nomeMarca,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de busca
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar produto") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de produtos com scroll
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(filteredProducts) { product ->
                    Card(
                        onClick = { selectedProduct = product },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (product == selectedProduct)
                                Color.LightGray.copy(alpha = 0.3f)
                            else
                                Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = product.nome,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = product.nomeMarca,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Botão fixo na parte inferior
        Button(
            onClick = {
                if (recomendacao != null && selectedProduct != null) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            viewModel.updateRecomendacao(
                                idRecomendacao = recomendacao.id,
                                produtoId = selectedProduct!!.id
                            )
                            showSuccessMessage = true
                            delay(1500)
                            onClose()
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Erro ao atualizar: ${e.message}")
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            shape = RoundedCornerShape(12.dp),
            enabled = selectedProduct != null && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Salvar Alterações", fontSize = 16.sp)
            }
        }

        // Feedback de sucesso
        AnimatedVisibility(visible = showSuccessMessage) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Sucesso",
                    tint = Color.Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recomendação atualizada com sucesso!", color = Color.Green)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }
}