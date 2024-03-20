package pe.edu.idat.appborabora.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.databinding.FragmentContactoBinding

class Contacto : Fragment(), View.OnClickListener {

    private var _binding: FragmentContactoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactoBinding.inflate(inflater, container, false)
        binding.btnChatear.setOnClickListener(this)
        binding.btnLlamar.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnChatear -> abrirChatWhatsApp()
            R.id.btnLlamar -> realizarLlamada()
        }
    }

    private fun abrirChatWhatsApp() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/51904392864")
        }
        startActivity(intent)
    }

    private fun realizarLlamada() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:51904392864")
        }
        startActivity(intent)
    }

}