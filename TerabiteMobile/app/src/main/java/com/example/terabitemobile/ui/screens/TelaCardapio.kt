package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomVinho
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.terabitemobile.data.models.*
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import com.example.terabitemobile.data.models.CardapioItem
import com.example.terabitemobile.data.models.MarcaModel
import com.example.terabitemobile.data.models.SubtipoModel
import com.example.terabitemobile.ui.theme.tomBege

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCardapio(viewModel: CardapioModel = viewModel()) {
    val fundoCinza = Color(0xFFD1D1D1)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<CardapioItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }

    // Observa os estados do ViewModel
    val produtos by viewModel.produtos.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState("")

    // Efeito para carregar dados quando a composição é criada
    LaunchedEffect(key1 = Unit) {
        viewModel.carregarProdutos()
    }

    Scaffold(
        bottomBar = {
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
            Text("Cardápio", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

            // Mostra indicador de carregamento quando estiver buscando dados
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
                            "Falha ao carregar dados",
                            color = Color.Red,
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
                            Text("Tentar novamente")
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
                        viewModel = viewModel,
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
                    "Administrador",
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

@SuppressLint("DefaultLocale")
@Composable
fun ListaProdutosCardapio(
    tomBege: Color,
    tomVinho: Color,
    fundoCinza: Color,
    searchText: String,
    produtos: List<CardapioItem>,
    viewModel: CardapioModel,
    onEditClick: (CardapioItem) -> Unit
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
                                Text("Editar", color = tomVinho, softWrap = false)
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
        "Cardápio" to Icons.AutoMirrored.Filled.List,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBottomSheetContent(
    product: CardapioItem?,
    onClose: () -> Unit,
    tomVinho: Color,
    viewModel: CardapioModel
) {
    val focusManager = LocalFocusManager.current
    val subtipoViewModel: SubtipoModel = viewModel()
    val marcasViewModel: MarcaModel = viewModel()
    val scope = rememberCoroutineScope()

    val isEditMode = product != null

    // Estados para todos os campos editáveis
    var editedName by remember { mutableStateOf(product?.nome ?: "") }
    var editedMarca by remember { mutableStateOf(product?.nomeMarca ?: "") }
    var editedTipo by remember { mutableStateOf(product?.tipo ?: "") }
    var editedSubtipo by remember { mutableStateOf(product?.nomeSubtipo ?: "") }
    var editedPreco by remember { mutableStateOf(product?.preco?.toString() ?: "") }
    var editedQtdCaixa by remember { mutableStateOf(product?.qtdCaixa?.toString() ?: "") }
    var editedQtdPorCaixa by remember { mutableStateOf(product?.qtdPorCaixas?.toString() ?: "") }
    var editedAtivo by remember { mutableStateOf(product?.ativo ?: true) }
    var editedLactose by remember { mutableStateOf(product?.temLactose ?: false) }
    var editedGluten by remember { mutableStateOf(product?.temGluten ?: false) }

    // Estado para controlar quando a validação deve ser exibida
    var showValidationErrors by remember { mutableStateOf(false) }

    // Estados para validação de campos
    val nameError = editedName.isEmpty() && showValidationErrors
    val marcaError = editedMarca.isEmpty() && showValidationErrors
    val subtipoError = editedSubtipo.isEmpty() && showValidationErrors
    val precoError =
        (editedPreco.isEmpty() || editedPreco.toDoubleOrNull() == null || editedPreco.toDoubleOrNull()!! <= 0) && showValidationErrors
    val qtdPorCaixaError =
        (editedQtdPorCaixa.isEmpty() || editedQtdPorCaixa.toIntOrNull() == null || editedQtdPorCaixa.toIntOrNull()!! <= 0) && showValidationErrors

    // Verifica se o formulário é válido
    val isFormValid = editedName.isNotEmpty() &&
            editedMarca.isNotEmpty() &&
            editedSubtipo.isNotEmpty() &&
            editedPreco.isNotEmpty() && editedPreco.toDoubleOrNull() != null && editedPreco.toDoubleOrNull()!! > 0 &&
            editedQtdPorCaixa.isNotEmpty() && editedQtdPorCaixa.toIntOrNull() != null && editedQtdPorCaixa.toIntOrNull()!! > 0

    var showSubtipoSelector by remember { mutableStateOf(false) }
    var showMarcasSelector by remember { mutableStateOf(false) }

    // Observar os subtipos do ViewModel
    val subtipos by subtipoViewModel.subtipos.observeAsState()
    val subtiposLoading by subtipoViewModel.isLoading.observeAsState(initial = false)
    val subtiposError by subtipoViewModel.error.observeAsState("")

    val marcas by marcasViewModel.marcas.observeAsState()
    val marcasLoading by marcasViewModel.isLoading.observeAsState(initial = false)
    val marcasError by marcasViewModel.error.observeAsState("")

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
                if (product != null) "Editar ${product.nome}" else "Adicionar Novo Produto",
                style = MaterialTheme.typography.headlineSmall,
                color = tomVinho,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Informações Básicas", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = editedName,
            onValueChange = { editedName = it },
            label = { Text("Nome*") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text("Nome é obrigatório", color = MaterialTheme.colorScheme.error)
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
            onValueChange = { /* No direct input, read-only */ },
            label = { Text("Marca*") },
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
                    Text("Marca é obrigatória", color = MaterialTheme.colorScheme.error)
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
                onDismissRequest = { showSubtipoSelector = false },
                title = { Text("Selecionar Subtipo") },
                text = {
                    LazyColumn(
                        modifier = Modifier.height(350.dp)
                    ) {
                        items(marcasList) { marca ->
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                            text = "Cancelar",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            )
        }

        // Seção de Tipo e Subtipo
        Spacer(modifier = Modifier.height(10.dp))
        Text("Categorização", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        // Campo de Tipo (somente leitura)
        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
            value = editedTipo,
            onValueChange = { /* Não modificável */ },
            label = { Text("Tipo") },
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

        // Campo de Subtipo (com diálogo de seleção)
        OutlinedTextField(
            value = editedSubtipo,
            onValueChange = { /* No direct input, read-only */ },
            label = { Text("Subtipo*") },
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
                    Text("Subtipo é obrigatório", color = MaterialTheme.colorScheme.error)
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
                title = { Text("Selecionar Subtipo") },
                text = {
                    LazyColumn(
                        modifier = Modifier.height(350.dp)
                    ) {
                        items(subtiposList) { subtipo ->
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                            text = "Cancelar",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            )
        }

        // Seção de Estoque e Preço
        Spacer(modifier = Modifier.height(10.dp))
        Text("Estoque e Preço", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))

        NumberTextField(
            value = editedQtdPorCaixa,
            onValueChange = { editedQtdPorCaixa = it },
            label = "Unid./Caixa*",
            modifier = Modifier.fillMaxWidth(),
            isError = qtdPorCaixaError,
            errorMessage = if (qtdPorCaixaError) "Quantidade por caixa é obrigatória e deve ser maior que zero" else null
        )

        OutlinedTextField(
            value = editedPreco,
            onValueChange = { editedPreco = it },
            label = { Text("Preço*") },
            prefix = { Text("R$") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = precoError,
            supportingText = {
                if (precoError) {
                    Text(
                        "Preço é obrigatório e deve ser maior que zero",
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

        // Seção de Status e Restrições
        Spacer(modifier = Modifier.height(10.dp))
        Text("Status e Restrições", style = MaterialTheme.typography.titleMedium)
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
            Text("Contém lactose")
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
            Text("Contém glúten")
        }

        // Texto indicando campos obrigatórios
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "* Campos obrigatórios",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        // Botões de Ação
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // Ativa validações
                showValidationErrors = true

                if (isFormValid) {
                    if (product == null) {
                        // Adicionar novo produto
                        val newId = (viewModel.produtos.value?.size ?: 0) + 1
                        val newProduct = CardapioItem(
                            id = newId,
                            nome = editedName,
                            nomeMarca = editedMarca,
                            tipo = editedTipo,
                            nomeSubtipo = editedSubtipo,
                            preco = editedPreco.toDoubleOrNull() ?: 0.0,
                            qtdCaixa = editedQtdCaixa.toIntOrNull() ?: 0,
                            qtdPorCaixas = editedQtdPorCaixa.toIntOrNull() ?: 0,
                            ativo = editedAtivo,
                            temLactose = editedLactose,
                            temGluten = editedGluten
                        )
                        viewModel.adicionarProduto(newProduct)
                    } else {
                        // Atualizar produto existente
                        val updatedProduct = product.copy(
                            nome = editedName,
                            nomeMarca = editedMarca,
                            tipo = editedTipo,
                            nomeSubtipo = editedSubtipo,
                            preco = editedPreco.toDoubleOrNull() ?: 0.0,
                            qtdCaixa = editedQtdCaixa.toIntOrNull() ?: 0,
                            qtdPorCaixas = editedQtdPorCaixa.toIntOrNull() ?: 0,
                            ativo = editedAtivo,
                            temLactose = editedLactose,
                            temGluten = editedGluten
                        )
                        viewModel.atualizarProduto(updatedProduct)
                    }
                    onClose()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
        ) {
            Text("Salvar Alterações", color = Color.White)
        }

        TextButton(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar", color = Color.Gray)
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

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        shape = RoundedCornerShape(16.dp),
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = com.example.terabitemobile.ui.theme.tomVinho,
            unfocusedBorderColor = Color.Gray,
            cursorColor = com.example.terabitemobile.ui.theme.tomVinho,
            focusedLabelColor = tomVinho,
        ),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun TelaCardapioPreview() {
}
