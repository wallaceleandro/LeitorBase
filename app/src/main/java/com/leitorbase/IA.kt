package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, pergunta: String): String {

    val textoPdf = PdfManager.textoPdf

    if (textoPdf.isNotEmpty()) {

        return when {
            pergunta.contains("resumo", true) ->
                textoPdf.take(300)

            pergunta.contains("explicar", true) ->
                "Explicação baseada no PDF: " + textoPdf.take(200)

            else ->
                "Baseado no PDF: " + textoPdf.take(150)
        }
    }

    return "Nenhum PDF carregado"
}
