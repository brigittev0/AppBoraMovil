package pe.edu.idat.appborabora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.idat.appborabora.view.fragments.Dashboard
import pe.edu.idat.appborabora.view.fragments.DetalleHistorialCompra

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, Dashboard())
                .commit()
        }
    }
}