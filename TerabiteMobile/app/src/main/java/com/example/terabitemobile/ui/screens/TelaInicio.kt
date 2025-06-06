package com.example.terabitemobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.R
import com.example.terabitemobile.ui.theme.background

@Composable
fun TelaInicio(navController: NavHostController) {
    val colors = TelaInicioColors()
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = background,
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(16.dp))
            EstoqueCard(colors, navController)
            Spacer(modifier = Modifier.height(16.dp))
            FerramentasSection(colors, navController)
        }
    }
}

@Composable
private fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = stringResource(R.string.accessibility_userProfile_img),
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
    }
}

@Composable
private fun EstoqueCard(colors: TelaInicioColors, navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.cardBege),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_manage_txt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                )
                Text(
                    text = stringResource(R.string.any_stock_txt),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color(0xFF343434)
                )

                Button(
                    onClick = {navController.navigate("estoque")},
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        stringResource(R.string.home_accessBtn_label),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.box),
                contentDescription = stringResource(R.string.accessibility_box_img),
                modifier = Modifier
                    .padding(16.dp)
                    .size(65.dp),
                colorFilter = ColorFilter.tint(Color(0xFF343434))
            )
        }
    }
}

@Composable
private fun FerramentasSection(colors: TelaInicioColors, navController: NavHostController) {
    Text(
        text = stringResource(R.string.any_tools_txt),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 20.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FerramentaItem(
            titulo = stringResource(R.string.any_stock_txt),
            corFundo = colors.primary,
            corTexto = Color.White,
            iconRes = R.drawable.box,
            onClick = { navController.navigate("estoque") }
        )

        FerramentaItem(
            titulo = stringResource(R.string.any_menu_txt),
            corFundo = colors.cardBege,
            corTexto = Color(0xFF343434),
            iconRes = R.drawable.scroll,
            onClick = { navController.navigate("cardapio") }
        )

        FerramentaItem(
            titulo = stringResource(R.string.any_depletions_txt),
            corFundo = colors.primary,
            corTexto = Color.White,
            iconRes = R.drawable.store,
            onClick = { navController.navigate("baixas") }
        )
    }

    VerMaisButton(colors, navController)
}

@Composable
private fun VerMaisButton(colors: TelaInicioColors, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, end = 14.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.home_viewMore_label),
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(25.dp))

        IconButton(
            onClick = { navController.navigate("ferramentas") },
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(colors.primary)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = stringResource(R.string.home_viewMore_label),
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun FerramentaItem(
    titulo: String,
    corFundo: Color,
    corTexto: Color,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(100.dp),
        colors = CardDefaults.cardColors(containerColor = corFundo),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = titulo,
                tint = corTexto,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,
                color = corTexto,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

class TelaInicioColors {
    val background = Color(0xFFF9FBFF)
    val primary = Color(0xFF8C3829)
    val cardBege = Color(0xFFE9DEB0)
}

@Preview(showBackground = true)
@Composable
fun TelaInicioPreview() {
    TelaInicio(rememberNavController())
}