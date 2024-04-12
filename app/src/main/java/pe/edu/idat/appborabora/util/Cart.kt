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

    fun removerProducto(producto: ProductCart) {
        productosSeleccionados.remove(producto)
    }

    fun obtenerProductos(): List<ProductCart> {
        return productosSeleccionados
    }
}