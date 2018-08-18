import BotTokenProperty.BOT_TOKEN
import bot.*
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.koin.dsl.module.applicationContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
}

val myBotModule = applicationContext {
    bean { HelloWorldCommand() }
    bean {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN)
            this.updater.dispatcher.apply {
                val helloWorldCommand: HelloWorldCommand = get()
                command(helloWorldCommand.commandName, helloWorldCommand.commandAction)
            }
        }.build())
    }

}

class BotApplication : KoinComponent {

    private val bot by inject<MyBot>()

    init {
        bot.runWithPolling()
    }
}

fun main(args: Array<String>) {
    startKoin(listOf(myBotModule), extraProperties = mapOf(BOT_TOKEN to "ANY_BOT_TOKEN"))
    BotApplication()
}

