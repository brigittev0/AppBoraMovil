package pe.edu.idat.appborabora.util

object Cart {

    val productosSeleccionados = mutableListOf<ProductCart>()

    fun agregarProducto(producto: ProductCart): Boolean {
        return if (!productosSeleccionados.contains(producto)) {
            productosSeleccionados.add(producto)
            true
        } else {
            false
        }
    }

    fun eliminarProducto(position: Int) {
        if (position >= 0 && position < productosSeleccionados.size) {
            productosSeleccionados.removeAt(position)
        }
    }

    fun obtenerProductos(): List<ProductCart> {
        return productosSeleccionados
    }
}