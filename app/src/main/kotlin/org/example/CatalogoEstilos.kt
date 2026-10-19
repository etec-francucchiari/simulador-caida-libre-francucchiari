package org.example

/**
 * Contrato del catálogo de estilos visuales de entornos (DIP).
 */
interface CatalogoEstilos {

    /** Devuelve el estilo correspondiente al entorno indicado. */
    fun para(entorno: EntornoGravitatorio): EstiloEntorno
}
