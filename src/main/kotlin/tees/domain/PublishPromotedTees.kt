package tees.domain

class PublishPromotedTees(
    private val publishGoneForeverTees: PublishGoneForeverTees,
    private val publishLastChanceTees: PublishLastChanceTees
) {

    operator fun invoke(chatId: Long, tShirts: List<Tee>) {
        publishGoneForeverTees(chatId, tShirts)
        publishLastChanceTees(chatId, tShirts)
    }
}