package com.example.costoscompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.costoscompras.databinding.FragmentFirstBinding
import java.text.DecimalFormat
import android.widget.Toast

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val formato = DecimalFormat("#,##0.00")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalcular.setOnClickListener {
            calcularCosto()
        }
    }

    private fun calcularCosto() {
        val producto = binding.etProducto.text.toString()
        val precioStr = binding.etPrecio.text.toString()
        val cantidadStr = binding.etCantidad.text.toString()

        if (producto.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull()
        val cantidad = cantidadStr.toIntOrNull()

        if (precio == null || cantidad == null) {
            Toast.makeText(requireContext(), "Valores numéricos inválidos", Toast.LENGTH_SHORT).show()
            return
        }

        val subtotal = precio * cantidad
        val iva = subtotal * 0.16
        val total = subtotal + iva

        val descuento = if (total > 30000) total * 0.10 else 0.0
        val totalPagar = total - descuento

        // Mostrar resultados
        binding.tvIva.text = "IVA: \$${formato.format(iva)}"
        binding.tvTotal.text = "Total: \$${formato.format(total)}"
        binding.tvDescuento.text = "Descuento: \$${formato.format(descuento)}"
        binding.tvTotalPagar.text = "Total a pagar: \$${formato.format(totalPagar)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
