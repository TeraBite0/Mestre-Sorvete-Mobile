package com.example.terabitemobile.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
// import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
// import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.terabitemobile.R
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PasswordVisualTransformationWithLastCharVisible : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        val passwordChar = '\u2022' // Bullet character
        val lastChar = text.text.last()

        val transformedText = buildString {
            // Replace all characters except the last one with bullets
            repeat(text.length - 1) {
                append(passwordChar)
            }
            // Show the last character as is
            append(lastChar)
        }

        return TransformedText(
            AnnotatedString(transformedText),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = offset
                override fun transformedToOriginal(offset: Int): Int = offset
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TelaLogin(navController: NavHostController) {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF482C21))
            // Tira o teclado da tela quando o elemento é clicado
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
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
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Usuário",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    placeholder = {
                        Text(
                            "exemplo@email.com",
                            color = Color(0xFF000000).copy(alpha = 0.6f),
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9),
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Senha",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = {
                        Text("digite sua senha...", color = Color(0xFF000000).copy(alpha = 0.6f),)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (senha.isEmpty()) PasswordVisualTransformation() else PasswordVisualTransformationWithLastCharVisible(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esqueceu a senha?",
                color = Color.White,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp)
                    .clickable {
                        //context.startActivity
                    }
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate("inicio")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD07E0E)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text("Entrar", color = Color.White, fontSize = 18.sp,)
            }

            Spacer(modifier = Modifier.height(40.dp))

        }
        Image(
            painter = painterResource(id = R.drawable.wave_login_cinza),
            contentDescription = "Onda decorativa",
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
        )
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
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bem-vindo de volta!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1C97B),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {
    TelaLogin(rememberNavController())
}
