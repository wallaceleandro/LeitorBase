package com.leitorbase

object IA {

    // 🔹 Memória simples
    var ultimaPergunta: String? = null
    var ultimoAssunto: String? = null

    fun processar(text: String): String {

        val pergunta = text.lowercase()

        // 🔹 Regras básicas + contexto
        val resposta = when {

            pergunta.contains("oi") -> "Olá!"

            pergunta.contains("jesus") -> {
                ultimoAssunto = "jesus"
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

        // 🔹 Salva última pergunta
        ultimaPergunta = text

        return resposta
    }
}
