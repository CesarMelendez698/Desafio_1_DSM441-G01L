package com.example.miperfil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResumenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        val tvResumenDatos = findViewById<TextView>(R.id.tvResumenDatos)
        val btnRegresarInicio = findViewById<Button>(R.id.btnRegresarInicio)
        val btnNuevoPerfil = findViewById<Button>(R.id.btnNuevoPerfil)

        val nombres = intent.getStringExtra("NOMBRES")
        val correo = intent.getStringExtra("CORREO")
        val telefono = intent.getStringExtra("TELEFONO")
        val fecha = intent.getStringExtra("FECHA")
        val direccion = intent.getStringExtra("DIRECCION")
        val estadoCamara = intent.getStringExtra("ESTADO_CAMARA")

        val resumen = "• Nombres y Apellidos:\n$nombres\n\n" +
                "• Correo Electrónico:\n$correo\n\n" +
                "• Número de Teléfono:\n$telefono\n\n" +
                "• Fecha de Nacimiento:\n$fecha\n\n" +
                "• Dirección:\n$direccion\n\n" +
                "• Estado de Cámara:\n$estadoCamara"

        tvResumenDatos.text = resumen

        btnRegresarInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

        btnNuevoPerfil.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}