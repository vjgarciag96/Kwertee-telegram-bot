package tees.domain

class PublishPromotedTees(
    private val publishGoneForeverTees: PublishGoneForeverTees,
    private val publishLastChanceTees: PublishLastChanceTees
) {

    operator fun invoke(chatId: Long, promotedTees: PromotedTees) {
        publishGoneForeverTees(chatId, promotedTees.goneForeverTees)
        publishLastChanceTees(chatId, promotedTees.goneForeverTees)
    }
}