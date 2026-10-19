package org.example

/**
 * Contrato del catálogo de exportadores disponibles (DIP).
 */
interface CatalogoDeExportadores {

    /** Exportadores disponibles. */
    fun disponibles(): List<ExportadorDatos>

    /** Extensiones soportadas (sin punto). */
    fun extensiones(): List<String>

    /** Busca un exportador por su extensión. */
    fun porExtension(extension: String): ExportadorDatos?
}
