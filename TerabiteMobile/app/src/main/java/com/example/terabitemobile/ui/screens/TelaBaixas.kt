package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.data.models.BaixaItem
import com.example.terabitemobile.data.models.BaixasModel
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomBege
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaBaixas(paddingValores: PaddingValues, viewModel: BaixasModel) {

    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var baixaName by remember { mutableStateOf("") }
    var qtdLotesText by remember { mutableStateOf("") }
    // NÃO ALTERAR FORMATAÇÃO!!
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var dataInicioTexto by remember { mutableStateOf("") }
    var dataFimTexto by remember { mutableStateOf("") }
    var dataInicioFiltro by remember { mutableStateOf<LocalDate?>(null) }
    var dataFimFiltro by remember { mutableStateOf<LocalDate?>(null) }
    var dataBaixaText by remember { mutableStateOf("") }
//    var dataBaixa by remember { mutableStateOf(LocalDate.now()) }
    val baixas by viewModel.baixas.observeAsState()

    val filteredBaixas = remember(baixas, searchText, dataInicioFiltro, dataFimFiltro) {
        baixas?.filter { baixa ->
            val nomeMatch = baixa.nome.contains(searchText, ignoreCase = true)
            val dataDentroDoIntervalo = baixa.data.let { data ->
                when {
                    dataInicioFiltro != null && dataFimFiltro != null -> data >= dataInicioFiltro && data <= dataFimFiltro
                    dataInicioFiltro != null -> data >= dataInicioFiltro
                    dataFimFiltro != null -> data <= dataFimFiltro
                    else -> true
                }
            }
            nomeMatch && dataDentroDoIntervalo
        } ?: emptyList()
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Adicionar Baixa") },
            text = {
                Column {
                    OutlinedTextField(
                        value = baixaName,
                        onValueChange = { baixaName = it },
                        label = { Text("Nome do lote") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = qtdLotesText,
                        onValueChange = { qtdLotesText = it },
                        label = { Text("Quantidade de lotes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = dataBaixaText,
                        onValueChange = { dataBaixaText = it },
                        label = { Text("Data da baixa (DD/MM/AAAA)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (baixaName.isNotBlank()
                            && qtdLotesText.isNotBlank()
                            && dataBaixaText.isNotBlank()
                        ) {
                            val qtd = qtdLotesText.toIntOrNull()
                            val data = runCatching {
                                LocalDate.parse(
                                    dataBaixaText,
                                    formatter
                                )
                            }.getOrNull()

                            if (qtd != null && data != null) {
                                viewModel.addBaixa(baixaName, qtd, data)
                                baixaName = ""
                                qtdLotesText = ""
                                dataBaixaText = ""
                                showDialog = false
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancelar", color = tomVinho)
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
                .padding(
                    top = paddingValores.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValores.calculateBottomPadding()
                )
        ) {
            ProfileBaixas(onAddClick = { showDialog = true })
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = dataInicioTexto,
                    onValueChange = {
                        dataInicioTexto = it
                        try {
                            dataInicioFiltro = LocalDate.parse(it, formatter)
                        } catch (e: Exception) {
                            dataInicioFiltro = null
                        }
                    },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Data",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Data início", style = MaterialTheme.typography.bodyMedium)
                        }
                    },
                    placeholder = { Text("DD/MM/AAAA") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = dataFimTexto,
                    onValueChange = {
                        dataFimTexto = it
                        try {
                            dataFimFiltro = LocalDate.parse(it, formatter)
                        } catch (e: Exception) {
                            dataFimFiltro = null
                        }
                    },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Data",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Data fim", style = MaterialTheme.typography.bodyMedium)
                        }
                    },
                    placeholder = { Text("DD/MM/AAAA") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Baixas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f)
//            ) {
//                items(filteredBaixas) { baixa ->
//                    BaixaListItem(
//                        baixa = baixa
////                        onDeleteClick = { viewModel.deleteBaixa(baixa.id) }
//                    )
//                }
//            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(filteredBaixas) { baixa ->
                    BaixaListItem(baixa = baixa)
                }
            }

        }
    }
}

//, onDeleteClick: () -> Unit
@Composable
private fun BaixaListItem(baixa: BaixaItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
//            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = tomBege),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
                    .background(tomVinho, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Data",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = baixa.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tomVinho, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "Quantidade",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${baixa.qtdLotes}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tomVinho, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Icecream,
                    contentDescription = "Produto",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = baixa.nome,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

//            IconButton(onClick = onDeleteClick) {
//                Icon(
//                    imageVector = Icons.Filled.Close,
//                    contentDescription = "Remover",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(36.dp)
//                        .background(tomVinho, shape = RoundedCornerShape(12.dp))
//                        .padding(6.dp)
//                )
//            }

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

        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
            modifier = Modifier.width(145.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar",
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
