package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.livedata.observeAsState
import com.example.terabitemobile.data.models.BaixaModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.terabitemobile.data.classes.BaixaItem
import com.example.terabitemobile.data.classes.BaixaRequest
import com.example.terabitemobile.data.classes.BaixaItemRequest
import androidx.compose.ui.text.style.TextOverflow
import com.example.terabitemobile.R
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomBege
import com.example.terabitemobile.ui.theme.tomVinho
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaBaixas(paddingBottom: PaddingValues, viewModel: BaixaModel, produtos: List<CardapioItem>?) {
    val baixas by viewModel.baixas.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState("")

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedDateInicio by remember { mutableStateOf<LocalDate?>(null) }
    var selectedDateFim by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePickerInicio by remember { mutableStateOf(false) }
    var showDatePickerFim by remember { mutableStateOf(false) }

    val limparFiltro = {
        selectedDateInicio = null
        selectedDateFim = null
    }

    val formatoApi = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val baixasFiltradas = remember(baixas, selectedDateInicio, selectedDateFim) {
        if (selectedDateInicio == null || selectedDateFim == null) {
            baixas ?: emptyList()
        } else {
            (baixas ?: emptyList()).filter { baixa ->
                try {
                    val dataBaixa = LocalDate.parse(baixa.dtSaida, formatoApi)
                    dataBaixa >= selectedDateInicio!! && dataBaixa <= selectedDateFim!!
                } catch (e: Exception) {
                    false
                }
            }
        }
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
            ProfileBaixas ( onAddClick = { showAddDialog = true } )
            Spacer(modifier = Modifier.height(16.dp))
            // Filtro de datas
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Campo Data Início
                OutlinedTextField(
                    value = selectedDateInicio?.format(formatter) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Data início",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Data início", style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black)
                        }
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Selecionar data início",
                            modifier = Modifier.clickable { showDatePickerInicio = true }
                        )
                    },
                    placeholder = { Text("DD/MM/AAAA", style = MaterialTheme.typography.bodyMedium) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = tomVinho,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = tomVinho,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                // Campo Data Fim
                OutlinedTextField(
                    value = selectedDateFim?.format(formatter) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Data fim",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Data fim", style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black)
                        }
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Selecionar data fim",
                            modifier = Modifier.clickable { showDatePickerFim = true }
                        )
                    },
                    placeholder = { Text("DD/MM/AAAA", style = MaterialTheme.typography.bodyMedium) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = tomVinho,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = tomVinho,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
            }

            // DatePicker para Data Início
            if (showDatePickerInicio) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        selectedDateInicio = date
                        showDatePickerInicio = false
                        // Se data fim é anterior à data início, limpa data fim
                        if (date != null) {
                            if (selectedDateFim != null && date > selectedDateFim!!) {
                                selectedDateFim = null
                            }
                        }
                    },
                    onDismiss = { showDatePickerInicio = false }
                )
            }

            if (showDatePickerFim) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        // Só permite selecionar data fim se for maior ou igual à data início
                        if (date != null) {
                            if (selectedDateInicio == null || date >= selectedDateInicio!!) {
                                selectedDateFim = date
                                showDatePickerFim = false
                            }
                        }
                    },
                    onDismiss = { showDatePickerFim = false }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Baixas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

            // Mostrar mensagem quando o filtro está ativo
            if (selectedDateInicio != null && selectedDateFim != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Mostrando baixas de ${selectedDateInicio!!.format(formatter)} a ${
                            selectedDateFim!!.format(
                                formatter
                            )
                        }",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextButton(
                        onClick = limparFiltro,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Limpar filtro",
                            color = tomVinho,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

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
                            onClick = { viewModel.carregarBaixas() },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(stringResource(R.string.error_tryAgain_label))
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(baixasFiltradas) { baixa ->
                        BaixaCard(
                            item = baixa,
                            viewModel = viewModel,
                            produtos = produtos
                        )
                    }
                }
            }

            if (showAddDialog) {
                ModalBottomSheet(
                    onDismissRequest = { showAddDialog = false },
                    containerColor = Color.White
                ) {
                    BottomSheetAddBaixa(
                        produtos = produtos ?: emptyList(),
                        onClose = { showAddDialog = false },
                        viewModel = viewModel,
                        tomVinho = tomVinho
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Selecione a data") },
        text = {
            DatePicker(
                state = datePickerState,
                title = null,
                headline = null
            )
        }
    )
}

@Composable
private fun ProfileBaixas(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddBaixa(
    produtos: List<CardapioItem>,
    onClose: () -> Unit,
    viewModel: BaixaModel,
    tomVinho: Color
) {
    var dataSaida by remember { mutableStateOf("") }
    var selectedProduto by remember { mutableStateOf<CardapioItem?>(null) }
    var qtdCaixas by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredProdutos = if (searchQuery.isBlank()) {
        produtos
    } else {
        produtos.filter {
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
                text = "Adicionar Baixa",
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

        // Data de Saída
        OutlinedTextField(
            value = dataSaida,
            onValueChange = { dataSaida = it },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Data de Saída (DD/MM/AAAA)")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray
            ),
            placeholder = { Text("Ex: 08/05/2025") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Seletor de Produto
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedProduto?.nome ?: searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.isNotEmpty()) isDropdownExpanded = true
                },
                label = { Text("Buscar Produto") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tomVinho,
                    unfocusedBorderColor = Color.Gray
                ),
                placeholder = { Text("Digite para buscar") }
            )

            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                if (filteredProdutos.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Nenhum produto encontrado") },
                        onClick = { }
                    )
                } else {
                    filteredProdutos.forEach { produto ->
                        DropdownMenuItem(
                            text = { Text(produto.nome) },
                            onClick = {
                                selectedProduto = produto
                                searchQuery = produto.nome
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quantidade de Caixas
        OutlinedTextField(
            value = qtdCaixas,
            onValueChange = {
                if (it.isEmpty() || it.toIntOrNull() != null) {
                    qtdCaixas = it
                }
            },
            label = { Text("Quantidade de Caixas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray
            )
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botão Cancelar
            Button(
                onClick = onClose,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancelar")
            }

            // Botão Salvar
            Button(
                onClick = {
                    if (dataSaida.isEmpty() || selectedProduto == null || qtdCaixas.isEmpty()) {
                        errorMessage = "Preencha todos os campos"
                        return@Button
                    }

                    try {
                        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val parsedDate = LocalDate.parse(dataSaida, dateFormat)
                        val formattedDate = parsedDate.format(DateTimeFormatter.ISO_DATE)

                        isLoading = true
                        val baixaRequest = BaixaRequest(
                            dtSaida = formattedDate,
                            saidaEstoques = listOf(
                                BaixaItemRequest(
                                    produtoId = selectedProduto!!.id,
                                    qtdCaixasSaida = qtdCaixas.toInt()
                                )
                            )
                        )

                        viewModel.adicionarBaixa(baixaRequest)
                        onClose()
                    } catch (e: Exception) {
                        errorMessage = "Data inválida. Use o formato DD/MM/AAAA"
                        isLoading = false
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Salvar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaixaCard(
    item: BaixaItem,
    viewModel: BaixaModel,
    produtos: List<CardapioItem>?
) {
    val backgroundColor = tomBege
    val cardColor = tomVinho
    val textoBranco = Color.White

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Estado para edição
    var selectedProduto by remember { mutableStateOf<CardapioItem?>(item.produto) }
    var qtdCaixas by remember { mutableStateOf(item.qtdCaixasSaida.toString()) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Box(
            modifier = Modifier
                .background(cardColor, shape = RoundedCornerShape(6.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = LocalDate.parse(item.dtSaida, DateTimeFormatter.ISO_DATE)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                color = textoBranco,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(cardColor, shape = RoundedCornerShape(6.dp))
                .padding(6.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
//            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Icon(Icons.Default.AddBox, contentDescription = null, tint = textoBranco)
                Spacer(modifier = Modifier.width(6.dp))
                Text("${item.qtdCaixasSaida}", color = textoBranco)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(cardColor, shape = RoundedCornerShape(6.dp))
                .padding(6.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Icon(Icons.Default.Icecream, contentDescription = null, tint = textoBranco)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = item.produto.nome,
                    color = textoBranco,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
        }
        // Adicionando os botões de ação
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { showEditDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = cardColor
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = cardColor
                )
            }
        }
    }

    // Modal de Edição
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Saída", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    // Data (somente leitura)
                    OutlinedTextField(
                        value = LocalDate.parse(item.dtSaida, DateTimeFormatter.ISO_DATE)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        onValueChange = {},
                        label = { Text("Data") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Seletor de Produto
                    ExposedDropdownMenuBox(
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedProduto?.nome ?: searchQuery,
                            onValueChange = {
                                searchQuery = it
                                if (it.isNotEmpty()) isDropdownExpanded = true
                            },
                            label = { Text("Produto") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = tomVinho,
                                unfocusedBorderColor = Color.Gray
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            produtos?.filter {
                                it.nome.contains(searchQuery, ignoreCase = true)
                            }?.forEach { produto ->
                                DropdownMenuItem(
                                    text = { Text(produto.nome) },
                                    onClick = {
                                        selectedProduto = produto
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Quantidade de Caixas
                    OutlinedTextField(
                        value = qtdCaixas,
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull() != null) {
                                qtdCaixas = it
                            }
                        },
                        label = { Text("Quantidade de Caixas") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = tomVinho,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedProduto != null && qtdCaixas.isNotEmpty()) {
                            viewModel.editarBaixa(
                                id = item.id,
                                produtoId = selectedProduto!!.id,
                                qtdCaixasSaida = qtdCaixas.toInt()
                            )
                            showEditDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Cancelar", color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }

    // Modal de Confirmação de Exclusão
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza que deseja excluir esta saída?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deletarBaixa(item.dtSaida, item)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Não", color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }
}