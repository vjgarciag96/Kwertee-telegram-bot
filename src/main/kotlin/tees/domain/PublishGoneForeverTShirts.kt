package tees.domain

import bot.actions.SendPhotoMessage

class PublishGoneForeverTShirts(private val sendPhotoMessage: SendPhotoMessage) {

    operator fun invoke(tShirts: List<Tee>, chatId: Long) {
        tShirts.forEach { tShirt ->
            sendPhotoMessage(chatId, tShirt.imageUrl, tShirt.toTextMessage())
        }
    }
}