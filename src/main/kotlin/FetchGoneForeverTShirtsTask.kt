import domain.GetGoneForeverTShirts
import domain.GetSubscriptions
import domain.PublishGoneForeverTShirts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class FetchGoneForeverTShirtsTask(
        private val getGoneForeverTShirts: GetGoneForeverTShirts,
        private val publishGoneForeverTShirts: PublishGoneForeverTShirts,
        private val getSubscriptions: GetSubscriptions
) : TimerTask() {

    override fun run() {
        Logger.getLogger("bot").log(Level.INFO, "Running scheduled task")
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val tShirts = getGoneForeverTShirts()
                val subscriptions = getSubscriptions()

                subscriptions.forEach { subscription ->
                    publishGoneForeverTShirts(tShirts, subscription.userId)
                }
            }
        }
    }
}