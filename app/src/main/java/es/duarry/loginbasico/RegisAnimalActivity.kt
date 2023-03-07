package es.duarry.loginbasico

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import es.duarry.loginbasico.R.drawable.petlogopet
import es.duarry.loginbasico.databinding.RegisAnimalActivityBinding


class RegisAnimalActivity : AppCompatActivity() {

    private lateinit var binding: RegisAnimalActivityBinding
    private lateinit var bd: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisAnimalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bd = BaseDatos(this)

        val intent = intent
        val usuario = intent.getStringExtra("usuario")

        binding.usuario.text = "Usuario: $usuario"

        binding.btnAlta.setOnClickListener{
            val nombre = binding.nomAnimal.text.toString()
            val raza = binding.razaAnimal.text.toString()
            val sexo = binding.sexoAnimal.text.toString()
            val fecha = binding.fechAnimal.text.toString()
            val dni = binding.dniAnimal.text.toString()

            if ( TextUtils.isEmpty(nombre) || TextUtils.isEmpty(raza) || TextUtils.isEmpty(sexo) || TextUtils.isEmpty(fecha) || TextUtils.isEmpty(dni) ) {
                Snackbar.make(binding.textView2, "Debes rellenar todos los campos excepto Cod. Ident.", Snackbar.LENGTH_SHORT).show()
            } else {
                //insertar datos
                bd.altaAnimal(nombre,raza,sexo,fecha,dni,"https://media.ambito.com/p/cb928e70188562a84888b60513e8134c/adjuntos/239/imagenes/040/169/0040169794/mascotas-perrosjpg.jpg")
                Snackbar.make(binding.textView2, "Animal registrado con éxito", Snackbar.LENGTH_SHORT).show()

                //limpiar campos
                limpiaCampos()
            }

        }

        binding.btnModifica.setOnClickListener{
            val cod = binding.codAnimal.text.toString()
            val nombre = binding.nomAnimal.text.toString()
            val raza = binding.razaAnimal.text.toString()
            val sexo = binding.sexoAnimal.text.toString()
            val fecha = binding.fechAnimal.text.toString()
            val dni = binding.dniAnimal.text.toString()

            if ( TextUtils.isEmpty(cod)) {
                Snackbar.make(binding.textView2, "Debes introducir el código del animal", Snackbar.LENGTH_SHORT).show()
            } else {
                //insertar datos
                bd.modificaAnimal(cod,nombre,raza,sexo,fecha,dni)
                Snackbar.make(binding.textView2, "Animal modificado con éxito", Snackbar.LENGTH_SHORT).show()

                //limpiar campos
                limpiaCampos()
            }
        }

        binding.btnBorra.setOnClickListener{
            val cod = binding.codAnimal.text.toString()

            if ( TextUtils.isEmpty(cod)) {
                Snackbar.make(binding.textView2, "Debes introducir el código del animal", Snackbar.LENGTH_SHORT).show()
            } else {
                //insertar datos
                bd.borraAnimal(cod)
                Snackbar.make(binding.textView2, "Animal borrado con éxito", Snackbar.LENGTH_SHORT).show()

            }
            //limpia campos
            limpiaCampos()
        }

        binding.btnConsulta.setOnClickListener{
            val cod = binding.codAnimal.text.toString()
            val animal = bd.consultaAnimal(cod)

            if ( TextUtils.isEmpty(cod)) {
                Snackbar.make(binding.textView2, "Debes introducir el código del animal registrado", Snackbar.LENGTH_SHORT).show()
            } else if (animal.cod == 0){
                Snackbar.make(binding.textView2, "No existe ningún animal registrado con ese código", Snackbar.LENGTH_SHORT).show()
            }else{
                //actualizar campos
                binding.nomAnimal.setText(animal.nombre)
                binding.razaAnimal.setText(animal.raza)
                binding.sexoAnimal.setText(animal.sexo)
                binding.fechAnimal.setText(animal.fechNac)
                binding.dniAnimal.setText(animal.Dni)
                Glide.with(binding.fotoAnimal.context).load(animal.foto).into(binding.fotoAnimal)
            }
        }

        binding.btnTodos.setOnClickListener{
            val intent = Intent(this, ListadoActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)

        }

    }

    fun limpiaCampos(){
        binding.codAnimal.setText("")
        binding.nomAnimal.setText("")
        binding.razaAnimal.setText("")
        binding.sexoAnimal.setText("")
        binding.fechAnimal.setText("")
        binding.dniAnimal.setText("")
        //binding.fotoAnimal.setImageBitmap(BitmapFactory.decodeResource(this.resources, petlogopet))
    }

    override fun onBackPressed() {
        // impedir que usuario vuelva a pantalla login
    }
}