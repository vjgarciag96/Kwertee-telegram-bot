import bot.BotApplication
import ioc.myBotModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        fileProperties()
        modules(myBotModule)
    }

    BotApplication.run()
}
