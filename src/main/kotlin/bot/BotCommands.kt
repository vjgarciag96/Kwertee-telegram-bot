package bot

import domain.GetGoneForeverTShirts
import domain.Subscribe
import domain.toTextMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update

typealias CommandAction = (Bot, Update, List<String>) -> Unit

abstract class BotCommand(
        val commandName: String = "default",
        val commandAction: CommandAction
)

class HelloWorldCommand : BotCommand("helloworld",
        object : CommandAction {
            override fun invoke(bot: Bot, update: Update, args: List<String>) {
                update.message?.let {
                    bot.sendMessage(it.chat.id, "Hello World!!")
                }
            }
        })

class StartCommand : BotCommand(
        "start",
        object : CommandAction {
            override fun invoke(bot: Bot, update: Update, args: List<String>) {
                update.message?.let {
                    bot.sendMessage(it.chat.id,
                            "Welcome to Qweerte t-shirts bot. In this bot you will see offers from https://www.qwertee.com/. For more info about the features of this bot, run the /help command")
                }
            }

        }
)

class GoneForeverTShirtsCommand(
        private val getGoneForeverTShirts: GetGoneForeverTShirts
) : BotCommand(
        "goneforever",
        object : CommandAction {
            override fun invoke(bot: Bot, update: Update, args: List<String>) {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val tShirts = getGoneForeverTShirts()
                        tShirts.forEach { tShirt ->
                            update.message?.let { message ->
                                bot.sendPhoto(message.chat.id, tShirt.imageUrl, tShirt.toTextMessage()
                                )
                            }
                        }
                    }
                }
            }
        })

class SubscribeCommand(
        private val subscribe: Subscribe
) : BotCommand(
        "subscribe",
        object : CommandAction {
            override fun invoke(bot: Bot, update: Update, args: List<String>) {
                update.message?.let {
                    subscribe(it.chat.id, it.chat.username ?: "unknown")
                }

            }
        }
)