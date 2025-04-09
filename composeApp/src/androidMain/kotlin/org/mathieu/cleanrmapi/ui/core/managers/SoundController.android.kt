package org.mathieu.cleanrmapi.ui.core.managers

import android.content.Context
import android.media.MediaPlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.R

actual class SoundController : KoinComponent {
    private val context: Context by inject()

    actual fun playClickSound() {
            val mediaPlayer = MediaPlayer.create(context, R.raw.cartoon)
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()
    }
}