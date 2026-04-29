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
    private lateinit var buttonPause: Button
    private lateinit var buttonParar: Button
    private lateinit var buttonAbrirPdf: Button

    private var ultimoTexto = ""
    private var pausado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)

        buttonPerguntar = findViewById(R.id.buttonPerguntar)
        buttonLer = findViewById(R.id.buttonLer)
        buttonPause = findViewById(R.id.buttonPause)
        buttonParar = findViewById(R.id.buttonParar)
        buttonAbrirPdf = findViewById(R.id.buttonAbrirPdf)

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
            val textoTela = outputText.text.toString().trim()

            when {
                textoDigitado.isNotEmpty() -> {
                    ultimoTexto = textoDigitado
                    VoiceController.falar(textoDigitado)
                }

                textoTela.isNotEmpty() -> {
                    ultimoTexto = textoTela
                    VoiceController.falar(textoTela)
                }
            }

            pausado = false
            buttonPause.text = "Pausar"
        }

        buttonPause.setOnClickListener {

            if (!pausado) {
                VoiceController.parar()
                pausado = true
                buttonPause.text = "Continuar"
            } else {
                if (ultimoTexto.isNotEmpty()) {
                    VoiceController.falar(ultimoTexto)
                }
                pausado = false
                buttonPause.text = "Pausar"
            }
        }

        buttonParar.setOnClickListener {
            VoiceController.parar()
            pausado = false
            buttonPause.text = "Pausar"
        }

        buttonAbrirPdf.setOnClickListener {
            abrirPdf()
        }
    }

    private fun perguntarIA() {

        val pergunta = inputText.text.toString().trim()

        if (pergunta.isEmpty()) {
            outputText.text = "Digite uma pergunta."
            return
        }

        val resposta = IA.processar(this, pergunta)

        outputText.text = resposta
        ultimoTexto = resposta
        VoiceController.falar(resposta)
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
            ultimoTexto = textoPdf
        }
    }

    override fun onDestroy() {
        VoiceController.parar()
        tts.shutdown()
        super.onDestroy()
    }
}
