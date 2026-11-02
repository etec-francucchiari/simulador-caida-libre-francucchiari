package org.example

class MotorEstadoCaida(
    private val calculo: CalculadoraCaidaLibre
) : MotorSimulacion {
    /**
     * Motor de cálculo del estado instantáneo de la caída libre.
     *
     * Concentra las reglas físicas de avance temporal y detección de impacto,
     * delegando las fórmulas en la abstracción [CalculadoraCaidaLibre] inyectada
     * por constructor (DIP).
     */

    private val umbralPrecisionTiempo = 1e-6
    private val umbralAlturaSuelo = 1e-5

    /** Tiempo total de caída en segundos (0.0 si no es calculable). */
    override fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double {
        return calculo.calcularTiempoCaida(alturaInicial, velocidadInicial, gravedad) ?: 0.0
    }

    /** Determina si el cuerpo se encuentra detenido en el suelo. */
    override fun haLlegadoAlSuelo(tiempoTotal: Double, tiempoActual: Double, alturaActual: Double): Boolean {
        if (alturaActual <= umbralAlturaSuelo) {
            return true
        }
        return alcanzaTiempoFinal(tiempoTotal, tiempoActual)
    }

    /**
     * Calcula el estado cinemático para el tiempo solicitado, limitándolo al
     * instante de impacto.
     */
    override fun estadoEn(alturaInicial: Double, velocidadInicial: Double, gravedad: Double, tiempoSolicitado: Double): EstadoInstantaneo {
        val tiempoTotal = tiempoCaida(alturaInicial, velocidadInicial, gravedad)
        val tEfectivo = ajustarTiempo(tiempoTotal, tiempoSolicitado.coerceIn(0.0, tiempoTotal))
        val posicion = calculo.calcularPosicion(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        val velocidad = calculo.calcularVelocidad(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        if (haLlegadoAlSuelo(tiempoTotal, tEfectivo, posicion)) {
            val vImpacto = calculo.calcularVelocidadImpacto(alturaInicial, velocidadInicial, gravedad) ?: velocidad
            return EstadoInstantaneo(tEfectivo, 0.0, vImpacto, true)
        }
        return EstadoInstantaneo(tEfectivo, posicion, velocidad, false)
    }

    /** Comprueba si el tiempo transcurrido ha alcanzado el tiempo total de caída dentro de la precisión. */
    private fun alcanzaTiempoFinal(tiempoTotal: Double, tiempoActual: Double): Boolean {
        return tiempoTotal > 0.0 && (tiempoTotal - tiempoActual) <= umbralPrecisionTiempo
    }

    /** Ajusta el instante evaluado al tiempo exacto de impacto cuando se encuentra en el umbral. */
    private fun ajustarTiempo(tiempoTotal: Double, tiempoLimitado: Double): Double {
        return if (alcanzaTiempoFinal(tiempoTotal, tiempoLimitado)) tiempoTotal else tiempoLimitado
    }
}
