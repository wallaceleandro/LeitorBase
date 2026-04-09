package com.leitorbase

import android.content.Context
import android.net.Uri

object PdfManager {

    fun pdfSelecionado(context: Context, uri: Uri?): String {

        return if (uri != null) {
            "PDF selecionado com sucesso"
        } else {
            "Erro ao selecionar PDF"
        }
    }
}
