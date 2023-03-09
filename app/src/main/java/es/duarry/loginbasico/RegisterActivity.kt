package es.duarry.loginbasico
//buenassss again jeje
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.duarry.loginbasico.databinding.ActivityRegisterBinding
import es.duarry.loginbasico.databinding.LoginActivityBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.btregistro.setOnClickListener(){
            registerUser()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // El usuario está autenticado, mostrar la vista para usuario autenticado
            // por ejemplo, mostrar un mensaje de bienvenida y un botón de cerrar sesión

        } else {
            // El usuario no está autenticado, mostrar la vista para usuario no autenticado
            // por ejemplo, mostrar un formulario de inicio de sesión y ocultar botón de cerrar sesión
        }
    }
    private fun registerUser() {
        val email = binding.user.editText?.text.toString()
        val password = binding.pasword.editText?.text.toString()
        val confirmPassword = binding.pasword2.editText?.text.toString()

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                // Registration success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmailAndPassword:success")
                val user = result.user
                withContext(Dispatchers.Main) {
                    updateUI(user)
                }
            } catch (e: Exception) {
                // If registration fails, display a message to the user.
                Log.w(TAG, "createUserWithEmailAndPassword:failure", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        }
    }

}