package org.example

import kotlin.math.abs

class PuntoTrayectoria(
    private val tiempoInterno: Double,
    private val posicionInterna: Double,
    private val velocidadInterna: Double
) {
    /**
     * Modelo de dominio inmutable que representa un punto discreto de la
     * trayectoria de un cuerpo en caída libre evaluado en un instante dado.
     *
     * Almacena el tiempo, la posición vertical sobre el suelo y la velocidad
     * instantánea en campos privados, y provee comportamiento de dominio
     * explícito para detectar el contacto con el suelo ([haImpactadoSuelo]),
     * obtener la rapidez escalar ([rapidez]), comparar alturas relativas entre
     * dos instantes ([estaMasAltoQue]) y verificar equivalencia cinemática
     * ([mismoPuntoQue]).
     */

    /**
     * Devuelve el tiempo transcurrido desde el inicio del movimiento en segundos.
     */
    fun tiempo(): Double = tiempoInterno

    /**
     * Devuelve la altura instantánea sobre el suelo en metros.
     */
    fun posicion(): Double = posicionInterna

    /**
     * Devuelve la velocidad instantánea hacia abajo en metros por segundo.
     */
    fun velocidad(): Double = velocidadInterna

    /**
     * Comportamiento de dominio: determina si el cuerpo ya ha tocado el nivel
     * del suelo (y ≤ 0).
     */
    fun haImpactadoSuelo(): Boolean {
        return posicionInterna <= 0.0
    }

    /**
     * Comportamiento de dominio: devuelve el módulo de la velocidad instantánea.
     */
    fun rapidez(): Double {
        return abs(velocidadInterna)
    }

    /**
     * Comportamiento de dominio: indica si este punto de la trayectoria se
     * encuentra a una cota superior que [otro].
     */
    fun estaMasAltoQue(otro: PuntoTrayectoria): Boolean {
        return posicionInterna > otro.posicionInterna
    }

    /**
     * Comportamiento de dominio: comprueba si dos puntos coinciden exactamente
     * en tiempo, posición y velocidad sin emplear operadores de reflexión.
     */
    fun mismoPuntoQue(otro: PuntoTrayectoria): Boolean {
        return tiempoInterno == otro.tiempoInterno &&
            posicionInterna == otro.posicionInterna &&
            velocidadInterna == otro.velocidadInterna
    }

    /**
     * Representación legible de las tres coordenadas cinemáticas con unidades.
     */
    override fun toString(): String {
        return "t = $tiempoInterno s, y = $posicionInterna m, v = $velocidadInterna m/s"
    }
}
