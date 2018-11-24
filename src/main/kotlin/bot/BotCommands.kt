package bot

import domain.GetGoneForeverTShirts
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

class StartCommand : BotCommand("start",
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
) : BotCommand("goneforever",
        object : CommandAction {
            override fun invoke(bot: Bot, update: Update, args: List<String>) {
                getGoneForeverTShirts.invoke()
                        .subscribe {
                            it.forEach {
                                val tShirt = it
                                update.message?.let {
                                    bot.sendPhoto(
                                            it.chat.id,
                                            tShirt.imageUrl,
                                            caption = tShirt.title
                                                    .plus("\n")
                                                    .plus("Price:${tShirt.eurPrice}|${tShirt.usdPrice}|${tShirt.gbpPrice}"))
                                }
                            }
                        }
            }
        })