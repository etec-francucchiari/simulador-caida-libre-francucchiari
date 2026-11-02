package org.example

class FabricaContextoSimulacion(
    private val configuracion: ConfiguracionSimulacion,
    private val calculo: CalculadoraCaidaLibre
) {
    /**
     * Fábrica que construye el gestor de estado reactivo de la simulación con
     * sus memorias observables y su motor físico inyectados (DIP).
     *
     * Centraliza la creación de [MemoriaParametrosSimulacion],
     * [MemoriaInstantaneaSimulacion] y [MotorEstadoCaida] para que tanto la raíz
     * de composición como las pruebas unitarias instancien el gestor sin duplicar
     * la configuración de sus colaboradores.
     */

    /**
     * Construye un nuevo [GestorEstadoSimulacion] inicializado con el entorno
     * gravitatorio indicado y en estado reiniciado.
     *
     * @param planetaInicial entorno gravitatorio seleccionado por defecto.
     */
    fun crear(planetaInicial: EntornoGravitatorio) =
        GestorEstadoSimulacion(
            parametros = MemoriaParametrosSimulacion(configuracion, planetaInicial),
            instantanea = MemoriaInstantaneaSimulacion(configuracion),
            motor = crearMotor(),
            estadoInicial = EstadoEjecucionReiniciado()
        )

    /**
     * Instancia el motor de evaluación cinemática delegando en [CalculadoraCaidaLibre].
     */
    private fun crearMotor() = MotorEstadoCaida(calculo)
}
