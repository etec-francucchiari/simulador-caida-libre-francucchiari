package org.example

class EstadoEjecucionEnModoEjercicio(
    private val desafio: DesafioAvanzado
) : EstadoEjecucion {
    /**
     * Implementación del Patrón State ([EstadoEjecucion]) que modela la fase de
     * ejecución vinculada a un ejercicio del Modo Avanzado.
     *
     * Conserva la referencia al [DesafioAvanzado] activo para coordinar las
     * transiciones de inicio, pausa, reinicio y alternancia sin perder el
     * contexto pedagógico del ejercicio en curso.
     *
     * @property desafio instancia del desafío interactivo asociado.
     */

    private var iniciado: Boolean = false

    /**
     * Nombre identificador del estado en modo ejercicio.
     */
    override fun nombre(): String = "Ejercicio"

    /**
     * Indica si el bucle se encuentra iniciado dentro de la fase de ejercicio.
     */
    override fun esActiva(): Boolean = iniciado && desafio.opcionesFormula().isNotEmpty()

    /**
     * Inicia la ejecución dentro del modo ejercicio conservando el desafío activo.
     */
    override fun iniciar(): EstadoEjecucion =
        EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = desafio.opcionesFormula().isNotEmpty() }

    /**
     * Pausa el avance conservando el vínculo con el desafío activo.
     */
    override fun pausar(): EstadoEjecucion = EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }

    /**
     * Reinicia el estado manteniendo la fase de ejercicio en reposo.
     */
    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }

    /**
     * Conmuta el estado de avance dentro del modo ejercicio.
     */
    override fun alternar(): EstadoEjecucion =
        if (esActiva()) EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }
        else EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = true }

    /**
     * Autoriza el avance temporal cuando el estado está activo y el incremento
     * [deltaSegundos] es positivo.
     */
    override fun debeAvanzar(deltaSegundos: Double): Boolean = esActiva() && deltaSegundos > 0.0
}
