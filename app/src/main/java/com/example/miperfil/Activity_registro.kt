package com.example.miperfil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 100
    private var permisoConcedido = false

    private lateinit var etNombres: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var etDireccion: EditText
    private lateinit var tvEstadoPermiso: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etNombres = findViewById(R.id.etNombres)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        etDireccion = findViewById(R.id.etDireccion)
        tvEstadoPermiso = findViewById(R.id.tvEstadoPermiso)
        val btnTomarFoto = findViewById<Button>(R.id.btnTomarFoto)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnTomarFoto.setOnClickListener {
            pedirPermisoCamara()
        }

        btnGuardar.setOnClickListener {
            validarYGuardarDatos()
        }
    }

    private fun pedirPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            permisoConcedido = true
            tvEstadoPermiso.text = "El usuario concedió el permiso para la cámara."
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisoConcedido = true
                tvEstadoPermiso.text = "El usuario concedió el permiso para la cámara."
            } else {
                permisoConcedido = false
                tvEstadoPermiso.text = "No se puede acceder a la cámara."
                Toast.makeText(this, "No puede acceder a la cámara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarYGuardarDatos() {
        val nombres = etNombres.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val fechaNac = etFechaNacimiento.text.toString().trim()
        val direccion = etDireccion.text.toString().trim()

        if (nombres.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaNac.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.error = "Formato de correo inválido"
            return
        }

        if (telefono.length < 8) {
            etTelefono.error = "Número de teléfono inválido"
            return
        }

        val regexFecha = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/([0-9]{4})$"
        if (!Pattern.matches(regexFecha, fechaNac)) {
            etFechaNacimiento.error = "Formato inválido (Use DD/MM/AAAA)"
            return
        }

        val intent = Intent(this, ResumenActivity::class.java).apply {
            putExtra("NOMBRES", nombres)
            putExtra("CORREO", correo)
            putExtra("TELEFONO", telefono)
            putExtra("FECHA", fechaNac)
            putExtra("DIRECCION", direccion)
            putExtra("ESTADO_CAMARA", if (permisoConcedido) "Permiso concedido" else "Permiso no concedido")
        }
        startActivity(intent)
        finish()
    }
}