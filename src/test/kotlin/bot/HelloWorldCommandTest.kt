package bot

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.*
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Chat
import me.ivmg.telegram.entities.Message
import me.ivmg.telegram.entities.Update
import myBotModule
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest


class HelloWorldCommandTest : AutoCloseKoinTest() {

    private val bot: Bot = mock()
    private val update: Update = mock()
    private val message: Message = mock()
    private val chat: Chat = mock()

    private val helloWorldCommand by inject<HelloWorldCommand>()

    @Before
    fun before() {
        startKoin(listOf(myBotModule))
    }

    @Test
    fun `Should have helloWorld as command name`() {
        assertThat(helloWorldCommand.commandName, equalTo("helloWorld"))
    }

    @Test
    fun `Should send a message to the chat that invoked the command`() {
        givenAMockedMessage()
        givenAMockedUpdateWithMessage()

        whenHelloWorldCommandIsInvoked()

        thenAMessageIsSentToTheChatWhichInvokedTheCommand()
    }

    @Test
    fun `Should send a message with 'Hello World!!' as content when the command is invoked`() {
        givenAMockedMessage()
        givenAMockedUpdateWithMessage()

        whenHelloWorldCommandIsInvoked()

        thenAMessageIsSentWithAppropiateContent()
    }

    @Test
    fun `Should not send a message when the message of the command is empty`() {
        whenHelloWorldCommandIsInvoked()

        thenTheCommandDoesntSendAMessage()
    }

    private fun givenAMockedMessage() {
        whenever(message.chat).thenReturn(chat)
        whenever(chat.id).thenReturn(ANY_CHAT_ID)
    }

    private fun givenAMockedUpdateWithMessage() {
        whenever(update.message).thenReturn(message)
    }

    private fun whenHelloWorldCommandIsInvoked() {
        helloWorldCommand.commandAction.invoke(bot, update, emptyList())
    }

    private fun thenAMessageIsSentToTheChatWhichInvokedTheCommand() {
        /* The last five args are added for avoiding an InvalidUseOfMatchersException
        related to the number of parameters. This is because when we mock a method with
        optional parameters with mockito we need to provide a value for every
        parameter due to the way mockito is generating the mock.
        More info at: https://github.com/nhaarman/mockito-kotlin/issues/174 */
        verify(bot).sendMessage(eq(ANY_CHAT_ID),
                any(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull())
    }

    private fun thenAMessageIsSentWithAppropiateContent() {
        verify(bot).sendMessage(any(),
                eq("Hello World!!"),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull())
    }

    private fun thenTheCommandDoesntSendAMessage() {
        verify(bot, never()).sendMessage(any(),
                any(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
                anyOrNull())
    }

    companion object {
        private const val ANY_CHAT_ID: Long = 123445
    }
}