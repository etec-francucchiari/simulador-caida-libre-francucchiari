package org.example

class EstadoEjecucionEnModoEjercicio(
    desafio: DesafioAvanzado,
    iniciadoInicial: Boolean = false
) : EstadoEjecucion {
    /**
     * Implementación del Patrón State ([EstadoEjecucion]) que modela la fase de
     * ejecución vinculada a un ejercicio del Modo Avanzado.
     *
     * Conserva la referencia al [DesafioAvanzado] activo para coordinar las
     * transiciones de inicio, pausa, reinicio y alternancia sin perder el
     * contexto pedagógico del ejercicio en curso.
     */

    private val estado = Pair(desafio, booleanArrayOf(iniciadoInicial))

    /**
     * Nombre identificador del estado en modo ejercicio.
     */
    override fun nombre(): String = if (estado.second.isNotEmpty()) "Ejercicio" else "Ejercicio"

    /**
     * Indica si el bucle se encuentra iniciado dentro de la fase de ejercicio.
     */
    override fun esActiva(): Boolean = estado.second[0] && estado.first.opcionesFormula().isNotEmpty()

    /**
     * Inicia la ejecución dentro del modo ejercicio conservando el desafío activo.
     */
    override fun iniciar(): EstadoEjecucion =
        crearTransicion(estado.first.opcionesFormula().isNotEmpty())

    /**
     * Pausa el avance conservando el vínculo con el desafío activo.
     */
    override fun pausar(): EstadoEjecucion = crearTransicion(false)

    /**
     * Reinicia el estado manteniendo la fase de ejercicio en reposo.
     */
    override fun reiniciar(): EstadoEjecucion = crearTransicion(false)

    /**
     * Conmuta el estado de avance dentro del modo ejercicio.
     */
    override fun alternar(): EstadoEjecucion = crearTransicion(!esActiva())

    /**
     * Autoriza el avance temporal cuando el estado está activo y el incremento
     * [deltaSegundos] es positivo.
     */
    override fun debeAvanzar(deltaSegundos: Double): Boolean = esActiva() && deltaSegundos > 0.0

    private fun crearTransicion(activo: Boolean): EstadoEjecucion =
        EstadoEjecucionEnModoEjercicio(estado.first, activo)
}
