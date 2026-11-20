package org.example

import javafx.scene.control.*
import javafx.scene.layout.*

class PanelParametrosIniciales(
    estado: EstadoSimulacion,
    comandos: ComandosSimulacion,
    validador: ValidadorParametros
) : VBox(12.0) {
    constructor(contexto: ContextoSimulacion, configuracion: ConfiguracionSimulacion) :
        this(contexto, contexto, ValidadorParametrosIniciales(configuracion))

    /**
     * Panel de configuración de los parámetros iniciales (altura h₀ y velocidad v₀).
     *
     * Recibe por constructor las abstracciones [EstadoSimulacion] y
     * [ComandosSimulacion] (DIP), además del [ValidadorParametros].
     */

    private val estadoPanel = Pair(
        Triple(estado, comandos, validador),
        Pair(
            arrayOf(TextField(validador.formatear(estado.obtenerAlturaInicial())), TextField(validador.formatear(estado.obtenerVelocidadInicial()))),
            arrayOf(Label(), Label(), Label())
        )
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        val titulo = Label("Parámetros Iniciales")
        titulo.style = "-fx-text-fill: #2c3e50; -fx-font-size: 16px; -fx-font-weight: bold;"
        style = "-fx-background-color: #f4f6f8; -fx-padding: 14; -fx-background-radius: 6;"
        val campos = estadoPanel.second.first
        val etiquetas = estadoPanel.second.second
        campos.forEach {
            it.prefColumnCount = 8
            it.style = "-fx-alignment: center-right;"
        }
        etiquetas[0].style = "-fx-text-fill: #27ae60; -fx-font-size: 13px;"
        etiquetas[1].style = "-fx-text-fill: #27ae60; -fx-font-size: 13px;"
        etiquetas[2].style = "-fx-text-fill: #e74c3c; -fx-font-size: 13px;"
        etiquetas[2].isWrapText = true
        etiquetas[2].minHeight = 30.0
        children.addAll(titulo, construirGrilla(), etiquetas[2])
        vincularEventos()
    }

    private fun construirGrilla(): GridPane {
        val grilla = GridPane()
        grilla.hgap = 14.0
        grilla.vgap = 10.0
        grilla.style = "-fx-alignment: center-left;"
        val campos = estadoPanel.second.first
        val etiquetas = estadoPanel.second.second
        grilla.add(crearEtiqueta("Altura inicial (h₀) [m]:", "#34495e"), 0, 0)
        grilla.add(campos[0], 1, 0)
        grilla.add(etiquetas[0], 2, 0)
        grilla.add(crearEtiqueta("Velocidad inicial (v₀) [m/s]:", "#34495e"), 0, 1)
        grilla.add(campos[1], 1, 1)
        grilla.add(etiquetas[1], 2, 1)
        return grilla
    }

    private fun vincularEventos() {
        val est = estadoPanel.first.first
        val validador = estadoPanel.first.third
        val campos = estadoPanel.second.first
        val etiquetas = estadoPanel.second.second
        configurarCampo(campos[0], true)
        configurarCampo(campos[1], false)
        campos[0].disableProperty().bind(est.enEjecucionObservable())
        campos[1].disableProperty().bind(est.enEjecucionObservable())
        est.alturaInicialObservable().addListener { _, _, v ->
            etiquetas[0].text = "→ aplicado: ${validador.formatear(v.toDouble())} m"
        }
        est.velocidadInicialObservable().addListener { _, _, v ->
            etiquetas[1].text = "→ aplicado: ${validador.formatear(v.toDouble())} m/s"
        }
        etiquetas[0].text = "→ aplicado: ${validador.formatear(est.obtenerAlturaInicial())} m"
        etiquetas[1].text = "→ aplicado: ${validador.formatear(est.obtenerVelocidadInicial())} m/s"
    }

    private fun configurarCampo(campo: TextField, esCampoAltura: Boolean) {
        val comandos = estadoPanel.first.second
        val validador = estadoPanel.first.third
        campo.textProperty().addListener { _, _, nuevoTexto ->
            val texto = nuevoTexto ?: ""
            val error = if (esCampoAltura) validador.validarAltura(texto) else validador.validarVelocidad(texto)
            campo.style = if (error == null) "-fx-alignment: center-right;" else "-fx-alignment: center-right; -fx-border-color: #e74c3c; -fx-border-width: 1.5;"
            if (error == null) {
                val valor = texto.trim().replace(',', '.').toDouble()
                if (esCampoAltura) comandos.establecerAlturaInicial(valor) else comandos.establecerVelocidadInicial(valor)
            }
            actualizarEtiquetaError()
        }
    }

    private fun actualizarEtiquetaError() {
        val validador = estadoPanel.first.third
        val campos = estadoPanel.second.first
        val errores = mutableListOf<String>()
        validador.validarAltura(campos[0].text)?.let { errores += "Altura inicial: $it" }
        validador.validarVelocidad(campos[1].text)?.let { errores += "Velocidad inicial: $it" }
        estadoPanel.second.second[2].text = errores.joinToString("\n")
    }

    private fun crearEtiqueta(texto: String, color: String): Label {
        val etiqueta = Label(if (estadoPanel.second.first.isNotEmpty()) texto else texto)
        etiqueta.style = "-fx-text-fill: $color; -fx-font-size: 13px;"
        return etiqueta
    }
}
