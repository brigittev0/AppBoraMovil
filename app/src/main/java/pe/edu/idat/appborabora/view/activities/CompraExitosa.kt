package pe.edu.idat.appborabora.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.view.fragments.Dashboard

class CompraExitosa : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra_exitosa)

        val btnvolverdash = findViewById<Button>(R.id.btnvolverdash)
        btnvolverdash.setOnClickListener(this)

        val btnfactura = findViewById<Button>(R.id.btnfactura)
        btnfactura.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnvolverdash -> {
                val intent = Intent(this, HomeNavigation::class.java)
                startActivity(intent)
            }

            R.id.btnfactura -> {
                val intent = Intent(this, Factura::class.java)
                startActivity(intent)
            }
        }
    }
}