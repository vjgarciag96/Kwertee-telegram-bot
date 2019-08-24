package bot.commands

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import subscriptions.domain.Subscribe

class SubscribeCommand(
    private val subscribe: Subscribe
) : BotCommand {

    override val name: String
        get() = "subscribe"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            val subscribeResult = subscribe(it.chat.id, it.chat.username ?: "unknown")

            if (subscribeResult) {
                bot.sendMessage(it.chat.id, "You'll be notified every day with " +
                    "the promoted t-shirts from Qwertee website. " +
                    "If you want to check the current promoted t-shirts," +
                    " run the command /promoted")
            } else {
                bot.sendMessage(it.chat.id, "You are already subscribed")
            }
        }
    }
}