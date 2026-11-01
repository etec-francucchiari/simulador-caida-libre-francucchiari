package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class ModeSelectionScreen(
    private val onModoSeleccionado: (ModoSimulacion) -> Unit
) : VBox(24.0) {
    /**
     * Pantalla 1 - Selección de Modo.
     *
     * Responsabilidad Única (SRP): Solo gestiona la selección inicial del modo
     * de operación del simulador ([ModoSimulacion.PRINCIPIANTE] o
     * [ModoSimulacion.ESTANDAR]), presentando tarjetas minimalistas y limpias
     * que delegan la navegación mediante el callback inyectado por constructor.
     */

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 40; -fx-background-color: linear-gradient(to bottom, #f8fafc, #e2e8f0);"
        val titulo = Label("Simulador de Caída Libre")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 36px; -fx-font-weight: bold;"
        children.add(titulo)
        for (modo in ModoSimulacion.entries) {
            children.add(crearTarjetaModo(modo))
        }
        val nota = Label("Selecciona un modo para continuar →")
        nota.style = "-fx-text-fill: #94a3b8; -fx-font-size: 14px;"
        children.add(nota)
    }

    private fun crearTarjetaModo(modo: ModoSimulacion): VBox {
        val tarjeta = VBox(12.0)
        tarjeta.prefWidth = 420.0
        tarjeta.maxWidth = 420.0
        tarjeta.style = "-fx-alignment: center; -fx-padding: 24 32; -fx-background-color: ${modo.colorFondo()}; " +
            "-fx-background-radius: 16; -fx-border-color: ${modo.colorBorde()}; -fx-border-width: 2; " +
            "-fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4); -fx-cursor: hand;"
        if (!modo.esPrincipiante()) {
            val labelIcono = Label(modo.icono())
            labelIcono.style = "-fx-font-size: 48px;"
            tarjeta.children.add(labelIcono)
        }
        val labelTitulo = Label(modo.nombreTarjeta())
        labelTitulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 22px; -fx-font-weight: bold;"
        val boton = Button("Seleccionar")
        boton.prefWidth = 180.0
        boton.prefHeight = 44.0
        boton.style = "-fx-background-color: ${modo.colorBorde()}; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8;"
        boton.setOnAction { onModoSeleccionado(modo) }
        tarjeta.setOnMouseClicked { onModoSeleccionado(modo) }
        tarjeta.children.addAll(labelTitulo, boton)
        return tarjeta
    }
}
