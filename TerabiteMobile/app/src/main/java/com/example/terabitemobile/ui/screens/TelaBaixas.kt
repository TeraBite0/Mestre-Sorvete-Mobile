package com.example.terabitemobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.livedata.observeAsState
import com.example.terabitemobile.data.models.BaixaModel
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.terabitemobile.data.models.BaixaItem
import androidx.compose.ui.text.style.TextOverflow
import com.example.terabitemobile.R
import com.example.terabitemobile.ui.theme.background
import com.example.terabitemobile.ui.theme.tomBege
import com.example.terabitemobile.ui.theme.tomVinho

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaBaixas(paddingBottom: PaddingValues, viewModel: BaixaModel) {
    val baixas by viewModel.baixas.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState("")

    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }

    val formatoApi = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatoInput = DateTimeFormatter.ofPattern("dd/MM")

    // Filtragem
    val baixasFiltradas = (baixas ?: emptyList()).filter {
        try {
            val data = LocalDate.parse(it.dtSaida, formatoApi)
            val inicio =
                dataInicio.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it, formatoInput) }
            val fim = dataFim.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it, formatoInput) }

            (inicio == null || data >= inicio) && (fim == null || data <= fim)
        } catch (e: Exception) {
            true
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
            ProfileBaixas { }
            Spacer(modifier = Modifier.height(16.dp))
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                OutlinedTextField(
//                    value = dataInicio,
//                    onValueChange = { dataInicio = it },
//                    label = { Text("De (DD/MM)") },
//                    modifier = Modifier.weight(1f)
//                )
//                OutlinedTextField(
//                    value = dataFim,
//                    onValueChange = { dataFim = it },
//                    label = { Text("Até (DD/MM)") },
//                    modifier = Modifier.weight(1f)
//                )
//            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = dataInicio,
                    onValueChange = {
                        dataInicio = it
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
                    Text("Data início", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tomVinho,
                unfocusedBorderColor = Color.Gray,
                cursorColor = tomVinho,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            placeholder = { Text("DD/MM/AAAA", fontSize = 14.sp)},
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = dataFim,
                onValueChange = {
                    dataFim = it
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
                        Text("Data fim", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tomVinho,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = tomVinho,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                placeholder = { Text("DD/MM/AAAA", fontSize = 14.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
        }
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Baixas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(14.dp))

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
//                    contentPadding = PaddingValues(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(baixasFiltradas) { baixa ->
                        BaixaCard(baixa)
                    }
                }
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


@Composable
fun BaixaCard(item: BaixaItem) {
    val backgroundColor = tomBege
    val cardColor = tomVinho
    val textoBranco = Color.White

    Column(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .fillMaxWidth()
            .height(135.dp)
    ) {
        Box(
            modifier = Modifier
                .background(cardColor, shape = RoundedCornerShape(6.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = LocalDate.parse(item.dtSaida, DateTimeFormatter.ISO_DATE)
                    .format(DateTimeFormatter.ofPattern("dd/MM")),
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
    }
}


