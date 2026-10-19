package org.example

/**
 * Motor de cálculo del estado instantáneo de la caída libre.
 *
 * Concentra las reglas físicas de avance temporal y detección de impacto,
 * delegando las fórmulas en la abstracción [CalculadoraCaidaLibre] inyectada
 * por constructor (DIP).
 */
class MotorEstadoCaida(
    private val calculo: CalculadoraCaidaLibre
) : MotorSimulacion {

    private val umbralPrecisionTiempo = 1e-6
    private val umbralAlturaSuelo = 1e-5

    /** Tiempo total de caída en segundos (0.0 si no es calculable). */
    override fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double =
        calculo.calcularTiempoCaida(alturaInicial, velocidadInicial, gravedad) ?: 0.0

    /** Determina si el cuerpo se encuentra detenido en el suelo. */
    override fun haLlegadoAlSuelo(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double,
        tiempoActual: Double,
        alturaActual: Double
    ): Boolean {
        val tiempoTotal = tiempoCaida(alturaInicial, velocidadInicial, gravedad)
        return alturaActual <= umbralAlturaSuelo ||
            (tiempoTotal > 0.0 && (tiempoTotal - tiempoActual) <= umbralPrecisionTiempo)
    }

    /**
     * Calcula el estado cinemático para el tiempo solicitado, limitándolo al
     * instante de impacto.
     */
    override fun estadoEn(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double,
        tiempoSolicitado: Double
    ): EstadoInstantaneo {
        val tiempoTotal = tiempoCaida(alturaInicial, velocidadInicial, gravedad)
        val tiempoLimitado = tiempoSolicitado.coerceIn(0.0, tiempoTotal)
        val tiempoEfectivo =
            if (tiempoTotal > 0.0 && (tiempoTotal - tiempoLimitado) <= umbralPrecisionTiempo) {
                tiempoTotal
            } else {
                tiempoLimitado
            }

        val posicion = calculo.calcularPosicion(
            alturaInicial,
            velocidadInicial,
            gravedad,
            tiempoEfectivo
        ) ?: 0.0
        val velocidad = calculo.calcularVelocidad(
            alturaInicial,
            velocidadInicial,
            gravedad,
            tiempoEfectivo
        ) ?: 0.0

        val enSuelo = posicion <= umbralAlturaSuelo || tiempoEfectivo >= tiempoTotal
        return if (enSuelo) {
            val velocidadImpacto = calculo.calcularVelocidadImpacto(
                alturaInicial,
                velocidadInicial,
                gravedad
            ) ?: velocidad
            EstadoInstantaneo(tiempoEfectivo, 0.0, velocidadImpacto, true)
        } else {
            EstadoInstantaneo(tiempoEfectivo, posicion, velocidad, false)
        }
    }
}
