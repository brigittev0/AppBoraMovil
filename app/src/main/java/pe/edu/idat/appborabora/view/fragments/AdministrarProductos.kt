package pe.edu.idat.appborabora.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import pe.edu.idat.appborabora.R
import pe.edu.idat.appborabora.databinding.FragmentAdministrarProductosBinding

class AdministrarProductos : Fragment() {
    private var _binding: FragmentAdministrarProductosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdministrarProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Navega mediante el boton hacia el fragment
        binding.btnCrear.setOnClickListener {
            findNavController().navigate(R.id.crearProducto)
        }
        binding.btnActualizar.setOnClickListener {
            findNavController().navigate(R.id.actualizarProducto)
        }
        binding.btnEliminar.setOnClickListener {
            findNavController().navigate(R.id.eliminarProducto)
        }
        binding.btnVer.setOnClickListener {
            findNavController().navigate(R.id.verProductos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}