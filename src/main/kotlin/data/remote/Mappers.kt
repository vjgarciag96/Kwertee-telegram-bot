package data.remote

import domain.TShirt

fun TShirtDTO.toDomainModel(): TShirt = TShirt(
    eurPrice.plus("€"),
    usdPrice.plus("\$"),
    gbpPrice.plus("£"),
    title,
    imageUrl
)