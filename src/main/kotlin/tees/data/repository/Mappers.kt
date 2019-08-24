package tees.data.repository

import tees.data.local.PromotedTeesDO
import tees.data.local.TeeDO
import tees.data.remote.PromotedTeesDto
import tees.data.remote.TeeDto

fun PromotedTeesDto.toDataModel(): PromotedTeesData = PromotedTeesData(
    timeToLive = timeToLive,
    goneForeverTees = goneForeverTees.map(TeeDto::toDataModel),
    lastChanceTees = lastChanceTees.map(TeeDto::toDataModel)
)

fun TeeDto.toDataModel(): TeeData = TeeData(
    eurPrice = eurPrice.toInt(),
    usdPrice = usdPrice.toInt(),
    gbpPrice = gbpPrice.toInt(),
    title = title,
    imageUrl = imageUrl

)

fun TeeDO.toDataModel(): TeeData = TeeData(
    eurPrice = eurPrice,
    usdPrice = usdPrice,
    gbpPrice = gbpPrice,
    title = title,
    imageUrl = imageUrl
)

fun PromotedTeesDO.toDataModel(): PromotedTeesData = PromotedTeesData(
    timeToLive = timeToLive,
    goneForeverTees = goneForeverTees.map(TeeDO::toDataModel),
    lastChanceTees = lastChanceTees.map(TeeDO::toDataModel)
)