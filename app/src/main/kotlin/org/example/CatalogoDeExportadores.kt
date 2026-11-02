package org.example

interface CatalogoDeExportadores {
    /**
     * Contrato del catálogo de formatos de exportación de datos de la simulación
     * (Principio de Inversión de Dependencias - DIP).
     *
     * Permite a la barra de exportación ([BarraExportacion]) consultar los
     * exportadores registrados (CSV, JSON, etc.) sin depender de la clase
     * concreta [CatalogoExportadores] ni de cada formato individual.
     */

    /**
     * Devuelve la lista inmutable de todos los [ExportadorDatos] registrados
     * en el catálogo.
     */
    fun disponibles(): List<ExportadorDatos>

    /**
     * Devuelve la lista de extensiones de archivo soportadas en minúsculas y
     * sin punto inicial (por ejemplo, `["csv", "json"]`).
     */
    fun extensiones(): List<String>

    /**
     * Busca y devuelve el [ExportadorDatos] que corresponde a la [extension]
     * indicada (ignorando mayúsculas/minúsculas y el punto inicial), o `null`
     * si el formato no se encuentra registrado.
     */
    fun porExtension(extension: String): ExportadorDatos?
}
