package com.example.costoscompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onCambioCantidad: () -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvPrecio = itemView.findViewById<TextView>(R.id.tvPrecio)
        val tvCantidad = itemView.findViewById<TextView>(R.id.tvCantidad)
        val btnAgregar = itemView.findViewById<Button>(R.id.btnAgregar)
        val btnQuitar = itemView.findViewById<Button>(R.id.btnQuitar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Precio: \$${producto.precio}"
        holder.tvCantidad.text = "Cantidad: ${producto.cantidad}"

        holder.btnAgregar.setOnClickListener {
            producto.cantidad++
            notifyItemChanged(position)
            onCambioCantidad()
        }

        holder.btnQuitar.setOnClickListener {
            if (producto.cantidad > 0) {
                producto.cantidad--
                notifyItemChanged(position)
                onCambioCantidad()
            }
        }
    }

    override fun getItemCount(): Int = productos.size
}
