package com.leitorbase

import android.content.Context

object Memoria {

    fun salvar(context: Context, pergunta: String) {
        val prefs = context.getSharedPreferences("memoria", Context.MODE_PRIVATE)
        prefs.edit().putString("ultima_pergunta", pergunta).apply()
    }

    fun ler(context: Context): String {
        val prefs = context.getSharedPreferences("memoria", Context.MODE_PRIVATE)
        return prefs.getString("ultima_pergunta", "") ?: ""
    }
}
