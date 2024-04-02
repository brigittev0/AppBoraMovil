package pe.edu.idat.appborabora.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.navigateUp
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.databinding.ActivityHomeNavigationBinding
import pe.edu.idat.appborabora.view.activities.Compra
import pe.edu.idat.appborabora.view.activities.Login
import pe.edu.idat.appborabora.view.activities.MainActivity

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

        //Codigo para visualizar el username en la header home
        val sharedPref = getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

        val headerView = navView.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.headerUsername)

        val username = sharedPref.getString("username", "!Bienvenido!")

        navUsername.text = username


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

        updateMenu(navView.menu)
    }

    // Navegación cuando se presiona el botón de hamburguesa
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Cierre de sesion
    private fun logout() {
        val sharedPref = getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        sharedPref.edit().remove("username").apply()
        sharedPref.edit().remove("role").apply()
        sharedPref.edit().remove("jwt").apply()

        // Actualiza la visibilidad de los elementos del menú
        updateMenu(binding.navView.menu)

        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Si el usuario a iniciado sesión
    // Se actualiza la visibilidad de los elementos del menú
    private fun updateMenu(menu: Menu) {
        val sharedPref = getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        val jwt = sharedPref.getString("jwt", null)

        val isLoggedIn = jwt != null
        menu.findItem(R.id.activity_login).isVisible = !isLoggedIn
        menu.findItem(R.id.perfil).isVisible = isLoggedIn
        menu.findItem(R.id.logout).isVisible = isLoggedIn
    }
}



