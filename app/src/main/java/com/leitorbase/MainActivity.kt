package com.leitorbase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)

        val buttonPerguntar = findViewById<Button>(R.id.buttonPerguntar)
        val buttonLer = findViewById<Button>(R.id.buttonLer)
        val buttonPdf = findViewById<Button>(R.id.buttonAbrirPdf)

        tts = TextToSpeech(this, this)

        // IA local
        buttonPerguntar.setOnClickListener {
            val text = inputText.text.toString()
            val resposta = IA.processar(this, text)

            outputText.text = resposta
            falar(resposta)
        }

        // Ler texto
        buttonLer.setOnClickListener {
            val text = inputText.text.toString()
            falar(text)
        }

        // Abrir PDF
        buttonPdf.setOnClickListener {
            PdfManager.abrirPdf(this)
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
        super.onDestroy()
        tts.shutdown()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
        val uri = data?.data

        if (uri != null) {
            val texto = PdfManager.lerPdf(uri, this)

            val outputText = findViewById<TextView>(R.id.outputText)
            outputText.text = texto

            falar(texto)
        }
    }
}
