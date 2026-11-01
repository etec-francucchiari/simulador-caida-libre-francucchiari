package org.example

class EstadoEjecucionReiniciado : EstadoEjecucion {
    /**
     * Implementación del Patrón State ([EstadoEjecucion]) que representa la fase
     * inicial de reposo (`t = 0 s`) previa al lanzamiento o posterior a un
     * reinicio explícito.
     *
     * Mantiene inactivo el temporizador de fotogramas y permite iniciar la
     * caída libre transicionando hacia [EstadoEjecucionEnCurso] cuando el
     * usuario pulsa el botón de reproducción.
     */

    /**
     * Nombre descriptivo del estado reiniciado.
     */
    override fun nombre(): String = "Reiniciado"

    /**
     * Indica que la simulación se encuentra detenida en el origen temporal.
     */
    override fun esActiva(): Boolean = false

    /**
     * Comienza el movimiento de caída libre pasando a [EstadoEjecucionEnCurso].
     */
    override fun iniciar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    /**
     * Pausar en estado reiniciado mantiene el reposo inicial sin cambios.
     */
    override fun pausar(): EstadoEjecucion = this

    /**
     * Reiniciar cuando ya se está en el origen conserva el mismo estado.
     */
    override fun reiniciar(): EstadoEjecucion = this

    /**
     * Alternar desde el estado reiniciado arranca la ejecución activa.
     */
    override fun alternar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    /**
     * En estado reiniciado no se integran incrementos de tiempo.
     */
    override fun debeAvanzar(deltaSegundos: Double): Boolean = false
}
