package org.example

interface RecursosEntornoSimulacion {
    /**
     * Contrato proveedor del catálogo de entornos gravitatorios y del servicio
     * de cálculo cinemático base (DIP).
     */

    /**
     * Devuelve el catálogo de entornos gravitatorios disponibles.
     */
    fun catalogo(): CatalogoEntornos

    /**
     * Devuelve la calculadora de caída libre del dominio.
     */
    fun calculo(): CalculadoraCaidaLibre
}
