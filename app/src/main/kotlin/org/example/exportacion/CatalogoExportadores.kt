package org.example

class CatalogoExportadores(
    private val exportadores: List<ExportadorDatos>
) : CatalogoDeExportadores {
    /**
     * Implementación instanciable del catálogo de formatos de exportación de
     * datos ([CatalogoDeExportadores]).
     *
     * Recibe por constructor la colección de estrategias [ExportadorDatos]
     * habilitadas (Principio de Inversión de Dependencias - DIP), permitiendo
     * incorporar nuevos formatos sin modificar esta clase (OCP).
     *
     * @property exportadores lista privada de exportadores registrados.
     */

    /**
     * Devuelve la lista completa de exportadores disponibles en el catálogo.
     */
    override fun disponibles(): List<ExportadorDatos> = exportadores

    /**
     * Proyecta las extensiones asociadas a cada exportador registrado.
     */
    override fun extensiones(): List<String> = exportadores.map { it.extension() }

    /**
     * Localiza un exportador a partir de su [extension] de archivo, ignorando
     * espacios en blanco, el punto inicial opcional y diferencias de mayúsculas.
     */
    override fun porExtension(extension: String): ExportadorDatos? {
        val normalizada = extension.trim().trimStart('.').lowercase()
        return exportadores.firstOrNull { it.extension().lowercase() == normalizada }
    }
}
