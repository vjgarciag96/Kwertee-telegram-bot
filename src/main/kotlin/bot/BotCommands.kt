package bot

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

typealias CommandAction = (Bot, Update, List<String>) -> Unit

abstract class BotCommand(
        val commandName: String = "default",
        val commandAction: CommandAction
)

class HelloWorldCommand: BotCommand("helloWorld", object : CommandAction {
    override fun invoke(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            bot.sendMessage(it.chat.id, "Hello World!!")
        }
    }
})