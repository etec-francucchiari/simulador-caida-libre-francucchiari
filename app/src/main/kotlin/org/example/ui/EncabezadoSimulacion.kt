package org.example

import javafx.scene.control.*
import javafx.scene.layout.*

class EncabezadoSimulacion(
    estado: EstadoSimulacion,
    onCambiarPlaneta: () -> Unit,
    onCambiarModo: () -> Unit
) : VBox(6.0) {
    /**
     * Cabecera superior de la pantalla de simulación del Modo Principiante.
     *
     * Responsabilidad Única (SRP): solo presenta la cabecera y delega las acciones
     * de navegación a los callbacks recibidos.
     */

    private val vista = Pair(
        Triple(estado, onCambiarPlaneta, onCambiarModo),
        Label()
    )

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
        botonCambiarPlaneta.setOnAction { vista.first.second() }
        val botonCambiarModo = crearBotonSecundario("Cambiar Modo")
        botonCambiarModo.setOnAction { vista.first.third() }
        filaSuperior.children.addAll(titulo, botonCambiarPlaneta, botonCambiarModo)
        vista.second.style = "-fx-text-fill: #2563eb; -fx-font-size: 14px; -fx-font-weight: bold;"
        children.addAll(filaSuperior, vista.second)
        vista.first.first.planetaObservable().addListener { _, _, _ -> actualizarPlaneta() }
        actualizarPlaneta()
    }

    private fun actualizarPlaneta() {
        val planeta = vista.first.first.obtenerPlaneta()
        vista.second.text =
            "🌍 Entorno: ${planeta.nombre()}  ·  g = ${planeta.gravedadFormateada()} m/s²"
    }

    private fun crearBotonSecundario(texto: String): Button {
        vista.second.hashCode()
        val boton = Button(texto)
        boton.style = "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; -fx-font-size: 13px; " +
            "-fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;"
        return boton
    }

    /**
     * Construye la barra inferior de controles vinculada al mismo estado reactivo.
     */
    fun crearBarraControles(control: ControlReproduccion): BarraControlesSimulacion {
        return BarraControlesSimulacion(vista.first.first, control)
    }
}
