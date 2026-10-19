package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Window

/**
 * Barra inferior de exportación de la tabla de datos de la simulación.
 *
 * Encapsula los botones CSV/JSON, el estado de la última exportación y el
 * acceso al selector de archivo. Recibe las dependencias por constructor
 * (DIP) y un proveedor de la ventana propietaria del diálogo.
 */
class BarraExportacion(
    private val servicioExportacion: ExportacionDatos,
    private val catalogoExportadores: CatalogoDeExportadores,
    private val selectorArchivo: SelectorArchivoExportacion,
    private val proveedorVentana: () -> Window?,
    private val fabrica: FabricaControlesUi
) : HBox(12.0) {

    private val etiquetaEstado = Label("Sin exportar")

    init {
        alignment = Pos.CENTER_LEFT
        padding = Insets(10.0, 14.0, 10.0, 14.0)
        style = "-fx-background-color: #eef2ff;"

        val etiquetaTitulo = Label("💾 Exportar tabla de datos (t, y, v, a):")
        etiquetaTitulo.font = Font.font("System", FontWeight.BOLD, 13.0)
        etiquetaTitulo.style = "-fx-text-fill: #3730a3;"

        val botonCsv = fabrica.botonSecundario("Exportar CSV")
        botonCsv.setOnAction { exportar("csv") }

        val botonJson = fabrica.botonSecundario("Exportar JSON")
        botonJson.setOnAction { exportar("json") }

        etiquetaEstado.font = Font.font("System", 12.0)
        etiquetaEstado.style = "-fx-text-fill: #475569;"

        children.addAll(etiquetaTitulo, botonCsv, botonJson, etiquetaEstado)
    }

    /** Restablece el mensaje de estado. */
    fun reiniciarMensaje() {
        etiquetaEstado.text = "Sin exportar"
        etiquetaEstado.style = "-fx-text-fill: #475569;"
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
            etiquetaEstado.style = "-fx-text-fill: #16a34a;"
        } catch (excepcion: Exception) {
            mostrarError("Error al exportar: ${excepcion.message}")
        }
    }

    private fun mostrarError(mensaje: String) {
        etiquetaEstado.text = mensaje
        etiquetaEstado.style = "-fx-text-fill: #dc2626;"
    }
}
