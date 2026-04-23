package com.leitorbase

import android.app.Activity
import android.content.Intent

object PdfManager {

    fun abrirPdf(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        activity.startActivityForResult(intent, 100)
    }
}
