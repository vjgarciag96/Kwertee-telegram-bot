package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message

class StartCommand : BotCommand {
    override val name: String
        get() = "start"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        bot.sendMessage(
            command.chat.id,
            "Welcome to Qweerte t-shirts bot. In this bot you will see " +
                "information about 'gone forever' t-shirts from https://www.qwertee.com/. " +
                "For more info about this bot's features, run the /help command")
    }
}