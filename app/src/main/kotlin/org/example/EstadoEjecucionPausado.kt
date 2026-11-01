package org.example

class EstadoEjecucionPausado : EstadoEjecucion {
    /**
     * Implementación del Patrón State ([EstadoEjecucion]) que modela la fase de
     * pausa de la simulación.
     *
     * Cuando la simulación se encuentra en este estado, las coordenadas
     * cinemáticas instantáneas (tiempo, altura y velocidad) se conservan en su
     * último valor calculado, pero el bucle de animación no integra nuevos
     * incrementos temporales hasta que el usuario solicite reanudar.
     */

    /**
     * Nombre descriptivo del estado de pausa.
     */
    override fun nombre(): String = "Pausado"

    /**
     * Indica que el bucle de animación no se encuentra en ejecución activa.
     */
    override fun esActiva(): Boolean = false

    /**
     * Reanuda el descenso transicionando a [EstadoEjecucionEnCurso].
     */
    override fun iniciar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    /**
     * Al estar ya pausada, volver a pausar conserva el estado actual.
     */
    override fun pausar(): EstadoEjecucion = this

    /**
     * Restablece la simulación al instante inicial mediante [EstadoEjecucionReiniciado].
     */
    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionReiniciado()

    /**
     * Conmuta desde la pausa hacia la ejecución en curso.
     */
    override fun alternar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    /**
     * Impide que el motor físico avance el tiempo mientras dure la pausa.
     */
    override fun debeAvanzar(deltaSegundos: Double): Boolean = false
}
