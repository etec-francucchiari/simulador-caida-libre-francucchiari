package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class PanelExplicacionFisica(
    private val estado: EstadoSimulacion,
    private val explicador: ExplicadorFisica
) : VBox(10.0) {
    /**
     * Panel que presenta la explicación didáctica de la física paso a paso para el
     * Modo Principiante.
     *
     * Recibe por constructor la abstracción [EstadoSimulacion] (DIP) y el servicio
     * [ExplicadorFisica]. Delega cada tarjeta en [TarjetaFormulaDidactica] (SRP).
     */

    private val etiquetaTiempoCabecera = Label()
    private val tarjetaPosicion = TarjetaFormulaDidactica("1. Posición: y(t) = y₀ + v₀·t - ½·g·t²", "#3b82f6")
    private val tarjetaVelocidad = TarjetaFormulaDidactica("2. Velocidad: v(t) = v₀ - g·t", "#f59e0b")
    private val tarjetaTiempoCaida = TarjetaFormulaDidactica("3. Tiempo de caída esperado: t_final = √(2·y₀ / g)", "#8b5cf6")

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-padding: 12; -fx-background-color: #ffffff; -fx-background-radius: 8; " +
            "-fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-border-width: 1;"
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
        filaSuperior.style = "-fx-alignment: center-left;"
        val titulo = Label("Explicación Física Paso a Paso (MRUV)")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        val badgeModo = Label("Modo Principiante")
        badgeModo.style = "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; " +
            "-fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 12;"
        filaSuperior.children.addAll(titulo, badgeModo)
        etiquetaTiempoCabecera.style = "-fx-text-fill: #2563eb; -fx-font-size: 12px; -fx-font-weight: bold;"
        return VBox(4.0, filaSuperior, etiquetaTiempoCabecera)
    }

    /** Recalcula y actualiza reactivamente todos los textos y cálculos. */
    fun actualizarExplicaciones() {
        val h0 = estado.obtenerAlturaInicial()
        val v0 = estado.obtenerVelocidadInicial()
        val g = estado.obtenerPlaneta().gravedad()
        val t = estado.obtenerTiempoActual()
        etiquetaTiempoCabecera.text = "⏱ Instante actual: t = ${explicador.formatear(t)} s"
        tarjetaPosicion.actualizar(explicador.explicarPosicion(h0, v0, g, t))
        tarjetaVelocidad.actualizar(explicador.explicarVelocidad(h0, v0, g, t))
        tarjetaTiempoCaida.actualizar(explicador.explicarTiempoCaida(h0, v0, g))
    }
}
