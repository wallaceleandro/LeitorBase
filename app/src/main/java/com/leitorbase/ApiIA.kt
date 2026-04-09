package com.leitorbase

import android.content.Context

object ApiIA {

    fun responder(context: Context, pergunta: String): String {
        return "Resposta online simulada: $pergunta"
    }
}
