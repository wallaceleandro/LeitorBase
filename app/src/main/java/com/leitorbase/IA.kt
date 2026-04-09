package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, pergunta: String): String {

        try {

            Memoria.salvar(context, pergunta)

            val textoAnterior = Memoria.ler(context)

            return when {
                pergunta.contains("resumo", true) ->
                    "Resumo do texto: $textoAnterior"

                pergunta.contains("explicar", true) ->
                    "Explicação: $textoAnterior"

                else ->
                    "Resposta: $pergunta"
            }

        } catch (e: Exception) {
            return "Erro interno na IA"
        }
    }
}
