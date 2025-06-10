package com.example.terabitemobile.ui.screens

import android.R.attr.shape
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.terabitemobile.R
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.classes.CardapioPost
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.MarcaModel
import com.example.terabitemobile.data.models.SubtipoModel
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.fundoCinza
import com.example.terabitemobile.ui.theme.tomBege
import com.example.terabitemobile.ui.theme.tomVinho
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCardapio(paddingBottom: PaddingValues, viewModel: CardapioModel = koinViewModel()) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<CardapioItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }

    val produtos by viewModel.produtos.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState("")

    // Efeito para carregar dados quando a composição é criada
    LaunchedEffect(key1 = Unit) {
        viewModel.carregarProdutos()
    }

    Scaffold(
        containerColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingBottom)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileCardapio(
                onAddClick = {
                    selectedProduct = null
                    showBottomSheet = true
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.any_menu_txt), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = tomVinho)
                }
            } else if (error.isNotEmpty()) {
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
                            onClick = { viewModel.carregarProdutos() },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(stringResource(R.string.error_tryAgain_label))
                        }
                    }
                }
            } else {
                produtos?.let {
                    ListaProdutosCardapio(
                        tomBege = tomBege,
                        tomVinho = tomVinho,
                        fundoCinza = fundoCinza,
                        searchText = searchText,
                        produtos = it,
                        onEditClick = { produto ->
                            selectedProduct = produto
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
                EditBottomSheetContent(
                    product = selectedProduct,
                    onClose = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showBottomSheet = false
                        }
                    },
                    tomVinho = tomVinho,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private fun ProfileCardapio(
    onAddClick: () -> Unit
) {
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
                    stringResource(R.string.any_role_txt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            modifier = Modifier
                .width(145.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.any_addItem_txt),
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )

            Text(
                text = stringResource(R.string.any_addItem_txt),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
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
        placeholder = { Text(stringResource(R.string.any_searchField_placeholder), style = MaterialTheme.typography.bodyMedium) },
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

@SuppressLint("DefaultLocale")
@Composable
fun ListaProdutosCardapio(
    tomBege: Color,
    tomVinho: Color,
    fundoCinza: Color,
    searchText: String,
    produtos: List<CardapioItem>,
    onEditClick: (CardapioItem) -> Unit
) {

    val produtosFiltrados = produtos.filter {
        it.nome.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 5.dp)
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
                            stringResource(R.string.menu_noItems_txt)
                        } else {
                            stringResource(R.string.menu_itemsNotFound_txt, searchText)
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
                                .size(55.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (produto.imagemUrl != null) {
                                AsyncImage(
                                    model = produto.imagemUrl,
                                    contentDescription = "foto do produto",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
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
                        Column(
                            modifier = Modifier
                                .weight(2f)
                        ) {
                            Text(
                                text = produto.nome,
                                color = Color.White,
                                fontSize = 16.5.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "R$" + String.format("%.2f", produto.preco),
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { onEditClick(produto) },
                                colors = ButtonDefaults.buttonColors(containerColor = tomBege),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            ) {
                                Text(stringResource(R.string.menu_editBtn_label), color = tomVinho, softWrap = false)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBottomSheetContent(
    product: CardapioItem?,
    onClose: () -> Unit,
    tomVinho: Color,
    viewModel: CardapioModel = koinViewModel()
) {
    val focusManager = LocalFocusManager.current
    val subtipoViewModel: SubtipoModel = koinViewModel()
    val marcasViewModel: MarcaModel = koinViewModel()

    var editedId by remember { mutableStateOf(product?.id) }
    var editedName by remember { mutableStateOf(product?.nome ?: "") }
    var editedMarca by remember { mutableStateOf(product?.nomeMarca ?: "") }
    var editedTipo by remember { mutableStateOf(product?.tipo ?: "") }
    var editedSubtipo by remember { mutableStateOf(product?.nomeSubtipo ?: "") }
    var editedPreco by remember { mutableStateOf(product?.preco?.toString() ?: "") }
    var editedQtdPorCaixa by remember { mutableStateOf(product?.qtdPorCaixas?.toString() ?: "") }
    var editedLactose by remember { mutableStateOf(product?.temLactose == true) }
    var editedGluten by remember { mutableStateOf(product?.temGluten == true) }

    var showValidationErrors by remember { mutableStateOf(false) }

    val nameError = editedName.isEmpty() && showValidationErrors
    val marcaError = editedMarca.isEmpty() && showValidationErrors
    val subtipoError = editedSubtipo.isEmpty() && showValidationErrors
    val precoError =
        (editedPreco.isEmpty() || editedPreco.toDoubleOrNull() == null || editedPreco.toDoubleOrNull()!! <= 0) && showValidationErrors
    val qtdPorCaixaError =
        (editedQtdPorCaixa.isEmpty() || editedQtdPorCaixa.toIntOrNull() == null || editedQtdPorCaixa.toIntOrNull()!! <= 0) && showValidationErrors

    val isFormValid = editedName.isNotEmpty() &&
            editedMarca.isNotEmpty() &&
            editedSubtipo.isNotEmpty() &&
            editedPreco.isNotEmpty() && editedPreco.toDoubleOrNull() != null && editedPreco.toDoubleOrNull()!! > 0 &&
            editedQtdPorCaixa.isNotEmpty() && editedQtdPorCaixa.toIntOrNull() != null && editedQtdPorCaixa.toIntOrNull()!! > 0

    var showSubtipoSelector by remember { mutableStateOf(false) }
    var showMarcasSelector by remember { mutableStateOf(false) }

    val subtipos by subtipoViewModel.subtipos.observeAsState()

    val marcas by marcasViewModel.marcas.observeAsState()

    LaunchedEffect(key1 = Unit) {
        subtipoViewModel.carregarSubtipos()
        marcasViewModel.carregarMarcas()
    }

    val subtiposList = remember(subtipos) {
        subtipos?.sortedBy { it.nome } ?: emptyList()
    }

    val marcasList = remember(marcas) {
        marcas?.sortedBy { it.nome } ?: emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .fillMaxHeight(0.9f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (product != null) stringResource(R.string.edit_product_title, product.nome) else stringResource(R.string.add_new_product_title),
                style = MaterialTheme.typography.headlineSmall,
                color = tomVinho,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.basic_info_section), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = editedName,
            onValueChange = { editedName = it },
            label = { Text(stringResource(R.string.name_label)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text(stringResource(R.string.name_required_error), color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedLabelColor = tomVinho,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )

        OutlinedTextField(
            value = editedMarca,
            onValueChange = {},
            label = { Text(stringResource(R.string.brand_label)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showMarcasSelector = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Selecionar Marca")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = marcaError,
            supportingText = {
                if (marcaError) {
                    Text(stringResource(R.string.brand_required_error), color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedLabelColor = tomVinho,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )

        if (showMarcasSelector) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { showMarcasSelector = false },
                title = { Text(stringResource(R.string.select_brand_dialog_title)) },
                text = {
                    LazyColumn(
                        modifier = Modifier.height(350.dp)
                    ) {
                        items(marcasList) { marca ->
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = tomBege,
                                    contentColor = Color(0xFF343434)
                                ),
                                onClick = {
                                    editedMarca = marca.nome
                                    showMarcasSelector = false
                                }) {
                                Text(marca.nome)
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = tomVinho,
                            contentColor = Color.White
                        ),
                        onClick = { showMarcasSelector = false }
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_cancel_button),
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(R.string.categorization_section), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
            value = editedTipo,
            onValueChange = { /* Não modificável */ },
            label = { Text(stringResource(R.string.type_label)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedLabelColor = tomVinho,
                disabledBorderColor = Color.LightGray,
                disabledTextColor = Color.DarkGray
            ),
            enabled = false
        )

        OutlinedTextField(
            value = editedSubtipo,
            onValueChange = { /* No direct input, read-only */ },
            label = { Text(stringResource(R.string.subtype_label)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showSubtipoSelector = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Selecionar Subtipo")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = subtipoError,
            supportingText = {
                if (subtipoError) {
                    Text(stringResource(R.string.subtype_required_error), color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedLabelColor = tomVinho,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )

        if (showSubtipoSelector) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { showSubtipoSelector = false },
                title = { Text(stringResource(R.string.select_subtype_dialog_title)) },
                text = {
                    LazyColumn(
                        modifier = Modifier.height(350.dp)
                    ) {
                        items(subtiposList) { subtipo ->
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = tomBege,
                                    contentColor = Color(0xFF343434)
                                ),
                                onClick = {
                                    editedSubtipo = subtipo.nome
                                    editedTipo = subtipo.tipo.nome
                                    showSubtipoSelector = false
                                }) {
                                Text(subtipo.nome)
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = tomVinho,
                            contentColor = Color.White
                        ),
                        onClick = { showSubtipoSelector = false }
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_cancel_button),
                            Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(R.string.stock_price_section), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        NumberTextField(
            value = editedQtdPorCaixa,
            onValueChange = { editedQtdPorCaixa = it },
            label = stringResource(R.string.units_per_box_label),
            modifier = Modifier.fillMaxWidth(),
            isError = qtdPorCaixaError,
            errorMessage = if (qtdPorCaixaError) stringResource(R.string.units_per_box_error) else null
        )

        OutlinedTextField(
            value = editedPreco,
            onValueChange = { editedPreco = it },
            label = { Text(stringResource(R.string.price_label)) },
            prefix = { Text("R$") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = precoError,
            supportingText = {
                if (precoError) {
                    Text(
                        stringResource(R.string.price_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedLabelColor = tomVinho,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            )
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(R.string.status_restrictions_section), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = editedLactose,
                onCheckedChange = { editedLactose = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = tomVinho,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(stringResource(R.string.contains_lactose))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = editedGluten,
                onCheckedChange = { editedGluten = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = tomVinho,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(stringResource(R.string.contains_gluten))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.required_fields_note),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                showValidationErrors = true

                if (isFormValid) {
                    if (product == null) {
                        val newProduct = CardapioPost(
                            id = 0,
                            nome = editedName,
                            nomeMarca = editedMarca,
                            nomeSubtipo = editedSubtipo,
                            preco = editedPreco.toDoubleOrNull() ?: 0.0,
                            qtdPorCaixas = editedQtdPorCaixa.toIntOrNull() ?: 0,
                            temLactose = editedLactose,
                            temGluten = editedGluten
                        )
                        viewModel.adicionarProduto(newProduct)
                        viewModel.carregarProdutos()
                    } else {
                        val updatedProduct = editedId?.let {
                            CardapioPost(
                                id = it,
                                nome = editedName,
                                nomeMarca = editedMarca,
                                nomeSubtipo = editedSubtipo,
                                preco = editedPreco.toDoubleOrNull() ?: 0.0,
                                qtdPorCaixas = editedQtdPorCaixa.toIntOrNull() ?: 0,
                                temLactose = editedLactose,
                                temGluten = editedGluten
                            )
                        }

                        if (updatedProduct != null) {
                            viewModel.atualizarProduto(updatedProduct)
                        }
                    }
                    onClose()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
        ) {
            Text(stringResource(R.string.save_changes_button), color = Color.White)
        }

        TextButton(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cancel_button), color = Color.Gray)
        }
    }
}

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        shape = RoundedCornerShape(16.dp),
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = tomVinho,
            unfocusedBorderColor = Color.Gray,
            cursorColor = tomVinho,
            focusedLabelColor = tomVinho,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error
        ),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier,
        isError = isError,
        supportingText = {
            if (isError && errorMessage != null) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TelaCardapioPreview() {
}
