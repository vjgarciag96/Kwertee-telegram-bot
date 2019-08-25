package bot

import bot.actions.botActionsModule
import bot.commands.GoneForeverTeesCommand
import bot.commands.LastChanceTeesCommand
import bot.commands.PromotedTeesCommand
import bot.commands.StartCommand
import bot.commands.SubscribeCommand
import bot.commands.UnsubscribeCommand
import bot.commands.HelpCommand
import bot.commands.botCommandsModule
import org.koin.dsl.module

const val BOT_TOKEN_CONFIG_FIELD_KEY = "bot-token-property"

val botModule = module {
    single {
        val startCommand: StartCommand = get()
        val goneForeverTeesCommand: GoneForeverTeesCommand = get()
        val lastChanceTeesCommand: LastChanceTeesCommand = get()
        val promotedTeesCommand: PromotedTeesCommand = get()
        val subscribeCommand: SubscribeCommand = get()
        val unsubscribeCommand: UnsubscribeCommand = get()
        val helpCommand: HelpCommand = get()

        val bot = bot.get(
            getProperty(BOT_TOKEN_CONFIG_FIELD_KEY),
            startCommand,
            goneForeverTeesCommand,
            lastChanceTeesCommand,
            promotedTeesCommand,
            subscribeCommand,
            unsubscribeCommand,
            helpCommand
        )
        MyBot(bot)
    }
}

val botModules = botActionsModule + botCommandsModule + botModule