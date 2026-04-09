package com.leitorbase

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    buttonAbrirPdf.setOnClickListener {

    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "application/pdf"
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    startActivityForResult(
        Intent.createChooser(intent, "Selecionar PDF"),
        100
    )
}

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)

        val buttonLer = findViewById<Button>(R.id.buttonLer)
        val buttonPerguntar = findViewById<Button>(R.id.buttonPerguntar)
        val buttonAbrirPdf = findViewById<Button>(R.id.buttonAbrirPdf)

        tts = TextToSpeech(this, this)

        // 🔊 BOTÃO LEITOR
        buttonLer.setOnClickListener {
            val texto = inputText.text.toString()
            falar(texto)
        }

        // 🧠 BOTÃO IA
        buttonPerguntar.setOnClickListener {

    val text = inputText.text.toString()

    if (text.isEmpty()) {
        outputText.text = "Digite algo primeiro"
        return@setOnClickListener
    }

    val resposta = IA.processar(this, text)

    outputText.text = resposta
    falar(resposta)
}
    }

    // 🔊 Inicialização da voz
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("pt", "BR")
        }
    }

    // 🔊 Função de fala
    private fun falar(texto: String) {
        tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    // 🔊 Liberar memória
    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }
}
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 100 && resultCode == RESULT_OK) {

        val uri = data?.data

        val resultado = PdfManager.pdfSelecionado(this, uri)
        val outputText = findViewById<TextView>(R.id.outputText)

        outputText.text = resultado
    }
}
