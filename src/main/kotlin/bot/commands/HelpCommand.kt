package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

class HelpCommand : BotCommand {
    override val name: String
        get() = "help"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            bot.sendMessage(it.chat.id, "The bot's way of working is really simple. " +
                "To watch the currently available promoted t-shirts, run the /promoted command. " +
                "To receive updates every day, run /subscribe. " +
                "Once subscribed, if you want to stop receiving notifications, run the /unsubscribe command. " +
                "And that's all, have fun!! :)")
        }
    }
}