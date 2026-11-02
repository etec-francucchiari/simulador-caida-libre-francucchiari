package org.example

import javafx.scene.control.*
import javafx.scene.layout.*

class PanelParametrosIniciales(
    private val estado: EstadoSimulacion,
    private val comandos: ComandosSimulacion,
    private val validador: ValidadorParametros
) : VBox(12.0) {
    /**
     * Panel de configuración de los parámetros iniciales (altura h₀ y velocidad v₀).
     *
     * Recibe por constructor las abstracciones [EstadoSimulacion] y
     * [ComandosSimulacion] (DIP), además del [ValidadorParametros].
     */

    private val campoAltura = crearCampo(estado.obtenerAlturaInicial())
    private val campoVelocidad = crearCampo(estado.obtenerVelocidadInicial())
    private val etiquetaValorAltura = crearEtiqueta("#27ae60")
    private val etiquetaValorVelocidad = crearEtiqueta("#27ae60")
    private val etiquetaError = crearEtiqueta("#e74c3c")

    init {
        inicializar()
    }

    private fun inicializar() {
        val titulo = Label("Parámetros Iniciales")
        titulo.style = "-fx-text-fill: #2c3e50; -fx-font-size: 16px; -fx-font-weight: bold;"
        style = "-fx-background-color: #f4f6f8; -fx-padding: 14; -fx-background-radius: 6;"
        val grilla = GridPane()
        grilla.hgap = 14.0
        grilla.vgap = 10.0
        grilla.style = "-fx-alignment: center-left;"
        grilla.add(crearEtiqueta("Altura inicial (h₀) [m]:", "#34495e"), 0, 0)
        grilla.add(campoAltura, 1, 0)
        grilla.add(etiquetaValorAltura, 2, 0)
        grilla.add(crearEtiqueta("Velocidad inicial (v₀) [m/s]:", "#34495e"), 0, 1)
        grilla.add(campoVelocidad, 1, 1)
        grilla.add(etiquetaValorVelocidad, 2, 1)
        etiquetaError.isWrapText = true
        etiquetaError.minHeight = 30.0
        children.addAll(titulo, grilla, etiquetaError)
        vincularEventos()
    }

    private fun vincularEventos() {
        configurarCampo(campoAltura, true)
        configurarCampo(campoVelocidad, false)
        campoAltura.disableProperty().bind(estado.enEjecucionObservable())
        campoVelocidad.disableProperty().bind(estado.enEjecucionObservable())
        estado.alturaInicialObservable().addListener { _, _, v ->
            etiquetaValorAltura.text = "→ aplicado: ${validador.formatear(v.toDouble())} m"
        }
        estado.velocidadInicialObservable().addListener { _, _, v ->
            etiquetaValorVelocidad.text = "→ aplicado: ${validador.formatear(v.toDouble())} m/s"
        }
        etiquetaValorAltura.text = "→ aplicado: ${validador.formatear(estado.obtenerAlturaInicial())} m"
        etiquetaValorVelocidad.text = "→ aplicado: ${validador.formatear(estado.obtenerVelocidadInicial())} m/s"
    }

    private fun configurarCampo(campo: TextField, esCampoAltura: Boolean) {
        campo.textProperty().addListener { _, _, nuevoTexto ->
            val texto = nuevoTexto ?: ""
            val error = if (esCampoAltura) validador.validarAltura(texto) else validador.validarVelocidad(texto)
            campo.style = if (error == null) "" else "-fx-border-color: #e74c3c; -fx-border-width: 1.5;"
            if (error == null) {
                val valor = texto.trim().replace(',', '.').toDouble()
                if (esCampoAltura) comandos.establecerAlturaInicial(valor) else comandos.establecerVelocidadInicial(valor)
            }
            actualizarEtiquetaError()
        }
    }

    private fun actualizarEtiquetaError() {
        val errores = mutableListOf<String>()
        validador.validarAltura(campoAltura.text)?.let { errores += "Altura inicial: $it" }
        validador.validarVelocidad(campoVelocidad.text)?.let { errores += "Velocidad inicial: $it" }
        etiquetaError.text = errores.joinToString("\n")
    }

    private fun crearCampo(valorInicial: Double): TextField {
        val campo = TextField(validador.formatear(valorInicial))
        campo.prefColumnCount = 8
        campo.style = "-fx-alignment: center-right;"
        return campo
    }

    private fun crearEtiqueta(color: String): Label = crearEtiqueta("", color)

    private fun crearEtiqueta(texto: String, color: String): Label {
        val etiqueta = Label(texto)
        etiqueta.style = "-fx-text-fill: $color; -fx-font-size: 13px;"
        return etiqueta
    }
}
