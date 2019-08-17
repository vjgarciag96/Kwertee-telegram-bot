package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import subscriptions.domain.Unsubscribe

class UnsubscribeCommand(
    private val unsubscribe: Unsubscribe
) : BotCommand {

    override val name: String
        get() = "unsubscribe"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            val unsubscribeResult = unsubscribe(it.chat.id)

            if (unsubscribeResult) {
                bot.sendMessage(it.chat.id, "You'll stop receiving notifications " +
                    "about Qwertee 'gone forever' t-shirts. If you want to check " +
                    "the currently available 'gone forever' t-shirts, run /goneforever command. If you want to " +
                    "restart receiving the t-shirt updates again, run the /subscribe command")
            } else {
                bot.sendMessage(it.chat.id, "You are not subscribed to updates, so you won't receive them")
            }
        }
    }

}