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
 * [ExplicadorFisica]. Delega cada tarjeta en [TarjetaFormulaDidactica] (SRP).
 */
class PanelExplicacionFisica(
    private val estado: EstadoSimulacion,
    private val explicador: ExplicadorFisica
) : VBox(10.0) {

    private val etiquetaTiempoCabecera = Label()

    private val tarjetaPosicion = TarjetaFormulaDidactica(
        titulo = "1. Posición: y(t) = y₀ + v₀·t - ½·g·t²",
        colorBorde = "#3b82f6"
    )

    private val tarjetaVelocidad = TarjetaFormulaDidactica(
        titulo = "2. Velocidad: v(t) = v₀ - g·t",
        colorBorde = "#f59e0b"
    )

    private val tarjetaTiempoCaida = TarjetaFormulaDidactica(
        titulo = "3. Tiempo de caída esperado: t_final = √(2·y₀ / g)",
        colorBorde = "#8b5cf6"
    )

    init {
        padding = Insets(12.0)
        style = "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #cbd5e1; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;"

        children.addAll(
            construirEncabezado(),
            tarjetaPosicion.nodo(),
            tarjetaVelocidad.nodo(),
            tarjetaTiempoCaida.nodo()
        )

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

    /**
     * Recalcula y actualiza reactivamente todos los textos y cálculos.
     */
    fun actualizarExplicaciones() {
        val h0 = estado.obtenerAlturaInicial()
        val v0 = estado.obtenerVelocidadInicial()
        val g = estado.obtenerPlaneta().gravedad()
        val t = estado.obtenerTiempoActual()

        etiquetaTiempoCabecera.text = "⏱ Instante actual: t = ${formatear(t)} s"

        tarjetaPosicion.actualizar(explicador.explicarPosicion(h0, v0, g, t))
        tarjetaVelocidad.actualizar(explicador.explicarVelocidad(h0, v0, g, t))
        tarjetaTiempoCaida.actualizar(explicador.explicarTiempoCaida(h0, v0, g))
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)
}
