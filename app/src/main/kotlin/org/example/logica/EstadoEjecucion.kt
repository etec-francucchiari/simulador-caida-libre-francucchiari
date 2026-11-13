package org.example

interface EstadoEjecucion {
    /**
     * Contrato de la máquina de estados del ciclo de vida de la simulación de
     * caída libre (Patrón de Diseño State).
     *
     * Encapsula de manera polimórfica el comportamiento y las transiciones de
     * cada fase operativa del simulador:
     * - Ejecutándose ([EstadoEjecucionEnCurso]): el móvil desciende en el aire.
     * - Pausado ([EstadoEjecucionPausado]): el reloj se detiene conservando `t`.
     * - Reiniciado ([EstadoEjecucionReiniciado]): reposo en el instante inicial.
     * - Ejercicio ([EstadoEjecucionEnModoEjercicio]): fase controlada por el
     *   desafío interactivo del Modo Avanzado.
     */

    /**
     * Devuelve el nombre legible del estado actual.
     */
    fun nombre(): String

    /**
     * Indica si la simulación se encuentra en ejecución activa y el temporizador
     * de fotogramas debe permanecer encendido.
     */
    fun esActiva(): Boolean

    /**
     * Devuelve el estado resultante de solicitar el inicio o reanudación de la
     * simulación.
     */
    fun iniciar(): EstadoEjecucion

    /**
     * Devuelve el estado resultante de solicitar la pausa de la simulación.
     */
    fun pausar(): EstadoEjecucion

    /**
     * Devuelve el estado resultante de solicitar el reinicio a `t = 0 s`.
     */
    fun reiniciar(): EstadoEjecucion

    /**
     * Devuelve el estado resultante de conmutar entre reproducción y pausa.
     */
    fun alternar(): EstadoEjecucion

    /**
     * Determina si el motor físico debe integrar el intervalo [deltaSegundos]
     * en el fotograma actual.
     */
    fun debeAvanzar(deltaSegundos: Double): Boolean
}
