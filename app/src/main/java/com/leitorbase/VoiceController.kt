package com.leitorbase

import android.speech.tts.TextToSpeech

object VoiceController {

    private var tts: TextToSpeech? = null

    private var partes = listOf<String>()
    private var indiceAtual = 0
    private var pausado = false

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance
    }

    fun falar(texto: String) {

        partes = texto.split(".", "!", "?")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        indiceAtual = 0
        pausado = false

        falarProximaParte()
    }

    private fun falarProximaParte() {

        if (indiceAtual >= partes.size || pausado) return

        val trecho = partes[indiceAtual]

        tts?.speak(
            trecho,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "LEITURA"
        )

        indiceAtual++
    }

    fun pausar() {
        pausado = true
        tts?.stop()
    }

    fun continuar() {
        pausado = false
        falarProximaParte()
    }

    fun parar() {
        pausado = true
        indiceAtual = 0
        tts?.stop()
    }
}
