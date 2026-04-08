package com.leitorbase

import android.content.Context

object Memoria {

    private const val NOME = "memoria"
    private const val CHAVE_PERGUNTA = "ultima_pergunta"
    private const val CHAVE_ASSUNTO = "ultimo_assunto"

    fun salvarPergunta(context: Context, pergunta: String) {
        val prefs = context.getSharedPreferences(NOME, Context.MODE_PRIVATE)
        prefs.edit().putString(CHAVE_PERGUNTA, pergunta).apply()
    }

    fun lerPergunta(context: Context): String? {
        val prefs = context.getSharedPreferences(NOME, Context.MODE_PRIVATE)
        return prefs.getString(CHAVE_PERGUNTA, null)
    }

    fun salvarAssunto(context: Context, assunto: String) {
        val prefs = context.getSharedPreferences(NOME, Context.MODE_PRIVATE)
        prefs.edit().putString(CHAVE_ASSUNTO, assunto).apply()
    }

    fun lerAssunto(context: Context): String? {
        val prefs = context.getSharedPreferences(NOME, Context.MODE_PRIVATE)
        return prefs.getString(CHAVE_ASSUNTO, null)
    }
}
