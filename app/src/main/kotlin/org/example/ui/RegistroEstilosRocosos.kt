package org.example

class RegistroEstilosRocosos(
    private val baseExterna: ProveedorMapaEstilos = RegistroEstilosGigantes()
) : ProveedorMapaEstilos {
    /**
     * Proveedor que incorpora el estilo de la Tierra al catálogo de estilos
     * planetarios ([ProveedorMapaEstilos]).
     */

    override fun crearMapa(): Map<String, EstiloEntorno> {
        return mapOf("Tierra" to EstiloTierra()) + baseExterna.crearMapa()
    }
}
