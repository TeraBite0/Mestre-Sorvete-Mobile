import com.example.terabitemobile.data.classes.BaixaItem

data class BaixaResponse(
    val dtSaida: String,
    val saidaEstoques: List<BaixaItem>
)