import BotTokenProperty.BOT_TOKEN
import BotTokenProperty.QWERTEE_URL
import bot.*
import data.LocalSubscriptionsDataSource
import data.SubscriptionsRepository
import domain.GetGoneForeverTShirts
import domain.Subscribe
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
import webscrapper.RxJsoupWebScrapper
import webscrapper.RxWebScrapper

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
    const val QWERTEE_URL = "qwertee-url-property"
}

val myBotModule = applicationContext {
    // commands
    bean { HelloWorldCommand() }
    bean { StartCommand() }
    bean { GoneForeverTShirtsCommand(get()) }
    bean { SubscribeCommand(get()) }

    // use cases
    bean { GetGoneForeverTShirts(get(), get()) }
    bean { Subscribe(get()) }

    // data
    bean { SubscriptionsRepository(get()) }
    bean { LocalSubscriptionsDataSource() }

    // mapper
    bean { GoneForeverTShirtDTOToGoneForeverTShirtMapper() }

    // web scrapper
    bean { RxJsoupWebScrapper() as RxWebScrapper }
    bean {
        QwerteeWebScrapper(get(), Jsoup.connect(getProperty(QWERTEE_URL))
                .method(Connection.Method.GET))
    }

    // bot
    bean {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN)
            this.updater.dispatcher.apply {
                val helloWorldCommand: HelloWorldCommand = get()
                val startCommand: StartCommand = get()
                val goneForeverTShirtsCommand: GoneForeverTShirtsCommand = get()
                val subscribeCommand: SubscribeCommand = get()
                command(helloWorldCommand.commandName, helloWorldCommand.commandAction)
                command(startCommand.commandName, startCommand.commandAction)
                command(goneForeverTShirtsCommand.commandName, goneForeverTShirtsCommand.commandAction)
                command(subscribeCommand.commandName, subscribeCommand.commandAction)
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
            BOT_TOKEN to Config.BOT_TOKEN,
            QWERTEE_URL to "https://www.qwertee.com"))
    BotApplication()
}
