package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.terabitemobile.ui.theme.tomVinho
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R
import com.example.terabitemobile.data.models.BaixasModel
import com.example.terabitemobile.data.models.SaidaEstoque
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomMarcas

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaBaixas(paddingScaffold: PaddingValues, viewModel: BaixasModel) {

    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("04/12/1999") }
    var selectedProductId by remember { mutableStateOf(0) }
    var qtdCaixas by remember { mutableStateOf("") }

    val baixas by viewModel.baixas.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState("")
    val produtos by viewModel.produtos.observeAsState(initial = emptyList())

    val filteredBaixas = remember(baixas, searchText) {
        baixas?.flatMap { saida ->
            saida.saidaEstoques.filter {
                it.produto.nome.contains(searchText, ignoreCase = true)
            }
        } ?: emptyList()
    }

    if (showDialog) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.add_outflow_dialog_title)) },
            text = {
                Column {
                    // Date Picker
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.date_label),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Button(
                            onClick = { /* TODO: Show date picker */ },
                            colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                        ) {
                            Text(selectedDate.toString())
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = produtos.find { it.id == selectedProductId }?.nome ?: "",
                            onValueChange = {},
                            label = { Text(stringResource(R.string.product_label)) },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, "")
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = tomVinho,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            produtos.forEach { produto ->
                                DropdownMenuItem(
                                    text = { Text(produto.nome) },
                                    onClick = {
                                        selectedProductId = produto.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quantity Input
                    OutlinedTextField(
                        value = qtdCaixas,
                        onValueChange = { qtdCaixas = it },
                        label = { Text(stringResource(R.string.boxes_quantity_label)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        if (selectedProductId > 0 && qtdCaixas.isNotBlank()) {
                            viewModel.addBaixa(
                                selectedDate,
                                selectedProductId,
                                qtdCaixas.toInt()
                            )
                            showDialog = false
                            qtdCaixas = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text(stringResource(R.string.confirm_button_label))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.dialog_cancel_button))
                }
            }
        )
    }

    Scaffold(
        containerColor = background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingScaffold)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileBaixas(onAddClick = { showDialog = true })
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(R.string.outflows_title),
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(R.string.error_load_data_failure),
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(5.dp)
                        .weight(1f)
                ) {
                    baixas?.forEach { saida ->
                        item {
                            Text(
                                text = saida.dtSaida,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(saida.saidaEstoques) { baixa ->
                            BaixaListItem(
                                baixa = baixa,
                                onDeleteClick = { /* TODO */ }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileBaixas(onAddClick: () -> Unit) {
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
private fun BaixaListItem(baixa: SaidaEstoque, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.box),
            contentDescription = stringResource(R.string.box_icon_desc),
            modifier = Modifier
                .padding(end = 10.dp)
                .size(25.dp),
            colorFilter = ColorFilter.tint(tomMarcas)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = baixa.produto.nome,
                fontSize = 16.sp
            )
            Text(
                text = "${baixa.qtdCaixasSaida} ${stringResource(R.string.boxes_label)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete_label),
                tint = tomVinho
            )
        }
    }
}

