package bot

import domain.GetGoneForeverTShirts
import domain.Subscribe
import domain.toTextMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

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
                "Welcome to Qweerte t-shirts bot. In this bot you will see offers from https://www.qwertee.com/. For more info about the features of this bot, run the /help command")
        }
    }
}

class GoneForeverTShirtsCommand(
    private val getGoneForeverTShirts: GetGoneForeverTShirts
) : BotCommand {

    override val name: String
        get() = "goneforever"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = getGoneForeverTShirts()
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
            subscribe(it.chat.id, it.chat.username ?: "unknown")
        }
    }

}