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
    private var ultimoTexto = ""
    private var pausado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)

        buttonPerguntar = findViewById(R.id.buttonPerguntar)
        buttonLer = findViewById(R.id.buttonLer)
        buttonAbrirPdf = findViewById(R.id.buttonAbrirPdf)
        buttonParar = findViewById(R.id.buttonParar)

        tts = TextToSpeech(this, this)
        VoiceController.init(tts)

        configurarBotoes()
    }

    private fun configurarBotoes() {

        buttonPerguntar.setOnClickListener {
            perguntarIA()
        }

        buttonLer.setOnClickListener {

    val textoDigitado = inputText.text.toString().trim()

    when {
        textoDigitado.isNotEmpty() -> VoiceController.falar(textoDigitado)

        ultimoTexto.isNotEmpty() -> VoiceController.falar(ultimoTexto)

        outputText.text.toString().trim().isNotEmpty() ->
            VoiceController.falar(outputText.text.toString())

        pausado = false
buttonPause.text = "Pausar"

    }
}

        buttonAbrirPdf.setOnClickListener {
            abrirPdf()
        }

        buttonParar.setOnClickListener {
            VoiceController.parar()
        }
        
        buttonPause.setOnClickListener {

    if (!pausado) {
        VoiceController.parar()
        pausado = true
        buttonPause.text = "Continuar"
    } else {
        VoiceController.falar(ultimoTexto)
        pausado = false
        buttonPause.text = "Pausar"
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

            ultimoTexto = textoPdf
outputText.text = textoPdf

        }
    }

    override fun onDestroy() {
        VoiceController.parar()
        tts.shutdown()
        super.onDestroy()
    }
}
