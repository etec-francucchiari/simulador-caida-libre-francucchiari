package org.example

class EjercicioCaidaLibre(
    private val entornoInterno: EntornoGravitatorio,
    private val parametrosInternos: ParametrosIniciales,
    private val objetivoInterno: ObjetivoEjercicio,
    private val tiempoEvaluacionInterno: Double,
    private val calculoInterno: CalculadoraCaidaLibre
) {
    /**
     * Modelo de dominio que representa un ejercicio de caída libre del Modo
     * Avanzado.
     *
     * Encapsula el escenario físico (entorno, parámetros iniciales), el objetivo
     * de cálculo y el tiempo de evaluación, y expone comportamiento de dominio
     * explícito delegando en [CalculadoraCaidaLibre] (DIP).
     */

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

    /** Tiempo total que tarda el cuerpo en llegar al suelo. */
    fun tiempoTotalCaida(): Double {
        return calculoInterno.calcularTiempoCaida(alturaInicial(), velocidadInicial(), gravedad()) ?: 0.0
    }

    /** Valor numérico correcto según el objetivo. */
    fun valorCorrecto(): Double {
        return when (objetivoInterno) {
            ObjetivoEjercicio.CALCULAR_POSICION -> calculoInterno.calcularPosicion(alturaInicial(), velocidadInicial(), gravedad(), tiempoEvaluacionInterno) ?: 0.0
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> calculoInterno.calcularVelocidad(alturaInicial(), velocidadInicial(), gravedad(), tiempoEvaluacionInterno) ?: 0.0
            ObjetivoEjercicio.CALCULAR_TIEMPO -> tiempoTotalCaida()
        }
    }

    /** Unidad del resultado correcto. */
    fun unidadResultado(): String = objetivoInterno.unidad()

    /** Resultado correcto formateado junto con su unidad física. */
    fun textoResultado(): String = "${formatear(valorCorrecto())} ${unidadResultado()}"

    /** Fórmula simbólica que resuelve el objetivo. */
    fun formulaSimbolica(): String = when (objetivoInterno) {
        ObjetivoEjercicio.CALCULAR_POSICION -> "y(t) = y₀ − v₀·t − ½·g·t²"
        ObjetivoEjercicio.CALCULAR_VELOCIDAD -> "v(t) = v₀ + g·t"
        ObjetivoEjercicio.CALCULAR_TIEMPO -> "t = (−v₀ + √(v₀² + 2·g·y₀)) / g"
    }

    /** Fórmula con los datos numéricos ya sustituidos. */
    fun expresionConValores(): String {
        val y0 = formatear(alturaInicial())
        val v0 = formatear(velocidadInicial())
        val g = formatear(gravedad())
        val t = formatear(tiempoEvaluacionInterno)
        return when (objetivoInterno) {
            ObjetivoEjercicio.CALCULAR_POSICION -> "y($t) = $y0 − $v0·$t − ½·$g·$t²"
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> "v($t) = $v0 + $g·$t"
            ObjetivoEjercicio.CALCULAR_TIEMPO -> "t = (−$v0 + √($v0² + 2·$g·$y0)) / $g"
        }
    }

    /** Enunciado completo del ejercicio en lenguaje natural. */
    fun enunciado(): String {
        val escenario = "Un objeto se deja caer desde y₀ = ${formatear(alturaInicial())} m " +
            "en ${entornoInterno.nombre()} (g = ${entornoInterno.gravedadFormateada()} m/s²) " +
            "con una velocidad inicial v₀ = ${formatear(velocidadInicial())} m/s."
        val pregunta = when (objetivoInterno) {
            ObjetivoEjercicio.CALCULAR_POSICION -> " Calcula su altura y tras t = ${formatear(tiempoEvaluacionInterno)} s de caída."
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> " Calcula su velocidad v tras t = ${formatear(tiempoEvaluacionInterno)} s de caída."
            ObjetivoEjercicio.CALCULAR_TIEMPO -> " Calcula el tiempo total t que tarda en llegar al suelo."
        }
        return escenario + pregunta
    }

    /** Variables que el estudiante debe sustituir en la fórmula elegida. */
    fun camposSustitucion(): List<CampoSustitucion> {
        return parametrosInternos.crearCamposSustitucion(gravedad(), tiempoEvaluacionInterno, !objetivoInterno.esTiempo())
    }

    private fun formatear(valor: Double): String = parametrosInternos.formatearMagnitud(valor)

    override fun toString(): String = enunciado()
}
