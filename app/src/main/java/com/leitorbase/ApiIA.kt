package com.leitorbase

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ApiIA {

    private val client = OkHttpClient()

    fun perguntar(pergunta: String, callback: (String) -> Unit) {

        val json = JSONObject()
        json.put("model", "gpt-3.5-turbo")
        json.put("messages", listOf(
            mapOf("role" to "user", "content" to pergunta)
        ))

        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer SUA_API_KEY_AQUI")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback("Erro de conexão")
            }

            override fun onResponse(call: Call, response: Response) {

                val resposta = response.body?.string()

                try {
                    val json = JSONObject(resposta)
                    val texto = json
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                    callback(texto)

                } catch (e: Exception) {
                    callback("Erro ao processar resposta")
                }
            }
        })
    }
}
