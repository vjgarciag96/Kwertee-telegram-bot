package tees.domain

class FetchPromotedTees(private val teesBL: TeesBL) {

    suspend operator fun invoke(): PromotedTees = teesBL.fetchPromoted()
}