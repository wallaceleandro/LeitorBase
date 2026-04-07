package com.leitorbase

object IA {

    fun processar(pergunta: String): String {

        val texto = pergunta.lowercase()

        return when {
            texto.contains("oi") -> "Olá! Como posso te ajudar?"
            texto.contains("seu nome") -> "Eu sou a IA do LeitorBase."
            texto.contains("jesus") -> "Jesus é apresentado no Apocalipse como o Alfa e o Ômega, o princípio e o fim."
            texto.isBlank() -> "Digite algo para eu responder."
            else -> "Ainda estou aprendendo. Você perguntou: $pergunta"
        }
    }
}
