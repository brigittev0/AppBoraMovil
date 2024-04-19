package pe.edu.idat.appborabora.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
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
import pe.edu.idat.appborabora.util.Cart
import pe.edu.idat.appborabora.view.activities.Purchase
import pe.edu.idat.appborabora.view.activities.Login
import pe.edu.idat.appborabora.view.activities.MainActivity
import pe.edu.idat.appborabora.view.fragments.HistorialCompra

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
                R.id.detalleHistorialCompra, R.id.detalleProducto, R.id.administrarProductos), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Codigo para visualizar el username en la header home
        val sharedPref = getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)

        val headerView = navView.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.headerUsername)

        val username = sharedPref.getString("username", "Invitado")

        //codigo para que muestre su rol + username
        val role = sharedPref.getString("role", null)
        val displayRole = when (role) {
            "ROLE_ADMIN_BASIC" -> "Admin"
            "ROLE_ADMIN_FULL" -> "Admin Full"
            "ROLE_USER" -> "User"
            else -> "Se encuentra como"
        }

        navUsername.text = "$displayRole $username"


        // Manejar la navegación a CompraAct
        navView.menu.findItem(R.id.compra).setOnMenuItemClickListener {
            val intent = Intent(this, Purchase::class.java)
            startActivity(intent)
            true
        }

        // Manejar el cierre de sesión
        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            logout()
            true
        }

        // Manejar el inicio de sesión
        navView.menu.findItem(R.id.login).setOnMenuItemClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            true
        }

        // Manejar la navegación a historialCompra


        updateMenu(navView.menu)
    }

    // Navegación cuando se presiona el botón de hamburguesa
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Cierre de sesion
    private fun logout() {
        //Limpieza
        val sPUserLogged= getSharedPreferences("UsuarioLogueado", Context.MODE_PRIVATE)
        sPUserLogged.edit().clear().apply()

        val sPPayment= getSharedPreferences("Payment", Context.MODE_PRIVATE)
        sPPayment.edit().clear().apply()


        val sPDeliveryPickup = getSharedPreferences("DeliveryPickup", Context.MODE_PRIVATE)
        sPDeliveryPickup.edit().clear().apply()
        Cart.limpiarCarrito()

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
        val role = sharedPref.getString("role", null)

        val isLoggedIn = jwt != null
        val isAdminBasic = role == "ROLE_ADMIN_BASIC"
        val isAdminFull = role == "ROLE_ADMIN_FULL"
        val isUser = role == "ROLE_USER"

        menu.findItem(R.id.login).isVisible = !isLoggedIn
        menu.findItem(R.id.perfil).isVisible = isLoggedIn
        menu.findItem(R.id.logout).isVisible = isLoggedIn
        menu.findItem(R.id.historialCompra).isVisible = isUser
        menu.findItem(R.id.administrarProductos).isVisible = isAdminBasic || isAdminFull
    }
}



