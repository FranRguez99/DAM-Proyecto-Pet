package es.duarry.loginbasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class VerAnimal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_animal)

        // Recuperar los datos del intent
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val fechaNacimiento = intent.getStringExtra("fecha_nacimiento")
        val sexo = intent.getStringExtra("sexo")

        // Encontrar los TextView correspondientes por ID
        val tvNombre = findViewById<TextView>(R.id.nom_animal)
        val tvRaza = findViewById<TextView>(R.id.raza_animal)
        val tvFechaNacimiento = findViewById<TextView>(R.id.fech_animal)
        val tvSexo = findViewById<TextView>(R.id.sexo_animal)

        // Establecer el texto de los TextView correspondientes
        tvNombre.text = nombre
        tvRaza.text = raza
        tvFechaNacimiento.text = fechaNacimiento
        tvSexo.text = sexo
    }
}