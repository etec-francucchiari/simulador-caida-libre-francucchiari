package org.example

import javafx.stage.Window

class FabricaBarraExportacion(
    estado: EstadoSimulacion,
    calculo: CalculadoraCaidaLibre
) {
    /**
     * Fábrica que ensambla la barra de exportación de datos con su servicio de
     * muestreo cinemático (DIP).
     */

    private val datos = Pair(estado, calculo)

    /**
     * Construye una [BarraExportacion] vinculada al proveedor de ventana anfitriona
     * para mostrar el diálogo nativo de guardado.
     *
     * @param proveedorVentana función que resuelve la ventana activa de la escena.
     */
    fun crear(proveedorVentana: () -> Window?): BarraExportacion {
        return BarraExportacion(
            servicioExportacion = crearServicio(),
            proveedorVentana = proveedorVentana
        )
    }

    /**
     * Construye el servicio de exportación conectado al estado y calculadora activos.
     */
    private fun crearServicio(): ExportacionDatos =
        ServicioExportacion(datos.first, datos.second)
}
