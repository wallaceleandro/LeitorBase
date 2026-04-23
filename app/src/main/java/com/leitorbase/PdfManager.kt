package com.leitorbase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import android.app.Activity
import android.content.Intent

object PdfManager {

    fun abrirPdf(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        activity.startActivityForResult(intent, 100)
    }
}

object PdfManager {

var textoPdf: String = ""

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

            textoPdf = stringBuilder.toString()
val resultado = textoPdf

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
