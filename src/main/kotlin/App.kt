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
import model.GoneForeverTShirtDTOToGoneForeverTShirtMapper

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
    const val QWERTEE_URL = "qwertee-url-property"
}

val myBotModule = applicationContext {
    // commands
    bean { HelloWorldCommand() }
    bean { GoneForeverTShirtsCommand(get()) }

    // use cases
    bean { GetGoneForeverTShirts(get(), get()) }

    // mapper
    bean { GoneForeverTShirtDTOToGoneForeverTShirtMapper() }

    // web scrapper
    bean {
        QwerteeWebScrapper(Jsoup.connect(getProperty(QWERTEE_URL))
                .method(Connection.Method.GET))
    }

    // bot
    bean {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN)
            this.updater.dispatcher.apply {
                val helloWorldCommand: HelloWorldCommand = get()
                val goneForeverTShirtsCommand: GoneForeverTShirtsCommand = get()
                command(helloWorldCommand.commandName, helloWorldCommand.commandAction)
                command(goneForeverTShirtsCommand.commandName, goneForeverTShirtsCommand.commandAction)
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
    startKoin(listOf(myBotModule), extraProperties = mapOf(
            BOT_TOKEN to "ANY_BOT_TOKEN",
            QWERTEE_URL to "https://www.qwertee.com"))
    BotApplication()
}

