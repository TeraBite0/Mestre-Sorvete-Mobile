package com.example.telaloginterabite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaLogin()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin() {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val poppins = FontFamily.SansSerif
    val context = LocalContext.current // Para navegação entre telas

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF482C21)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF8C3829), Color(0xFFA73E2B))
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1C97B),
                fontFamily = poppins
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Usuário",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontFamily = poppins,
                    modifier = Modifier.padding(start = 12.dp)
                )
                TextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    placeholder = {
                        Text("exemplo@email.com", color = Color(0xFF000000).copy(alpha = 0.6f), fontFamily = poppins)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Senha",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontFamily = poppins,
                    modifier = Modifier.padding(start = 12.dp)
                )
                TextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = {
                        Text("digite sua senha...", color = Color(0xFF000000).copy(alpha = 0.6f), fontFamily = poppins)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esqueceu a senha?",
                color = Color.White,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                fontFamily = poppins,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp)
                    .clickable {
                        //context.startActivity
                    }
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { /* Implementar lógica de login */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD07E0E)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text("Entrar", color = Color.White, fontSize = 18.sp, fontFamily = poppins)
            }

            Spacer(modifier = Modifier.height(40.dp))

            
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mestre Sorvete",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1C97B),
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bem-vindo de volta!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1C97B),
            fontFamily = poppins
        )
    }
}

//class EsqueceuSenhaActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//                EsqueceuSenhaTela()
//        }
//    }
//}
//@Composable
//fun EsqueceuSenhaTela() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF8C3829)),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "Tela de recuperação de senha",
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {
        TelaLogin()
}
