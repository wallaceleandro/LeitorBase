package com.leitorbase

import android.speech.tts.TextToSpeech

object VoiceController {

    private var tts: TextToSpeech? = null
    private var textoAtual = ""

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance
    }

    fun falar(texto: String) {
        textoAtual = texto
        tts?.stop()
        tts?.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun pausar() {
        tts?.stop()
    }

    fun continuar() {
        falar(textoAtual)
    }

    fun parar() {
        tts?.stop()
    }
}
