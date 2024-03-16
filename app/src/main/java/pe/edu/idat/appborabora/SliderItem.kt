package pe.edu.idat.appborabora

class SliderItem {
    var titulo: String? = null
    var imagen: Int = 0

    constructor()

    constructor(imagen: Int, titulo: String) {
        this.imagen = imagen
        this.titulo = titulo
    }
}