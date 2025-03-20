package com.example.terabitemobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R

val PoppinsFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic)
)

val TerabiteTypography = Typography(
    // Texto grande
    displayLarge = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),

    // Cabeçalhos
    headlineLarge = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    // Títulos
    titleLarge = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    // Corpo do texto
    bodyLarge = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = PoppinsFamily,
//        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    // Rótulos
    labelLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)