package org.mathieu.cleanrmapi.platform

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.SoundPlayer
import org.mathieu.cleanrmapi.R

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {
    override fun playClickSound() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.cartoon)
        mediaPlayer.setOnCompletionListener { it.release() }
        mediaPlayer.start()
    }
}