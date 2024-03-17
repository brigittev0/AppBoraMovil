package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.view.fragments.Dashboard

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar = findViewById<Button>(R.id.btningresar)
        btningresar.setOnClickListener(this)
        val btnregistrar = findViewById<TextView>(R.id.btnregistrar)
        btnregistrar.setOnClickListener(this)
        val btninvitado = findViewById<TextView>(R.id.btninvitado)
        btninvitado.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btningresar -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            R.id.btnregistrar -> {
                val intent = Intent(this, RegisterUser::class.java)
                startActivity(intent)
            }
            R.id.btninvitado -> {
                val intent = Intent(this, HomeNavigation::class.java)
                startActivity(intent)
            }
        }
    }
}