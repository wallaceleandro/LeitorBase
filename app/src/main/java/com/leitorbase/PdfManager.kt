package com.leitorbase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.itextpdf.text.pdf.PdfReader

object PdfManager {

    fun abrirPdf(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        activity.startActivityForResult(intent, 100)
    }

    fun lerPdf(uri: Uri, activity: Activity): String {
        try {
            val inputStream = activity.contentResolver.openInputStream(uri)
            val reader = PdfReader(inputStream)

            val texto = StringBuilder()

            for (i in 1..reader.numberOfPages) {
                texto.append(
                    com.itextpdf.text.pdf.parser.PdfTextExtractor.getTextFromPage(reader, i)
                )
            }

            reader.close()
            return texto.toString()

        } catch (e: Exception) {
            return "Erro ao ler PDF"
        }
    }
}
