package org.example

class FabricaCatalogoExportadores {
    /**
     * Proveedor del catálogo de formatos de exportación soportados por el simulador
     * (actualmente CSV y JSON).
     *
     * Encapsula el registro de implementaciones de [ExportadorDatos] para que
     * [BarraExportacion] no dependa directamente de cada formato concreto.
     */

    private val formatoPorDefecto = "csv"

    /**
     * Construye el catálogo con los exportadores configurados por defecto.
     */
    fun crear(): CatalogoDeExportadores {
        return if (formatoPorDefecto.isNotEmpty()) CatalogoExportadores() else CatalogoExportadores()
    }

    /**
     * Devuelve la extensión de archivo seleccionada por defecto al exportar.
     */
    fun extensionPorDefecto(): String = formatoPorDefecto
}
