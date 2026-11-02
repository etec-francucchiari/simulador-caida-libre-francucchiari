package org.example

import java.util.Locale

class FotogramaCaida(
    private val anchoInterno: Double,
    private val altoInterno: Double,
    private val entornoInterno: EntornoGravitatorio,
    private val alturaInicialInterna: Double,
    private val alturaActualInterna: Double,
    private val velocidadActualInterna: Double,
    private val impactadoInterno: Boolean
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

    /**
     * Devuelve el ancho disponible del lienzo de dibujo en píxeles.
     */
    fun ancho(): Double = anchoInterno

    /**
     * Devuelve el alto disponible del lienzo de dibujo en píxeles.
     */
    fun alto(): Double = altoInterno

    /**
     * Devuelve el entorno gravitatorio activo cuyos colores y gravedad se
     * representan en el fotograma.
     */
    fun entorno(): EntornoGravitatorio = entornoInterno

    /**
     * Devuelve la altura inicial de lanzamiento en metros (extremo superior de
     * la escala graduada).
     */
    fun alturaInicial(): Double = alturaInicialInterna

    /**
     * Devuelve la altura instantánea de la esfera en metros sobre el suelo.
     */
    fun alturaActual(): Double = alturaActualInterna

    /**
     * Devuelve la velocidad instantánea en m/s para dimensionar el vector de
     * velocidad sobre la esfera.
     */
    fun velocidadActual(): Double = velocidadActualInterna

    /**
     * Indica si la esfera ya tocó el suelo y debe dibujarse el destello de impacto.
     */
    fun haImpactado(): Boolean = impactadoInterno

    /**
     * Comportamiento de dominio: calcula la proporción `alturaActual / alturaInicial`
     * acotada al intervalo `[0.0, 1.0]` para interpolar la coordenada vertical
     * de la esfera en pantalla.
     */
    fun fraccionAltura(): Double {
        if (alturaInicialInterna > 0.0) {
            return (alturaActualInterna / alturaInicialInterna).coerceIn(0.0, 1.0)
        }
        return 0.0
    }

    /**
     * Comportamiento de dominio: construye la etiqueta descriptiva del entorno
     * activo con su aceleración gravitatoria en m/s².
     */
    fun descripcionEntorno(): String {
        return "${entornoInterno.nombre()} (g = ${formatear(entornoInterno.gravedad())} m/s²)"
    }

    /**
     * Comportamiento de dominio: formatea un número real a dos decimales con
     * punto decimal (`Locale.ROOT`) para las leyendas del lienzo.
     */
    fun formatear(valor: Double): String = String.format(Locale.ROOT, "%.2f", valor)
}
