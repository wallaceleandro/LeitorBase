package com.leitorbase

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener

object VoiceController {

    private var tts: TextToSpeech? = null

    private var partes = mutableListOf<String>()
    private var indiceAtual = 0

    private var pausado = false
    private var lendo = false

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {

            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                if (!pausado && lendo) {
                    proximo()
                }
            }

            override fun onError(utteranceId: String?) {}
        })
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

        falarAtual()
    }

    private fun falarAtual() {

        if (indiceAtual >= partes.size) return

        val trecho = partes[indiceAtual]

        tts?.speak(
            trecho,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "ID_$indiceAtual"
        )
    }

    private fun proximo() {
        indiceAtual++
        if (indiceAtual < partes.size) {
            falarAtual()
        }
    }

    fun pausar() {
        pausado = true
        tts?.stop()
    }

    fun continuar() {
        if (!lendo) return
        pausado = false
        falarAtual() // volta exatamente no trecho atual
    }

    fun parar() {
        pausado = false
        lendo = false
        indiceAtual = 0
        tts?.stop()
    }
}
