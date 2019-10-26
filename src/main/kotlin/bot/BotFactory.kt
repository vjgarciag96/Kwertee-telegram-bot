package bot

import bot.commands.BotCommand
import me.ivmg.telegram.Bot
import me.ivmg.telegram.dispatcher.command

fun get(botToken: String, vararg commands: BotCommand): Bot = Bot.Builder()
    .apply {
        this.token = botToken
        this.updater.dispatcher.apply {
            commands.forEach {botCommand ->
                command(botCommand.name) { bot, update, args ->
                    val command = update.message
                    checkNotNull(command)
                    botCommand.action(bot, command, args)
                }
            }
        }
    }.build()