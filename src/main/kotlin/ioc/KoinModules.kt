package ioc

import bot.GoneForeverTShirtsCommand
import bot.MyBot
import bot.StartCommand
import bot.SubscribeCommand
import data.local.SubscriptionsLocalDataSource
import data.local.exposed.SetUpDatabase
import data.remote.QwerteeWebScrapper
import data.remote.WebScrapper
import data.repository.SubscriptionsRepository
import data.repository.TShirtRepository
import domain.*
import ioc.BotTokenProperty.BOT_TOKEN
import ioc.BotTokenProperty.QWERTEE_URL
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.jetbrains.exposed.sql.Database
import org.jsoup.Connection
import org.jsoup.Jsoup
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

    // use cases
    factory { SendPhotoMessage(get()) }
    factory { SendTextMessage(get()) }
    factory { GetGoneForeverTShirts(get()) }
    factory { Subscribe(get()) }
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
    factory { WebScrapper() }
    factory { QwerteeWebScrapper(get(), Jsoup.connect(getProperty(QWERTEE_URL)).method(Connection.Method.GET)) }

    // bot
    single {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN)
            this.updater.dispatcher.apply {
                val startCommand: StartCommand = get()
                val goneForeverTShirtsCommand: GoneForeverTShirtsCommand = get()
                val subscribeCommand: SubscribeCommand = get()
                command(startCommand.name, startCommand::action)
                command(goneForeverTShirtsCommand.name, goneForeverTShirtsCommand::action)
                command(subscribeCommand.name, subscribeCommand::action)
            }
        }.build())
    }
}