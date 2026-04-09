object ApiIA {

    fun responder(pergunta: String): String {
        return "Resposta da API aqui"
    }
}

val resposta = if (temInternet()) {
    ApiIA.responder(text)
} else {
    IA.processar(this, text)
}
