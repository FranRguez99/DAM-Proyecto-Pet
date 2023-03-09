package es.duarry.loginbasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import es.duarry.loginbasico.databinding.ActivityVerAnimalBinding

class VerAnimal : AppCompatActivity() {
    private lateinit var  binding: ActivityVerAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar los datos del intent
        val cod = intent.getIntExtra("cod", 0)
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val fechaNacimiento = intent.getStringExtra("fecha_nacimiento")
        val sexo = intent.getStringExtra("sexo")
        val dni = intent.getStringExtra("dni")

        // Establecer el texto de los TextView correspondientes
        binding.codAnimal.setText(cod.toString())
        binding.nomAnimal.setText(nombre)
        binding.razaAnimal.setText(raza)
        binding.fechAnimal.setText(fechaNacimiento)
        binding.sexoAnimal.setText(sexo)
        binding.dniAnimal.setText(dni)
    }
}