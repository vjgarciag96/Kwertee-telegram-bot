import domain.GetGoneForeverTShirts
import domain.GetSubscriptions
import domain.PublishGoneForeverTShirts
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import model.TShirt
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class FetchGoneForeverTShirtsTask(
    private val getGoneForeverTShirts: GetGoneForeverTShirts,
    private val publishGoneForeverTShirts: PublishGoneForeverTShirts,
    private val getSubscriptions: GetSubscriptions
) : TimerTask() {

    private var rxSubscription: Disposable = Disposables.empty()

    override fun run() {
        Logger.getLogger("bot").log(Level.INFO, "Running scheduled task")
        rxSubscription = getGoneForeverTShirts()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                ::onGetGoneForeverTShirtsSuccess,
                ::onGetGoneForeverTShirtsError
            )
    }

    private fun onGetGoneForeverTShirtsSuccess(tShirts: List<TShirt>) {
        val subscriptions = getSubscriptions()

        subscriptions.forEach { subscription ->
            publishGoneForeverTShirts(tShirts, subscription.userId)
        }

        rxSubscription.dispose()
    }

    private fun onGetGoneForeverTShirtsError(throwable: Throwable) {
        Logger.getLogger("bot").log(Level.WARNING, "Error fetching t-shirts $throwable")
        rxSubscription.dispose()
    }

    override fun cancel(): Boolean {
        val cancelResult = super.cancel()
        rxSubscription.dispose()
        return cancelResult
    }
}