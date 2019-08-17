package bot.actions

import bot.MyBot

class SendPhotoMessage(private val bot: MyBot) {

    operator fun invoke(chatId: Long, photoUrl: String, photoText: String) {
        bot.sendPhoto(chatId, photoUrl, photoText)
    }
}