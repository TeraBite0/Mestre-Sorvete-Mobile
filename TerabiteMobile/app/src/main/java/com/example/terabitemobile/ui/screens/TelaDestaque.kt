package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.classes.DestaqueItem
import com.example.terabitemobile.data.models.DestaqueModel
import com.example.terabitemobile.ui.theme.background
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.terabitemobile.ui.theme.fundoCinza

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaDestaque(paddingValores: PaddingValues, destaqueViewModel: DestaqueModel, produtosViewModel: CardapioModel) {

    val destaque by destaqueViewModel.destaque.observeAsState()
    val isLoading by destaqueViewModel.isLoading.observeAsState(initial = false)
    val error by destaqueViewModel.error.observeAsState("")

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDestaque by remember { mutableStateOf<DestaqueItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val produtos by produtosViewModel.produtos.observeAsState()

    Scaffold(
        containerColor = background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileDestaque()
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.destaque_title), fontWeight = FontWeight.Bold, fontSize = 22.sp)
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
                            onClick = { destaqueViewModel.carregarDestaque() },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(stringResource(R.string.error_tryAgain_label))
                        }
                    }
                }
            }
            else {
                destaque?.let { item ->
                    DestaqueListItem(
                        destaque = item,
                        onEditClick = {
                            selectedDestaque = item
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
                    destaque = selectedDestaque,
                    produtos = produtos ?: emptyList(),
                    onClose = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showBottomSheet = false
                        }
                    },
                    tomVinho = tomVinho,
                    viewModel = destaqueViewModel
                )
            }
        }
    }
}

@Composable
private fun ProfileDestaque() {
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
                    stringResource(R.string.user_name_placeholder),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    stringResource(R.string.any_role_txt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun DestaqueListItem(
    destaque: DestaqueItem, onEditClick: (DestaqueItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column (modifier = Modifier.padding(12.dp)){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                        .border(1.5.dp, tomVinho, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
//                Text("470×470", fontSize = 10.sp, color = Color.DarkGray)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(2f)) {

                    Row { Text(text = destaque.produto.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .background(fundoCinza, RoundedCornerShape(8.dp))
                        .padding(4.dp))
                    { Text(text = stringResource(R.string.destaque_brand_label) + destaque.produto.nomeMarca, fontSize = 12.sp) }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .background(fundoCinza, RoundedCornerShape(8.dp))
                        .padding(5.dp))
                    { Text(text = stringResource(R.string.destaque_type_label) + destaque.produto.tipo, fontSize = 12.sp) }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp, max = 150.dp)
                    .verticalScroll(rememberScrollState())
                    .background(fundoCinza, RoundedCornerShape(8.dp))
                    .padding(5.dp)
            ) {
                Text(text = destaque.texto, fontSize = 12.sp)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { onEditClick(destaque) },
        colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.destaque_edit_button), color = Color.White)
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(R.string.edit_icon_desc),
            tint = Color.White,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(16.dp)
        )
    }
}

@Composable
fun BottomSheetContent(
    destaque: DestaqueItem?,
    produtos: List<CardapioItem>,
    onClose: () -> Unit,
    tomVinho: Color,
    viewModel: DestaqueModel
) {
    val errorTemplate = stringResource(R.string.error_update_recommendation)
    val defaultError = stringResource(R.string.error_generic)

    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedProduct by remember(destaque?.id) {
        mutableStateOf(destaque?.produto)
    }
//    var searchQuery by remember { mutableStateOf(destaque?.produto?.nome ?: "") }
    var searchQuery by remember { mutableStateOf("") }
    var editedText by remember { mutableStateOf(destaque?.texto ?: "") }

    val filteredProducts = produtos.filter {
        it.nome.contains(searchQuery, ignoreCase = true)
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
                text = stringResource(R.string.destaque_edit_title),
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
            label = { Text(stringResource(R.string.destaque_search_label)) },
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

        // CAMPO DE TEXTO LIVRE
        OutlinedTextField(
            value = editedText,
            onValueChange = { editedText = it },
            label = { Text(stringResource(R.string.destaque_text_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 12.dp),
            maxLines = 6,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray
            )
        )

        // BOTÃO DE SALVAR
        Button(
            onClick = {
                if (selectedProduct != null) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            viewModel.updateDestaque(
                                produtoId = selectedProduct!!.id,
                                texto = editedText
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
                Text(stringResource(R.string.save_changes_button), fontSize = 16.sp)
            }
        }

        // MENSAGEM DE SUCESSO
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
                Text(stringResource(R.string.destaque_success_message), color = Color.Green)
            }
        }

        // SNACKBAR DE ERRO
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }
}