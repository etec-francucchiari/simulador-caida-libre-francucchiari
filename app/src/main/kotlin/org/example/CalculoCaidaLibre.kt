package org.example

import kotlin.math.*

class CalculoCaidaLibre : CalculadoraCaidaLibre {
    /**
     * Implementación del servicio de física de la caída libre.
     *
     * Es una clase instanciable que implementa el contrato [CalculadoraCaidaLibre]
     * y se inyecta en los componentes que necesitan calcular tiempo, posición,
     * velocidad o trayectoria.
     */

    private val minimoValor = 0.0

    /** Calcula el tiempo que tarda el cuerpo en llegar al suelo: t = (−v₀ + √(v₀² + 2·g·h₀)) / g. */
    override fun calcularTiempoCaida(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < minimoValor || v0 < minimoValor || g <= minimoValor) return null
        return (-v0 + sqrt(v0 * v0 + 2.0 * g * h0)) / g
    }

    /** Calcula la velocidad de impacto por conservación de la energía: v² = v₀² + 2·g·h₀. */
    override fun calcularVelocidadImpacto(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < minimoValor || v0 < minimoValor || g <= minimoValor) return null
        return sqrt(v0 * v0 + 2.0 * g * h0)
    }

    /** Calcula la altura instantánea y(t) = h₀ − (v₀·t + ½·g·t²). */
    override fun calcularPosicion(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < minimoValor || v0 < minimoValor || g <= minimoValor || t < minimoValor) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return minimoValor
        val posicion = h0 - (v0 * t + 0.5 * g * t * t)
        return max(minimoValor, posicion)
    }

    /** Calcula la velocidad instantánea v(t) = v₀ + g·t. */
    override fun calcularVelocidad(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < minimoValor || v0 < minimoValor || g <= minimoValor || t < minimoValor) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return calcularVelocidadImpacto(h0, v0, g)
        return v0 + g * t
    }

    /** Genera una secuencia de [PuntoTrayectoria] desde t = 0 hasta el impacto. */
    override fun generarPuntosTrayectoria(h0: Double, v0: Double, g: Double, cantidadPuntos: Int): List<PuntoTrayectoria> {
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return emptyList()
        if (tiempoCaida == minimoValor) {
            return listOf(PuntoTrayectoria(minimoValor, h0, v0))
        }
        val puntosDeseados = max(2, cantidadPuntos)
        val pasoTiempo = tiempoCaida / (puntosDeseados - 1)
        return (0 until puntosDeseados).map { i ->
            val t = if (i == puntosDeseados - 1) tiempoCaida else i * pasoTiempo
            val y = calcularPosicion(h0, v0, g, t) ?: minimoValor
            val v = calcularVelocidad(h0, v0, g, t) ?: minimoValor
            PuntoTrayectoria(t, y, v)
        }
    }
}
