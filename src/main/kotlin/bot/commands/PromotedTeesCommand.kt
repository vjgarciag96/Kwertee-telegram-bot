package bot.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message
import tees.domain.FetchPromotedTees
import tees.domain.toTextMessage

class PromotedTeesCommand(private val fetchPromotedTees: FetchPromotedTees) : BotCommand {

    override val name: String
        get() = "promoted"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val promotedTees = fetchPromotedTees()
            val allTees = promotedTees.goneForeverTees + promotedTees.lastChanceTees
            allTees.forEach { tShirt ->
                bot.sendPhoto(command.chat.id, tShirt.imageUrl, tShirt.toTextMessage())
            }
        }
    }
}