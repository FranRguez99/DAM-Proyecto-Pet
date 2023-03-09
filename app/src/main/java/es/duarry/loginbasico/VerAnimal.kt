package es.duarry.loginbasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class VerAnimal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_animal)

        // Recuperar los datos del intent
        val cod = intent.getIntExtra("cod", 0)
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val fechaNacimiento = intent.getStringExtra("fecha_nacimiento")
        val sexo = intent.getStringExtra("sexo")
        val dni = intent.getStringExtra("dni")

        // Encontrar los TextView correspondientes por ID
        val tvCod = findViewById<TextView>(R.id.cod_animal)
        val tvNombre = findViewById<TextView>(R.id.nom_animal)
        val tvRaza = findViewById<TextView>(R.id.raza_animal)
        val tvFechaNacimiento = findViewById<TextView>(R.id.fech_animal)
        val tvSexo = findViewById<TextView>(R.id.sexo_animal)
        val tvDni = findViewById<TextView>(R.id.dni_animal)

        // Establecer el texto de los TextView correspondientes
        tvCod.text = cod.toString()
        tvNombre.text = nombre
        tvRaza.text = raza
        tvFechaNacimiento.text = fechaNacimiento
        tvSexo.text = sexo
        tvDni.text = dni
    }
}