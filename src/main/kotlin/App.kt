import bot.MyBot
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class BotApplication : KoinComponent {

    private val bot by inject<MyBot>()
    private val fetchGoneForeverTShirts by inject<FetchGoneForeverTShirts>()

    init {
        fetchGoneForeverTShirts()
        bot.runWithPolling()
    }
}

fun main() {
    startKoin {
        fileProperties()
        modules(myBotModule)
    }
    BotApplication()

}
