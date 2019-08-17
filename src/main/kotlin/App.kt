import bot.BotApplication
import bot.botModules
import org.koin.core.context.startKoin
import subscriptions.subscriptionsModules
import tees.teesModules

fun main(args: Array<String>) {
    startKoin {
        fileProperties()
        modules(botModules + subscriptionsModules + teesModules)
    }

    BotApplication.run()
}
