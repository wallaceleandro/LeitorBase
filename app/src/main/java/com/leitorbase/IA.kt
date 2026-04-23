package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, pergunta: String): String {

        return when {
            pergunta.contains("jesus", true) ->
                "Jesus é o filho de Deus."

            pergunta.contains("milagre", true) ->
                "Milagre é uma intervenção divina."

            pergunta.contains("resumo", true) ->
                "Ainda não há resumo de PDF disponível."

            else ->
                "Você disse: $pergunta"
        }
    }
}
