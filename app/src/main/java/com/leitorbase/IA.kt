package com.leitorbase

import android.content.Context

object IA {

    fun processar(context: Context, pergunta: String): String {

        val textoPdf = PdfManager.textoPdf

        // 🔹 RESPOSTAS BÁSICAS (PRIORIDADE)
        if (pergunta.contains("jesus", true)) {
            return "Jesus é o Filho de Deus, central no cristianismo."
        }

        if (pergunta.contains("milagre", true)) {
            return "Milagre é um evento sobrenatural interpretado como ação divina."
        }

        // 🔹 PDF (SÓ SE PEDIR)
        if (textoPdf.isNotEmpty()) {

            if (pergunta.contains("resumo", true)) {
                return "Resumo do PDF: " + textoPdf.take(300)
            }

            if (pergunta.contains("explicar", true)) {
                return "Explicação baseada no PDF: " + textoPdf.take(200)
            }
        }

        return "Pergunta não reconhecida"
    }
}
