package pe.edu.idat.appborabora.util

import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse

object Cart {

    val productosSeleccionados = mutableListOf<ProductoDashboardResponse>()

    fun agregarProducto(producto: ProductoDashboardResponse): Boolean {
        return if (!productosSeleccionados.contains(producto)) {
            productosSeleccionados.add(producto)
            true
        } else {
            false
        }
    }

    fun removerProducto(producto: ProductoDashboardResponse) {
        productosSeleccionados.remove(producto)
    }

    fun obtenerProductos(): List<ProductoDashboardResponse> {
        return productosSeleccionados
    }
}