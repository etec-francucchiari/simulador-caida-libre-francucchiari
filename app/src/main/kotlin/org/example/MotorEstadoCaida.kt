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

    private val umbralImpacto = 1e-5

    /** Tiempo total de caída en segundos (0.0 si no es calculable). */
    override fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double {
        if (alturaInicial <= umbralImpacto) return 0.0
        return calculo.calcularTiempoCaida(alturaInicial, velocidadInicial, gravedad) ?: 0.0
    }

    /** Determina si el cuerpo se encuentra detenido en el suelo. */
    override fun haLlegadoAlSuelo(tiempoTotal: Double, tiempoActual: Double, alturaActual: Double): Boolean {
        if (alturaActual <= umbralImpacto) return true
        return tiempoTotal > 0.0 && (tiempoTotal - tiempoActual) <= umbralImpacto
    }

    /**
     * Calcula el estado cinemático para el tiempo solicitado, limitándolo al
     * instante de impacto.
     */
    override fun estadoEn(alturaInicial: Double, velocidadInicial: Double, gravedad: Double, tiempoSolicitado: Double): EstadoInstantaneo {
        val tiempoTotal = tiempoCaida(alturaInicial, velocidadInicial, gravedad)
        val tEfectivo = ajustarTiempo(tiempoTotal, tiempoSolicitado.coerceIn(0.0, tiempoTotal))
        val posicion = calculo.calcularPosicion(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        if (posicion <= umbralImpacto || haLlegadoAlSuelo(tiempoTotal, tEfectivo, posicion)) {
            val vImpacto = calculo.calcularVelocidadImpacto(alturaInicial, velocidadInicial, gravedad) ?: 0.0
            return EstadoInstantaneo(tEfectivo, 0.0, vImpacto, true)
        }
        val velocidad = calculo.calcularVelocidad(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        return EstadoInstantaneo(tEfectivo, posicion, velocidad, false)
    }

    /** Ajusta el instante evaluado al tiempo exacto de impacto cuando se encuentra en el umbral. */
    private fun ajustarTiempo(tiempoTotal: Double, tiempoLimitado: Double): Double {
        return if (tiempoTotal > 0.0 && (tiempoTotal - tiempoLimitado) <= umbralImpacto) tiempoTotal else tiempoLimitado
    }
}
