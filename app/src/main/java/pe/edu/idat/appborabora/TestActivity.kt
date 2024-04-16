package pe.edu.idat.appborabora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.idat.appborabora.view.fragments.Catalogo
import pe.edu.idat.appborabora.view.fragments.DetalleProducto
import pe.edu.idat.appborabora.view.fragments.ListarProdCategoria

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(com.google.android.material.R.id.container, DetalleProducto())
                .commit()
        }
    }
}