package org.example

class FabricaPantallaPrincipiante(
    private val contexto: ContextoSimulacion,
    private val control: ControlReproduccion,
    private val vistaFormulas: VistaPestanaFormulas,
    private val vistaSimulacion: VistaPestanaSimulacion
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
     * @param onCambiarPlaneta acción para regresar a la selección de entorno.
     * @param onCambiarModo acción para regresar a la selección de modo.
     */
    fun crear(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): PrincipianteSimulationScreen {
        val encabezado = crearEncabezado(onCambiarPlaneta, onCambiarModo)
        val controles = BarraControlesSimulacion(contexto, control)
        return PrincipianteSimulationScreen(
            encabezado = encabezado,
            pestanaFormulas = vistaFormulas.construir(),
            pestanaSimulacion = vistaSimulacion.construir(),
            barraControles = controles
        )
    }

    /**
     * Construye la barra de encabezado superior vinculada al estado reactivo.
     */
    private fun crearEncabezado(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): EncabezadoSimulacion {
        return EncabezadoSimulacion(contexto, onCambiarPlaneta, onCambiarModo)
    }
}
