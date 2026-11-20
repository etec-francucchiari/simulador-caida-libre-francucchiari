package org.example

class RegistroEstilosEntorno(
    private val baseRocosa: ProveedorMapaEstilos = RegistroEstilosRocosos()
) : ProveedorMapaEstilos {
    /**
     * Proveedor de estilos visuales predefinidos para los entornos del sistema solar.
     *
     * Desacopla a [CatalogoEstilosEntorno] de las implementaciones concretas de cada
     * planeta delegando en [ProveedorMapaEstilos] (DIP / CBO).
     */

    /**
     * Construye el mapa inmutable de estilos indexado por el nombre canónico del entorno.
     */
    override fun crearMapa(): Map<String, EstiloEntorno> {
        return baseRocosa.crearMapa() + mapOf("Luna" to EstiloLuna())
    }

    /**
     * Devuelve la cantidad total de estilos planetarios registrados en el catálogo base.
     */
    fun cantidadRegistrados(): Int {
        return crearMapa().size
    }
}
