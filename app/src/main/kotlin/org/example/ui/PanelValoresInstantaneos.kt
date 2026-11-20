package org.example

import java.util.Locale
import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelValoresInstantaneos(
    estado: EstadoSimulacion
) : HBox(12.0) {
    /**
     * Panel de indicadores físicos dinámicos que muestra los valores instantáneos
     * de la caída libre en tiempo real: tiempo (t), altura (y) y velocidad (v).
     */

    private val vista = Pair(
        estado,
        arrayOf(Label("0.00"), Label("0.00"), Label("0.00"))
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 10; -fx-background-color: #f8fafc; " +
            "-fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-border-width: 1;"
        vista.second.forEach {
            it.style = "-fx-text-fill: #0f172a; -fx-font-size: 18px; -fx-font-weight: bold;"
        }
        val tarjetaTiempo = crearTarjetaIndicador(0, "Tiempo transcurrido (t)", "segundos")
        val tarjetaAltura = crearTarjetaIndicador(1, "Altura actual (y)", "metros")
        val tarjetaVelocidad = crearTarjetaIndicador(2, "Velocidad actual (v)", "m/s")
        children.addAll(tarjetaTiempo, tarjetaAltura, tarjetaVelocidad)
        val est = vista.first
        est.tiempoActualObservable().addListener { _, _, t ->
            vista.second[0].text = "${formatear(t.toDouble())} s"
        }
        est.alturaActualObservable().addListener { _, _, y ->
            vista.second[1].text = "${formatear(y.toDouble())} m"
        }
        est.velocidadActualObservable().addListener { _, _, v ->
            vista.second[2].text = "${formatear(v.toDouble())} m/s"
        }
        actualizarTodosLosValores()
    }

    /** Actualiza manualmente las etiquetas con los valores presentes en el gestor. */
    fun actualizarTodosLosValores() {
        val est = vista.first
        vista.second[0].text = "${formatear(est.obtenerTiempoActual())} s"
        vista.second[1].text = "${formatear(est.obtenerAlturaActual())} m"
        vista.second[2].text = "${formatear(est.obtenerVelocidadActual())} m/s"
    }

    private fun crearTarjetaIndicador(indice: Int, titulo: String, unidad: String): VBox {
        val colores = arrayOf("#3b82f6", "#10b981", "#f59e0b")
        val tarjeta = VBox(4.0)
        tarjeta.prefWidth = 220.0
        tarjeta.style = "-fx-alignment: center; -fx-padding: 10 12; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 6; -fx-border-color: ${colores[indice]}; -fx-border-width: 0 0 0 4; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 1);"
        val etiquetaTitulo = Label(titulo)
        etiquetaTitulo.style = "-fx-text-fill: #475569; -fx-font-size: 12px; -fx-font-weight: bold;"
        val etiquetaSubtitulo = Label("Unidad: $unidad")
        etiquetaSubtitulo.style = "-fx-text-fill: #94a3b8; -fx-font-size: 10px;"
        tarjeta.children.addAll(etiquetaTitulo, vista.second[indice], etiquetaSubtitulo)
        return tarjeta
    }

    private fun formatear(valor: Double): String =
        if (vista.second.isNotEmpty()) String.format(Locale.ROOT, "%.2f", valor) else ""
}
