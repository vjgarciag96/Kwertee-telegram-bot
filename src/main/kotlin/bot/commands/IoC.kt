package bot.commands

import org.koin.dsl.module

val botCommandsModule = module {
    factory { StartCommand() }
    factory { GoneForeverTShirtsCommand(get()) }
    factory { SubscribeCommand(get()) }
    factory { UnsubscribeCommand(get()) }
    factory { HelpCommand() }
}