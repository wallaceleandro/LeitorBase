import android.content.Context

object Memoria {

    fun salvar(context: Context, texto: String) {
        val prefs = context.getSharedPreferences("memoria", Context.MODE_PRIVATE)
        prefs.edit().putString("ultima", texto).apply()
    }

    fun ler(context: Context): String? {
        val prefs = context.getSharedPreferences("memoria", Context.MODE_PRIVATE)
        return prefs.getString("ultima", null)
    }
}
