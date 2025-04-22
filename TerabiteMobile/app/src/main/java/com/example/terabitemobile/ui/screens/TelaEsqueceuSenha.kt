package com.example.terabitemobile.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.terabitemobile.R
import androidx.compose.ui.res.stringResource

class PasswordVisualTransformationWithLastCharVisible2 : VisualTransformation {
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
fun TelaEsqueceuSenha(navController: NavHostController) {
    var senhaNova by remember { mutableStateOf("") }
    var repetirSenha by remember { mutableStateOf("") }

    val poppins = FontFamily.SansSerif

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
                text = stringResource(R.string.reset_passwd_title_txt),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1C97B),
                fontFamily = poppins
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.reset_passwd_field_txt),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontFamily = poppins,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = senhaNova,
                    onValueChange = { senhaNova = it },
                    placeholder = {
                        Text(
                            stringResource(R.string.reset_passwd_placeholder_txt),
                            color = Color(0xFF000000).copy(alpha = 0.6f),
                            fontFamily = poppins,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9),
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                    // visualTransformation = if (senhaNova.isEmpty()) PasswordVisualTransformationWithLastCharVisible2() else PasswordVisualTransformationWithLastCharVisible(),
                    visualTransformation = PasswordVisualTransformationWithLastCharVisible2(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.reset_passwd_field2_txt),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontFamily = poppins,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = repetirSenha,
                    onValueChange = { repetirSenha = it },
                    placeholder = {
                        Text(
                            stringResource(R.string.reset_passwd_placeholder2_txt),
                            color = Color(0xFF000000).copy(alpha = 0.6f),
                            fontFamily = poppins,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFD9D9D9)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                    // visualTransformation = if (repetirSenha.isEmpty()) PasswordVisualTransformationWithLastCharVisible2() else PasswordVisualTransformationWithLastCharVisible(),
                    visualTransformation = PasswordVisualTransformationWithLastCharVisible2(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(68.dp))

            Button(
                onClick = {/* Implementar lógica de redefinir senha */
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD07E0E)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text(stringResource(R.string.reset_passwd_btn_label), color = Color.White, fontSize = 18.sp, fontFamily = poppins)
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
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_welcomeMsg_txt),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF1C97B),
            fontFamily = poppins
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaEsqueceuSenhaPreview() {
    TelaEsqueceuSenha(rememberNavController())
}
