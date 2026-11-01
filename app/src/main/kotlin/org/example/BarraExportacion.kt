package org.example

import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.stage.Window

class BarraExportacion(
    private val servicioExportacion: ExportacionDatos,
    private val catalogoExportadores: CatalogoDeExportadores,
    private val selectorArchivo: SelectorArchivoExportacion,
    private val proveedorVentana: () -> Window?,
    private val fabrica: FabricaControlesUi
) : HBox(12.0) {
    /**
     * Barra inferior de exportación de la tabla de datos de la simulación.
     *
     * Encapsula los botones CSV/JSON, el estado de la última exportación y el
     * acceso al selector de archivo. Recibe las dependencias por constructor (DIP).
     */

    private val etiquetaEstado = Label()

    init {
        inicializar()
    }

    private fun inicializar() {
        isVisible = false
        isManaged = false
        etiquetaEstado.style = "-fx-text-fill: #475569; -fx-font-size: 12px;"
    }

    /** Restablece el mensaje de estado. */
    fun reiniciarMensaje() {
        etiquetaEstado.text = ""
        etiquetaEstado.style = "-fx-text-fill: #475569; -fx-font-size: 12px;"
    }

    /** Exporta la muestra actual en el formato indicado. */
    fun exportar(extension: String) {
        val exportador = catalogoExportadores.porExtension(extension)
        if (exportador == null) {
            mostrarError("Formato no soportado: $extension")
            return
        }
        val archivo = selectorArchivo.seleccionarDestino(
            ventana = proveedorVentana(),
            nombreSugerido = "simulacion_caida_libre",
            exportador = exportador
        ) ?: return
        try {
            servicioExportacion.exportar(exportador, archivo)
            etiquetaEstado.text = "Exportado: ${archivo.name}"
            etiquetaEstado.style = "-fx-text-fill: #16a34a; -fx-font-size: 12px;"
        } catch (excepcion: Exception) {
            mostrarError("Error al exportar: ${excepcion.message}")
        }
    }

    private fun mostrarError(mensaje: String) {
        etiquetaEstado.text = mensaje
        etiquetaEstado.style = "-fx-text-fill: #dc2626; -fx-font-size: 12px;"
    }
}
