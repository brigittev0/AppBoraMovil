package pe.edu.idat.appborabora.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.view.activities.MainActivity
import pe.edu.idat.appborabora.view.activities.Registro

class Dashboard : Fragment() , View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSesion = view.findViewById<Button>(R.id.btnsesion)
        val btncuenta = view.findViewById<Button>(R.id.btncuenta)
        btnSesion.setOnClickListener(this)
        btncuenta.setOnClickListener(this)
    }
    override fun onClick(v: View?)

    {
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