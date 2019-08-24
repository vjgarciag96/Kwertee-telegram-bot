package tees.data.local

data class PromotedTeesDO(
    val timeToLive: Int,
    val goneForeverTees: List<TeeDO>,
    val lastChanceTees: List<TeeDO>
)

data class TeeDO(
    val eurPrice: Int,
    val usdPrice: Int,
    val gbpPrice: Int,
    val title: String,
    val imageUrl: String
)

enum class TypeDO {
    GONE_FOREVER,
    LAST_CHANCE
}