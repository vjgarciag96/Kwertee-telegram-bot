import bot.BotApplication
import bot.botModules
import core.coreModule
import org.koin.core.context.startKoin
import publications.publicationsModule
import subscriptions.subscriptionsModules
import tees.teesModules

fun main(args: Array<String>) {
    startKoin {
        fileProperties()
        modules(botModules + coreModule + publicationsModule + subscriptionsModules + teesModules)
    }

    BotApplication.run()
}
