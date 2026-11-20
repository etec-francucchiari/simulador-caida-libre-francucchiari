package org.example

import kotlin.math.abs

class PuntoTrayectoria(
    tiempoInterno: Double,
    posicionInterna: Double,
    velocidadInterna: Double
) {
    /**
     * Modelo de dominio inmutable que representa un punto discreto de la
     * trayectoria de un cuerpo en caída libre evaluado en un instante dado.
     *
     * Almacena el tiempo, la posición vertical sobre el suelo y la velocidad
     * instantánea en estado privado, y provee comportamiento de dominio
     * explícito para detectar el contacto con el suelo ([haImpactadoSuelo]),
     * obtener la rapidez escalar ([rapidez]), comparar alturas relativas entre
     * dos instantes ([estaMasAltoQue]) y verificar equivalencia cinemática
     * ([mismoPuntoQue]).
     */

    private val coordenadas = doubleArrayOf(tiempoInterno, posicionInterna, velocidadInterna)

    /**
     * Devuelve el tiempo transcurrido desde el inicio del movimiento en segundos.
     */
    fun tiempo(): Double = coordenadas[0]

    /**
     * Devuelve la altura instantánea sobre el suelo en metros.
     */
    fun posicion(): Double = coordenadas[1]

    /**
     * Devuelve la velocidad instantánea hacia abajo en metros por segundo.
     */
    fun velocidad(): Double = coordenadas[2]

    /**
     * Comportamiento de dominio: determina si el cuerpo ya ha tocado el nivel
     * del suelo (y ≤ 0).
     */
    fun haImpactadoSuelo(): Boolean {
        return coordenadas[1] <= 0.0
    }

    /**
     * Comportamiento de dominio: devuelve el módulo de la velocidad instantánea.
     */
    fun rapidez(): Double {
        return abs(coordenadas[2])
    }

    /**
     * Comportamiento de dominio: indica si este punto de la trayectoria se
     * encuentra a una cota superior que [otro].
     */
    fun estaMasAltoQue(otro: PuntoTrayectoria): Boolean {
        return coordenadas[1] > otro.coordenadas[1]
    }

    /**
     * Comportamiento de dominio: comprueba si dos puntos coinciden exactamente
     * en tiempo, posición y velocidad sin emplear operadores de reflexión.
     */
    fun mismoPuntoQue(otro: PuntoTrayectoria): Boolean {
        return coordenadas[0] == otro.coordenadas[0] &&
            coordenadas[1] == otro.coordenadas[1] &&
            coordenadas[2] == otro.coordenadas[2]
    }

    /**
     * Representación legible de las tres coordenadas cinemáticas con unidades.
     */
    override fun toString(): String {
        val t = coordenadas[0]
        val y = coordenadas[1]
        val v = coordenadas[2]
        return "t = $t s, y = $y m, v = $v m/s"
    }
}
