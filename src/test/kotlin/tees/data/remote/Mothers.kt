package tees.data.remote

private const val ANY_PRICE = "5"
private const val ANY_TITLE = "Ruka"
private const val ANY_IMAGE_URL = "https://www.mydrugs.io/rukaina.png"

fun teeDto(
    eurPrice: String = ANY_PRICE,
    usdPrice: String = ANY_PRICE,
    gbpPrice: String = ANY_PRICE,
    title: String = ANY_TITLE,
    imageUrl: String = ANY_IMAGE_URL
): TeeDto = TeeDto(
    eurPrice = eurPrice,
    usdPrice = usdPrice,
    gbpPrice = gbpPrice,
    title = title,
    imageUrl = imageUrl
)