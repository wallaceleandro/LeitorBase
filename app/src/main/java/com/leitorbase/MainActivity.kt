package com.leitorbase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.inputTexto)
        val botao = findViewById<Button>(R.id.botaoExecutar)
        val resultado = findViewById<TextView>(R.id.resultadoTexto)

        botao.setOnClickListener {

            val texto = input.text.toString().lowercase()

            val resposta = when {
                texto.contains("olá") -> "Olá! Tudo bem com você?"
                texto.contains("seu nome") -> "Eu sou seu assistente."
                texto.contains("jesus") -> "Jesus é descrito como glorioso em Apocalipse 1:13-18."
                texto.isEmpty() -> "Digite algo primeiro."
                else -> "Você disse: $texto"
            }

            resultado.text = resposta
        }
    }
}
