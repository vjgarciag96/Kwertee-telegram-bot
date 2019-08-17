package bot

import bot.actions.botActionsModule
import bot.commands.*
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command
import org.koin.dsl.module

const val BOT_TOKEN_CONFIG_FIELD_KEY = "bot-token-property"

val botModule = module {
    single {
        MyBot(Bot.Builder().apply {
            this.token = getProperty(BOT_TOKEN_CONFIG_FIELD_KEY)
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

val botModules = botActionsModule + botCommandsModule + botModule