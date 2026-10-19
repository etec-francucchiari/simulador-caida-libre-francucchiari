package org.example

/**
 * Implementación de la configuración física del simulador.
 *
 * Además de los valores por defecto y los límites válidos, expone
 * comportamiento de dominio para ajustar magnitudes al rango permitido y
 * describir la configuración, evitando una clase meramente anémica.
 *
 * @property alturaPorDefectoInterna altura inicial por defecto en metros.
 * @property velocidadPorDefectoInterna velocidad inicial por defecto en m/s.
 * @property alturaMinimaInterna altura mínima admitida en metros.
 * @property velocidadMinimaInterna velocidad mínima admitida en m/s.
 */
class ConfiguracionFisica(
    private val alturaPorDefectoInterna: Double = 100.0,
    private val velocidadPorDefectoInterna: Double = 0.0,
    private val alturaMinimaInterna: Double = 0.0,
    private val velocidadMinimaInterna: Double = 0.0
) : ConfiguracionSimulacion {

    override fun alturaPorDefecto(): Double = alturaPorDefectoInterna

    override fun velocidadPorDefecto(): Double = velocidadPorDefectoInterna

    override fun alturaMinima(): Double = alturaMinimaInterna

    override fun velocidadMinima(): Double = velocidadMinimaInterna

    override fun esAlturaValida(altura: Double): Boolean =
        altura.isFinite() && altura >= alturaMinimaInterna

    override fun esVelocidadValida(velocidad: Double): Boolean =
        velocidad.isFinite() && velocidad >= velocidadMinimaInterna

    /**
     * Comportamiento de dominio: ajusta una altura al rango permitido.
     */
    fun ajustarAltura(altura: Double): Double =
        if (esAlturaValida(altura)) altura else alturaMinimaInterna

    /**
     * Comportamiento de dominio: ajusta una velocidad al rango permitido.
     */
    fun ajustarVelocidad(velocidad: Double): Double =
        if (esVelocidadValida(velocidad)) velocidad else velocidadMinimaInterna

    /** Comportamiento de dominio: resumen legible de la configuración. */
    fun resumen(): String =
        "Altura por defecto: $alturaPorDefectoInterna m; " +
            "velocidad por defecto: $velocidadPorDefectoInterna m/s; " +
            "mínimos: $alturaMinimaInterna m y $velocidadMinimaInterna m/s."
}
