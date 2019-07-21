import BotTokenProperty.BOT_TOKEN
import BotTokenProperty.QWERTEE_URL
import bot.*
import data.SubscriptionsRepository
import data.TShirtRepository
import data.local.SubscriptionsLocalDataSource
import data.remote.QwerteeWebScrapper
import data.remote.WebScrapper
import domain.GetGoneForeverTShirts
import domain.GetSubscriptions
import domain.PublishGoneForeverTShirts
import domain.Subscribe
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.koin.dsl.module

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
    factory { GetGoneForeverTShirts(get()) }
    factory { Subscribe(get()) }
    factory { GetSubscriptions(get()) }
    factory { PublishGoneForeverTShirts(get()) }
    factory { FetchGoneForeverTShirtsTask(get(), get(), get()) }
    factory { FetchGoneForeverTShirts(get()) }

    // data
    single { SubscriptionsRepository(get()) }
    factory { SubscriptionsLocalDataSource() }
    single { TShirtRepository(get()) }

    // web scrapper
    factory { WebScrapper() }
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
                command(helloWorldCommand.name, helloWorldCommand::action)
                command(startCommand.name, startCommand::action)
                command(goneForeverTShirtsCommand.name, goneForeverTShirtsCommand::action)
                command(subscribeCommand.name, subscribeCommand::action)
            }
        }.build())
    }
}