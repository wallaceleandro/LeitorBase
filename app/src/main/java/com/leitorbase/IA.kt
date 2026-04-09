package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, pergunta: String): String {

        // 🔹 Salva a pergunta
        Memoria.salvar(context, pergunta)

        // 🔹 Recupera última memória
        val textoAnterior = Memoria.ler(context)

        // 🔹 Processamento simples
        return when {
            pergunta.contains("resumo", ignoreCase = true) ->
                "Resumo do texto: $textoAnterior"

            pergunta.contains("explicar", ignoreCase = true) ->
                "Explicação: $textoAnterior"

            pergunta.contains("jesus", ignoreCase = true) ->
                "Jesus é o Filho de Deus e realiza milagres conforme a fé."

            else ->
                "Resposta padrão: $pergunta"
        }
    }
}
