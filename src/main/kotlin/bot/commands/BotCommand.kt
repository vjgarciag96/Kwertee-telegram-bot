package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

interface BotCommand {
    val name: String
    fun action(bot: Bot, update: Update, args: List<String>)
}



