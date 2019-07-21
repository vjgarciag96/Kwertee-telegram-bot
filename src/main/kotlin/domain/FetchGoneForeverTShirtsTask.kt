package domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.TimerTask
import java.util.logging.Level
import java.util.logging.Logger

class FetchGoneForeverTShirtsTask(
    private val getGoneForeverTShirts: GetGoneForeverTShirts,
    private val publishGoneForeverTShirts: PublishGoneForeverTShirts,
    private val getSubscriptions: GetSubscriptions
) : TimerTask() {

    override fun run() {
        Logger.getLogger("bot").log(Level.INFO, "Running scheduled task")
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = getGoneForeverTShirts()
            val subscriptions = getSubscriptions()

            subscriptions.forEach { subscription ->
                publishGoneForeverTShirts(tShirts, subscription.userId)
            }
        }
    }
}