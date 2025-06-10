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

    LaunchedEffect(key1 = Unit) {
        viewModel.carregarBaixas();
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDateInicio?.format(formatter) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.data_inicio_label), style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black)
                        }
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Selecionar data início",
                            tint = Color.Black,
                            modifier = Modifier.clickable { showDatePickerInicio = true }
                        )
                    },
                    placeholder = { Text(stringResource(R.string.date_placeholder), style = MaterialTheme.typography.bodyMedium) },
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

                OutlinedTextField(
                    value = selectedDateFim?.format(formatter) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.data_fim_label), style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black)
                        }
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Selecionar data fim",
                            tint = Color.Black,
                            modifier = Modifier.clickable { showDatePickerFim = true }
                        )
                    },
                    placeholder = { Text(stringResource(R.string.date_placeholder), style = MaterialTheme.typography.bodyMedium) },
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

            if (showDatePickerInicio) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        selectedDateInicio = date
                        showDatePickerInicio = false
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
            Text(stringResource(R.string.baixas_title), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

            if (selectedDateInicio != null && selectedDateFim != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.mostrando_baixas_format,
                            selectedDateInicio!!.format(formatter),
                            selectedDateFim!!.format(formatter)),
                        style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = limparFiltro
                    ) {
                        Text(
                            stringResource(R.string.limpar_filtro_label),
                            color = tomVinho,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp
                            ),
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
                if (baixasFiltradas.isEmpty() && selectedDateInicio == null && selectedDateFim == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.nenhuma_baixa_msg),
                                fontSize = 18.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.instrucao_baixa_msg),
                                fontSize = 16.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
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
            }

            if (showAddDialog) {
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
    var showDatePicker by remember { mutableStateOf(false) }

    val filteredProdutos = if (searchQuery.isBlank()) {
        produtos
    } else {
        produtos.filter {
            it.nome.contains(searchQuery, ignoreCase = true)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = { date ->
                if (date != null) {
                    dataSaida = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                stringResource(R.string.adicionar_baixa_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = dataSaida,
                    onValueChange = { dataSaida = it },
                    label = {
                        Text(stringResource(R.string.data_saida_label), color = Color.Black)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = tomVinho,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = tomVinho,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    readOnly = true,
                    placeholder = { Text("DD/MM/AAAA", style = MaterialTheme.typography.bodyMedium) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Selecionar data",
                            tint = tomVinho,
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

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
                        label = { Text(stringResource(R.string.buscar_produto_label), color = Color.Black) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = tomVinho,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = tomVinho,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = { Text(stringResource(R.string.selecione_produto_placeholder))},
                        shape = RoundedCornerShape(16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            .height(200.dp)
                    ) {
                        if (filteredProdutos.isEmpty()) {
                            DropdownMenuItem(
                                text = { stringResource(R.string.product_notfound_txt) },
                                onClick = { },
                                modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            )
                        } else {
                            filteredProdutos.forEach { produto ->
                                DropdownMenuItem(
                                    text = { Text(produto.nome) },
                                    onClick = {
                                        selectedProduto = produto
                                        searchQuery = produto.nome
                                        isDropdownExpanded = false
                                    },
                                    modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = qtdCaixas,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            qtdCaixas = it
                        }
                    },
                    label = { Text(stringResource(R.string.quantidade_caixas_label), color = Color.Black) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = tomVinho,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = tomVinho,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
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
                modifier = Modifier.fillMaxWidth(),
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
                    Text(stringResource(R.string.salvar_label))
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.cancelar_label))
            }
        },
        containerColor = Color.White
    )
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

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text(stringResource(R.string.editar_saida_title), fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = LocalDate.parse(item.dtSaida, DateTimeFormatter.ISO_DATE)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        onValueChange = {},
                        label = { Text(stringResource(R.string.data_label), color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
                            label = { Text(stringResource(R.string.buscar_produto_label), color = Color.Black) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = tomVinho,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = tomVinho,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                .height(200.dp)
                        ) {
                            produtos?.filter {
                                it.nome.contains(searchQuery, ignoreCase = true)
                            }?.forEach { produto ->
                                DropdownMenuItem(
                                    text = { Text(produto.nome) },
                                    onClick = {
                                        selectedProduto = produto
                                        isDropdownExpanded = false
                                    },
                                    modifier = Modifier.background(color = Color.White,
                                        shape = RoundedCornerShape(16.dp))
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = qtdCaixas,
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull() != null) {
                                qtdCaixas = it
                            }
                        },
                        label = { Text(stringResource(R.string.quantidade_caixas_label), color = Color.Black) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = tomVinho,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = tomVinho,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
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
                    Text(stringResource(R.string.confirmar_label))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(stringResource(R.string.cancelar_label), color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.confirmar_exclusao_title)) },
            text = { Text(stringResource(R.string.confirmar_exclusao_msg)) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deletarBaixa(item.dtSaida, item)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text(stringResource(R.string.sim_label))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(stringResource(R.string.nao_label), color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }
}