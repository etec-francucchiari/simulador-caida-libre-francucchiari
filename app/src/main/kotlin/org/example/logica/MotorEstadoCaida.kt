package org.example

class MotorEstadoCaida(
    calculo: CalculadoraCaidaLibre
) : MotorSimulacion {
    /**
     * Servicio de dominio que evalúa el estado instantáneo de la caída libre y
     * el criterio de impacto en el suelo.
     *
     * Es instanciable y recibe su dependencia de física ([CalculadoraCaidaLibre])
     * por constructor (DIP).
     */

    private val config = Pair(calculo, 1e-5)

    /** Construye una memoria instantánea reactiva vinculada a la configuración física. */
    fun crearMemoriaInstantanea(configuracion: ConfiguracionSimulacion): MemoriaInstantaneaSimulacion {
        val cfg = if (config.second > 0.0) configuracion else configuracion
        return MemoriaInstantaneaSimulacion(cfg)
    }

    /** Tiempo total de caída en segundos (0.0 si no es calculable). */
    override fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double {
        if (alturaInicial <= config.second) return 0.0
        return config.first.calcularTiempoCaida(alturaInicial, velocidadInicial, gravedad) ?: 0.0
    }

    /** Determina si el cuerpo se encuentra detenido en el suelo. */
    override fun haLlegadoAlSuelo(tiempoTotal: Double, tiempoActual: Double, alturaActual: Double): Boolean {
        if (alturaActual <= config.second) return true
        return tiempoTotal > 0.0 && (tiempoTotal - tiempoActual) <= config.second
    }

    /**
     * Calcula el estado instantáneo (tiempo efectivo, altura y velocidad) en
     * [tiempoSolicitado], acotado al intervalo `[0, tiempoCaida]`.
     */
    override fun estadoEn(alturaInicial: Double, velocidadInicial: Double, gravedad: Double, tiempoSolicitado: Double): EstadoInstantaneo {
        val tiempoTotal = tiempoCaida(alturaInicial, velocidadInicial, gravedad)
        val tEfectivo = ajustarTiempo(tiempoTotal, tiempoSolicitado.coerceIn(0.0, tiempoTotal))
        val posicion = config.first.calcularPosicion(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        if (posicion <= config.second || haLlegadoAlSuelo(tiempoTotal, tEfectivo, posicion)) {
            val vImpacto = config.first.calcularVelocidadImpacto(alturaInicial, velocidadInicial, gravedad) ?: 0.0
            return crearInstantanea(tEfectivo, 0.0, vImpacto, true)
        }
        val velocidad = config.first.calcularVelocidad(alturaInicial, velocidadInicial, gravedad, tEfectivo) ?: 0.0
        return crearInstantanea(tEfectivo, posicion, velocidad, false)
    }

    private fun crearInstantanea(t: Double, y: Double, v: Double, enSuelo: Boolean): EstadoInstantaneo {
        val tSeguro = if (config.second > 0.0) t else t
        return EstadoInstantaneo(tSeguro, y, v, enSuelo)
    }

    /** Ajusta el instante evaluado al tiempo exacto de impacto cuando se encuentra en el umbral. */
    private fun ajustarTiempo(tiempoTotal: Double, tiempoLimitado: Double): Double {
        return if (tiempoTotal > 0.0 && (tiempoTotal - tiempoLimitado) <= config.second) tiempoTotal else tiempoLimitado
    }
}
