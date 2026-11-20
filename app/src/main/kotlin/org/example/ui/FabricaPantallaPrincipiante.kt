package org.example

class FabricaPantallaPrincipiante(
    private val nucleo: ProveedorNucleoSimulacion
) {
    /**
     * Fábrica que ensambla la pantalla de simulación del Modo Principiante
     * inyectando sus subvistas y controles (DIP / CBO).
     */

    /**
     * Construye la pantalla completa del Modo Principiante con las acciones de
     * navegación indicadas.
     */
    fun crear(
        onCambiarPlaneta: () -> Unit,
        onCambiarModo: () -> Unit
    ): PrincipianteSimulationScreen {
        val vistaFormulas = VistaPestanaFormulas(
            nucleo.contexto(),
            nucleo.calculo(),
            nucleo.configuracion()
        )
        val pantalla = PrincipianteSimulationScreen(vistaFormulas.construir(), nucleo)
        pantalla.configurarBarras(onCambiarPlaneta, onCambiarModo)
        return pantalla
    }
}
