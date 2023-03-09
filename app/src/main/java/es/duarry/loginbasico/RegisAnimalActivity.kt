package es.duarry.loginbasico

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import es.duarry.loginbasico.databinding.RegisAnimalActivityBinding


class RegisAnimalActivity : AppCompatActivity() {

    private lateinit var binding: RegisAnimalActivityBinding
    private lateinit var bd: BaseDatos
    private lateinit var database: DatabaseReference
    private var animalId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisAnimalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bd = BaseDatos(this)
        database = FirebaseDatabase.getInstance().reference

        val intent = intent
        val usuario = intent.getStringExtra("usuario")

        binding.usuario.text = "Usuario: $usuario"

        binding.btnAlta.setOnClickListener {
            agregarAnimal()
            limpiaCampos()
        }

        binding.btnModifica.setOnClickListener {
            modificaAnimal()
            limpiaCampos()
        }

        binding.btnBorra.setOnClickListener {
            if(animalId != null) {
                eliminaAnimal(animalId!!)
                limpiaCampos()
            } else {
                Toast.makeText(this, "No se puede eliminar el animal: no se ha seleccionado ningún animal.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnConsulta.setOnClickListener {
            consultarAnimalPorCodigo()
        }

        binding.btnTodos.setOnClickListener {
            val intent = Intent(this, ListadoActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        FirebaseApp.initializeApp(this)
        database =
            FirebaseDatabase.getInstance("https://pmdmpet-default-rtdb.europe-west1.firebasedatabase.app").reference
    }

    private fun agregarAnimal() {

        val cod = binding.codAnimal.text.toString().trim().toInt()
        val nombre = binding.nomAnimal.text.toString().trim()
        val raza = binding.razaAnimal.text.toString().trim()
        val sexo = binding.sexoAnimal.text.toString().trim()
        val fecha = binding.fechAnimal.text.toString().trim()
        val Dni = binding.dniAnimal.text.toString().trim()

        if (cod == null || nombre.isEmpty() || fecha.isEmpty() || raza.isEmpty() || sexo.isEmpty() || Dni.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese los datos del animal", Toast.LENGTH_SHORT)
                .show()
            return
        }

        animalId = database.child("animales").push().key
        val animal = Animales(
            cod = cod,
            nombre = nombre,
            raza = raza,
            sexo = sexo,
            fechNac = fecha,
            Dni = Dni,
            foto = ""
        )

        if (animalId != null) {
            database.child("animales").child(animalId!!).setValue(animal)
                .addOnSuccessListener {
                    Toast.makeText(this, "Animal agregado correctamente", Toast.LENGTH_SHORT).show()
                    limpiaCampos()
                    Log.d(
                        "TAG",
                        "cod: $cod, nombre: $nombre, raza: $raza, sexo: $sexo, fecha: $fecha, dni: $Dni"
                    )
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al agregar el animal", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun modificaAnimal() {
        val cod = binding.codAnimal.text.toString().trim().toInt()
        val nombre = binding.nomAnimal.text.toString().trim()
        val raza = binding.razaAnimal.text.toString().trim()
        val sexo = binding.sexoAnimal.text.toString().trim()
        val fecha = binding.fechAnimal.text.toString().trim()
        val Dni = binding.dniAnimal.text.toString().trim()

        if (nombre.isEmpty() || fecha.isEmpty() || raza.isEmpty() || sexo.isEmpty() || Dni.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese los datos del animal", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val animal = Animales(
            cod = cod,
            nombre = nombre,
            raza = raza,
            sexo = sexo,
            fechNac = fecha,
            Dni = Dni,
            foto = ""
        )

        val animalRef = database.child("animales").child(animalId ?: "")

        animalRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    animalRef.setValue(animal)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@RegisAnimalActivity,
                                "Animal modificado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            limpiaCampos()
                            Log.d(
                                "TAG",
                                "cod: $cod, nombre: $nombre, raza: $raza, sexo: $sexo, fecha: $fecha, dni: $Dni"
                            )
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@RegisAnimalActivity,
                                "Error al modificar el animal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        this@RegisAnimalActivity,
                        "El animal con el código $cod no existe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@RegisAnimalActivity,
                    "Error al modificar el animal",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun eliminaAnimal(animalId: String) {
        database.child("animales").child(animalId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Animal eliminado correctamente", Toast.LENGTH_SHORT).show()
                this.animalId = null
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar el animal", Toast.LENGTH_SHORT).show()
            }
    }

    private fun consultarAnimalPorCodigo() {
        val cod = binding.codAnimal.text.toString().trim()
        if (cod.isEmpty()) {
            Toast.makeText(this, "Ingrese un código de animal válido", Toast.LENGTH_SHORT).show()
            return
        }

        val animalRef = database.child("animales").orderByChild("cod").equalTo(cod)

        animalRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val animal = dataSnapshot.children.first().getValue(Animales::class.java)
                    animalId = dataSnapshot.children.first().key
                    binding.codAnimal.setText(animal?.cod.toString())
                    binding.nomAnimal.setText(animal?.nombre)
                    binding.razaAnimal.setText(animal?.raza)
                    binding.sexoAnimal.setText(animal?.sexo)
                    binding.fechAnimal.setText(animal?.fechNac)
                    binding.dniAnimal.setText(animal?.Dni)
                } else {
                    Toast.makeText(
                        this@RegisAnimalActivity,
                        "Animal no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                    limpiaCampos()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@RegisAnimalActivity,
                    "Error al consultar el animal",
                    Toast.LENGTH_SHORT
                ).show()
                limpiaCampos()
            }
        })
    }



    fun limpiaCampos() {
        binding.codAnimal.setText("")
        binding.nomAnimal.setText("")
        binding.razaAnimal.setText("")
        binding.sexoAnimal.setText("")
        binding.fechAnimal.setText("")
        binding.dniAnimal.setText("")
        //binding.fotoAnimal.setImageBitmap(BitmapFactory.decodeResource(this.resources, petlogopet))
    }


}