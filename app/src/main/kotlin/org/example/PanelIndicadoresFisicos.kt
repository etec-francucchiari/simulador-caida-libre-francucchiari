package org.example

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale

/**
 * Panel con los indicadores físicos derivados de los parámetros
 * configurados y del planeta seleccionado.
 *
 * Se suscribe al gestor de estado reactivo y recalcula automáticamente
 * los valores al cambiar la altura inicial, la velocidad inicial o la
 * gravedad, utilizando [CalculoCaidaLibre] (lógica física desacoplada).
 */
class PanelIndicadoresFisicos(
    private val gestorEstado: GestorEstadoSimulacion
) : VBox(12.0) {

    private val etiquetaAltura: Label = crearEtiquetaValor()
    private val etiquetaVelocidad: Label = crearEtiquetaValor()
    private val etiquetaGravedad: Label = crearEtiquetaValor()
    private val etiquetaTiempoCaida: Label = crearEtiquetaValor()
    private val etiquetaVelocidadImpacto: Label = crearEtiquetaValor()

    init {
        val titulo = Label("Fórmulas Físicas (se actualizan automáticamente)")
        titulo.font = Font.font("System", FontWeight.BOLD, 16.0)
        titulo.style = "-fx-text-fill: #2c3e50;"

        style = "-fx-background-color: #ffffff; " +
                "-fx-padding: 14; " +
                "-fx-background-radius: 6; " +
                "-fx-border-color: #d5dbe0; " +
                "-fx-border-radius: 6;"

        val grilla = GridPane()
        grilla.hgap = 20.0
        grilla.vgap = 8.0
        grilla.alignment = Pos.CENTER_LEFT

        val columnaEtiquetaCuenta = ColumnConstraints(230.0)
        val columnaValor = ColumnConstraints(150.0)
        grilla.columnConstraints.addAll(columnaEtiquetaCuenta, columnaValor)

        grilla.add(crearEtiquetaCampo("Altura inicial (h₀):"), 0, 0)
        grilla.add(etiquetaAltura, 1, 0)

        grilla.add(crearEtiquetaCampo("Velocidad inicial (v₀):"), 0, 1)
        grilla.add(etiquetaVelocidad, 1, 1)

        grilla.add(crearEtiquetaCampo("Gravedad del planeta (g):"), 0, 2)
        grilla.add(etiquetaGravedad, 1, 2)

        grilla.add(crearEtiquetaCampo("Tiempo de caída:  t = (√(v₀² + 2·g·h₀) − v₀) / g"), 0, 3)
        grilla.add(etiquetaTiempoCaida, 1, 3)

        grilla.add(
            crearEtiquetaCampo("Velocidad al impactar:  v = √(v₀² + 2·g·h₀)"),
            0,
            4
        )
        grilla.add(etiquetaVelocidadImpacto, 1, 4)

        children.addAll(titulo, grilla)

        // Suscripciones reactivas: cualquier cambio de estado recalcula las fórmulas.
        gestorEstado.alturaInicialProperty.addListener { _, _, _ -> actualizarIndicadores() }
        gestorEstado.velocidadInicialProperty.addListener { _, _, _ -> actualizarIndicadores() }
        gestorEstado.planetaActivoProperty.addListener { _, _, _ -> actualizarIndicadores() }
        actualizarIndicadores()
    }

    /**
     * Recalcula todos los indicadores a partir del estado actual
     * y de las fórmulas físicas de [CalculoCaidaLibre].
     */
    private fun actualizarIndicadores() {
        val altura = gestorEstado.obtenerAlturaInicial()
        val velocidad = gestorEstado.obtenerVelocidadInicial()
        val gravedad = gestorEstado.obtenerPlaneta().gravedad
        val nombrePlaneta = gestorEstado.obtenerPlaneta().nombre

        etiquetaAltura.text = "${formatear(altura)} m"
        etiquetaVelocidad.text = "${formatear(velocidad)} m/s"
        etiquetaGravedad.text = "$nombrePlaneta → ${formatear(gravedad)} m/s²"

        val tiempo = CalculoCaidaLibre.calcularTiempoCaida(altura, velocidad, gravedad)
        etiquetaTiempoCaida.text = tiempo?.let { "${formatear(it)} s" } ?: "No disponible"

        val velocidadImpacto =
            CalculoCaidaLibre.calcularVelocidadImpacto(altura, velocidad, gravedad)
        etiquetaVelocidadImpacto.text =
            velocidadImpacto?.let { "${formatear(it)} m/s" } ?: "No disponible"
    }

    private fun crearEtiquetaCampo(texto: String): Label {
        val etiqueta = Label(texto)
        etiqueta.font = Font.font("System", 13.0)
        etiqueta.style = "-fx-text-fill: #34495e; -fx-wrap-text: true;"
        return etiqueta
    }

    private fun crearEtiquetaValor(): Label {
        val etiqueta = Label()
        etiqueta.font = Font.font("System", FontWeight.BOLD, 13.0)
        etiqueta.style = "-fx-text-fill: #2c3e50;"
        return etiqueta
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)
}