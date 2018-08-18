package bot

import me.ivmg.telegram.Bot

class MyBot(private val bot: Bot) {
    fun runWithPolling() {
        bot.startPolling()
    }
}