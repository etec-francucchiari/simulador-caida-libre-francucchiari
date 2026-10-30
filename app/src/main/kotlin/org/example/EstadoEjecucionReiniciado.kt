package org.example;

/**
 * Estado inactivo de reposo de la simulación, previo a reiniciar o iniciar.
 *
 * Encapsula la fase inicial del ciclo: sin ejecución física activa hasta que
 * se solicite iniciar.
 */
class EstadoEjecucionReiniciado : EstadoEjecucion {

    override fun nombre(): String = "Reiniciado"

    override fun esActiva(): Boolean = false

    override fun iniciar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    override fun pausar(): EstadoEjecucion = this

    override fun reiniciar(): EstadoEjecucion = this

    override fun alternar(): EstadoEjecucion = EstadoEjecucionEnCurso()

    override fun debeAvanzar(deltaSegundos: Double): Boolean = false
}
