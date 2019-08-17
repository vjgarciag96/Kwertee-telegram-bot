package bot.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import subscriptions.domain.Subscribe
import subscriptions.domain.Unsubscribe
import tees.domain.FetchGoneForeverTees
import tees.domain.toTextMessage


interface BotCommand {
    val name: String
    fun action(bot: Bot, update: Update, args: List<String>)
}

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

class HelpCommand : BotCommand {
    override val name: String
        get() = "help"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        update.message?.let {
            bot.sendMessage(it.chat.id, "The bot's way of working is really simple. " +
                "To watch the currently available 'gone forever' t-shirts, run the /goneforever command. " +
                "To receive updates every day, run /subscribe. " +
                "Once subscribed, if you want to stop receiving notifications, run the /unsubscribe command. " +
                "And that's all, have fun!! :)")
        }
    }

}

class GoneForeverTShirtsCommand(
    private val fetchGoneForeverTees: FetchGoneForeverTees
) : BotCommand {

    override val name: String
        get() = "goneforever"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = fetchGoneForeverTees()
            tShirts.forEach { tShirt ->
                update.message?.let { message ->
                    bot.sendPhoto(message.chat.id, tShirt.imageUrl, tShirt.toTextMessage())
                }
            }
        }
    }
}

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
                    "the updated 'gone forever' t-shirts from Qwertee website. " +
                    "If you want to check the current 'gone forever' t-shirts," +
                    " run the command /goneforever")
            } else {
                bot.sendMessage(it.chat.id, "You are already subscribed")
            }
        }
    }
}

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