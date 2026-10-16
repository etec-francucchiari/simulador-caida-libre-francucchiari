package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Cabecera superior de la pantalla de simulación del Modo Principiante.
 *
 * Muestra de forma destacada:
 * - El título del modo activo ("Modo Principiante (Fórmulas paso a paso)").
 * - El planeta/entorno gravitatorio actual con su gravedad (reactivo).
 * - Botones para "Cambiar Planeta" y "Cambiar Modo".
 *
 * Responsabilidad Única (SRP): solo presenta la cabecera y delega las acciones
 * de navegación a los callbacks recibidos.
 */
class EncabezadoSimulacion(
    private val estado: EstadoSimulacion,
    private val onCambiarPlaneta: () -> Unit,
    private val onCambiarModo: () -> Unit
) : VBox(6.0) {

    private val etiquetaPlaneta = Label()

    init {
        padding = Insets(14.0, 20.0, 14.0, 20.0)
        style = "-fx-background-color: #eff6ff; " +
                "-fx-border-color: #bfdbfe; " +
                "-fx-border-width: 0 0 1 0;"

        val filaSuperior = HBox(12.0)
        filaSuperior.alignment = Pos.CENTER_LEFT

        val titulo = Label("Modo Principiante (Fórmulas paso a paso)")
        titulo.font = Font.font("System", FontWeight.BOLD, 20.0)
        titulo.style = "-fx-text-fill: #1e3a8a;"

        val espaciador = Region()
        HBox.setHgrow(espaciador, Priority.ALWAYS)

        val botonCambiarPlaneta = crearBotonSecundario("Cambiar Planeta")
        botonCambiarPlaneta.setOnAction { onCambiarPlaneta() }

        val botonCambiarModo = crearBotonSecundario("Cambiar Modo")
        botonCambiarModo.setOnAction { onCambiarModo() }

        filaSuperior.children.addAll(titulo, espaciador, botonCambiarPlaneta, botonCambiarModo)

        etiquetaPlaneta.font = Font.font("System", FontWeight.BOLD, 14.0)
        etiquetaPlaneta.style = "-fx-text-fill: #2563eb;"

        children.addAll(filaSuperior, etiquetaPlaneta)

        estado.planetaObservable().addListener { _, _, _ -> actualizarPlaneta() }
        actualizarPlaneta()
    }

    private fun actualizarPlaneta() {
        val planeta = estado.obtenerPlaneta()
        etiquetaPlaneta.text =
            "🌍 Entorno: ${planeta.nombre()}  ·  g = ${planeta.gravedadFormateada()} m/s²  ·  ${planeta.descripcion()}"
    }

    private fun crearBotonSecundario(texto: String): Button {
        val boton = Button(texto)
        boton.font = Font.font("System", FontWeight.BOLD, 13.0)
        boton.style = "-fx-background-color: #dbeafe; " +
                "-fx-text-fill: #1e40af; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8 16; " +
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand;"
        return boton
    }
}