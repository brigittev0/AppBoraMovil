package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.HomeNavigation

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar = findViewById<Button>(R.id.btningresar)
        btningresar.setOnClickListener(this)
        val tvregistro = findViewById<TextView>(R.id.tvregistro)
        tvregistro.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btningresar -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            R.id.tvregistro -> {
                val intent = Intent(this, Registro::class.java)
                startActivity(intent)
            }
        }
    }
}