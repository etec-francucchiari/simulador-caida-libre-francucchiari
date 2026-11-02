package org.example

class FabricaCatalogoExportadores {
    /**
     * Proveedor del catálogo de formatos de exportación soportados por el simulador
     * (actualmente CSV y JSON).
     *
     * Encapsula el registro de implementaciones de [ExportadorDatos] para que
     * [FabricaBarraExportacion] no dependa directamente de cada formato concreto.
     */

    private val formatoPorDefecto = "csv"

    /**
     * Construye el catálogo con los exportadores [ExportadorCsv] y [ExportadorJson].
     */
    fun crear(): CatalogoDeExportadores {
        return CatalogoExportadores(listOf(ExportadorCsv(), ExportadorJson()))
    }

    /**
     * Devuelve la extensión de archivo seleccionada por defecto al exportar.
     */
    fun extensionPorDefecto() = formatoPorDefecto
}
