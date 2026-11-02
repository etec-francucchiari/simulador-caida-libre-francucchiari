package org.example

import java.util.Locale
import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelValoresInstantaneos(
    private val estado: EstadoSimulacion
) : HBox(12.0) {
    /**
     * Panel de indicadores físicos dinámicos que muestra los valores instantáneos
     * de la caída libre en tiempo real: tiempo (t), altura (y) y velocidad (v).
     */

    private val etiquetaValorTiempo = crearEtiquetaValor()
    private val etiquetaValorAltura = crearEtiquetaValor()
    private val etiquetaValorVelocidad = crearEtiquetaValor()

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 10; -fx-background-color: #f8fafc; " +
            "-fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-border-width: 1;"
        val tarjetaTiempo = crearTarjetaIndicador("Tiempo transcurrido (t)", "segundos", etiquetaValorTiempo, "#3b82f6")
        val tarjetaAltura = crearTarjetaIndicador("Altura actual (y)", "metros", etiquetaValorAltura, "#10b981")
        val tarjetaVelocidad = crearTarjetaIndicador("Velocidad actual (v)", "m/s", etiquetaValorVelocidad, "#f59e0b")
        children.addAll(tarjetaTiempo, tarjetaAltura, tarjetaVelocidad)
        estado.tiempoActualObservable().addListener { _, _, t ->
            etiquetaValorTiempo.text = "${formatear(t.toDouble())} s"
        }
        estado.alturaActualObservable().addListener { _, _, y ->
            etiquetaValorAltura.text = "${formatear(y.toDouble())} m"
        }
        estado.velocidadActualObservable().addListener { _, _, v ->
            etiquetaValorVelocidad.text = "${formatear(v.toDouble())} m/s"
        }
        actualizarTodosLosValores()
    }

    /** Actualiza manualmente las etiquetas con los valores presentes en el gestor. */
    fun actualizarTodosLosValores() {
        etiquetaValorTiempo.text = "${formatear(estado.obtenerTiempoActual())} s"
        etiquetaValorAltura.text = "${formatear(estado.obtenerAlturaActual())} m"
        etiquetaValorVelocidad.text = "${formatear(estado.obtenerVelocidadActual())} m/s"
    }

    private fun crearTarjetaIndicador(titulo: String, unidad: String, etiquetaValor: Label, colorBorde: String): VBox {
        val tarjeta = VBox(4.0)
        tarjeta.prefWidth = 220.0
        tarjeta.style = "-fx-alignment: center; -fx-padding: 10 12; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 6; -fx-border-color: $colorBorde; -fx-border-width: 0 0 0 4; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 1);"
        val etiquetaTitulo = Label(titulo)
        etiquetaTitulo.style = "-fx-text-fill: #475569; -fx-font-size: 12px; -fx-font-weight: bold;"
        val etiquetaSubtitulo = Label("Unidad: $unidad")
        etiquetaSubtitulo.style = "-fx-text-fill: #94a3b8; -fx-font-size: 10px;"
        tarjeta.children.addAll(etiquetaTitulo, etiquetaValor, etiquetaSubtitulo)
        return tarjeta
    }

    private fun crearEtiquetaValor(): Label {
        val etiqueta = Label("0.00")
        etiqueta.style = "-fx-text-fill: #0f172a; -fx-font-size: 18px; -fx-font-weight: bold;"
        return etiqueta
    }

    private fun formatear(valor: Double): String = String.format(Locale.ROOT, "%.2f", valor)
}
