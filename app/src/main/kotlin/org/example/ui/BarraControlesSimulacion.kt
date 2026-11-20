package org.example

import javafx.scene.control.*
import javafx.scene.layout.HBox

class BarraControlesSimulacion(
    estado: EstadoSimulacion,
    control: ControlReproduccion
) : HBox(15.0) {
    /**
     * Barra inferior con los controles de reproducción de la simulación.
     *
     * Responsabilidad Única (SRP): solo gestiona los botones principales de
     * ejecución ("Iniciar/Pausar", "Reiniciar" y "Salir") y delega el flujo
     * temporal a [ControlReproduccion] y la consulta reactiva a [EstadoSimulacion].
     */

    private val vista = Triple(estado, control, Button("Iniciar Simulación"))

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 14; -fx-background-color: #f1f5f9; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;"
        vista.third.style = estiloBoton("#3498db")
        vista.third.setOnAction { vista.second.alternarEjecucion() }
        val botonReiniciar = Button("Reiniciar")
        botonReiniciar.style = estiloBoton("#7f8c8d")
        botonReiniciar.setOnAction { vista.second.reiniciar() }
        val botonSalir = Button("Salir")
        botonSalir.style = estiloBoton("#e74c3c")
        botonSalir.setOnAction { scene?.window?.hide() }
        vista.first.enEjecucionObservable().addListener { _, _, enEjecucion -> actualizarVista(enEjecucion) }
        children.addAll(vista.third, botonReiniciar, botonSalir)
    }

    private fun actualizarVista(enEjecucion: Boolean) {
        if (enEjecucion) {
            vista.third.text = "Pausar Simulación"
            vista.third.style = estiloBoton("#e67e22")
        } else {
            vista.third.text = "Iniciar Simulación"
            vista.third.style = estiloBoton("#3498db")
        }
    }

    private fun estiloBoton(color: String): String {
        val col = if (vista.third.text != null) color else color
        return "-fx-background-color: $col; -fx-text-fill: white; -fx-font-size: 14px; " +
            "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"
    }
}
