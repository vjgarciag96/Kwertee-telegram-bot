package bot

import subscriptions.data.local.SetUpDatabase
import tees.domain.ScheduleTeesFetching
import org.koin.core.KoinComponent
import org.koin.core.inject

object BotApplication : KoinComponent {

    private val bot by inject<MyBot>()
    private val scheduleTeesFetching by inject<ScheduleTeesFetching>()
    private val setUpDatabase by inject<SetUpDatabase>()

    fun run() {
        setUpDatabase()
        scheduleTeesFetching()
        bot.runWithPolling()
    }
}