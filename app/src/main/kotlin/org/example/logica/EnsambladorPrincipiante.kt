package org.example

class EnsambladorPrincipiante(
    private val nucleo: ProveedorNucleoSimulacion
) {
    /**
     * Ensambla la pantalla de simulación del Modo Principiante a partir de las
     * dependencias provistas por el [ProveedorNucleoSimulacion].
     *
     * Extraído de [ComposicionAplicacion] para mantener cada ensamblador de modo
     * enfocado y con acoplamiento mínimo (SRP / CBO).
     */

    /**
     * Construye la pantalla completa del Modo Principiante conectada al estado
     * y al controlador del núcleo.
     */
    fun construir(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): PrincipianteSimulationScreen {
        return FabricaPantallaPrincipiante(nucleo).crear(onCambiarPlaneta, onCambiarModo)
    }

    /**
     * Delega en [EnsambladorAvanzado] la construcción de la pantalla del Modo Avanzado.
     */
    fun construirAvanzada(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        return EnsambladorAvanzado(nucleo).construir(onCambiarModo)
    }
}
