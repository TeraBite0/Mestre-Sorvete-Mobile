import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terabitemobile.R
import androidx.compose.ui.layout.ContentScale

@Composable
fun TelaFerramentas() {
    val fundoCinza = Color(0xFFD3D3D3)
    val tomVinho = Color(0xFF8C3829)
    val tomBege = Color(0xFFE9DEB0)
    val tomBaixas = Color(0xFFE8C726)
    val tomEstoque = Color(0xFF0E7A4A)
    val tomDestaques = Color(0xFFF6992F)

    // Definindo a família de fontes Poppins com as variantes disponíveis
    val poppins = FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(R.drawable.ferramentas_background),
            contentDescription = "Fundo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Cabeçalho - Botao de voltar e título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 25.dp,
                        end = 25.dp,
                        top = 40.dp,
                        bottom = 25.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                    fontSize = 26.sp,
                    color = Color(0xFF2C2C2C),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(top = 3.dp, end = 31.dp)
                )
            }

            // Conteúdo principal - Lista de ferramentas
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)
                    .wrapContentHeight()
            ) {
                FerramentaItem(
                    iconeResId = R.drawable.scroll,
                    titulo = "Cardápio",
                    descricao = "Gerencie seus produtos",
                    corIcone = tomVinho,
                    fontFamily = poppins
                )

                Spacer(modifier = Modifier.height(16.dp))

                FerramentaItem(
                    iconeResId = R.drawable.store,
                    titulo = "Baixas",
                    descricao = "Gerencie a saída de estoque",
                    corIcone = tomBaixas,
                    fontFamily = poppins
                )

                Spacer(modifier = Modifier.height(16.dp))

                FerramentaItem(
                    iconeResId = R.drawable.box,
                    titulo = "Estoque",
                    descricao = "Organize os produtos em estoque",
                    corIcone = tomEstoque,
                    fontFamily = poppins
                )

                Spacer(modifier = Modifier.height(16.dp))

                FerramentaItem(
                    iconeResId = R.drawable.star,
                    titulo = "Destaques",
                    descricao = "Altere a recomendação do dia",
                    corIcone = tomDestaques,
                    fontFamily = poppins
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FerramentaItem(
    iconeResId: Int,
    titulo: String,
    descricao: String,
    corIcone: Color,
    fontFamily: FontFamily
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(25.dp)
            )
            .height(97.dp)
            .border(
                width = 2.dp,
                color = Color(0x40000000),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconeResId),
            contentDescription = titulo,
            modifier = Modifier.size(57.dp),
            colorFilter = ColorFilter.tint(corIcone)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = titulo,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                letterSpacing = -1.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = descricao,
                fontSize = 17.5.sp,
                color = Color(0xA1000000),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = -1.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaFerramentasPreview() {
    TelaFerramentas()
}