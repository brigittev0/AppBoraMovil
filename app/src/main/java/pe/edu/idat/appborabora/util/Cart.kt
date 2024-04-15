package pe.edu.idat.appborabora.util

import android.util.Log

object Cart {

    val productosSeleccionados = mutableListOf<ProductCart>()

    fun agregarProducto(producto: ProductCart): Boolean {
        val result = if (!productosSeleccionados.contains(producto)) {
            productosSeleccionados.add(producto)
            true
        } else {
            false
        }
        Log.d("Cart", "Producto agregado: $producto")
        return result
    }

    fun eliminarProducto(position: Int) {
        if (position >= 0 && position < productosSeleccionados.size) {
            productosSeleccionados.removeAt(position)
        }
    }

    fun actualizarCantidadProducto(producto: ProductCart, nuevaCantidad: Int) {
        val productoEnCarrito = productosSeleccionados.find { it == producto }
        productoEnCarrito?.quantity = nuevaCantidad
        Log.d("Cart", "Cantidad actualizada: ${producto.quantity}") // Agrega un mensaje de registro
    }

    fun obtenerProductos(): List<ProductCart> {
        return productosSeleccionados
    }

    fun limpiarCarrito() {
        productosSeleccionados.clear()
    }
}