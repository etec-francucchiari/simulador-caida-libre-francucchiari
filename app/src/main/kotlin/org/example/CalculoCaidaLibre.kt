package org.example

import kotlin.math.max
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

    /**
     * Calcula la altura instantánea y(t) sobre el suelo en el tiempo t.
     *
     * Aplica la ecuación horaria de posición: y(t) = h₀ − (v₀·t + ½·g·t²).
     * Si el tiempo excede el tiempo total de caída, el cuerpo permanece
     * en el suelo (y = 0).
     *
     * @param h0 altura inicial en metros (h₀ ≥ 0).
     * @param v0 velocidad inicial en m/s (v₀ ≥ 0).
     * @param g aceleración de la gravedad en m/s² (g > 0).
     * @param t tiempo transcurrido en segundos (t ≥ 0).
     * @return altura en metros, o `null` si alguno de los parámetros es físicamente inválido.
     */
    fun calcularPosicion(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0 || t < 0.0) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return 0.0
        val posicion = h0 - (v0 * t + 0.5 * g * t * t)
        return max(0.0, posicion)
    }

    /**
     * Calcula la velocidad instantánea v(t) del cuerpo en el tiempo t.
     *
     * Aplica la ecuación horaria de velocidad: v(t) = v₀ + g·t.
     * Si el tiempo excede el tiempo de caída, la velocidad es la velocidad
     * con la que impactó el suelo.
     *
     * @param h0 altura inicial en metros (h₀ ≥ 0).
     * @param v0 velocidad inicial en m/s (v₀ ≥ 0).
     * @param g aceleración de la gravedad en m/s² (g > 0).
     * @param t tiempo transcurrido en segundos (t ≥ 0).
     * @return velocidad en m/s, o `null` si alguno de los parámetros es físicamente inválido.
     */
    fun calcularVelocidad(h0: Double, v0: Double, g: Double, t: Double): Double? {
        if (h0 < 0.0 || v0 < 0.0 || g <= 0.0 || t < 0.0) return null
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return null
        if (t >= tiempoCaida) return calcularVelocidadImpacto(h0, v0, g)
        return v0 + g * t
    }

    /**
     * Genera una secuencia de puntos discretos de la trayectoria completa
     * desde t = 0 hasta el impacto en el suelo (t = t_caída).
     *
     * @param h0 altura inicial en metros.
     * @param v0 velocidad inicial en m/s.
     * @param g aceleración de la gravedad en m/s².
     * @param cantidadPuntos número de muestras a generar a lo largo del intervalo.
     * @return lista de [PuntoTrayectoria] calculados, o lista vacía si los parámetros son inválidos.
     */
    fun generarPuntosTrayectoria(
        h0: Double,
        v0: Double,
        g: Double,
        cantidadPuntos: Int = 100
    ): List<PuntoTrayectoria> {
        val tiempoCaida = calcularTiempoCaida(h0, v0, g) ?: return emptyList()
        val velocidadImpacto = calcularVelocidadImpacto(h0, v0, g) ?: return emptyList()

        if (tiempoCaida == 0.0) {
            return listOf(PuntoTrayectoria(tiempo = 0.0, posicion = h0, velocidad = v0))
        }

        val puntosDeseados = max(2, cantidadPuntos)
        val pasoTiempo = tiempoCaida / (puntosDeseados - 1)
        val puntos = ArrayList<PuntoTrayectoria>(puntosDeseados)

        for (i in 0 until puntosDeseados - 1) {
            val t = i * pasoTiempo
            val y = calcularPosicion(h0, v0, g, t) ?: 0.0
            val v = calcularVelocidad(h0, v0, g, t) ?: 0.0
            puntos.add(PuntoTrayectoria(tiempo = t, posicion = y, velocidad = v))
        }

        // El punto final coincide con el impacto exacto
        puntos.add(
            PuntoTrayectoria(
                tiempo = tiempoCaida,
                posicion = 0.0,
                velocidad = velocidadImpacto
            )
        )

        return puntos
    }
}