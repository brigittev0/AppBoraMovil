package pe.edu.idat.appborabora.util

object Cart {

    val productosSeleccionados = mutableListOf<ProductCart>()

    fun agregarProducto(producto: ProductCart): Boolean {
        val existingProduct = productosSeleccionados.find { it.idProducto == producto.idProducto }
        return if (existingProduct == null) {
            productosSeleccionados.add(producto)
            true
        } else {
            existingProduct.quantity++
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