package com.example.terabitemobile.ui.screens

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.R
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.terabitemobile.data.classes.LoginResponse
import com.example.terabitemobile.data.models.LoginModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class PasswordVisualTransformationWithLastCharVisible : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        val passwordChar = '\u2022'
        val lastChar = text.text.last()

        val transformedText = buildString {
            repeat(text.length - 1) {
                append(passwordChar)
            }
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
fun TelaLogin(navController: NavHostController, usuarioaAtual: LoginResponse = koinInject()) {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val loginModel: LoginModel = koinViewModel()
    val loginState by loginModel.loginState.observeAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginModel.LoginState.Success -> {
                navController.popBackStack()
                navController.navigate("inicio")
            }
            else -> {}
        }
    }

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
                text = stringResource(R.string.login_title_txt),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1C97B),
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.login_user_txt),
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
                            stringResource(R.string.login_email_placeholder),
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
                    text = stringResource(R.string.login_passw_txt),
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
                        Text(
                            stringResource(R.string.login_passw_placeholder),
                            color = Color(0xFF000000).copy(alpha = 0.6f),
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                     else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = if (passwordVisible) {
                                    stringResource(R.string.hide_password)
                                } else {
                                    stringResource(R.string.show_password)
                                },
                                tint = Color(0xFF482C21)
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    loginModel.fazerLogin(usuario, senha)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD07E0E)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                if (loginState is LoginModel.LoginState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(stringResource(R.string.login_loginBtn_label), color = Color.White, fontSize = 18.sp)
                }
            }
            if (loginState is LoginModel.LoginState.Error) {
                makeText(navController.context, (loginState as LoginModel.LoginState.Error).message, Toast.LENGTH_SHORT).show()
                loginModel.limparErro()
            }

            Spacer(modifier = Modifier.height(40.dp))

        }
        Image(
            painter = painterResource(id = R.drawable.wave_login_cinza),
            contentDescription = stringResource(R.string.accessibility_loginWave_img),
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
            text = stringResource(R.string.app_name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1C97B),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_welcomeMsg_txt),
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
