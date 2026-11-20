package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class PanelGraficosSimulacion(
    estado: EstadoSimulacion,
    series: SeriesCinematica
) : VBox(8.0) {
    constructor(estado: EstadoSimulacion, calculo: CalculadoraCaidaLibre) :
        this(estado, ConstructorSeriesCinematica(calculo))

    /**
     * Componente visual reactivo encargado de graficar en tiempo real las funciones
     * cinemáticas de la caída libre: y(t) y v(t).
     */

    private val vista = Pair(
        Pair(estado, series),
        Pair(
            crearGrafico("Altura y(t) [m]", "Posición vs Tiempo y(t)", "Curva teórica y(t)", "Punto actual (t, y)"),
            crearGrafico("Velocidad v(t) [m/s]", "Velocidad vs Tiempo v(t)", "Curva teórica v(t)", "Punto actual (t, v)")
        )
    )

    private fun crearGrafico(ejeY: String, titulo: String, curva: String, punto: String): GraficoCurvaCinematica =
        GraficoCurvaCinematica("Tiempo t [s]", ejeY, titulo, curva, punto)

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: center; -fx-padding: 8; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 8; -fx-border-color: #d5dbe0; -fx-border-radius: 8; -fx-border-width: 1;"
        val encabezado = Label("Gráficos Cinemáticos en Tiempo Real")
        encabezado.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        val tabPosicion = Tab("Posición vs Tiempo y(t)", vista.second.first.nodo())
        tabPosicion.isClosable = false
        val tabVelocidad = Tab("Velocidad vs Tiempo v(t)", vista.second.second.nodo())
        tabVelocidad.isClosable = false
        children.addAll(encabezado, TabPane(tabPosicion, tabVelocidad))
        val est = vista.first.first
        est.alturaInicialObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        est.velocidadInicialObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        est.planetaObservable().addListener { _, _, _ -> actualizarGraficosCompletos() }
        est.tiempoActualObservable().addListener { _, _, _ -> actualizarMarcadoresActuales() }
        actualizarGraficosCompletos()
    }

    /** Recalcula y redibuja las curvas teóricas completas de ambos gráficos. */
    fun actualizarGraficosCompletos() {
        val est = vista.first.first
        val ser = vista.first.second
        val h0 = est.obtenerAlturaInicial()
        val v0 = est.obtenerVelocidadInicial()
        val g = est.obtenerPlaneta().gravedad()
        vista.second.first.actualizarCurva(ser.posicion(h0, v0, g))
        vista.second.second.actualizarCurva(ser.velocidad(h0, v0, g))
        actualizarMarcadoresActuales()
    }

    private fun actualizarMarcadoresActuales() {
        val est = vista.first.first
        val t = est.obtenerTiempoActual()
        vista.second.first.actualizarPunto(t, est.obtenerAlturaActual())
        vista.second.second.actualizarPunto(t, est.obtenerVelocidadActual())
    }
}
