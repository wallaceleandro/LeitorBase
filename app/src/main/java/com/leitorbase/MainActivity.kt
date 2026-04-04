package com.leitorbase

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.inputTexto)
        val botao = findViewById<Button>(R.id.botaoAcao)

        botao.setOnClickListener {

            val textoDigitado = input.text.toString()

            val resultado = TextoUtils.processarTexto(textoDigitado)

            input.setText(resultado)
        }
    }
}
