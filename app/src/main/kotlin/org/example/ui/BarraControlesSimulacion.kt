package org.example

import javafx.scene.control.*
import javafx.scene.layout.HBox

class BarraControlesSimulacion(
    private val estado: EstadoSimulacion,
    private val control: ControlReproduccion
) : HBox(15.0) {
    /**
     * Barra inferior con los controles de reproducción de la simulación.
     *
     * Responsabilidad Única (SRP): solo gestiona los botones principales de
     * ejecución ("Iniciar/Pausar", "Reiniciar" y "Salir") y delega el flujo
     * temporal a [ControlReproduccion] y la consulta reactiva a [EstadoSimulacion].
     *
     * Cumple con el Principio de Inversión de Dependencias (DIP) al recibir
     * ambas abstracciones por constructor sin instanciar colaboradores concretos.
     */

    private val botonIniciar = Button("Iniciar Simulación")

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 14; -fx-background-color: #f1f5f9; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;"
        botonIniciar.style = estiloBoton("#3498db")
        botonIniciar.setOnAction { control.alternarEjecucion() }
        val botonReiniciar = Button("Reiniciar")
        botonReiniciar.style = estiloBoton("#7f8c8d")
        botonReiniciar.setOnAction { control.reiniciar() }
        val botonSalir = Button("Salir")
        botonSalir.style = estiloBoton("#e74c3c")
        botonSalir.setOnAction { scene?.window?.hide() }
        estado.enEjecucionObservable().addListener { _, _, enEjecucion -> actualizarVista(enEjecucion) }
        children.addAll(botonIniciar, botonReiniciar, botonSalir)
    }

    private fun actualizarVista(enEjecucion: Boolean) {
        if (enEjecucion) {
            botonIniciar.text = "Pausar Simulación"
            botonIniciar.style = estiloBoton("#e67e22")
        } else {
            botonIniciar.text = "Iniciar Simulación"
            botonIniciar.style = estiloBoton("#3498db")
        }
    }

    private fun estiloBoton(color: String): String =
        "-fx-background-color: $color; -fx-text-fill: white; -fx-font-size: 14px; " +
            "-fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;"
}
