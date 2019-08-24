package tees.data.local

import org.jetbrains.exposed.sql.ResultRow
import tees.data.local.exposed.TeesTable
import tees.data.remote.PromotedTeesDto
import tees.data.remote.TeeDto

fun PromotedTeesDto.toDO(): PromotedTeesDO = PromotedTeesDO(
    timeToLive = timeToLive,
    goneForeverTees = goneForeverTees.map(TeeDto::toDO),
    lastChanceTees = lastChanceTees.map(TeeDto::toDO)
)

fun TeeDto.toDO(): TeeDO = TeeDO(
    eurPrice = eurPrice.toInt(),
    gbpPrice = gbpPrice.toInt(),
    usdPrice = usdPrice.toInt(),
    title = title,
    imageUrl = imageUrl
)

fun ResultRow.toTeeDO(): TeeDO = TeeDO(
    eurPrice = this[TeesTable.eurPrice],
    usdPrice = this[TeesTable.usdPrice],
    gbpPrice = this[TeesTable.gbpPrice],
    title = this[TeesTable.title],
    imageUrl = this[TeesTable.imageUrl]
)

fun ResultRow.getTimeToLive(): Int = this[TeesTable.timeToLive]
fun ResultRow.getStorageTimestamp(): Long = this[TeesTable.storageTimestamp]