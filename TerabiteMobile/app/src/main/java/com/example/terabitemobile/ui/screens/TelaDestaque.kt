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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.ui.models.DestaqueItem
import com.example.terabitemobile.ui.models.DestaqueModel
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.fundoCinza

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TelaDestaque(paddingValores: PaddingValues, viewModel: DestaqueModel) {

    var showDialog by remember { mutableStateOf(false) }
    var destaqueName by remember { mutableStateOf("") }
    var destaqueSelecionadoId by remember { mutableIntStateOf(1) }
    var destaqueDescricao by remember { mutableStateOf("") }
    val destaque by viewModel.destaque.observeAsState()

    val destaqueList = destaque ?: emptyList()


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Alterar Destaque") },
            text = {
                Column {
                    OutlinedTextField(
                        value = destaqueName,
                        onValueChange = { destaqueName = it },
                        label = { Text("Nome do destaque") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = destaqueDescricao,
                        onValueChange = { destaqueDescricao = it },
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (destaqueName.isNotBlank() && destaqueDescricao.isNotBlank()) {
                            viewModel.editDestaque(destaqueSelecionadoId, destaqueName, destaqueDescricao)
                            destaqueName = ""
                            destaqueDescricao = ""
                            showDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = tomVinho)
                ) {
                    Text("Salvar")
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
            ProfileDestaque()
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Destaque", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(destaqueList) { destaque ->
                    DestaqueListItem(
                        destaque = destaque,
                        onEditClick = { id, nome, descricao ->
                            destaqueSelecionadoId = id
                            destaqueName = nome
                            destaqueDescricao = descricao
                            showDialog = true
                        }
                    )
                }
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
    }
}

@Composable
private fun DestaqueListItem(destaque: DestaqueItem,
                             onEditClick: (Int, String, String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
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

                    Row { Text(text = destaque.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .background(fundoCinza, RoundedCornerShape(8.dp))
                        .padding(4.dp))
                    { Text("Marca: Senhor Sorvete", fontSize = 12.sp) }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .background(fundoCinza, RoundedCornerShape(8.dp))
                        .padding(5.dp))
                    { Text("Categoria: Picolé", fontSize = 12.sp) }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(fundoCinza, RoundedCornerShape(8.dp))
                .padding(5.dp))
            {
                Text(text = destaque.descricao, fontSize = 12.sp)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { onEditClick(destaque.id, destaque.nome, destaque.descricao) },
        colors = ButtonDefaults.buttonColors(containerColor = tomVinho),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Alterar Destaque", color = Color.White)
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Editar",
            tint = Color.White,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(16.dp)
        )
    }
}
