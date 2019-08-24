package tees.domain

import tees.data.repository.PromotedTeesData
import tees.data.repository.TeeData

fun TeeData.toDomainModel(): Tee = Tee(
    eurPrice = eurPrice,
    usdPrice = usdPrice,
    gbpPrice = gbpPrice,
    title = title,
    imageUrl = imageUrl
)

fun PromotedTeesData.toDomainModel(): PromotedTees = PromotedTees(
    goneForeverTees = goneForeverTees,
    lastChanceTees = lastChanceTees
)

fun Tee.toTextMessage(): String =
    title
        .plus("\n")
        .plus("Price:$eurPrice€|$usdPrice\$|$gbpPrice£")