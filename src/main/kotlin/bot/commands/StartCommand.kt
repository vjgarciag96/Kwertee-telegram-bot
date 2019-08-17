package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

class StartCommand : BotCommand {
    override val name: String
        get() = "start"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            bot.sendMessage(
                it.chat.id,
                "Welcome to Qweerte t-shirts bot. In this bot you will see " +
                    "information about 'gone forever' t-shirts from https://www.qwertee.com/. " +
                    "For more info about this bot's features, run the /help command")
        }
    }
}