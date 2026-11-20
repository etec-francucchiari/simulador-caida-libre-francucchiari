package org.example

import java.util.Locale

class FotogramaCaida(
    anchoInterno: Double,
    altoInterno: Double,
    entornoInterno: EntornoGravitatorio,
    alturaInicialInterna: Double,
    alturaActualInterna: Double,
    velocidadActualInterna: Double,
    impactadoInterno: Boolean
) {
    /**
     * Modelo de dominio inmutable que empaqueta toda la información geométrica
     * y física necesaria para renderizar un fotograma del lienzo de caída libre
     * ([RenderizadorAnimacionCaida]).
     *
     * Evita listas largas de parámetros primitivos y dota a los datos del
     * fotograma de comportamiento propio para calcular la fracción normalizada
     * de altura restante ([fraccionAltura]), construir la leyenda del entorno
     * ([descripcionEntorno]) y formatear magnitudes con precisión uniforme
     * ([formatear]).
     */

    private val estadoFotograma = Pair(
        entornoInterno,
        doubleArrayOf(
            anchoInterno,
            altoInterno,
            alturaInicialInterna,
            alturaActualInterna,
            velocidadActualInterna,
            if (impactadoInterno) 1.0 else 0.0
        )
    )

    /**
     * Devuelve el ancho disponible del lienzo de dibujo en píxeles.
     */
    fun ancho(): Double = estadoFotograma.second[0]

    /**
     * Devuelve el alto disponible del lienzo de dibujo en píxeles.
     */
    fun alto(): Double = estadoFotograma.second[1]

    /**
     * Devuelve el entorno gravitatorio activo cuyos colores y gravedad se
     * representan en el fotograma.
     */
    fun entorno(): EntornoGravitatorio = estadoFotograma.first

    /**
     * Devuelve la altura inicial de lanzamiento en metros (extremo superior de
     * la escala graduada).
     */
    fun alturaInicial(): Double = estadoFotograma.second[2]

    /**
     * Devuelve la altura instantánea de la esfera en metros sobre el suelo.
     */
    fun alturaActual(): Double = estadoFotograma.second[3]

    /**
     * Devuelve la velocidad instantánea en m/s para dimensionar el vector de
     * velocidad sobre la esfera.
     */
    fun velocidadActual(): Double = estadoFotograma.second[4]

    /**
     * Indica si la esfera ya tocó el suelo y debe dibujarse el destello de impacto.
     */
    fun haImpactado(): Boolean = estadoFotograma.second[5] > 0.5

    /**
     * Comportamiento de dominio: calcula la proporción `alturaActual / alturaInicial`
     * acotada al intervalo `[0.0, 1.0]` para interpolar la coordenada vertical
     * de la esfera en pantalla.
     */
    fun fraccionAltura(): Double {
        if (estadoFotograma.second[2] > 0.0) {
            return (estadoFotograma.second[3] / estadoFotograma.second[2]).coerceIn(0.0, 1.0)
        }
        return 0.0
    }

    /**
     * Comportamiento de dominio: construye la etiqueta descriptiva del entorno
     * activo con su aceleración gravitatoria en m/s².
     */
    fun descripcionEntorno(): String {
        val nombre = estadoFotograma.first.nombre()
        val g = formatear(estadoFotograma.first.gravedad())
        return "$nombre (g = $g m/s²)"
    }

    /**
     * Comportamiento de dominio: formatea un número real a dos decimales con
     * punto decimal (`Locale.ROOT`) para las leyendas del lienzo.
     */
    fun formatear(valor: Double): String =
        if (estadoFotograma.second.isNotEmpty()) String.format(Locale.ROOT, "%.2f", valor) else ""
}
