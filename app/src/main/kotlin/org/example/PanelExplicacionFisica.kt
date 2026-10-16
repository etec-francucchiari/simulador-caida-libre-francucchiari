package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale

/**
 * Panel que presenta la explicación didáctica de la física paso a paso para el
 * Modo Principiante.
 *
 * Recibe por constructor la abstracción [EstadoSimulacion] (DIP) y el servicio
 * [ExplicadorFisicaPasoAPaso]. Muestra las tres ecuaciones del MRUV con el
 * reemplazo numérico y el cálculo en tiempo real.
 */
class PanelExplicacionFisica(
    private val estado: EstadoSimulacion,
    private val explicador: ExplicadorFisicaPasoAPaso
) : VBox(10.0) {

    // Componentes de la tarjeta de Posición
    private val etiquetaPosicionValores = Label()
    private val etiquetaPosicionSustitucion = Label()
    private val etiquetaPosicionIntermedia = Label()
    private val etiquetaPosicionResultado = Label()
    private val etiquetaPosicionAclaracion = Label()

    // Componentes de la tarjeta de Velocidad
    private val etiquetaVelocidadValores = Label()
    private val etiquetaVelocidadSustitucion = Label()
    private val etiquetaVelocidadIntermedia = Label()
    private val etiquetaVelocidadResultado = Label()
    private val etiquetaVelocidadAclaracion = Label()

    // Componentes de la tarjeta de Tiempo de Caída
    private val etiquetaTiempoValores = Label()
    private val etiquetaTiempoSustitucion = Label()
    private val etiquetaTiempoIntermedia = Label()
    private val etiquetaTiempoResultado = Label()
    private val etiquetaTiempoAclaracion = Label()

    private val etiquetaTiempoCabecera = Label()

    init {
        padding = Insets(12.0)
        style = "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #cbd5e1; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;"

        val encabezado = construirEncabezado()

        val tarjetaPosicion = construirTarjetaFormula(
            titulo = "1. Posición: y(t) = y₀ + v₀·t - ½·g·t²",
            colorBorde = "#3b82f6",
            etiquetaValores = etiquetaPosicionValores,
            etiquetaSustitucion = etiquetaPosicionSustitucion,
            etiquetaIntermedia = etiquetaPosicionIntermedia,
            etiquetaResultado = etiquetaPosicionResultado,
            etiquetaAclaracion = etiquetaPosicionAclaracion
        )

        val tarjetaVelocidad = construirTarjetaFormula(
            titulo = "2. Velocidad: v(t) = v₀ - g·t",
            colorBorde = "#f59e0b",
            etiquetaValores = etiquetaVelocidadValores,
            etiquetaSustitucion = etiquetaVelocidadSustitucion,
            etiquetaIntermedia = etiquetaVelocidadIntermedia,
            etiquetaResultado = etiquetaVelocidadResultado,
            etiquetaAclaracion = etiquetaVelocidadAclaracion
        )

        val tarjetaTiempoCaida = construirTarjetaFormula(
            titulo = "3. Tiempo de caída esperado: t_final = √(2·y₀ / g)",
            colorBorde = "#8b5cf6",
            etiquetaValores = etiquetaTiempoValores,
            etiquetaSustitucion = etiquetaTiempoSustitucion,
            etiquetaIntermedia = etiquetaTiempoIntermedia,
            etiquetaResultado = etiquetaTiempoResultado,
            etiquetaAclaracion = etiquetaTiempoAclaracion
        )

        children.addAll(encabezado, tarjetaPosicion, tarjetaVelocidad, tarjetaTiempoCaida)

        estado.tiempoActualObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        estado.alturaInicialObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        estado.velocidadInicialObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        estado.planetaObservable().addListener { _, _, _ -> actualizarExplicaciones() }

        actualizarExplicaciones()
    }

    private fun construirEncabezado(): VBox {
        val filaSuperior = HBox(8.0)
        filaSuperior.alignment = Pos.CENTER_LEFT

        val titulo = Label("Explicación Física Paso a Paso (MRUV)")
        titulo.font = Font.font("System", FontWeight.BOLD, 15.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val badgeModo = Label("Modo Principiante")
        badgeModo.font = Font.font("System", FontWeight.BOLD, 11.0)
        badgeModo.style = "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; " +
                "-fx-padding: 2 8; -fx-background-radius: 12;"

        HBox.setHgrow(titulo, Priority.ALWAYS)
        filaSuperior.children.addAll(titulo, badgeModo)

        val subtitulo = Label("Fórmulas teóricas, sustitución de variables y cálculo instantáneo:")
        subtitulo.font = Font.font("System", 12.0)
        subtitulo.style = "-fx-text-fill: #64748b;"

        etiquetaTiempoCabecera.font = Font.font("System", FontWeight.BOLD, 12.0)
        etiquetaTiempoCabecera.style = "-fx-text-fill: #2563eb;"

        return VBox(4.0, filaSuperior, subtitulo, etiquetaTiempoCabecera)
    }

    private fun construirTarjetaFormula(
        titulo: String,
        colorBorde: String,
        etiquetaValores: Label,
        etiquetaSustitucion: Label,
        etiquetaIntermedia: Label,
        etiquetaResultado: Label,
        etiquetaAclaracion: Label
    ): VBox {
        val tarjeta = VBox(5.0)
        tarjeta.padding = Insets(8.0, 10.0, 8.0, 12.0)
        tarjeta.style = "-fx-background-color: #f8fafc; " +
                "-fx-background-radius: 6; " +
                "-fx-border-color: $colorBorde; " +
                "-fx-border-width: 0 0 0 4; " +
                "-fx-border-radius: 0 6 6 0;"

        val labelTitulo = Label(titulo)
        labelTitulo.font = Font.font("System", FontWeight.BOLD, 13.0)
        labelTitulo.style = "-fx-text-fill: #0f172a;"

        val contenedorValores = VBox(2.0)
        val etiquetaPrefijoValores = Label("• Reemplazo con tus parámetros:")
        etiquetaPrefijoValores.font = Font.font("System", FontWeight.BOLD, 11.0)
        etiquetaPrefijoValores.style = "-fx-text-fill: #475569;"

        etiquetaValores.font = Font.font("Monospaced", 11.5)
        etiquetaValores.style = "-fx-text-fill: #0369a1; -fx-padding: 0 0 0 10;"

        contenedorValores.children.addAll(etiquetaPrefijoValores, etiquetaValores)

        val contenedorCalculo = VBox(2.0)
        val etiquetaPrefijoCalculo = Label("• Cálculo numérico en el instante actual:")
        etiquetaPrefijoCalculo.font = Font.font("System", FontWeight.BOLD, 11.0)
        etiquetaPrefijoCalculo.style = "-fx-text-fill: #475569;"

        etiquetaSustitucion.font = Font.font("Monospaced", 11.0)
        etiquetaSustitucion.style = "-fx-text-fill: #334155; -fx-padding: 0 0 0 10;"

        etiquetaIntermedia.font = Font.font("Monospaced", 11.0)
        etiquetaIntermedia.style = "-fx-text-fill: #334155; -fx-padding: 0 0 0 10;"

        etiquetaResultado.font = Font.font("System", FontWeight.BOLD, 12.0)
        etiquetaResultado.style = "-fx-text-fill: #166534; -fx-padding: 0 0 0 10;"

        contenedorCalculo.children.addAll(
            etiquetaPrefijoCalculo,
            etiquetaSustitucion,
            etiquetaIntermedia,
            etiquetaResultado
        )

        etiquetaAclaracion.font = Font.font("System", 10.5)
        etiquetaAclaracion.style = "-fx-text-fill: #64748b; -fx-wrap-text: true;"

        tarjeta.children.addAll(
            labelTitulo,
            contenedorValores,
            contenedorCalculo,
            etiquetaAclaracion
        )

        return tarjeta
    }

    /**
     * Recalcula y actualiza reactivamente todos los textos y cálculos.
     */
    fun actualizarExplicaciones() {
        val h0 = estado.obtenerAlturaInicial()
        val v0 = estado.obtenerVelocidadInicial()
        val g = estado.obtenerPlaneta().gravedad()
        val t = estado.obtenerTiempoActual()

        etiquetaTiempoCabecera.text = "⏱ Instante actual: t = ${formatear(t)} s"

        // 1. Posición
        val detallePos = explicador.explicarPosicion(h0, v0, g, t)
        etiquetaPosicionValores.text = detallePos.formulaConValores()
        etiquetaPosicionSustitucion.text = "Paso 1: ${detallePos.sustitucionInstante()}"
        etiquetaPosicionIntermedia.text = "Paso 2: ${detallePos.evaluacionIntermedia()}"
        etiquetaPosicionResultado.text = "Resultado: ${detallePos.resultadoFinal()}"
        etiquetaPosicionAclaracion.text = "💡 ${detallePos.aclaracionDidactica()}"

        // 2. Velocidad
        val detalleVel = explicador.explicarVelocidad(h0, v0, g, t)
        etiquetaVelocidadValores.text = detalleVel.formulaConValores()
        etiquetaVelocidadSustitucion.text = "Paso 1: ${detalleVel.sustitucionInstante()}"
        etiquetaVelocidadIntermedia.text = "Paso 2: ${detalleVel.evaluacionIntermedia()}"
        etiquetaVelocidadResultado.text = "Resultado: ${detalleVel.resultadoFinal()}"
        etiquetaVelocidadAclaracion.text = "💡 ${detalleVel.aclaracionDidactica()}"

        // 3. Tiempo de caída esperado
        val detalleTiempo = explicador.explicarTiempoCaida(h0, v0, g)
        etiquetaTiempoValores.text = detalleTiempo.formulaConValores()
        etiquetaTiempoSustitucion.text = "Paso 1: ${detalleTiempo.sustitucionInstante()}"
        etiquetaTiempoIntermedia.text = "Paso 2: ${detalleTiempo.evaluacionIntermedia()}"
        etiquetaTiempoResultado.text = "Resultado: ${detalleTiempo.resultadoFinal()}"
        etiquetaTiempoAclaracion.text = "💡 ${detalleTiempo.aclaracionDidactica()}"
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)
}