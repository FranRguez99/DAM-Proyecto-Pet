package es.duarry.loginbasico

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import es.duarry.loginbasico.adapter.AnimalesAdapter
import es.duarry.loginbasico.databinding.ActivityListadoBinding
import es.duarry.loginbasico.databinding.ActivityListadoUsuarioBinding

class ListadoUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityListadoUsuarioBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener referencia a la base de datos
        database = FirebaseDatabase.getInstance("https://pmdmpet-default-rtdb.europe-west1.firebasedatabase.app").reference

        val intent = intent
        val usuario = intent.getStringExtra("usuario")

        binding.usuario.text = "Usuario: $usuario"

        iniciarRecycledView()

    }

    fun iniciarRecycledView() {
        binding.recycledAnimales.layoutManager = LinearLayoutManager(this)

        // Obtener datos de la base de datos
        database.child("animales").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalesList = mutableListOf<Animales>()
                for (data in snapshot.children) {
                    val animal = data.getValue(Animales::class.java)
                    animalesList.add(animal!!)
                }
                binding.recycledAnimales.adapter = AnimalesAdapter(animalesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores aquí
            }
        })
    }
}