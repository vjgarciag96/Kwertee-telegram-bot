package tees.domain

import subscriptions.domain.GetSubscriptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class FetchTeesTask(
    private val fetchGoneForeverTees: FetchGoneForeverTees,
    private val publishGoneForeverTees: PublishGoneForeverTees,
    private val getSubscriptions: GetSubscriptions
) {

    operator fun invoke() {
        Logger.getLogger("bot").log(Level.INFO, "Running scheduled task")
        CoroutineScope(Dispatchers.IO).launch {
            val tees = fetchGoneForeverTees()
            val subscriptions = getSubscriptions()

            subscriptions.forEach { subscription ->
                publishGoneForeverTees(subscription.userId, tees)
            }
        }
    }
}