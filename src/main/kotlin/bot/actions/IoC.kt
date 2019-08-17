package bot.actions

import org.koin.dsl.module

val botActionsModule = module {
    factory { SendPhotoMessage(get()) }
    factory { SendTextMessage(get()) }
}