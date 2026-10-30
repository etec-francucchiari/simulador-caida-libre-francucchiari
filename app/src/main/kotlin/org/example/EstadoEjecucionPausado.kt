package org.example;

/**
 * Estado inactivo por pausa de la simulación.
 *
 * Encapsula la decisión de no avanzar el tiempo mientras está pausado y de
 * reiniciar hacia el estado de ejecución cuando se solicite reanudar.
 */
class EstadoEjecucionPausado : EstadoEjecucion {

    override fun nombre(): String = "Pausado"

    override fun esActiva(): Boolean = false

    override fun iniciar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    override fun pausar(): EstadoEjecucion = this

    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionReiniciado()

    override fun alternar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    override fun debeAvanzar(deltaSegundos: Double): Boolean = false
}
