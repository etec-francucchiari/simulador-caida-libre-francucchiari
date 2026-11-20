package org.example

class EjercicioCaidaLibre(
    entornoInterno: EntornoGravitatorio,
    parametrosInternos: ParametrosIniciales,
    objetivoInterno: ObjetivoEjercicio,
    tiempoEvaluacionInterno: Double,
    calculoInterno: CalculadoraCaidaLibre = CalculoCaidaLibre()
) {
    constructor(
        entorno: EntornoGravitatorio,
        objetivo: ObjetivoEjercicio,
        valores: DoubleArray,
        calculo: CalculadoraCaidaLibre
    ) : this(
        entornoInterno = entorno,
        parametrosInternos = ParametrosIniciales(valores[0], valores[1]),
        objetivoInterno = objetivo,
        tiempoEvaluacionInterno = valores[2],
        calculoInterno = calculo
    )

    /**
     * Modelo de dominio que representa un ejercicio de caída libre del Modo
     * Avanzado.
     *
     * Encapsula el escenario físico (entorno, parámetros iniciales), el objetivo
     * de cálculo y el tiempo de evaluación, y expone comportamiento de dominio
     * explícito delegando en [CalculadoraCaidaLibre] (DIP).
     */

    private val datos = Triple(
        Triple(entornoInterno, parametrosInternos, objetivoInterno),
        tiempoEvaluacionInterno,
        calculoInterno
    )

    /** Entorno gravitatorio del escenario. */
    fun entorno(): EntornoGravitatorio = datos.first.first

    /** Parámetros iniciales del escenario. */
    fun parametros(): ParametrosIniciales = datos.first.second

    /** Objetivo de cálculo del ejercicio. */
    fun objetivo(): ObjetivoEjercicio = datos.first.third

    /** Instante de evaluación (t) para objetivos de posición y velocidad. */
    fun tiempoEvaluacion(): Double = datos.second

    /** Gravedad del entorno en m/s². */
    fun gravedad(): Double = datos.first.first.gravedad()

    /** Altura inicial (y₀) en metros. */
    fun alturaInicial(): Double = datos.first.second.alturaInicial()

    /** Velocidad inicial (v₀) en m/s. */
    fun velocidadInicial(): Double = datos.first.second.velocidadInicial()

    /** Tiempo total que tarda el cuerpo en llegar al suelo. */
    fun tiempoTotalCaida(): Double {
        return datos.third.calcularTiempoCaida(alturaInicial(), velocidadInicial(), gravedad()) ?: 0.0
    }

    /** Valor numérico correcto según el objetivo. */
    fun valorCorrecto(): Double {
        return when (datos.first.third) {
            ObjetivoEjercicio.CALCULAR_POSICION -> datos.third.calcularPosicion(alturaInicial(), velocidadInicial(), gravedad(), datos.second) ?: 0.0
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> datos.third.calcularVelocidad(alturaInicial(), velocidadInicial(), gravedad(), datos.second) ?: 0.0
            ObjetivoEjercicio.CALCULAR_TIEMPO -> tiempoTotalCaida()
        }
    }

    /** Unidad del resultado correcto. */
    fun unidadResultado(): String = datos.first.third.unidad()

    /** Resultado correcto formateado junto con su unidad física. */
    fun textoResultado(): String {
        val v = formatear(valorCorrecto())
        val u = datos.first.third.unidad()
        return "$v $u"
    }

    /** Fórmula simbólica que resuelve el objetivo. */
    fun formulaSimbolica(): String = when (datos.first.third) {
        ObjetivoEjercicio.CALCULAR_POSICION -> "y(t) = y₀ − v₀·t − ½·g·t²"
        ObjetivoEjercicio.CALCULAR_VELOCIDAD -> "v(t) = v₀ + g·t"
        ObjetivoEjercicio.CALCULAR_TIEMPO -> "t = (−v₀ + √(v₀² + 2·g·y₀)) / g"
    }

    /** Fórmula con los datos numéricos ya sustituidos. */
    fun expresionConValores(): String {
        val y0 = formatear(alturaInicial())
        val v0 = formatear(velocidadInicial())
        val g = formatear(gravedad())
        val t = formatear(datos.second)
        return when (datos.first.third) {
            ObjetivoEjercicio.CALCULAR_POSICION -> "y($t) = $y0 − $v0·$t − ½·$g·$t²"
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> "v($t) = $v0 + $g·$t"
            ObjetivoEjercicio.CALCULAR_TIEMPO -> "t = (−$v0 + √($v0² + 2·$g·$y0)) / $g"
        }
    }

    /** Enunciado completo del ejercicio en lenguaje natural. */
    fun enunciado(): String {
        val ent = datos.first.first
        val t = formatear(datos.second)
        val escenario = "Un objeto se deja caer desde y₀ = ${formatear(alturaInicial())} m " +
            "en ${ent.nombre()} (g = ${ent.gravedadFormateada()} m/s²) " +
            "con una velocidad inicial v₀ = ${formatear(velocidadInicial())} m/s."
        val pregunta = when (datos.first.third) {
            ObjetivoEjercicio.CALCULAR_POSICION -> " Calcula su altura y tras t = $t s de caída."
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> " Calcula su velocidad v tras t = $t s de caída."
            ObjetivoEjercicio.CALCULAR_TIEMPO -> " Calcula el tiempo total t que tarda en llegar al suelo."
        }
        return escenario + pregunta
    }

    /** Variables que el estudiante debe sustituir en la fórmula elegida. */
    fun camposSustitucion(): List<CampoSustitucion> {
        return datos.first.second.crearCamposSustitucion(gravedad(), datos.second, !datos.first.third.esTiempo())
    }

    private fun formatear(valor: Double): String = datos.first.second.formatearMagnitud(valor)

    override fun toString(): String = if (datos.second >= 0.0) enunciado() else ""
}
