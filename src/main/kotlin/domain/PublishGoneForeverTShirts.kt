package domain

class PublishGoneForeverTShirts(private val sendPhotoMessage: SendPhotoMessage) {

    operator fun invoke(tShirts: List<TShirt>, chatId: Long) {
        tShirts.forEach { tShirt ->
            sendPhotoMessage(chatId, tShirt.imageUrl, tShirt.toTextMessage())
        }
    }
}