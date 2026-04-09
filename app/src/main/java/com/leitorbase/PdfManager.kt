package com.leitorbase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor

object PdfManager {

    fun pdfSelecionado(context: Context, uri: Uri?): String {

        if (uri == null) return "Erro ao abrir PDF"

        return try {

            val inputStream = context.contentResolver.openInputStream(uri)
            val reader = PdfReader(inputStream)

            val stringBuilder = StringBuilder()

            for (i in 1..reader.numberOfPages) {
                val texto = PdfTextExtractor.getTextFromPage(reader, i)
                stringBuilder.append(texto)
            }

            reader.close()

            val resultado = stringBuilder.toString()

            if (resultado.isBlank()) {
                "PDF sem texto ou não suportado"
            } else {
                resultado
            }

        } catch (e: Exception) {
            Log.e("PDF_ERRO", e.toString())
            "Erro ao ler PDF"
        }
    }
}
