package es.duarry.loginbasico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import es.duarry.loginbasico.adapter.animalesAdapter
import es.duarry.loginbasico.databinding.ActivityListadoBinding

class ListadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListadoBinding
    private lateinit var bd: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bd = BaseDatos(this)

        val intent = intent
        val usuario = intent.getStringExtra("usuario")

        binding.usuario.text = "Usuario: $usuario"

        iniciarRecycledView()

        binding.btnVolver.setOnClickListener{
            val intent = Intent(this, RegisAnimalActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }

    fun iniciarRecycledView() {
        binding.recycledAnimales.layoutManager = LinearLayoutManager(this)
        binding.recycledAnimales.adapter = animalesAdapter(bd.mostrarAnimales())
    }

}