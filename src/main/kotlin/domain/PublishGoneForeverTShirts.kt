package domain

import bot.MyBot
import model.TShirt

class PublishGoneForeverTShirts(private val bot: MyBot) {

    operator fun invoke(tShirts: List<TShirt>, chatId: Long) {
        tShirts.forEach { tShirt ->
            bot.sendPhoto(chatId, tShirt.imageUrl, tShirt.toTextMessage())
        }
    }
}