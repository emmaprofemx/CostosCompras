package com.example.costoscompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.costoscompras.databinding.FragmentFirstBinding
import java.text.DecimalFormat

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val formato = DecimalFormat("#,##0.00")

    private val listaProductos = mutableListOf(
        Producto("Laptop", 16000.0),
        Producto("Monitor", 4500.0),
        Producto("Teclado", 700.0),
        Producto("Mouse", 350.0),
        Producto("Disco SSD 1TB", 2800.0),
        Producto("Gabinete RGB", 1500.0)
    )

    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductoAdapter(listaProductos) {
            calcularTotales()
        }

        binding.recyclerProductos.adapter = adapter
        binding.recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun calcularTotales() {
        val subtotal = listaProductos.sumOf { it.precio * it.cantidad }
        val iva = subtotal * 0.16
        val descuento = if (subtotal >= 3000.0) subtotal * 0.10 else 0.0
        val total = subtotal + iva - descuento

        binding.tvSubtotal.text = "Subtotal: \$${formato.format(subtotal)}"
        binding.tvIva.text = "IVA (16%): \$${formato.format(iva)}"
        binding.tvDescuento.text = "Descuento: \$${formato.format(descuento)}"
        binding.tvTotal.text = "Total a pagar: \$${formato.format(total)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

