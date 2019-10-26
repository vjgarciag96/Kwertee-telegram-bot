package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message

interface BotCommand {
    val name: String
    fun action(bot: Bot, command: Message, args: List<String>)
}