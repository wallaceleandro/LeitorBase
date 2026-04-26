package com.leitorbase

import android.app.Activity
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

    private lateinit var inputText: EditText
    private lateinit var outputText: TextView

    private lateinit var buttonPerguntar: Button
    private lateinit var buttonLer: Button
    private lateinit var buttonAbrirPdf: Button
    private lateinit var buttonParar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarComponentes()

        tts = TextToSpeech(this, this)
        VoiceController.init(tts)

        configurarBotoes()
    }

    private fun iniciarComponentes() {
        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)

        buttonPerguntar = findViewById(R.id.buttonPerguntar)
        buttonLer = findViewById(R.id.buttonLer)
        buttonAbrirPdf = findViewById(R.id.buttonAbrirPdf)

        buttonParar = findViewById(R.id.buttonParar)
    }

    private fun configurarBotoes() {

        buttonPerguntar.setOnClickListener {
            perguntarIA()
        }

        buttonLer.setOnClickListener {
            lerTextoDigitado()
        }

        buttonAbrirPdf.setOnClickListener {
            abrirPdf()
        }

        buttonParar.setOnClickListener {
            VoiceController.parar()
        }
    }

    private fun perguntarIA() {
        val pergunta = inputText.text.toString().trim()

        if (pergunta.isEmpty()) {
            outputText.text = "Digite algo."
            return
        }

        val resposta = IA.processar(this, pergunta)

        outputText.text = resposta
        VoiceController.falar(resposta)
    }

    private fun lerTextoDigitado() {
        val texto = inputText.text.toString().trim()

        if (texto.isEmpty()) {
            outputText.text = "Digite um texto."
            return
        }

        VoiceController.falar(texto)
    }

    private fun abrirPdf() {
        PdfManager.abrirPdf(this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("pt", "BR")
            tts.setSpeechRate(1.0f)
            tts.setPitch(1.0f)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            val uri = data?.data ?: return

            val textoPdf = PdfManager.lerPdf(uri, this)

            outputText.text = textoPdf

            VoiceController.falar(textoPdf)
        }
    }

    override fun onDestroy() {
        VoiceController.parar()

        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }

        super.onDestroy()
    }
}
