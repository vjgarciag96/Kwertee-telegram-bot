package tees.domain

import tees.data.repository.TeeData

typealias Tee = TeeData

data class PromotedTees(
    val goneForeverTees: List<Tee>,
    val lastChanceTees: List<Tee>
)