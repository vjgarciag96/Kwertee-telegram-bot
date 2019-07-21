package bot

import data.local.exposed.SetUpDatabase
import domain.FetchGoneForeverTShirts
import org.koin.core.KoinComponent
import org.koin.core.inject

object BotApplication : KoinComponent {

    private val bot by inject<MyBot>()
    private val fetchGoneForeverTShirts by inject<FetchGoneForeverTShirts>()
    private val setUpDatabase by inject<SetUpDatabase>()

    fun run() {
        setUpDatabase()
        fetchGoneForeverTShirts()
        bot.runWithPolling()
    }
}