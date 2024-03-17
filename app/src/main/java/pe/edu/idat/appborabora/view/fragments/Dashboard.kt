package pe.edu.idat.appborabora.view.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.SliderItem
import pe.edu.idat.appborabora.view.HomeNavigation
import pe.edu.idat.appborabora.view.SliderAdapter
import pe.edu.idat.appborabora.view.activities.MainActivity
import pe.edu.idat.appborabora.view.activities.Registro

class Dashboard : Fragment(), View.OnClickListener {

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var svCarrusel: SliderView
    private lateinit var loginOption: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        loginOption = view.findViewById(R.id.loginOption)

        val btnSesion = view.findViewById<Button>(R.id.btnsesion)
        val btncuenta = view.findViewById<Button>(R.id.btncuenta)
        btnSesion.setOnClickListener(this)
        btncuenta.setOnClickListener(this)

        init(view)
        initAdapter()
        loadData()

        return view
    }

    // Actualizacion de interfaz, segun inicio de sesion del usuario
    override fun onResume() {
        super.onResume()

        val sharedPref = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", null)

        Log.d("DashboardFragment", "Token: $token")

        if (token == null) {
            // El usuario no ha iniciado sesión, muestra la opción de inicio de sesión
            loginOption.visibility = View.VISIBLE
        } else {
            // El usuario ha iniciado sesión, oculta la opción de inicio de sesión
            loginOption.visibility = View.GONE
        }
    }

    private fun init(v: View) {
        svCarrusel = v.findViewById(R.id.svCarrusel)
    }

    private fun initAdapter() {
        sliderAdapter = SliderAdapter(requireContext())
        svCarrusel.setSliderAdapter(sliderAdapter)
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM)
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        svCarrusel.setIndicatorSelectedColor(Color.WHITE)
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY)
        svCarrusel.setScrollTimeInSec(4)
        svCarrusel.startAutoCycle()
    }

    private fun loadData() {
        val lista = mutableListOf<SliderItem>(
            SliderItem(R.drawable.img_8, ""),
            SliderItem(R.drawable.img_5, ""),
            SliderItem(R.drawable.img_01, ""),
            SliderItem(R.drawable.img_04, ""),
            SliderItem(R.drawable.img_3, "")
        )

        sliderAdapter.updateItem(lista)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnsesion -> {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btncuenta -> {
                val intent = Intent(activity, Registro::class.java)
                startActivity(intent)
            }
        }
    }
}