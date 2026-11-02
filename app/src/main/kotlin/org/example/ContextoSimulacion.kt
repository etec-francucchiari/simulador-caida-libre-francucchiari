package org.example

interface ContextoSimulacion : EstadoSimulacion, ComandosSimulacion {
    /**
     * Contrato combinado del estado observable y los comandos de la simulación.
     *
     * Se usa como dependencia inyectada del controlador del bucle de animación
     * y de los ensambladores que coordinan lectura y mutación del escenario.
     */

    /** Estado actual de la máquina de estados de la simulación (Patrón State). */
    fun estadoEjecucion(): EstadoEjecucion
}
