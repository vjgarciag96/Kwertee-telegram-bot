package bot

import me.ivmg.telegram.Bot

class MyBot(private val bot: Bot) {

    fun sendTextMessage(chatId: Long, text: String) {
        bot.sendMessage(chatId, text)
    }

    fun sendPhoto(chatId: Long, photoUrl: String, text: String) {
        bot.sendPhoto(chatId, photoUrl, text)
    }

    fun runWithPolling() {
        bot.startPolling()
    }
}