package org.example

import kotlin.math.max
import kotlin.math.sqrt

/**
 * Implementación del servicio de física de la caída libre.
 *
 * Es una clase instanciable (no una utilidad `object`) que implementa el
 * contrato [CalculadoraCaidaLibre] y se inyecta en los componentes que
 * necesitan calcular tiempo, posición, velocidad o trayectoria.
 *
 * Convención de signos: la altura se mide desde el suelo hacia arriba y el
 * cuerpo cae en dirección del suelo, con velocidad inicial no negativa.
 */
class CalculoCaidaLibre : CalculadoraCaidaLibre {

    /**
     * Calcula el tiempo que tarda el cuerpo en llegar al suelo.
     *
     * Aplica h₀ = v₀·t + ½·g·t², despejando `t`:
     * t = (−v₀ + √(v₀² + 2·g·h₀)) / g
     */
    override fun calcularTiempoCaida(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0) return null
        return (-v0 + sqrt(v0 * v0 + 2.0 * g * h0)) / g
    }

    /**
     * Calcula la velocidad de impacto por conservación de la energía:
     * v² = v₀² + 2·g·h₀.
     */
    override fun calcularVelocidadImpacto(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0) return null
        return sqrt(v0 * v0 + 2.0 * g * h0)
    }

    /**
     * Calcula la altura instantánea y(t) = h₀ − (v₀·t + ½·g·t²).
     */
    override fun calcularPosicion(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0 || t < 0.0) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return 0.0
        val posicion = h0 - (v0 * t + 0.5 * g * t * t)
        return max(0.0, posicion)
    }

    /**
     * Calcula la velocidad instantánea v(t) = v₀ + g·t.
     */
    override fun calcularVelocidad(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0 || t < 0.0) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return calcularVelocidadImpacto(h0, v0, g)
        return v0 + g * t
    }

    /**
     * Genera una secuencia de [PuntoTrayectoria] desde t = 0 hasta el impacto.
     */
    override fun generarPuntosTrayectoria(
        h0: Double,
        v0: Double,
        g: Double,
        cantidadPuntos: Int
    ): List<PuntoTrayectoria> {
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return emptyList()
        val velocidadImpacto = calcularVelocidadImpacto(h0, v0, g) ?: return emptyList()

        if (tiempoCaida == 0.0) {
            return listOf(PuntoTrayectoria(0.0, h0, v0))
        }

        val puntosDeseados = max(2, cantidadPuntos)
        val pasoTiempo = tiempoCaida / (puntosDeseados - 1)
        val puntos = ArrayList<PuntoTrayectoria>(puntosDeseados)

        for (i in 0 until puntosDeseados - 1) {
            val t = i * pasoTiempo
            val y = calcularPosicion(h0, v0, g, t) ?: 0.0
            val v = calcularVelocidad(h0, v0, g, t) ?: 0.0
            puntos.add(PuntoTrayectoria(t, y, v))
        }

        // El punto final coincide con el impacto exacto
        puntos.add(PuntoTrayectoria(tiempoCaida, 0.0, velocidadImpacto))
        return puntos
    }
}
