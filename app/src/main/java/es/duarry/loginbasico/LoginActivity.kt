package es.duarry.loginbasico

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import es.duarry.loginbasico.databinding.LoginActivityBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var usuario: String
    private lateinit var contra: String
    private lateinit var bd: BaseDatos
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicio bd y sharedPreferences
        bd = BaseDatos(this)
        sharedPreferences = this.getSharedPreferences("userdetails", MODE_PRIVATE)

        //Datos iniciales base datos
        datosIniciales()

        // Recuerdo usuario
        userGuardado()

        // login de user
        binding.login.setOnClickListener {

            usuario = binding.user.editText?.text.toString()
            contra = binding.pasword.editText?.text.toString()

            if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contra)) {
                Snackbar.make(binding.textView2, "Usuario y/o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            } else {
                checkUsuario()
            }
        }
    }

    // Confirmar que el usuario está registrado
    fun checkUsuario(){
        val checkUsuario = bd.verificaUsuario(usuario, contra)
        if (checkUsuario) {
            val intent = Intent(this, RegisAnimalActivity::class.java)

            if (binding.switchRecuerdo.isChecked) {
                sharedPreferences.edit().putString("usGuardado",usuario.trim()).apply()
                sharedPreferences.edit().putString("conGuardado",contra.trim()).apply()
            } else {
                sharedPreferences.edit().putString("usGuardado","").apply()
                sharedPreferences.edit().putString("conGuardado","").apply()
            }

            intent.putExtra("usuario", usuario)
            startActivity(intent)
        } else {
            Snackbar.make(binding.textView2, "Usuario y/o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
        }
    }

    // Datos iniciales de la bd
    fun datosIniciales(){
        val primeraVez = sharedPreferences.getBoolean("inicio",false)
        if (!primeraVez){
            bd.insterarUsuario()
            bd.insertarAnimales()
            sharedPreferences.edit().putBoolean("inicio",true).apply()
        }
    }

    // Guardar credenciales
    fun userGuardado(){
        usuario = sharedPreferences.getString("usGuardado","").toString()
        contra = sharedPreferences.getString("conGuardado","").toString()
        binding.user.editText?.setText(usuario)
        binding.pasword.editText?.setText(contra)
    }
}