package org.example

/**
 * Contrato del servicio de física de la caída libre (DIP).
 *
 * Las capas de estado, explicación, gráficos y exportación dependen de esta
 * abstracción y no de la implementación concreta [CalculoCaidaLibre].
 */
interface CalculadoraCaidaLibre {

    /** Tiempo total de caída en segundos, o `null` si no es válido. */
    fun calcularTiempoCaida(h0: Double, v0: Double, g: Double): Double?

    /** Velocidad de impacto en m/s, o `null` si no es válido. */
    fun calcularVelocidadImpacto(h0: Double, v0: Double, g: Double): Double?

    /** Altura instantánea y(t) en metros, o `null` si no es válido. */
    fun calcularPosicion(h0: Double, v0: Double, g: Double, t: Double): Double?

    /** Velocidad instantánea v(t) en m/s, o `null` si no es válido. */
    fun calcularVelocidad(h0: Double, v0: Double, g: Double, t: Double): Double?

    /** Secuencia de puntos de la trayectoria hasta el impacto. */
    fun generarPuntosTrayectoria(
        h0: Double,
        v0: Double,
        g: Double,
        cantidadPuntos: Int = 100
    ): List<PuntoTrayectoria>
}
