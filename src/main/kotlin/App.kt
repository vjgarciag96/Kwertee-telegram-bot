
import BotTokenProperty.BOT_TOKEN
import BotTokenProperty.QWERTEE_URL
import bot.*
import data.SubscriptionsLocalDataSource
import data.SubscriptionsRepository
import domain.GetGoneForeverTShirts
import domain.GetSubscriptions
import domain.PublishGoneForeverTShirts
import domain.Subscribe
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import model.GoneForeverTShirtDTOToGoneForeverTShirtMapper
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module
import webscrapper.QwerteeWebScrapper
import webscrapper.RxJsoupWebScrapper
import webscrapper.RxWebScrapper

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
    const val QWERTEE_URL = "qwertee-url-property"
}

val myBotModule = module {
    // commands
    factory { HelloWorldCommand() }
    factory { StartCommand() }
    factory { GoneForeverTShirtsCommand(get()) }
    factory { SubscribeCommand(get()) }

    // use cases
    factory { GetGoneForeverTShirts(get(), get()) }
    factory { Subscribe(get()) }
    factory { GetSubscriptions(get()) }
    factory { PublishGoneForeverTShirts(get()) }
    factory { FetchGoneForeverTShirtsTask(get(), get(), get()) }
    factory { FetchGoneForeverTShirts(get()) }

    // data
    single { SubscriptionsRepository(get()) }
    factory { SubscriptionsLocalDataSource() }

    // mapper
    factory { GoneForeverTShirtDTOToGoneForeverTShirtMapper() }

    // web scrapper
    factory { RxJsoupWebScrapper() as RxWebScrapper }
    factory { QwerteeWebScrapper(get(), Jsoup.connect(getProperty(QWERTEE_URL)).method(Connection.Method.GET)) }

    // bot
    single {
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
    private val fetchGoneForeverTShirts by inject<FetchGoneForeverTShirts>()

    init {
        fetchGoneForeverTShirts()
        bot.runWithPolling()
    }
}

fun main(args: Array<String>) {
    startKoin {
        fileProperties()
        modules(myBotModule)
    }
    BotApplication()

}
