package es.duarry.loginbasico

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

data class Animales(
    var cod: Int = 0,
    var nombre: String = "",
    var raza: String = "",
    var sexo: String = "",
    var fechNac: String = "",
    var Dni: String = "",
    var foto: String = ""
) {
    constructor() : this(0, "", "", "", "", "", "")

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}