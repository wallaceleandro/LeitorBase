package com.leitorbase

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val button = findViewById<Button>(R.id.button)
        val buttonLer = findViewById<Button>(R.id.buttonLer)
val buttonPerguntar = findViewById<Button>(R.id.buttonPerguntar)

        tts = TextToSpeech(this, this)

        button.setOnClickListener {
            val text = inputText.text.toString()

            val resposta = IA.processar(this, text)

            outputText.text = resposta
            falar(resposta)
        }
    }

            buttonLer.setOnClickListener {
    val texto = inputText.text.toString()
    falar(texto)
}

    private fun processarTexto(texto: String): String {
        return when {
            texto.contains("oi", true) -> "Olá! Tudo bem?"
            texto.contains("seu nome", true) -> "Eu sou o Leitor Universal IA"
            texto.contains("hora", true) -> "Ainda não sei ver horas 😅"
            texto.isBlank() -> "Digite algo primeiro"
            else -> "Você disse: $texto"
        }
    }

    private fun falar(texto: String) {
        tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("pt", "BR")
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
