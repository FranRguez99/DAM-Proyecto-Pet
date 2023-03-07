package es.duarry.loginbasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import es.duarry.loginbasico.databinding.ActivityRegisterBinding
import es.duarry.loginbasico.databinding.LoginActivityBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}