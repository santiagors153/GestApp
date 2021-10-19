package com.example.tesisv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tesisv1.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario:String = "Mariela"
        val password:String = "123456"

        binding.btnLogin.setOnClickListener {
            if (binding.etUsuario.text.toString() == usuario || binding.etPassword.text.toString() == password){
                val intent = Intent(this, MenuHistorial::class.java)
                startActivity(intent)
            }
        }
    }


}