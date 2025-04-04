package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.room.Delete
import com.example.terabitemobile.ui.models.MarcaItem
import com.example.terabitemobile.ui.models.MarcaModel
import com.example.terabitemobile.ui.theme.background

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaMarcas(paddingValores: PaddingValues, viewModel: MarcaModel) {

    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var marcaName by remember { mutableStateOf("") }
    val marcas by viewModel.marcas.observeAsState()

    val filteredMarcas = remember(marcas, searchText) {
        marcas?.filter { it.nome.contains(searchText, ignoreCase = true) } ?: emptyList()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Adicionar Marca") },
            text = {
                Column {
                    OutlinedTextField(
                        value = marcaName,
                        onValueChange = { marcaName = it },
                        label = { Text("Nome da marca") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (marcaName.isNotBlank()) {
                            viewModel.addMarca(marcaName)
                            marcaName = ""
                            showDialog = false
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ProfileMarcas(onAddClick = { showDialog = true })
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Marcas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .height(5.dp)
                .weight(1f)
            ) {
                items(filteredMarcas) { marca ->
                    MarcaListItem(
                        marca = marca,
                        onDeleteClick = { viewModel.deleteMarca(marca.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileMarcas(onAddClick: () -> Unit) {
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


@Composable
private fun MarcaListItem(marca: MarcaItem, onDeleteClick: () -> Unit) {
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
        Text(
            text = marca.nome,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Excluir",
                tint = tomVinho
            )
        }
    }
}