import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R

@Composable
fun TelaFerramentas() {
    val poppins = FontFamily.SansSerif

    val fundoCinza = Color(0xFFD3D3D3)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(tomBege.value))
    ) {
        // Botao de voltar e ferramentas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Espaça os elementos
        ) {
            IconButton(
                onClick = { /* Lógica de voltar */ },
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = Color(tomVinho.value),
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow_24),
                    contentDescription = "Voltar",
                    modifier = Modifier.size(25.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            // Texto "Ferramentas" no meio
            Text(
                text = "Ferramentas",
                fontSize = 24.sp, // Ajuste o tamanho conforme necessário
                color = Color.Black, // Cor do texto, ajuste se quiser
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f) // Ocupa o espaço central
                    .wrapContentWidth(Alignment.CenterHorizontally) // Centraliza o texto
                    .padding(top = 3.dp, end = 32.dp)
            )

            // A parte das ferramentas
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background()
            ) {

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaFerramentasPreview() {
    TelaFerramentas()
}
