package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelExplicacionFisica(
    estado: EstadoSimulacion,
    explicador: ExplicadorFisica
) : VBox(10.0) {
    constructor(estado: EstadoSimulacion, calculo: CalculadoraCaidaLibre) :
        this(estado, ExplicadorFisicaPasoAPaso(calculo))

    /**
     * Panel que presenta la explicación didáctica de la física paso a paso para el
     * Modo Principiante.
     *
     * Recibe por constructor la abstracción [EstadoSimulacion] (DIP) y el servicio
     * [ExplicadorFisica]. Delega cada tarjeta en [TarjetaFormulaDidactica] (SRP).
     */

    private val vista = Pair(
        Pair(estado, explicador),
        Pair(
            Label(),
            listOf(
                crearTarjeta("1. Posición: y(t) = y₀ + v₀·t - ½·g·t²", "#3b82f6"),
                crearTarjeta("2. Velocidad: v(t) = v₀ - g·t", "#f59e0b"),
                crearTarjeta("3. Tiempo de caída esperado: t_final = √(2·y₀ / g)", "#8b5cf6")
            )
        )
    )

    private fun crearTarjeta(titulo: String, color: String): TarjetaFormulaDidactica =
        TarjetaFormulaDidactica(titulo, color)

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-padding: 12; -fx-background-color: #ffffff; -fx-background-radius: 8; " +
            "-fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-border-width: 1;"
        val tarjetas = vista.second.second
        children.addAll(
            construirEncabezado(),
            tarjetas[0].nodo(),
            tarjetas[1].nodo(),
            tarjetas[2].nodo()
        )
        val est = vista.first.first
        est.tiempoActualObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        est.alturaInicialObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        est.velocidadInicialObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        est.planetaObservable().addListener { _, _, _ -> actualizarExplicaciones() }
        actualizarExplicaciones()
    }

    private fun construirEncabezado(): VBox {
        val filaSuperior = HBox(8.0)
        filaSuperior.style = "-fx-alignment: center-left;"
        val titulo = Label("Explicación Física Paso a Paso (MRUV)")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        val badgeModo = Label("Modo Principiante")
        badgeModo.style = "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; " +
            "-fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 12;"
        filaSuperior.children.addAll(titulo, badgeModo)
        vista.second.first.style = "-fx-text-fill: #2563eb; -fx-font-size: 12px; -fx-font-weight: bold;"
        return VBox(4.0, filaSuperior, vista.second.first)
    }

    /** Recalcula y actualiza reactivamente todos los textos y cálculos. */
    fun actualizarExplicaciones() {
        val est = vista.first.first
        val exp = vista.first.second
        val tarjetas = vista.second.second
        val h0 = est.obtenerAlturaInicial()
        val v0 = est.obtenerVelocidadInicial()
        val g = est.obtenerPlaneta().gravedad()
        val t = est.obtenerTiempoActual()
        vista.second.first.text = "⏱ Instante actual: t = ${exp.formatear(t)} s"
        tarjetas[0].actualizar(exp.explicarPosicion(h0, v0, g, t))
        tarjetas[1].actualizar(exp.explicarVelocidad(h0, v0, g, t))
        tarjetas[2].actualizar(exp.explicarTiempoCaida(h0, v0, g))
    }
}
