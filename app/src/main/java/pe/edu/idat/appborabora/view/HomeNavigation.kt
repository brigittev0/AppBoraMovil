package pe.edu.idat.appborabora.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.RelativeLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.databinding.ActivityHomeNavigationBinding
import pe.edu.idat.appborabora.view.activities.Compra
import pe.edu.idat.appborabora.view.activities.Login

class HomeNavigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeNavigation.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home_navigation)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboard, R.id.catalogo, R.id.contacto, R.id.perfil, R.id.historialCompra,
                R.id.metodoPago, R.id.metodoPagoTarjeta, R.id.detalleHistorialCompra, R.id.detalleProducto
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Manejar la navegación a CompraAct
        navView.menu.findItem(R.id.compra).setOnMenuItemClickListener {
            val intent = Intent(this, Compra::class.java)
            startActivity(intent)
            true
        }

        // Manejar el cierre de sesión
        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            logout()
            true
        }
    }

    // Cierre de sesion - provisional
    private fun logout() {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().remove("token").apply()
        sharedPref.edit().remove("username").apply()
        sharedPref.edit().remove("role").apply()

        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}



