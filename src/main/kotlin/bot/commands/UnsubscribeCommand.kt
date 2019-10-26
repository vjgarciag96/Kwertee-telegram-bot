package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message
import subscriptions.domain.Unsubscribe

class UnsubscribeCommand(
    private val unsubscribe: Unsubscribe
) : BotCommand {

    override val name: String
        get() = "unsubscribe"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        val unsubscribeResult = unsubscribe(command.chat.id)

        if (unsubscribeResult) {
            bot.sendMessage(command.chat.id, "You'll stop receiving notifications " +
                "about Qwertee promoted t-shirts. If you want to check " +
                "the currently available promoted t-shirts, run /promoted command. If you want to " +
                "restart receiving the t-shirt updates again, run the /subscribe command")
        } else {
            bot.sendMessage(command.chat.id, "You are not subscribed to updates, so you won't receive them")
        }
    }
}