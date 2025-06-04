package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.terabitemobile.R
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.classes.RecomendacaoItem
import com.example.terabitemobile.data.models.RecomendacaoModel
import com.example.terabitemobile.ui.theme.background
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaRecomendacao(
    paddingValores: PaddingValues,
    recomendacaoViewModel: RecomendacaoModel,
    produtosViewModel: CardapioModel
) {
    var searchText by remember { mutableStateOf("") }

    val recomendacoes by recomendacaoViewModel.recomendacoes.observeAsState()
    val isLoading by recomendacaoViewModel.isLoading.observeAsState(initial = false)
    val error by recomendacaoViewModel.error.observeAsState("")

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedRecomendacao by remember { mutableStateOf<RecomendacaoItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val produtos by produtosViewModel.produtos.observeAsState()

    val filteredRecomendacoes = remember(recomendacoes, searchText) {
        recomendacoes?.filter { it.produto.nome.contains(searchText, ignoreCase = true) }
            ?: emptyList()
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
            Text(
                text = stringResource(R.string.recommendations_title),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = tomVinho)
                }
            } else if (error.isNotEmpty()) {
                // Mostra mensagem de erro se houver falha
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(R.string.error_loadData_txt),
                            color = tomVinho,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(error, color = Color.Gray, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { recomendacaoViewModel.carregarRecomendacoes() },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(stringResource(R.string.error_tryAgain_label))
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(5.dp)
                        .weight(1f)
                ) {
                    items(filteredRecomendacoes) { recomendacao ->
                        RecomendacaoListItem(
                            recomendacao = recomendacao,
                            onEditClick = {
                                selectedRecomendacao = recomendacao
                                showBottomSheet = true
                            }
                        )
                    }
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
                    viewModel = recomendacaoViewModel
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
                contentDescription = stringResource(R.string.accessibility_userProfile_img),
                tint = tomVinho,
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = stringResource(R.string.user_name_placeholder),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(R.string.any_role_txt),
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
        placeholder = { Text(stringResource(R.string.any_searchField_placeholder)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon_desc)
            )
        },
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
    recomendacao: RecomendacaoItem,
    onEditClick: (RecomendacaoItem) -> Unit
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
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(2.dp, tomVinho, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (recomendacao.produto.urlImagem != null) {
                    AsyncImage(
                        model = "https://www.pudim.com.br/pudim.jpg",
                        contentDescription = "foto do produto",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ice_cream),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recomendacao.produto.nome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = recomendacao.produto.nomeMarca,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = { onEditClick(recomendacao) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_icon_desc),
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
    val errorTemplate = stringResource(R.string.error_update_recommendation)
    val defaultError = stringResource(R.string.error_generic)

    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedProduct by remember(recomendacao?.id) {
        mutableStateOf(recomendacao?.produto)
    }
    var searchQuery by remember { mutableStateOf("") }

    val filteredProducts = remember(searchQuery, produtos) {
        if (searchQuery.isEmpty()) produtos else produtos.filter {
            it.nome.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.edit_recommendation_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon_desc),
                    tint = tomVinho
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.5.dp, tomVinho, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedProduct!!.urlImagem != null) {
                        AsyncImage(
                            model = "https://www.pudim.com.br/pudim.jpg",
                            contentDescription = "foto do produto",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ice_cream),
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                        )
                    }
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

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(R.string.search_product_label)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
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

        Spacer(modifier = Modifier.height(8.dp))

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
                            val errorMsg = e.message ?: defaultError
                            val fullMessage = errorTemplate.replace("%s", errorMsg)
                            snackbarHostState.showSnackbar(fullMessage)
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
                Text(stringResource(R.string.save_changes_button))
            }
        }

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
                    contentDescription = stringResource(R.string.success_icon_desc),
                    tint = Color.Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.success_update_recommendation),
                    color = Color.Green
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }
}