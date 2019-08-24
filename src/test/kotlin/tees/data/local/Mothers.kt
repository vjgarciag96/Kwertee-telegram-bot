package tees.data.local

private const val ANY_PRICE = 5
private const val ANY_TITLE = "Ruka"
private const val ANY_IMAGE_URL = "https://www.rukeitor.com/jelou"

fun tee(
    eurPrice: Int = ANY_PRICE,
    usdPrice: Int = ANY_PRICE,
    gbpPrice: Int = ANY_PRICE,
    title: String = ANY_TITLE,
    imageUrl: String = ANY_IMAGE_URL
): TeeDO = TeeDO(
    eurPrice = eurPrice,
    usdPrice = usdPrice,
    gbpPrice = gbpPrice,
    title = title,
    imageUrl = imageUrl
)

private const val ANY_TIME_TO_LIVE = 21214

fun promotedTees(
    timeToLive: Int = ANY_TIME_TO_LIVE,
    goneForeverTees: List<TeeDO> = emptyList(),
    lastChanceTees: List<TeeDO> = emptyList()
): PromotedTeesDO = PromotedTeesDO(
    timeToLive = timeToLive,
    goneForeverTees = goneForeverTees,
    lastChanceTees = lastChanceTees
)