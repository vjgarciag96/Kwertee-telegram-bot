package data.remote

data class QwerteeConfig(
    val baseUrl: String
)

data class TShirtDTO(
    val eurPrice: String,
    val usdPrice: String,
    val gbpPrice: String,
    val title: String,
    val imageUrl: String
)