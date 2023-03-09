package es.duarry.loginbasico

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.duarry.loginbasico.databinding.LoginActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var usuario: String
    private lateinit var contra: String
    private lateinit var bd: BaseDatos
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // inicio bd y sharedPreferences
        bd = BaseDatos(this)
        sharedPreferences = this.getSharedPreferences("userdetails", MODE_PRIVATE)

        //Datos iniciales base datos
   /*     datosIniciales()

        // Recuerdo usuario
        userGuardado()*/

        binding.strRegistroPregunta.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        // login de user
        binding.login.setOnClickListener {

            usuario = binding.user.editText?.text.toString()
            contra = binding.pasword.editText?.text.toString()

            if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contra)) {
                Snackbar.make(binding.textView2, "Usuario y/o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            } else {
                autentificacionLogin()
            }
        }
    }
    private fun actualizaUI(user: FirebaseUser?) {
        if (user != null) {
            // El usuario está autenticado, mostrar la vista para usuario autenticado
            // por ejemplo, mostrar un mensaje de bienvenida y un botón de cerrar sesión

        } else {
            // El usuario no está autenticado, mostrar la vista para usuario no autenticado
            // por ejemplo, mostrar un formulario de inicio de sesión y ocultar botón de cerrar sesión
        }
    }

    private fun autentificacionLogin(){


        val contrasena = binding.pasword.editText?.text.toString()
        val usuario = binding.user.editText?.text.toString()
        if (usuario == "admin@admin.com") {
            // Si el usuario es admin
            intent = Intent(this, RegisAnimalActivity::class.java)
        } else {
            // Si el usuario no es admin
            intent = Intent(this, ListadoUsuario::class.java)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = auth.signInWithEmailAndPassword(usuario, contrasena).await()
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "Login: Correcto")
                val user = result.user

                withContext(Dispatchers.Main) {
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    actualizaUI(user)
                    Toast.makeText(baseContext, "Conectado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "Login: Erróneo", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(baseContext, "Autentificación errónea", Toast.LENGTH_SHORT).show()
                    actualizaUI(null)
                }
            }
        }

    }

}