package org.example

class FabricaPantallaPrincipiante(
    private val contexto: ContextoSimulacion,
    private val control: ControlReproduccion,
    private val vistaFormulas: VistaPestanaFormulas,
    private val calculo: CalculadoraCaidaLibre
) {
    /**
     * Fábrica que ensambla la pantalla de simulación del Modo Principiante
     * inyectando sus subvistas y controles (DIP).
     *
     * Permite que [PrincipianteSimulationScreen] actúe únicamente como un
     * contenedor de disposición visual sin acoplarse a los servicios de validación,
     * explicación algebraica o renderizado.
     */

    /**
     * Construye la pantalla completa del Modo Principiante con las acciones de
     * navegación indicadas.
     *
     * @param renderizador renderizador del lienzo de caída libre.
     * @param onCambiarPlaneta acción para regresar a la selección de entorno.
     * @param onCambiarModo acción para regresar a la selección de modo.
     */
    fun crear(
        renderizador: RenderizadorAnimacionCaida,
        onCambiarPlaneta: () -> Unit,
        onCambiarModo: () -> Unit
    ): PrincipianteSimulationScreen {
        val vistaSimulacion = VistaPestanaSimulacion(contexto, calculo, renderizador)
        val pantalla = PrincipianteSimulationScreen(
            pestanaFormulas = vistaFormulas.construir(),
            pestanaSimulacion = vistaSimulacion.construir()
        )
        pantalla.configurarBarras(contexto, control, onCambiarPlaneta, onCambiarModo)
        return pantalla
    }
}
