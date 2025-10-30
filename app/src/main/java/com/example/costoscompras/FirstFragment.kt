package com.example.costoscompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.costoscompras.databinding.FragmentFirstBinding
import java.text.DecimalFormat

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

        binding.btnAgregarProducto.setOnClickListener {
            agregarProducto()
        }

        binding.btnCalcular.setOnClickListener {
            calcularTotal()
        }
    }

    private fun agregarProducto() {
        val contexto = requireContext()
        val productoLayout = LinearLayout(contexto).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 16, 0, 16)
        }

        val etNombre = EditText(contexto).apply {
            hint = "Producto"
        }

        val etPrecio = EditText(contexto).apply {
            hint = "Precio"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        val etCantidad = EditText(contexto).apply {
            hint = "Cantidad"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        productoLayout.addView(etNombre)
        productoLayout.addView(etPrecio)
        productoLayout.addView(etCantidad)

        binding.productListContainer.addView(productoLayout)
    }

    private fun calcularTotal() {
        var subtotal = 0.0

        for (i in 0 until binding.productListContainer.childCount) {
            val productoView = binding.productListContainer.getChildAt(i) as LinearLayout

            val etNombre = productoView.getChildAt(0) as EditText
            val etPrecio = productoView.getChildAt(1) as EditText
            val etCantidad = productoView.getChildAt(2) as EditText

            val precio = etPrecio.text.toString().toDoubleOrNull()
            val cantidad = etCantidad.text.toString().toIntOrNull()

            if (precio == null || cantidad == null) {
                Toast.makeText(requireContext(), "Revisa los campos del producto ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }

            subtotal += precio * cantidad
        }

        val iva = subtotal * 0.16
        val total = subtotal + iva

        binding.tvSubtotal.text = "Subtotal: \$${formato.format(subtotal)}"
        binding.tvIva.text = "IVA: \$${formato.format(iva)}"
        binding.tvTotalPagar.text = "Total a pagar: \$${formato.format(total)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
