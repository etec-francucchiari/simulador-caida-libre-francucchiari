package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale

/**
 * Panel de indicadores físicos dinámicos que muestra los valores instantáneos
 * de la caída libre en tiempo real.
 *
 * Muestra los tres valores calculados requeridos por la Issue #4:
 * - Tiempo transcurrido (t) en segundos.
 * - Altura actual (y) en metros.
 * - Velocidad actual (v) en m/s.
 *
 * Se conecta reactivamente a [GestorEstadoSimulacion] para actualizar
 * los valores instantáneos automáticamente tanto al cambiar los parámetros iniciales
 * como al avanzar la simulación en el tiempo.
 */
class PanelValoresInstantaneos(
    private val gestorEstado: GestorEstadoSimulacion
) : HBox(12.0) {

    private val etiquetaValorTiempo: Label = crearEtiquetaValor()
    private val etiquetaValorAltura: Label = crearEtiquetaValor()
    private val etiquetaValorVelocidad: Label = crearEtiquetaValor()

    init {
        alignment = Pos.CENTER
        padding = Insets(10.0)
        style = "-fx-background-color: #f8fafc; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #cbd5e1; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;"

        val tarjetaTiempo = crearTarjetaIndicador(
            titulo = "Tiempo transcurrido (t)",
            unidad = "segundos",
            etiquetaValor = etiquetaValorTiempo,
            colorBorde = "#3b82f6"
        )
        val tarjetaAltura = crearTarjetaIndicador(
            titulo = "Altura actual (y)",
            unidad = "metros",
            etiquetaValor = etiquetaValorAltura,
            colorBorde = "#10b981"
        )
        val tarjetaVelocidad = crearTarjetaIndicador(
            titulo = "Velocidad actual (v)",
            unidad = "m/s",
            etiquetaValor = etiquetaValorVelocidad,
            colorBorde = "#f59e0b"
        )

        HBox.setHgrow(tarjetaTiempo, Priority.ALWAYS)
        HBox.setHgrow(tarjetaAltura, Priority.ALWAYS)
        HBox.setHgrow(tarjetaVelocidad, Priority.ALWAYS)

        children.addAll(tarjetaTiempo, tarjetaAltura, tarjetaVelocidad)

        // Suscripciones reactivas al gestor de estado:
        gestorEstado.tiempoActualProperty.addListener { _, _, nuevoTiempo ->
            etiquetaValorTiempo.text = "${formatear(nuevoTiempo.toDouble())} s"
        }
        gestorEstado.alturaActualProperty.addListener { _, _, nuevaAltura ->
            etiquetaValorAltura.text = "${formatear(nuevaAltura.toDouble())} m"
        }
        gestorEstado.velocidadActualProperty.addListener { _, _, nuevaVelocidad ->
            etiquetaValorVelocidad.text = "${formatear(nuevaVelocidad.toDouble())} m/s"
        }

        // Carga de valores iniciales
        actualizarTodosLosValores()
    }

    /**
     * Actualiza manualmente las etiquetas con los valores presentes en el gestor.
     */
    fun actualizarTodosLosValores() {
        etiquetaValorTiempo.text = "${formatear(gestorEstado.obtenerTiempoActual())} s"
        etiquetaValorAltura.text = "${formatear(gestorEstado.obtenerAlturaActual())} m"
        etiquetaValorVelocidad.text = "${formatear(gestorEstado.obtenerVelocidadActual())} m/s"
    }

    /**
     * Construye un contenedor visual tipo tarjeta para cada indicador instantáneo.
     */
    private fun crearTarjetaIndicador(
        titulo: String,
        unidad: String,
        etiquetaValor: Label,
        colorBorde: String
    ): VBox {
        val tarjeta = VBox(4.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(10.0, 12.0, 10.0, 12.0)
        tarjeta.style = "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 6; " +
                "-fx-border-color: $colorBorde; " +
                "-fx-border-width: 0 0 0 4; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 1);"

        val etiquetaTitulo = Label(titulo)
        etiquetaTitulo.font = Font.font("System", FontWeight.BOLD, 12.0)
        etiquetaTitulo.style = "-fx-text-fill: #475569;"

        val etiquetaSubtitulo = Label("Unidad: $unidad")
        etiquetaSubtitulo.font = Font.font("System", 10.0)
        etiquetaSubtitulo.style = "-fx-text-fill: #94a3b8;"

        tarjeta.children.addAll(etiquetaTitulo, etiquetaValor, etiquetaSubtitulo)
        return tarjeta
    }

    private fun crearEtiquetaValor(): Label {
        val etiqueta = Label("0.00")
        etiqueta.font = Font.font("System", FontWeight.BOLD, 18.0)
        etiqueta.style = "-fx-text-fill: #0f172a;"
        return etiqueta
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)
}
