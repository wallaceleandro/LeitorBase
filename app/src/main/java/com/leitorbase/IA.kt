package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, text: String): String {

        val pergunta = text.lowercase()

        val ultimaPergunta = Memoria.lerPergunta(context)
        val ultimoAssunto = Memoria.lerAssunto(context)

        val resposta = when {

            pergunta.contains("oi") -> "Olá!"

            pergunta.contains("jesus") -> {
                Memoria.salvarAssunto(context, "jesus")
                "Jesus é uma figura central do cristianismo"
            }

            pergunta.contains("milagre") && ultimoAssunto == "jesus" -> {
                "Sim, Jesus realizou milagres segundo a Bíblia"
            }

            pergunta.contains("ultima") -> {
                "Você perguntou: $ultimaPergunta"
            }

            else -> "Não sei responder ainda"
        }

        Memoria.salvarPergunta(context, text)

        return resposta
    }
}
