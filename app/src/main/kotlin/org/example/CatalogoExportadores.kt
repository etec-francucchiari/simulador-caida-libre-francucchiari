package org.example

/**
 * Implementación del catálogo de exportadores disponibles.
 *
 * Recibe por constructor la lista de [ExportadorDatos] (DIP), de modo que no
 * instancia formatos concretos internamente.
 */
class CatalogoExportadores(
    private val exportadores: List<ExportadorDatos>
) : CatalogoDeExportadores {

    override fun disponibles(): List<ExportadorDatos> = exportadores

    override fun extensiones(): List<String> = exportadores.map { it.extension() }

    /**
     * Comportamiento de dominio: busca un exportador por su extensión,
     * ignorando un punto inicial y las mayúsculas.
     */
    override fun porExtension(extension: String): ExportadorDatos? {
        val normalizada = extension.trim().trimStart('.').lowercase()
        return exportadores.firstOrNull { it.extension().lowercase() == normalizada }
    }
}
