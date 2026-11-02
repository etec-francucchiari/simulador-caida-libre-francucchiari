package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelIndicadoresFisicos(
    private val estado: EstadoSimulacion,
    private val calculo: CalculadoraCaidaLibre
) : VBox(12.0) {
    /**
     * Panel con los indicadores físicos derivados de los parámetros
     * configurados y del planeta seleccionado.
     *
     * Se suscribe al gestor de estado reactivo y recalcula automáticamente
     * los valores utilizando [CalculadoraCaidaLibre] (DIP).
     */

    private val etiquetaAltura = crearEtiquetaValor()
    private val etiquetaVelocidad = crearEtiquetaValor()
    private val etiquetaGravedad = crearEtiquetaValor()
    private val etiquetaTiempoCaida = crearEtiquetaValor()
    private val etiquetaVelocidadImpacto = crearEtiquetaValor()

    init {
        configurarPanel()
    }

    private fun configurarPanel() {
        val titulo = Label("Fórmulas Físicas (se actualizan automáticamente)")
        titulo.style = "-fx-text-fill: #2c3e50; -fx-font-size: 16px; -fx-font-weight: bold;"
        style = "-fx-background-color: #ffffff; -fx-padding: 14; -fx-background-radius: 6; " +
            "-fx-border-color: #d5dbe0; -fx-border-radius: 6;"
        val grilla = GridPane()
        grilla.hgap = 20.0
        grilla.vgap = 8.0
        grilla.style = "-fx-alignment: center-left;"
        grilla.add(crearEtiquetaCampo("Altura inicial (h₀):"), 0, 0)
        grilla.add(etiquetaAltura, 1, 0)
        grilla.add(crearEtiquetaCampo("Velocidad inicial (v₀):"), 0, 1)
        grilla.add(etiquetaVelocidad, 1, 1)
        grilla.add(crearEtiquetaCampo("Gravedad del planeta (g):"), 0, 2)
        grilla.add(etiquetaGravedad, 1, 2)
        grilla.add(crearEtiquetaCampo("Tiempo de caída:  t = (√(v₀² + 2·g·h₀) − v₀) / g"), 0, 3)
        grilla.add(etiquetaTiempoCaida, 1, 3)
        grilla.add(crearEtiquetaCampo("Velocidad al impactar:  v = √(v₀² + 2·g·h₀)"), 0, 4)
        grilla.add(etiquetaVelocidadImpacto, 1, 4)
        children.addAll(titulo, grilla)
        estado.alturaInicialObservable().addListener { _, _, _ -> actualizarIndicadores() }
        estado.velocidadInicialObservable().addListener { _, _, _ -> actualizarIndicadores() }
        estado.planetaObservable().addListener { _, _, _ -> actualizarIndicadores() }
        actualizarIndicadores()
    }

    private fun actualizarIndicadores() {
        val h = estado.obtenerAlturaInicial()
        val v = estado.obtenerVelocidadInicial()
        val g = estado.obtenerPlaneta().gravedad()
        val nombre = estado.obtenerPlaneta().nombre()
        etiquetaAltura.text = "${calculo.formatear(h)} m"
        etiquetaVelocidad.text = "${calculo.formatear(v)} m/s"
        etiquetaGravedad.text = "$nombre → ${calculo.formatear(g)} m/s²"
        val t = calculo.calcularTiempoCaida(h, v, g)
        etiquetaTiempoCaida.text = t?.let { "${calculo.formatear(it)} s" } ?: "No disponible"
        val vImp = calculo.calcularVelocidadImpacto(h, v, g)
        etiquetaVelocidadImpacto.text = vImp?.let { "${calculo.formatear(it)} m/s" } ?: "No disponible"
    }

    private fun crearEtiquetaCampo(texto: String): Label {
        val etiqueta = Label(texto)
        etiqueta.style = "-fx-text-fill: #34495e; -fx-font-size: 13px; -fx-wrap-text: true;"
        return etiqueta
    }

    private fun crearEtiquetaValor(): Label {
        val etiqueta = Label()
        etiqueta.style = "-fx-text-fill: #2c3e50; -fx-font-size: 13px; -fx-font-weight: bold;"
        return etiqueta
    }
}
