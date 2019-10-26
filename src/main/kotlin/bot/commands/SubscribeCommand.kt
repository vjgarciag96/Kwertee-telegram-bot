package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message
import subscriptions.domain.Subscribe

class SubscribeCommand(
    private val subscribe: Subscribe
) : BotCommand {

    override val name: String
        get() = "subscribe"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        val subscribeResult = subscribe(command.chat.id, command.chat.username ?: "unknown")

        if (subscribeResult) {
            bot.sendMessage(command.chat.id, "You'll be notified every day with " +
                "the promoted t-shirts from Qwertee website. " +
                "If you want to check the current promoted t-shirts," +
                " run the command /promoted")
        } else {
            bot.sendMessage(command.chat.id, "You are already subscribed")
        }
    }
}