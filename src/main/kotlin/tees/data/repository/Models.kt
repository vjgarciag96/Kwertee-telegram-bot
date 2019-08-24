package tees.data.repository

import tees.data.local.TeeDO

typealias TeeData = TeeDO
data class PromotedTeesData(
    val timeToLive: Int,
    val goneForeverTees: List<TeeData>,
    val lastChanceTees: List<TeeData>
)