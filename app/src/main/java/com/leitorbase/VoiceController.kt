package com.leitorbase

import android.speech.tts.TextToSpeech

object VoiceController {

    private var tts: TextToSpeech? = null

    private var partes = mutableListOf<String>()
    private var indiceAtual = 0
    private var pausado = false

    fun init(ttsInstance: TextToSpeech) {
        tts = ttsInstance
    }

    fun falar(texto: String) {

        partes.clear()

        val palavras = texto.split(" ")

        val tamanhoBloco = 20
        var i = 0

        while (i < palavras.size) {

            val fim = minOf(i + tamanhoBloco, palavras.size)

            partes.add(
                palavras.subList(i, fim).joinToString(" ")
            )

            i += tamanhoBloco
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
