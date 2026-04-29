package com.leitorbase

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener

object VoiceController {

    private var tts: TextToSpeech? = null

    private var partes = mutableListOf<String>()
    private var indiceAtual = 0
    private var pausado = false

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {

            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                if (!pausado) {
                    indiceAtual++
                    falarProximaParte()
                }
            }

            override fun onError(utteranceId: String?) {}
        })
    }

    fun falar(texto: String) {

        partes.clear()

        // 🔥 Divisão inteligente (frases + limite de tamanho)
        val frases = texto.split(Regex("(?<=[.!?])"))

        for (frase in frases) {

            val limpa = frase.trim()
            if (limpa.isEmpty()) continue

            // quebra frases muito grandes
            if (limpa.length > 120) {
                val palavras = limpa.split(" ")
                var i = 0

                while (i < palavras.size) {
                    val fim = minOf(i + 15, palavras.size)
                    partes.add(palavras.subList(i, fim).joinToString(" "))
                    i += 15
                }
            } else {
                partes.add(limpa)
            }
        }

        indiceAtual = 0
        pausado = false

        falarProximaParte()
    }

    private fun falarProximaParte() {

        if (pausado) return
        if (indiceAtual >= partes.size) return

        val trecho = partes[indiceAtual]

        tts?.speak(
            trecho,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "TRECHO_$indiceAtual"
        )
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
