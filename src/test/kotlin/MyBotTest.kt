import bot.MyBot
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import me.ivmg.telegram.Bot
import org.junit.Test

class MyBotTest {

    private val bot: Bot = mock()

    private val myBot: MyBot = MyBot(bot)

    @Test
    fun `Should start polling when 'runWithPolling()' is called`() {
        whenRunWithPollingIsCalled()

        thenBotStartsPolling()
    }

    private fun whenRunWithPollingIsCalled() {
        myBot.runWithPolling()
    }

    private fun thenBotStartsPolling() {
        verify(bot).startPolling()
    }
}