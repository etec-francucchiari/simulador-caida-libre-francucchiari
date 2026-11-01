package org.example

import javafx.stage.Window

class FabricaBarraExportacion(
    private val estado: EstadoSimulacion,
    private val calculo: CalculadoraCaidaLibre,
    private val fabricaControles: FabricaControlesUi
) {
    /**
     * Fábrica que ensambla la barra de exportación de datos con su servicio de
     * muestreo, catálogo de formatos y selector de archivos JavaFX (DIP).
     */

    private val fabricaCatalogo = FabricaCatalogoExportadores()

    /**
     * Construye una [BarraExportacion] vinculada al proveedor de ventana anfitriona
     * para mostrar el diálogo nativo de guardado.
     *
     * @param proveedorVentana función que resuelve la ventana activa de la escena.
     */
    fun crear(proveedorVentana: () -> Window?): BarraExportacion {
        return BarraExportacion(
            servicioExportacion = crearServicio(),
            catalogoExportadores = fabricaCatalogo.crear(),
            selectorArchivo = SelectorArchivoJavaFx(),
            proveedorVentana = proveedorVentana,
            fabrica = fabricaControles
        )
    }

    /**
     * Construye el servicio de exportación conectado al estado y calculadora activos.
     */
    private fun crearServicio() = ServicioExportacion(estado, calculo)
}
