package org.example

interface CatalogoEntornos {
    /**
     * Contrato del catálogo de entornos gravitatorios disponibles en el
     * simulador (Principio de Inversión de Dependencias - DIP).
     *
     * Desacopla la pantalla de selección de planetas ([PlanetSelectionScreen]) y
     * el generador de ejercicios ([GeneradorEjercicios]) de la implementación
     * concreta [CatalogoPlanetas].
     */

    /**
     * Devuelve la colección ordenada de entornos gravitatorios predefinidos del
     * sistema solar (Tierra, Luna, Marte y Júpiter).
     */
    fun predefinidos(): List<EntornoGravitatorio>

    /**
     * Crea una instancia de [EntornoGravitatorio] personalizada con la
     * aceleración de la [gravedad] definida por el usuario en m/s².
     */
    fun personalizado(gravedad: Double): EntornoGravitatorio

    /**
     * Valida si el valor numérico de [gravedad] ingresado por el usuario es
     * finito y estrictamente positivo (`g > 0`).
     */
    fun esGravedadValida(gravedad: Double): Boolean
}
