package org.example

class EstadoEjecucionEnCurso : EstadoEjecucion {
    /**
     * Implementación del Patrón State ([EstadoEjecucion]) que representa la fase
     * activa de la simulación en la cual el cuerpo se encuentra descendiendo en
     * caída libre y el bucle temporal avanza en cada fotograma.
     *
     * Define de forma polimórfica las transiciones hacia pausa
     * ([EstadoEjecucionPausado]) y reinicio ([EstadoEjecucionReiniciado]), así
     * como la condición de avance para incrementos temporales positivos.
     */

    /**
     * Nombre descriptivo del estado para diagnóstico e interfaz de usuario.
     */
    override fun nombre(): String = "Ejecutándose"

    /**
     * Indica que el bucle de animación debe permanecer activo.
     */
    override fun esActiva(): Boolean = true

    /**
     * Al estar ya en curso, solicitar inicio conserva el estado actual.
     */
    override fun iniciar(): EstadoEjecucion = this

    /**
     * Transiciona al estado pausado deteniendo el avance temporal.
     */
    override fun pausar(): EstadoEjecucion = EstadoEjecucionPausado()

    /**
     * Transiciona al estado reiniciado restableciendo el origen temporal.
     */
    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionReiniciado()

    /**
     * Al alternar desde la ejecución activa, conmuta al estado pausado.
     */
    override fun alternar(): EstadoEjecucion = pausar()

    /**
     * Autoriza el avance cinemático siempre que el intervalo [deltaSegundos]
     * transcurrido entre fotogramas sea estrictamente positivo.
     */
    override fun debeAvanzar(deltaSegundos: Double): Boolean = deltaSegundos > 0.0
}
