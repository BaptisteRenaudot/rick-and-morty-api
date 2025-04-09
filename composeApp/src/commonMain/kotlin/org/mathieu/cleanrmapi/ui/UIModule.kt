package org.mathieu.cleanrmapi.ui

import org.koin.dsl.module
import org.mathieu.cleanrmapi.ui.core.managers.SoundController

val uiModule = module {
    single {
        SoundController()
    }
}