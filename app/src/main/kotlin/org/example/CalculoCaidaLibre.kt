package org.example

import kotlin.math.sqrt

/**
 * Fórmulas físicas de la caída libre, desacopladas de la interfaz gráfica.
 *
 * Proporciona cálculos derivados a partir de los parámetros iniciales
 * (altura h₀, velocidad v₀) y de la gravedad del planeta seleccionado.
 *
 * Convención de signos: la altura se mide desde el suelo hacia arriba
 * y el cuerpo cae en dirección del suelo, con velocidad inicial no
 * negativa (se suelta o se lanza hacia abajo).
 */
object CalculoCaidaLibre {

    /**
     * Calcula el tiempo que tarda el cuerpo en llegar al suelo.
     *
     * Aplica la ecuación de movimiento h₀ = v₀·t + ½·g·t², despejando `t`:
     * t = (−v₀ + √(v₀² + 2·g·h₀)) / g
     *
     * @param h0 altura inicial en metros.
     * @param v0 velocidad inicial en m/s.
     * @param g aceleración de la gravedad en m/s².
     * @return tiempo en segundos, o `null` si los valores no son
     *         físicamente válidos (gravedad ≤ 0 o parámetros negativos).
     */
    fun calcularTiempoCaida(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0) return null
        return (-v0 + sqrt(v0 * v0 + 2.0 * g * h0)) / g
    }

    /**
     * Calcula la velocidad con la que el cuerpo impacta el suelo,
     * por conservación de la energía mecánica: v² = v₀² + 2·g·h₀.
     *
     * @param h0 altura inicial en metros.
     * @param v0 velocidad inicial en m/s.
     * @param g aceleración de la gravedad en m/s².
     * @return velocidad en m/s, o `null` si los valores no son
     *         físicamente válidos.
     */
    fun calcularVelocidadImpacto(h0: Double, v0: Double, g: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0) return null
        return sqrt(v0 * v0 + 2.0 * g * h0)
    }
}