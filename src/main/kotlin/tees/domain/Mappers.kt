package tees.domain

import tees.data.repository.TeeData

fun TeeData.toDomainModel(): Tee = Tee(
    eurPrice = eurPrice.plus("€"),
    usdPrice = usdPrice.plus("\$"),
    gbpPrice = gbpPrice.plus("£"),
    title = title,
    imageUrl = imageUrl
)

fun Tee.toTextMessage(): String =
    title
        .plus("\n")
        .plus("Price:$eurPrice|$usdPrice|$gbpPrice")