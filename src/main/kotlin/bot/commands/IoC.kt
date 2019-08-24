package bot.commands

import org.koin.dsl.module

val botCommandsModule = module {
    factory { StartCommand() }
    factory { GoneForeverTeesCommand(get()) }
    factory { LastChanceTeesCommand(get()) }
    factory { PromotedTeesCommand(get()) }
    factory { SubscribeCommand(get()) }
    factory { UnsubscribeCommand(get()) }
    factory { HelpCommand() }
}