package org.example

/**
 * Estado de ejecución activo de la simulación: el cuerpo se encuentra en vuelo.
 *
 * Encapsula el comportamiento correspondiente a la fase en curso, delegando
 * las transiciones y la decisión de avance al propio estado.
 */
class EstadoEjecucionEnCurso : EstadoEjecucion {

    override fun nombre(): String = "Ejecutándose"

    override fun esActiva(): Boolean = true

    override fun iniciar(): EstadoEjecucion = this

    override fun pausar(): EstadoEjecucion = EstadoEjecucionPausado()

    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionReiniciado()

    override fun alternar(): EstadoEjecucion = EstadoEjecucionPausado()

    override fun debeAvanzar(deltaSegundos: Double): Boolean = deltaSegundos > 0.0
}
