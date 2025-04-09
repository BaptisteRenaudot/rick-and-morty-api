package org.mathieu.cleanrmapi.platform

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import org.mathieu.cleanrmapi.SoundPlayer
import org.mathieu.cleanrmapi.initKoin

val soundModule = module {
    single<SoundPlayer> { AndroidSoundPlayer(androidContext()) }
}

fun initKoinAndroid(application: Application) {
    if (GlobalContext.getOrNull() == null) {
        initKoin {
            androidContext(application)
            modules(soundModule)
        }
    }
}