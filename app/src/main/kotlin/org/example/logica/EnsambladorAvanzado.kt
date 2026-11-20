package org.example

class EnsambladorAvanzado(
    nucleo: ProveedorNucleoSimulacion,
    controles: FabricaControlesUi = FabricaControles()
) {
    /**
     * Ensambla la pantalla del Modo Avanzado a partir de las dependencias
     * provistas por [ProveedorNucleoSimulacion].
     *
     * Coordina [FabricaControlesUi] y [FabricaPantallaAvanzada] para inyectar
     * cada colaborador por constructor (DIP).
     */

    private val fabrica = FabricaPantallaAvanzada(nucleo, controles)

    /**
     * Construye la pantalla completa del Modo Avanzado vinculada a la acción de
     * cambio de modo indicada.
     *
     * @param onCambiarModo callback para regresar a la selección de modo.
     */
    fun construir(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        return fabrica.crear(onCambiarModo)
    }
}
