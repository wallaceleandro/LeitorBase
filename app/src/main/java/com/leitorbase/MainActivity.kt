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

        tts = TextToSpeech(this, this)

        buttonPerguntar.setOnClickListener {
            val text = inputText.text.toString()

            ApiIA.perguntar(text) { respostaApi ->

                runOnUiThread {

                    if (respostaApi.contains("Erro")) {

                        val respostaLocal = IA.processar(this, text)
                        outputText.text = respostaLocal
                        falar(respostaLocal)

                    } else {

                        outputText.text = respostaApi
                        falar(respostaApi)
                    }
                }
            }
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
