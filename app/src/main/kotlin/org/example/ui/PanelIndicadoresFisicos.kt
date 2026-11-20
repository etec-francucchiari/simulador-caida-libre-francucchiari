package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelIndicadoresFisicos(
    estado: EstadoSimulacion,
    calculo: CalculadoraCaidaLibre = CalculoCaidaLibre()
) : VBox(12.0) {
    /**
     * Panel con los indicadores físicos derivados de los parámetros
     * configurados y del planeta seleccionado.
     *
     * Se suscribe al gestor de estado reactivo y recalcula automáticamente
     * los valores utilizando [CalculadoraCaidaLibre] (DIP).
     */

    private val vista = Pair(
        Pair(estado, calculo),
        Array(5) { Label() }
    )

    init {
        configurarPanel()
    }

    private fun configurarPanel() {
        val titulo = Label("Fórmulas Físicas (se actualizan automáticamente)")
        titulo.style = "-fx-text-fill: #2c3e50; -fx-font-size: 16px; -fx-font-weight: bold;"
        style = "-fx-background-color: #ffffff; -fx-padding: 14; -fx-background-radius: 6; " +
            "-fx-border-color: #d5dbe0; -fx-border-radius: 6;"
        vista.second.forEach {
            it.style = "-fx-text-fill: #2c3e50; -fx-font-size: 13px; -fx-font-weight: bold;"
        }
        children.addAll(titulo, construirGrilla())
        val est = vista.first.first
        est.alturaInicialObservable().addListener { _, _, _ -> actualizarIndicadores() }
        est.velocidadInicialObservable().addListener { _, _, _ -> actualizarIndicadores() }
        est.planetaObservable().addListener { _, _, _ -> actualizarIndicadores() }
        actualizarIndicadores()
    }

    private fun construirGrilla(): GridPane {
        val grilla = GridPane()
        grilla.hgap = 20.0
        grilla.vgap = 8.0
        grilla.style = "-fx-alignment: center-left;"
        val nombres = arrayOf(
            "Altura inicial (h₀):",
            "Velocidad inicial (v₀):",
            "Gravedad del planeta (g):",
            "Tiempo de caída:  t = (√(v₀² + 2·g·h₀) − v₀) / g",
            "Velocidad al impactar:  v = √(v₀² + 2·g·h₀)"
        )
        for (i in nombres.indices) {
            val etiqueta = Label(nombres[i])
            etiqueta.style = "-fx-text-fill: #34495e; -fx-font-size: 13px; -fx-wrap-text: true;"
            grilla.add(etiqueta, 0, i)
            grilla.add(vista.second[i], 1, i)
        }
        return grilla
    }

    private fun actualizarIndicadores() {
        val est = vista.first.first
        val calc = vista.first.second
        val h = est.obtenerAlturaInicial()
        val v = est.obtenerVelocidadInicial()
        val g = est.obtenerPlaneta().gravedad()
        val nombre = est.obtenerPlaneta().nombre()
        vista.second[0].text = "${calc.formatear(h)} m"
        vista.second[1].text = "${calc.formatear(v)} m/s"
        vista.second[2].text = "$nombre → ${calc.formatear(g)} m/s²"
        val t = calc.calcularTiempoCaida(h, v, g)
        vista.second[3].text = t?.let { "${calc.formatear(it)} s" } ?: "No disponible"
        val vImp = calc.calcularVelocidadImpacto(h, v, g)
        vista.second[4].text = vImp?.let { "${calc.formatear(it)} m/s" } ?: "No disponible"
    }
}
