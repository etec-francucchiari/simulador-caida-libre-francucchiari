package org.example

class FabricaPantallaAvanzada(
    nucleo: ProveedorNucleoSimulacion,
    fabricaControles: FabricaControlesUi
) {
    /**
     * Fábrica que ensambla la pantalla completa del Modo Avanzado inyectando
     * todos sus colaboradores (DIP).
     */

    private val componentes = FabricaComponentesAvanzados(nucleo, fabricaControles)

    /**
     * Construye una instancia configurada de [ModoAvanzadoScreen] lista para
     * presentarse en el contenedor raíz de navegación.
     *
     * @param onCambiarModo acción para regresar a la selección de modo.
     */
    fun crear(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        val pantalla = ModoAvanzadoScreen(componentes)
        return pantalla.configurar(onCambiarModo)
    }
}
