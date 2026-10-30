package org.example

/**
 * Estado de la máquina de estados de la simulación de caída libre (Patrón State).
 *
 * Define el comportamiento de cada fase del ciclo de vida de la simulación:
 * - Ejecutándose ([EstadoEjecucionEnCurso]).
 * - Pausado ([EstadoEjecucionPausado]).
 * - Reiniciado ([EstadoEjecucionReiniciado]).
 * - Ejercicio ([EstadoEjecucionEnModoEjercicio]).
 *
 * Las transiciones entre estados devuelven el nuevo estado, de modo que el
 * contexto (gestor) no necesita condicionales sobre el estado actual (OCP).
 */
interface EstadoEjecucion {

    /** Nombre legible del estado actual. */
    fun nombre(): String

    /** Indica si la simulación se encuentra en ejecución activa. */
    fun esActiva(): Boolean

    /** Transición al estado inicial de ejecución. */
    fun iniciar(): EstadoEjecucion

    /** Transición a un estado inactivo. */
    fun pausar(): EstadoEjecucion

    /** Transición a un estado de reposo antes de reiniciar. */
    fun reiniciar(): EstadoEjecucion

    /** Transición que alterna entre pausa e inicio según el estado actual. */
    fun alternar(): EstadoEjecucion

    /**
     * Indica si el motor debe avanzar el tiempo con el delta indicado.
     */
    fun debeAvanzar(deltaSegundos: Double): Boolean
}
