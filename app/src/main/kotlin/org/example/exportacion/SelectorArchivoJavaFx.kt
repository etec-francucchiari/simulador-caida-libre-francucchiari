package org.example

import java.io.File
import javafx.stage.*

class SelectorArchivoJavaFx : SelectorArchivoExportacion {
    /**
     * Implementación de [SelectorArchivoExportacion] basada en el cuadro de
     * diálogo nativo [FileChooser] de JavaFX.
     *
     * Encapsula la creación del selector, el título de la ventana, el nombre de
     * archivo inicial sugerido y el filtro de extensión asociado al
     * [ExportadorDatos] activo.
     */

    /**
     * Abre el diálogo modal de guardado sobre la [ventana] indicada y devuelve
     * el [File] elegido por el usuario, o `null` si se cancela la operación.
     */
    override fun seleccionarDestino(ventana: Window?, nombreSugerido: String, exportador: ExportadorDatos): File? {
        val selector = FileChooser()
        selector.title = "Exportar datos de la simulación"
        selector.initialFileName = "$nombreSugerido.${exportador.extension()}"
        selector.extensionFilters.add(
            FileChooser.ExtensionFilter(exportador.descripcion(), "*.${exportador.extension()}")
        )
        return selector.showSaveDialog(ventana)
    }
}
