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
            vistaSimulacion = crearVistaSimulacion()
        )
        return fabrica.crear(onCambiarPlaneta, onCambiarModo)
    }

    /**
     * Construye la vista de la pestaña didáctica de fórmulas paso a paso.
     */
    private fun crearVistaFormulas(): VistaPestanaFormulas {
        return VistaPestanaFormulas(
            contexto = nucleo.contexto(),
            explicador = ExplicadorFisicaPasoAPaso(nucleo.calculo()),
            validador = ValidadorParametrosIniciales(nucleo.configuracion())
        )
    }

    /**
     * Construye la vista de la pestaña de simulación interactiva en tiempo real.
     */
    private fun crearVistaSimulacion(): VistaPestanaSimulacion {
        return VistaPestanaSimulacion(
            estado = nucleo.contexto(),
            series = ConstructorSeriesCinematica(nucleo.calculo()),
            renderizador = nucleo.crearRenderizador()
        )
    }
}
