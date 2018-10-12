package model

data class GoneForeverTShirtDTO(
    val eurPrice: String,
    val usdPrice: String,
    val gbpPrice: String,
    val title: String,
    val imageUrl: String
)

typealias GoneForeverTShirt = GoneForeverTShirtDTO