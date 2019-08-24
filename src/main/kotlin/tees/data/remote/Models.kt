package tees.data.remote

data class TeeDto(
    val eurPrice: String,
    val usdPrice: String,
    val gbpPrice: String,
    val title: String,
    val imageUrl: String
)

data class PromotedTeesDto(
    val timeToLive: Int,
    val goneForeverTees: List<TeeDto>, 
    val lastChanceTees: List<TeeDto>
)