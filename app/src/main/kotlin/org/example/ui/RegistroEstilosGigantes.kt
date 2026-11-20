package org.example

class RegistroEstilosGigantes(
    private val claveMarte: String = "Marte"
) : ProveedorMapaEstilos {
    /**
     * Proveedor de los estilos visuales de Marte y Júpiter ([ProveedorMapaEstilos]).
     *
     * Extraído de [RegistroEstilosEntorno] para distribuir la creación de
     * paletas planetarias manteniendo baja la cantidad de dependencias concretas (DIP).
     */

    override fun crearMapa(): Map<String, EstiloEntorno> {
        return mapOf(
            claveMarte to EstiloMarte(),
            "Júpiter" to EstiloJupiter()
        )
    }
}
