package org.example

/**
 * Configuración física del simulador: valores por defecto y límites válidos.
 *
 * Es una clase instanciable (no una utilidad estática) que además de datos
 * expone comportamiento de validación, inyectable en el gestor de estado y en
 * el validador de parámetros.
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
) {

    /** Altura inicial por defecto (y₀) en metros. */
    fun alturaPorDefecto(): Double = alturaPorDefectoInterna

    /** Velocidad inicial por defecto (v₀) en m/s. */
    fun velocidadPorDefecto(): Double = velocidadPorDefectoInterna

    /** Altura mínima admitida en metros. */
    fun alturaMinima(): Double = alturaMinimaInterna

    /** Velocidad mínima admitida en m/s. */
    fun velocidadMinima(): Double = velocidadMinimaInterna

    /** Comportamiento de dominio: altura dentro del rango válido. */
    fun esAlturaValida(altura: Double): Boolean =
        altura.isFinite() && altura >= alturaMinimaInterna

    /** Comportamiento de dominio: velocidad dentro del rango válido. */
    fun esVelocidadValida(velocidad: Double): Boolean =
        velocidad.isFinite() && velocidad >= velocidadMinimaInterna
}