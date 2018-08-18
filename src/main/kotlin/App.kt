import BotTokenProperty.BOT_TOKEN
import BotTokenProperty.QWERTEE_URL
import bot.*
import domain.GetGoneForeverTShirts
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.koin.dsl.module.applicationContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import webscrapper.QwerteeWebScrapper
import webscrapper.mapper.GoneForeverTShirtDTOToGoneForeverTShirtMapper

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
    const val QWERTEE_URL = "qwertee-url-property"
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
    bean {
        QwerteeWebScrapper(Jsoup.connect(getProperty(QWERTEE_URL))
                .method(Connection.Method.GET))
    }
    bean { GoneForeverTShirtDTOToGoneForeverTShirtMapper() }
    bean { GetGoneForeverTShirts(get(), get()) }
}

class BotApplication : KoinComponent {

    private val bot by inject<MyBot>()

    init {
        bot.runWithPolling()
    }
}

fun main(args: Array<String>) {
    startKoin(listOf(myBotModule), extraProperties = mapOf(
            BOT_TOKEN to "ANY_BOT_TOKEN",
            QWERTEE_URL to "https://www.qwertee.com"))
    BotApplication()
}

