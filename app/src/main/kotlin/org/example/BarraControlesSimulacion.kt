package org.example

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Barra inferior con los controles de reproducción de la simulación (Issue #5).
 *
 * Ofrece:
 * - Botón Iniciar / Pausar (se alterna según el estado reactivo).
 * - Botón Reiniciar (vuelve a t = 0 e indicadores iniciales).
 * - Botón Salir.
 * - Etiqueta de estado que refleja la situación actual de la simulación.
 *
 * Responsabilidad Única (SRP): solo gestiona los controles y delega el flujo
 * temporal a [ControladorSimulacion] y el estado a [GestorEstadoSimulacion].
 */
class BarraControlesSimulacion(
    private val estado: EstadoSimulacion,
    private val control: ControlReproduccion
) : HBox(15.0) {

    private val etiquetaEstado = Label("Estado: Listo para iniciar")

    init {
        alignment = Pos.CENTER
        padding = Insets(14.0)
        style = "-fx-background-color: #f1f5f9; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;"

        val botonIniciar = Button("Iniciar Simulación")
        botonIniciar.style = estiloPrimario()
        botonIniciar.setOnAction { control.alternarEjecucion() }

        val botonReiniciar = Button("Reiniciar")
        botonReiniciar.style = estiloNeutro()
        botonReiniciar.setOnAction {
            control.reiniciar()
            etiquetaEstado.text = "Estado: Listo para iniciar"
            etiquetaEstado.style = "-fx-text-fill: #475569;"
        }

        val botonSalir = Button("Salir")
        botonSalir.style = estiloPeligro()
        botonSalir.setOnAction { Platform.exit() }

        etiquetaEstado.font = Font.font("System", FontWeight.BOLD, 13.0)
        etiquetaEstado.style = "-fx-text-fill: #475569;"

        estado.enEjecucionObservable().addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                botonIniciar.text = "Pausar Simulación"
                botonIniciar.style = estiloPausar()
                etiquetaEstado.text = "Estado: Simulación en curso..."
                etiquetaEstado.style = "-fx-text-fill: #2563eb;"
            } else {
                botonIniciar.text = "Iniciar Simulación"
                botonIniciar.style = estiloPrimario()
                when {
                    estado.haLlegadoAlSuelo() -> {
                        etiquetaEstado.text = "Estado: ¡Impacto contra el suelo alcanzado (y = 0 m)!"
                        etiquetaEstado.style = "-fx-text-fill: #16a34a;"
                    }
                    estado.obtenerTiempoActual() > 0.0 -> {
                        etiquetaEstado.text = "Estado: Simulación pausada"
                        etiquetaEstado.style = "-fx-text-fill: #d97706;"
                    }
                    else -> {
                        etiquetaEstado.text = "Estado: Listo para iniciar"
                        etiquetaEstado.style = "-fx-text-fill: #475569;"
                    }
                }
            }
        }

        children.addAll(
            botonIniciar,
            botonReiniciar,
            botonSalir,
            Separator(Orientation.VERTICAL),
            etiquetaEstado
        )
    }

    private fun estiloPrimario(): String =
        "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"

    private fun estiloPausar(): String =
        "-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"

    private fun estiloNeutro(): String =
        "-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"

    private fun estiloPeligro(): String =
        "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"
}