package org.example

import java.util.Locale

/**
 * Modelo de dominio que representa un ejercicio de caída libre del Modo
 * Avanzado.
 *
 * Encapsula el escenario físico (entorno, parámetros iniciales), el objetivo
 * de cálculo y el tiempo de evaluación, y expone comportamiento de dominio
 * explícito: enunciado, fórmula simbólica, expresión sustituida, valor
 * correcto y campos de sustitución. No es una clase anémica: delega en el
 * servicio [CalculoCaidaLibre] inyectado por constructor (DIP).
 *
 * @property entornoInterno entorno gravitatorio del escenario.
 * @property parametrosInternos altura inicial (y₀) y velocidad inicial (v₀).
 * @property objetivoInterno magnitud a calcular.
 * @property tiempoEvaluacionInterno instante t de evaluación (para posición y velocidad).
 * @property calculoInterno servicio de física inyectado.
 */
class EjercicioCaidaLibre(
    private val entornoInterno: EntornoGravitatorio,
    private val parametrosInternos: ParametrosIniciales,
    private val objetivoInterno: ObjetivoEjercicio,
    private val tiempoEvaluacionInterno: Double,
    private val calculoInterno: CalculadoraCaidaLibre
) {

    /** Entorno gravitatorio del escenario. */
    fun entorno(): EntornoGravitatorio = entornoInterno

    /** Parámetros iniciales del escenario. */
    fun parametros(): ParametrosIniciales = parametrosInternos

    /** Objetivo de cálculo del ejercicio. */
    fun objetivo(): ObjetivoEjercicio = objetivoInterno

    /** Instante de evaluación (t) para objetivos de posición y velocidad. */
    fun tiempoEvaluacion(): Double = tiempoEvaluacionInterno

    /** Gravedad del entorno en m/s². */
    fun gravedad(): Double = entornoInterno.gravedad()

    /** Altura inicial (y₀) en metros. */
    fun alturaInicial(): Double = parametrosInternos.alturaInicial()

    /** Velocidad inicial (v₀) en m/s. */
    fun velocidadInicial(): Double = parametrosInternos.velocidadInicial()

    /**
     * Comportamiento de dominio: tiempo total que tarda el cuerpo en llegar
     * al suelo.
     */
    fun tiempoTotalCaida(): Double =
        calculoInterno.calcularTiempoCaida(
            alturaInicial(),
            velocidadInicial(),
            gravedad()
        ) ?: 0.0

    /**
     * Comportamiento de dominio: valor numérico correcto según el objetivo.
     */
    fun valorCorrecto(): Double = when (objetivoInterno) {
        ObjetivoEjercicio.CALCULAR_POSICION -> calculoInterno.calcularPosicion(
            alturaInicial(),
            velocidadInicial(),
            gravedad(),
            tiempoEvaluacionInterno
        ) ?: 0.0

        ObjetivoEjercicio.CALCULAR_VELOCIDAD -> calculoInterno.calcularVelocidad(
            alturaInicial(),
            velocidadInicial(),
            gravedad(),
            tiempoEvaluacionInterno
        ) ?: 0.0

        ObjetivoEjercicio.CALCULAR_TIEMPO -> tiempoTotalCaida()
    }

    /** Unidad del resultado correcto. */
    fun unidadResultado(): String = objetivoInterno.unidad()

    /**
     * Comportamiento de dominio: fórmula simbólica que resuelve el objetivo.
     */
    fun formulaSimbolica(): String = when (objetivoInterno) {
        ObjetivoEjercicio.CALCULAR_POSICION -> "y(t) = y₀ − v₀·t − ½·g·t²"
        ObjetivoEjercicio.CALCULAR_VELOCIDAD -> "v(t) = v₀ + g·t"
        ObjetivoEjercicio.CALCULAR_TIEMPO -> "t = (−v₀ + √(v₀² + 2·g·y₀)) / g"
    }

    /**
     * Comportamiento de dominio: fórmula con los datos numéricos ya
     * sustituidos, para guiar al estudiante.
     */
    fun expresionConValores(): String = when (objetivoInterno) {
        ObjetivoEjercicio.CALCULAR_POSICION ->
            "y(${formatear(tiempoEvaluacionInterno)}) = ${formatear(alturaInicial())}" +
                " − ${formatear(velocidadInicial())}·${formatear(tiempoEvaluacionInterno)}" +
                " − ½·${formatear(gravedad())}·${formatear(tiempoEvaluacionInterno)}²"

        ObjetivoEjercicio.CALCULAR_VELOCIDAD ->
            "v(${formatear(tiempoEvaluacionInterno)}) = ${formatear(velocidadInicial())}" +
                " + ${formatear(gravedad())}·${formatear(tiempoEvaluacionInterno)}"

        ObjetivoEjercicio.CALCULAR_TIEMPO ->
            "t = (−${formatear(velocidadInicial())}" +
                " + √(${formatear(velocidadInicial())}²" +
                " + 2·${formatear(gravedad())}·${formatear(alturaInicial())}))" +
                " / ${formatear(gravedad())}"
    }

    /**
     * Comportamiento de dominio: enunciado completo del ejercicio en lenguaje
     * natural.
     */
    fun enunciado(): String {
        val escenario = "Un objeto se deja caer desde y₀ = ${formatear(alturaInicial())} m " +
            "en ${entornoInterno.nombre()} (g = ${entornoInterno.gravedadFormateada()} m/s²) " +
            "con una velocidad inicial v₀ = ${formatear(velocidadInicial())} m/s."

        val pregunta = when (objetivoInterno) {
            ObjetivoEjercicio.CALCULAR_POSICION ->
                " Calcula su altura y tras t = ${formatear(tiempoEvaluacionInterno)} s de caída."
            ObjetivoEjercicio.CALCULAR_VELOCIDAD ->
                " Calcula su velocidad v tras t = ${formatear(tiempoEvaluacionInterno)} s de caída."
            ObjetivoEjercicio.CALCULAR_TIEMPO ->
                " Calcula el tiempo total t que tarda en llegar al suelo."
        }
        return escenario + pregunta
    }

    /**
     * Comportamiento de dominio: variables que el estudiante debe sustituir en
     * la fórmula elegida, con su valor correcto.
     */
    fun camposSustitucion(): List<CampoSustitucion> {
        val campos = mutableListOf(
            CampoSustitucion("y₀", "Altura inicial", alturaInicial(), "m"),
            CampoSustitucion("v₀", "Velocidad inicial", velocidadInicial(), "m/s"),
            CampoSustitucion("g", "Gravedad", gravedad(), "m/s²")
        )
        if (!objetivoInterno.esTiempo()) {
            campos.add(
                CampoSustitucion(
                    "t",
                    "Tiempo de evaluación",
                    tiempoEvaluacionInterno,
                    "s"
                )
            )
        }
        return campos
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)

    override fun toString(): String = enunciado()
}
