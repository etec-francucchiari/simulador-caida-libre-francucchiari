package org.example

class EnsambladorPrincipiante(
    private val nucleo: NucleoSimulacion
) {
    /**
     * Ensambla la pantalla de simulación del Modo Principiante a partir de las
     * dependencias provistas por el [NucleoSimulacion].
     *
     * Extraído de [ComposicionAplicacion] para mantener cada ensamblador de modo
     * enfocado en una única pantalla (SRP) y con acoplamiento mínimo (CBO).
     */

    /**
     * Construye la pantalla completa del Modo Principiante conectada al estado
     * y al controlador del núcleo.
     *
     * @param onCambiarPlaneta callback para volver a la selección de planeta.
     * @param onCambiarModo callback para volver a la selección de modo.
     */
    fun construir(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): PrincipianteSimulationScreen {
        val fabrica = FabricaPantallaPrincipiante(
            contexto = nucleo.contexto(),
            control = nucleo.control(),
            vistaFormulas = crearVistaFormulas(),
            calculo = nucleo.calculo()
        )
        return fabrica.crear(nucleo.crearRenderizador(), onCambiarPlaneta, onCambiarModo)
    }

    /**
     * Construye la vista de la pestaña didáctica de fórmulas paso a paso.
     */
    private fun crearVistaFormulas(): VistaPestanaFormulas {
        return VistaPestanaFormulas(
            contexto = nucleo.contexto(),
            calculo = nucleo.calculo(),
            configuracion = nucleo.configuracion()
        )
    }
}
