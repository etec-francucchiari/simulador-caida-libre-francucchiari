package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class PanelGraficosSimulacion(
    private val estado: EstadoSimulacion,
    private val series: SeriesCinematica
) : VBox(8.0) {
    /**
     * Componente visual reactivo encargado de graficar en tiempo real las funciones
     * cinemáticas de la caída libre: y(t) y v(t).
     */

    private val graficoPosicion = GraficoCurvaCinematica(
        "Tiempo t [s]",
        "Altura y(t) [m]",
        "Posición vs Tiempo y(t)",
        "Curva teórica y(t)",
        "Punto actual (t, y)"
    )
    private val graficoVelocidad = GraficoCurvaCinematica(
        "Tiempo t [s]",
        "Velocidad v(t) [m/s]",
        "Velocidad vs Tiempo v(t)",
        "Curva teórica v(t)",
        "Punto actual (t, v)"
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 8; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 8; -fx-border-color: #d5dbe0; -fx-border-radius: 8; -fx-border-width: 1;"
        val encabezado = Label("Gráficos Cinemáticos en Tiempo Real")
        encabezado.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        val tabPosicion = Tab("Posición vs Tiempo y(t)", graficoPosicion.nodo())
        tabPosicion.isClosable = false
        val tabVelocidad = Tab("Velocidad vs Tiempo v(t)", graficoVelocidad.nodo())
        tabVelocidad.isClosable = false
        val tabPane = TabPane(tabPosicion, tabVelocidad)
        children.addAll(encabezado, tabPane)
        estado.alturaInicialObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        estado.velocidadInicialObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        estado.planetaObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        estado.tiempoActualObservable().addListener { _, _, _ -> actualizarMarcadoresActuales() }
        actualizarGraficosCompletos()
    }

    /** Recalcula y redibuja las curvas teóricas completas de ambos gráficos. */
    fun actualizarGraficosCompletos() {
        val h0 = estado.obtenerAlturaInicial()
        val v0 = estado.obtenerVelocidadInicial()
        val g = estado.obtenerPlaneta().gravedad()
        graficoPosicion.actualizarCurva(series.posicion(h0, v0, g))
        graficoVelocidad.actualizarCurva(series.velocidad(h0, v0, g))
        actualizarMarcadoresActuales()
    }

    private fun actualizarMarcadoresActuales() {
        val t = estado.obtenerTiempoActual()
        graficoPosicion.actualizarPunto(t, estado.obtenerAlturaActual())
        graficoVelocidad.actualizarPunto(t, estado.obtenerVelocidadActual())
    }
}
