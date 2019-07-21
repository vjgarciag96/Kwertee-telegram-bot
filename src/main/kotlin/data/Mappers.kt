package data

import data.remote.TShirtDTO
import domain.TShirt

fun TShirtDTO.toDomainModel(): TShirt = TShirt(
    eurPrice.plus("€"),
    usdPrice.plus("\$"),
    gbpPrice.plus("£"),
    title,
    imageUrl
)