package com.leitorbase

import android.speech.tts.TextToSpeech

object VoiceController {

    private var tts: TextToSpeech? = null

    private var partes = mutableListOf<String>()
    private var indiceAtual = 0

    private var pausado = false
    private var lendo = false

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance
    }

    fun falar(texto: String) {

        partes.clear()

        val frases = texto.split(Regex("(?<=[.!?])"))

        for (frase in frases) {
            val limpa = frase.trim()
            if (limpa.isNotEmpty()) {
                partes.add(limpa)
            }
        }

        indiceAtual = 0
        pausado = false
        lendo = true

        falarLoop()
    }

    private fun falarLoop() {

        if (!lendo || pausado) return
        if (indiceAtual >= partes.size) return

        val trecho = partes[indiceAtual]

        tts?.speak(
            trecho,
            TextToSpeech.QUEUE_ADD,
            null,
            "ID_$indiceAtual"
        )

        indiceAtual++

        // 🔥 chama próximo manualmente (sem depender de stop)
        tts?.playSilentUtterance(50, TextToSpeech.QUEUE_ADD, null)

        falarLoop()
    }

    fun pausar() {
        pausado = true
    }

    fun continuar() {
        if (!lendo) return

        pausado = false
        falarLoop()
    }

    fun parar() {
        pausado = false
        lendo = false
        indiceAtual = 0
        tts?.stop()
    }
}
