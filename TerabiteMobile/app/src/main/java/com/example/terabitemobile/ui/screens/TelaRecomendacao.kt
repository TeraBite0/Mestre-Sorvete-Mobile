package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.ui.models.RecomendacaoItem
import com.example.terabitemobile.ui.models.RecomendacaoModel
import com.example.terabitemobile.ui.theme.background

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaRecomendacao(paddingValores: PaddingValues, viewModel: RecomendacaoModel) {

    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var recomendacaoName by remember { mutableStateOf("") }
    var recomendacaoMarca by remember { mutableStateOf("") }
    val recomendacoes by viewModel.recomendacoes.observeAsState()

    val filteredRecomendacoes = remember(recomendacoes, searchText) {
        recomendacoes?.filter { it.nome.contains(searchText, ignoreCase = true) } ?: emptyList()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Adicionar Recomendação") },
            text = {
                Column {
                    OutlinedTextField(
                        value = recomendacaoName,
                        onValueChange = { recomendacaoName = it },
                        label = { Text("Nome da recomendação") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = recomendacaoMarca,
                        onValueChange = { recomendacaoMarca = it },
                        label = { Text("Nome da marca") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (recomendacaoName.isNotBlank() && recomendacaoMarca.isNotBlank()) {
                            viewModel.addRecomendacao(recomendacaoName, recomendacaoMarca)
                            recomendacaoName = ""
                            recomendacaoMarca = ""
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
            ProfileRecomendacoes(onAddClick = { showDialog = true })
            Spacer(modifier = Modifier.height(16.dp))
            CampoBusca(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Recomendados", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(filteredRecomendacoes) { recomendacao ->
                    RecomendacaoListItem(
                        recomendacao = recomendacao,
                        onDeleteClick = { viewModel.deleteRecomendacao(recomendacao.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileRecomendacoes(onAddClick: () -> Unit) {
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
private fun RecomendacaoListItem(recomendacao: RecomendacaoItem, onDeleteClick: () -> Unit) {
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
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                    .border(1.5.dp, tomVinho, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
//                Text("470×470", fontSize = 10.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = recomendacao.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = recomendacao.marca, color = Color.Gray, fontSize = 12.sp)
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remover",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                        .background(tomVinho, shape = RoundedCornerShape(12.dp))
                        .padding(6.dp)
                )
            }
        }
    }
}