package ioc

import bot.*
import data.local.SubscriptionsLocalDataSource
import data.local.exposed.SetUpDatabase
import data.remote.JsoupHtmlParser
import data.remote.QwerteeWebScrapper
import data.repository.SubscriptionsRepository
import data.repository.TShirtRepository
import domain.*
import ioc.BotTokenProperty.BOT_TOKEN
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import okhttp3.OkHttpClient
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

object BotTokenProperty {
    const val BOT_TOKEN = "bot-token-property"
    const val QWERTEE_URL = "qwertee-url-property"
}

val myBotModule = module {
    // commands
    factory { StartCommand() }
    factory { GoneForeverTShirtsCommand(get()) }
    factory { SubscribeCommand(get()) }
    factory { UnsubscribeCommand(get()) }
    factory { HelpCommand() }

    // use cases
    factory { SendPhotoMessage(get()) }
    factory { SendTextMessage(get()) }
    factory { GetGoneForeverTShirts(get()) }
    factory { Subscribe(get()) }
    factory { Unsubscribe(get()) }
    factory { GetSubscriptions(get()) }
    factory { PublishGoneForeverTShirts(get()) }
    factory { FetchGoneForeverTShirtsTask(get(), get(), get()) }
    factory { FetchGoneForeverTShirts(get()) }

    // data
    single { SubscriptionsRepository(get()) }
    single {
        // Provides SQLite in file DB
        Database.connect("jdbc:sqlite:src/main/resources/qwertee.db", "org.sqlite.JDBC")
    }
    factory { SubscriptionsLocalDataSource() }
    single { TShirtRepository(get()) }
    factory { SetUpDatabase(get()) }

    // web scrapper
    factory { JsoupHtmlParser() }
    factory { OkHttpClient.Builder().build() }
    factory { QwerteeWebScrapper(get(), get(), get()) }

    // bot
    single {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN)
            this.updater.dispatcher.apply {
                val startCommand: StartCommand = get()
                val goneForeverTShirtsCommand: GoneForeverTShirtsCommand = get()
                val subscribeCommand: SubscribeCommand = get()
                val unsubscribeCommand: UnsubscribeCommand = get()
                val helpCommand: HelpCommand = get()
                command(startCommand.name, startCommand::action)
                command(goneForeverTShirtsCommand.name, goneForeverTShirtsCommand::action)
                command(subscribeCommand.name, subscribeCommand::action)
                command(unsubscribeCommand.name, unsubscribeCommand::action)
                command(helpCommand.name, helpCommand::action)
            }
        }.build())
    }
}