package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R
import com.example.terabitemobile.data.classes.FornecedorRequest
import com.example.terabitemobile.data.classes.LotePost
import com.example.terabitemobile.data.classes.LoteProdutoResponse
import com.example.terabitemobile.data.models.CardapioItem
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.EstoqueItem
import com.example.terabitemobile.data.models.EstoqueModel
import com.example.terabitemobile.data.models.FornecedorModel
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomVinho
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEstoque(paddingBottom: PaddingValues, viewModel: EstoqueModel = koinViewModel()) {
    var searchText by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val estoque by viewModel.estoque.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.carregarEstoque()
    }

    Scaffold(containerColor = background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingBottom)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileEstoque(onAddClick = { showBottomSheet = true })
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(searchText = searchText, onSearchTextChanged = { searchText = it })
            Spacer(modifier = Modifier.height(14.dp))
            Text(stringResource(R.string.any_stock_txt), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = tomVinho)
                }
            } else if (error.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                            onClick = { viewModel.carregarEstoque() },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(stringResource(R.string.error_tryAgain_label))
                        }
                    }
                }
            } else {
                val listaFiltrada = estoque?.filter {
                    it.nome.contains(searchText, ignoreCase = true) || it.marca.contains(searchText, ignoreCase = true)
                }
                if (listaFiltrada.isNullOrEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (searchText.isEmpty()) stringResource(R.string.empty_stock_message)
                            else stringResource(R.string.no_search_results),
                            fontSize = 18.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(listaFiltrada.size) { index ->
                            ItemEstoque(item = listaFiltrada[index])
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            AddBottomSheetContent(estoqueModel = viewModel, onDismiss = { showBottomSheet = false })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomSheetContent(estoqueModel: EstoqueModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var expanded by remember { mutableStateOf(false) }
    var expandedFornecedor by remember { mutableStateOf(false) }
    var selectedProduto by remember { mutableStateOf<CardapioItem?>(null) }
    var selectedFornecedor by remember { mutableStateOf<FornecedorRequest?>(null) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var quantidadeCaixas by remember { mutableStateOf("") }
    var valorLote by remember { mutableStateOf("") }
    var isAdding by remember { mutableStateOf(false) }

    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val isoFormatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }
    val produtoViewModel: CardapioModel = koinViewModel()
    val produtos by produtoViewModel.produtos.observeAsState()
    val fornecedorViewModel: FornecedorModel = koinViewModel()
    val fornecedores by fornecedorViewModel.fornecedores.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .fillMaxHeight(0.9f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Adicionar Lote",
                style = MaterialTheme.typography.headlineSmall,
                color = tomVinho,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Produto", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedProduto?.nome ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = { Text("Selecione um produto", style = MaterialTheme.typography.bodyMedium) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tomVinho,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = tomVinho,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                produtos?.forEach { produto ->
                    DropdownMenuItem(
                        text = { Text(produto.nome) },
                        onClick = {
                            selectedProduto = produto
                            expanded = false
                        },
                        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text("Fornecedor", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(expanded = expandedFornecedor, onExpandedChange = { expandedFornecedor = !expandedFornecedor }) {
            OutlinedTextField(
                value = selectedFornecedor?.nome ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFornecedor) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = { Text("Selecione um fornecedor", style = MaterialTheme.typography.bodyMedium) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tomVinho,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = tomVinho,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expandedFornecedor,
                onDismissRequest = { expandedFornecedor = false },
                modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                fornecedores?.forEach { fornecedor ->
                    DropdownMenuItem(
                        text = { Text(fornecedor.nome) },
                        onClick = {
                            selectedFornecedor = fornecedor
                            expandedFornecedor = false
                        },
                        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text("Data de Entrega", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = selectedDate?.format(formatter) ?: "",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Selecionar data",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            shape = RoundedCornerShape(16.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = { Text("Selecione a data", style = MaterialTheme.typography.bodyMedium) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDateSelected = { date -> selectedDate = date; showDatePicker = false },
                onDismiss = { showDatePicker = false }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text("Qtd. Caixas", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = quantidadeCaixas,
            onValueChange = { if (it.all { char -> char.isDigit() }) quantidadeCaixas = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ex: 10", style = MaterialTheme.typography.bodyMedium) },
            shape = RoundedCornerShape(16.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text("Valor do Lote", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = valorLote,
            onValueChange = { if (it.all { char -> char.isDigit() }) valorLote = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ex: 100", style = MaterialTheme.typography.bodyMedium) },
            shape = RoundedCornerShape(16.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (selectedProduto == null || selectedFornecedor == null || selectedDate == null || quantidadeCaixas.isBlank() || valorLote.isBlank()) {
                    Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                isAdding = true
                val dataEntregaFormatada = selectedDate!!.format(isoFormatter)
                val dataPedidoFormatada = LocalDate.now().format(isoFormatter)
                val loteProduto = LoteProdutoResponse(
                    produtoId = selectedProduto!!.id,
                    qtdCaixasCompradas = quantidadeCaixas.toInt()
                )
                val lotePost = LotePost(
                    nomeFornecedor = selectedFornecedor!!.nome,
                    dtEntrega = dataEntregaFormatada,
                    dtPedido = dataPedidoFormatada,
                    valorLote = valorLote.toInt(),
                    loteProdutos = listOf(loteProduto)
                )
                estoqueModel.adicionarLote(lotePost)
                onDismiss()
                Toast.makeText(context, "Lote adicionado!", Toast.LENGTH_SHORT).show()
            },
            enabled = !isAdding,
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Adicionar",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(onDateSelected: (LocalDate?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate); onDismiss() }) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun ProfileEstoque(onAddClick: () -> Unit) {
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
        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            modifier = Modifier.width(145.dp)
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
private fun CampoBusca(searchText: String, onSearchTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text(stringResource(R.string.any_searchField_placeholder), style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier.fillMaxWidth().background(Color.Transparent, RoundedCornerShape(16.dp)),
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
private fun ItemEstoque(item: EstoqueItem) {
    Column(
        modifier = Modifier
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(16.dp))
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Nome", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp))
            Text(item.nome, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal, fontSize = 15.sp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Marca", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp))
            Text(item.marca, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal, fontSize = 15.sp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Qtd de Caixas", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp))
            Text(item.qtdCaixasEstoque.toString(), style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal, fontSize = 15.sp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Qtd por Caixas", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp))
            Text(item.qtdPorCaixas.toString(), style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal, fontSize = 15.sp))
        }
    }
}