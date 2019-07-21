package domain

import bot.MyBot

class SendTextMessage(private val bot: MyBot) {

    operator fun invoke(chatId: Long, text: String) {
        bot.sendTextMessage(chatId, text)
    }
}