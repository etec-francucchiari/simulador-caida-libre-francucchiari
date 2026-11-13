package org.example

import javafx.scene.control.*
import javafx.scene.layout.*

class EncabezadoSimulacion(
    private val estado: EstadoSimulacion,
    private val onCambiarPlaneta: () -> Unit,
    private val onCambiarModo: () -> Unit
) : VBox(6.0) {
    /**
     * Cabecera superior de la pantalla de simulación del Modo Principiante.
     *
     * Responsabilidad Única (SRP): solo presenta la cabecera y delega las acciones
     * de navegación a los callbacks recibidos.
     */

    private val etiquetaPlaneta = Label()

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-padding: 14 20; -fx-background-color: #eff6ff; -fx-border-color: #bfdbfe; -fx-border-width: 0 0 1 0;"
        val filaSuperior = HBox(12.0)
        filaSuperior.style = "-fx-alignment: center-left;"
        val titulo = Label("Modo Principiante (Fórmulas paso a paso)")
        titulo.style = "-fx-text-fill: #1e3a8a; -fx-font-size: 20px; -fx-font-weight: bold;"
        titulo.prefWidth = 760.0
        val botonCambiarPlaneta = crearBotonSecundario("Cambiar Planeta")
        botonCambiarPlaneta.setOnAction { onCambiarPlaneta() }
        val botonCambiarModo = crearBotonSecundario("Cambiar Modo")
        botonCambiarModo.setOnAction { onCambiarModo() }
        filaSuperior.children.addAll(titulo, botonCambiarPlaneta, botonCambiarModo)
        etiquetaPlaneta.style = "-fx-text-fill: #2563eb; -fx-font-size: 14px; -fx-font-weight: bold;"
        children.addAll(filaSuperior, etiquetaPlaneta)
        estado.planetaObservable().addListener { _, _, _ -> actualizarPlaneta() }
        actualizarPlaneta()
    }

    private fun actualizarPlaneta() {
        val planeta = estado.obtenerPlaneta()
        etiquetaPlaneta.text =
            "🌍 Entorno: ${planeta.nombre()}  ·  g = ${planeta.gravedadFormateada()} m/s²"
    }

    private fun crearBotonSecundario(texto: String): Button {
        val boton = Button(texto)
        boton.style = "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; -fx-font-size: 13px; " +
            "-fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;"
        return boton
    }
}
