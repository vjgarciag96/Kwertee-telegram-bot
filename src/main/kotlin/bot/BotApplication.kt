package bot

import core.SetUpDatabase
import tees.domain.PublishFreshTeesTask
import org.koin.core.KoinComponent
import org.koin.core.inject

object BotApplication : KoinComponent {

    private val bot by inject<MyBot>()
    private val publishFreshTeesTask by inject<PublishFreshTeesTask>()
    private val setUpDatabase by inject<SetUpDatabase>()

    fun run() {
        setUpDatabase()
        publishFreshTeesTask()
        bot.runWithPolling()
    }
}