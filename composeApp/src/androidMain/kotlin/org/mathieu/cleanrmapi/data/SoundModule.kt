package org.mathieu.cleanrmapi.data

import org.mathieu.cleanrmapi.SoundPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.mathieu.cleanrmapi.platform.AndroidSoundPlayer

val soundModule = module {
    single<SoundPlayer> { AndroidSoundPlayer(androidContext()) }
}