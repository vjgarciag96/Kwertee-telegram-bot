package tees.domain

import bot.actions.SendPhotoMessage
import bot.actions.SendTextMessage

class PublishGoneForeverTees(
    private val sendTextMessage: SendTextMessage,
    private val sendPhotoMessage: SendPhotoMessage
) {

    operator fun invoke(chatId: Long, tShirts: List<Tee>) {
        sendTextMessage(chatId, "Gone forever tees!")
        tShirts.forEach { tShirt ->
            sendPhotoMessage(chatId, tShirt.imageUrl, tShirt.toTextMessage())
        }
    }
}