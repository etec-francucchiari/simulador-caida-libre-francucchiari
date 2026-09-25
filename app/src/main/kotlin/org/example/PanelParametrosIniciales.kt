package org.example

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale

/**
 * Panel de configuración de los parámetros iniciales (altura h₀ y
 * velocidad v₀) del simulador de caída libre.
 *
 * Encapsula los campos de entrada, su validación en vivo y la conexión
 * con el gestor de estado reactivo:
 * - Cuando el texto es válido, el valor se publica en
 *   [GestorEstadoSimulacion] y las fórmulas físicas se actualizan solas.
 * - Cuando el texto es inválido, se muestra el error en la interfaz sin
 *   romper la aplicación y se conserva el último valor válido aplicado.
 *
 * No conoce física: delega la validación a [ValidadorParametrosIniciales]
 * y el estado a [GestorEstadoSimulacion].
 */
class PanelParametrosIniciales(
    private val gestorEstado: GestorEstadoSimulacion
) : VBox(12.0) {

    private val validador = ValidadorParametrosIniciales()

    private val campoAltura: TextField =
        crearCampo(gestorEstado.obtenerAlturaInicial())
    private val campoVelocidad: TextField =
        crearCampo(gestorEstado.obtenerVelocidadInicial())

    private val etiquetaValorAltura: Label = crearEtiquetaValorAceptado()
    private val etiquetaValorVelocidad: Label = crearEtiquetaValorAceptado()
    private val etiquetaError: Label = crearEtiquetaError()

    init {
        val titulo = Label("Parámetros Iniciales")
        titulo.font = Font.font("System", FontWeight.BOLD, 16.0)
        titulo.style = "-fx-text-fill: #2c3e50;"

        style = "-fx-background-color: #f4f6f8; " +
                "-fx-padding: 14; " +
                "-fx-background-radius: 6;"

        val grilla = GridPane()
        grilla.hgap = 14.0
        grilla.vgap = 10.0
        grilla.alignment = Pos.CENTER_LEFT
        grilla.columnConstraints.addAll(
            ColumnConstraints(190.0),
            ColumnConstraints(180.0),
            ColumnConstraints(230.0)
        )

        grilla.add(crearEtiquetaCampo("Altura inicial (h₀) [m]:"), 0, 0)
        grilla.add(campoAltura, 1, 0)
        grilla.add(etiquetaValorAltura, 2, 0)

        grilla.add(crearEtiquetaCampo("Velocidad inicial (v₀) [m/s]:"), 0, 1)
        grilla.add(campoVelocidad, 1, 1)
        grilla.add(etiquetaValorVelocidad, 2, 1)

        children.addAll(titulo, grilla, etiquetaError)

        // Conexión reactiva: cada campo valida su texto y, si es válido,
        // publica el valor en el gestor de estado (dispara las suscripciones).
        configurarCampo(campoAltura, esCampoAltura = true)
        configurarCampo(campoVelocidad, esCampoAltura = false)

        // Indicadores del valor aplicado: se refrescan solos al cambiar el estado.
        gestorEstado.alturaInicialProperty.addListener { _, _, nuevoValor ->
            etiquetaValorAltura.text = "→ aplicado: ${formatear(nuevoValor.toDouble())} m"
        }
        gestorEstado.velocidadInicialProperty.addListener { _, _, nuevoValor ->
            etiquetaValorVelocidad.text = "→ aplicado: ${formatear(nuevoValor.toDouble())} m/s"
        }
        etiquetaValorAltura.text =
            "→ aplicado: ${formatear(gestorEstado.obtenerAlturaInicial())} m"
        etiquetaValorVelocidad.text =
            "→ aplicado: ${formatear(gestorEstado.obtenerVelocidadInicial())} m/s"
    }

    /**
     * Configura la reacción de un campo ante cambios de texto:
     * valida, resalta el borde y publica el valor en el estado si es válido.
     */
    private fun configurarCampo(campo: TextField, esCampoAltura: Boolean) {
        campo.textProperty().addListener { _, _, nuevoTexto ->
            val texto = nuevoTexto ?: ""
            val error = if (esCampoAltura) {
                validador.validarAltura(texto)
            } else {
                validador.validarVelocidad(texto)
            }

            actualizarBordeCampo(campo, error == null)

            if (error == null) {
                val valor = parsearNumero(texto)
                if (esCampoAltura) {
                    gestorEstado.establecerAlturaInicial(valor)
                } else {
                    gestorEstado.establecerVelocidadInicial(valor)
                }
            }

            actualizarEtiquetaError()
        }
    }

    /**
     * Recalcula y muestra los errores de ambos campos en la interfaz.
     * Verifica cada campo por separado para indicar cuál tiene el problema.
     */
    private fun actualizarEtiquetaError() {
        val errores = mutableListOf<String>()

        val errorAltura = validador.validarAltura(campoAltura.text)
        if (errorAltura != null) {
            errores += "Altura inicial: $errorAltura"
        }

        val errorVelocidad = validador.validarVelocidad(campoVelocidad.text)
        if (errorVelocidad != null) {
            errores += "Velocidad inicial: $errorVelocidad"
        }

        etiquetaError.text = errores.joinToString("\n")
    }

    /**
     * Resalta el borde del campo en rojo cuando el valor es inválido.
     */
    private fun actualizarBordeCampo(campo: TextField, valido: Boolean) {
        campo.style = if (valido) {
            ""
        } else {
            "-fx-border-color: #e74c3c; -fx-border-width: 1.5;"
        }
    }

    private fun crearCampo(valorInicial: Double): TextField {
        val campo = TextField(formatear(valorInicial))
        campo.prefColumnCount = 8
        campo.alignment = Pos.CENTER_RIGHT
        return campo
    }

    private fun crearEtiquetaCampo(texto: String): Label {
        val etiqueta = Label(texto)
        etiqueta.font = Font.font("System", 14.0)
        etiqueta.style = "-fx-text-fill: #34495e;"
        return etiqueta
    }

    private fun crearEtiquetaValorAceptado(): Label {
        val etiqueta = Label()
        etiqueta.font = Font.font("System", 13.0)
        etiqueta.style = "-fx-text-fill: #27ae60;"
        return etiqueta
    }

    /**
     * Etiqueta de errores: se reserva espacio para evitar saltos de layout.
     */
    private fun crearEtiquetaError(): Label {
        val etiqueta = Label()
        etiqueta.wrapTextProperty().set(true)
        etiqueta.minHeight = RESERVIDO_PARA_ERRORES
        etiqueta.font = Font.font("System", 13.0)
        etiqueta.style = "-fx-text-fill: #e74c3c;"
        return etiqueta
    }

    /**
     * Convierte un texto ya validado en su valor numérico.
     */
    private fun parsearNumero(texto: String): Double =
        texto.trim().replace(',', '.').toDouble()

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)

    companion object {
        private const val RESERVIDO_PARA_ERRORES = 30.0
    }
}