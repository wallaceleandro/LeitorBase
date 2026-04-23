package com.leitorbase

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

buttonLer.setOnClickListener {
    val text = inputText.text.toString()
    falar(text)
val buttonPdf = findViewById<Button>(R.id.buttonAbrirPdf)

buttonPdf.setOnClickListener {
    PdfManager.abrirPdf(this)

        tts = TextToSpeech(this, this)

        buttonPerguntar.setOnClickListener {
            val text = inputText.text.toString()

            val resposta = IA.processar(this, text)

            outputText.text = resposta
            falar(resposta)
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
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 100 && resultCode == RESULT_OK) {
        val uri = data?.data
        Toast.makeText(this, "PDF selecionado", Toast.LENGTH_SHORT).show()
    }
}
