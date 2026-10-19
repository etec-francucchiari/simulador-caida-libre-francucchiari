package org.example

/**
 * Contrato del catálogo de entornos gravitatorios (DIP).
 *
 * Desacopla la vista de selección y el generador de ejercicios de la
 * implementación concreta [CatalogoPlanetas].
 */
interface CatalogoEntornos {

    /** Entornos predefinidos disponibles. */
    fun predefinidos(): List<EntornoGravitatorio>

    /** Crea un entorno personalizado con la gravedad indicada. */
    fun personalizado(gravedad: Double): EntornoGravitatorio

    /** Indica si una gravedad es físicamente utilizable. */
    fun esGravedadValida(gravedad: Double): Boolean
}
