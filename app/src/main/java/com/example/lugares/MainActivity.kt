package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //lateini: variable creada sin valor que debe ser inicializada luego (costructor)
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth
        binding.btRegister.setOnClickListener{
            registro()
        }
        binding.btLogin.setOnClickListener {
            login()
        }
    }

    //fun: function
    private fun registro() {
        val email = binding.etEmail.text.toString()
        val contrasena = binding.etPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("Creando usuario", "Registrado")
                    val user = auth.currentUser
                    cambioPantalla(user)
                } else {
                    Log.d("Creando usuario", "Falló")
                    Toast.makeText(baseContext, "Falló", Toast.LENGTH_LONG).show()
                    cambioPantalla(null)
                }
            }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val contrasena = binding.etPassword.text.toString()

        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("Creando usuario", "Registrado")
                    val user = auth.currentUser
                    cambioPantalla(user)
                } else {
                    Log.d("Creando usuario", "Falló")
                    Toast.makeText(baseContext, "Falló", Toast.LENGTH_LONG).show()
                    cambioPantalla(null)
                }
            }
    }

    //Mostrar actividad, es decir cambiar de pantalla
    private fun cambioPantalla(usuario: FirebaseUser?) {
        if (usuario != null) {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    //En caso que el usuario ya este logeado no se pidan credenciales de nuevo
    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        cambioPantalla(user)
    }

}