package org.example

import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Componente visual reactivo encargado de graficar en tiempo real las funciones
 * cinemáticas de la caída libre:
 * - Posición vs Tiempo: y(t)
 * - Velocidad vs Tiempo: v(t)
 *
 * Utiliza componentes estándar de JavaFX ([LineChart], [NumberAxis], [XYChart]).
 *
 * Se suscribe al [GestorEstadoSimulacion] para:
 * 1. Regenerar las curvas teóricas cuando cambian los parámetros iniciales o el planeta.
 * 2. Mover el marcador de punto instantáneo a medida que la simulación avanza.
 */
class PanelGraficosSimulacion(
    private val gestorEstado: GestorEstadoSimulacion
) : VBox(8.0) {

    private val ejeXPosicion = NumberAxis()
    private val ejeYPosicion = NumberAxis()
    private val graficoPosicion: LineChart<Number, Number>

    private val serieTeoricaPosicion = XYChart.Series<Number, Number>()
    private val serieActualPosicion = XYChart.Series<Number, Number>()

    private val ejeXVelocidad = NumberAxis()
    private val ejeYVelocidad = NumberAxis()
    private val graficoVelocidad: LineChart<Number, Number>

    private val serieTeoricaVelocidad = XYChart.Series<Number, Number>()
    private val serieActualVelocidad = XYChart.Series<Number, Number>()

    init {
        alignment = Pos.CENTER
        padding = Insets(8.0)
        style = "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #d5dbe0; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;"

        val encabezado = Label("Gráficos Cinemáticos en Tiempo Real")
        encabezado.font = Font.font("System", FontWeight.BOLD, 15.0)
        encabezado.style = "-fx-text-fill: #1e293b;"

        // Configuración de los ejes
        configurarEje(ejeXPosicion, "Tiempo t [s]")
        configurarEje(ejeYPosicion, "Altura y(t) [m]")
        configurarEje(ejeXVelocidad, "Tiempo t [s]")
        configurarEje(ejeYVelocidad, "Velocidad v(t) [m/s]")

        // Creación y configuración de los gráficos
        graficoPosicion = crearGrafico(ejeXPosicion, ejeYPosicion, "Posición vs Tiempo y(t)")
        graficoVelocidad = crearGrafico(ejeXVelocidad, ejeYVelocidad, "Velocidad vs Tiempo v(t)")

        // Series para posición
        serieTeoricaPosicion.name = "Curva teórica y(t)"
        serieActualPosicion.name = "Punto actual (t, y)"
        graficoPosicion.data.addAll(serieTeoricaPosicion, serieActualPosicion)

        // Series para velocidad
        serieTeoricaVelocidad.name = "Curva teórica v(t)"
        serieActualVelocidad.name = "Punto actual (t, v)"
        graficoVelocidad.data.addAll(serieTeoricaVelocidad, serieActualVelocidad)

        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        val tabPosicion = Tab("Posición vs Tiempo y(t)", graficoPosicion)
        val tabVelocidad = Tab("Velocidad vs Tiempo v(t)", graficoVelocidad)

        tabPane.tabs.addAll(tabPosicion, tabVelocidad)
        VBox.setVgrow(tabPane, Priority.ALWAYS)

        children.addAll(encabezado, tabPane)

        // Suscripción reactiva a cambios en los parámetros del modelo
        gestorEstado.alturaInicialProperty.addListener { _, _, _ -> actualizarGraficosCompletos() }
        gestorEstado.velocidadInicialProperty.addListener { _, _, _ -> actualizarGraficosCompletos() }
        gestorEstado.planetaActivoProperty.addListener { _, _, _ -> actualizarGraficosCompletos() }

        // Suscripción reactiva al avance del tiempo de la simulación
        gestorEstado.tiempoActualProperty.addListener { _, _, _ -> actualizarMarcadoresActuales() }

        // Renderizado inicial
        actualizarGraficosCompletos()
    }

    /**
     * Recalcula y redibuja las curvas teóricas completas de ambos gráficos
     * a partir de los parámetros vigentes en el gestor de estado.
     */
    fun actualizarGraficosCompletos() {
        val h0 = gestorEstado.obtenerAlturaInicial()
        val v0 = gestorEstado.obtenerVelocidadInicial()
        val g = gestorEstado.obtenerPlaneta().gravedad

        val puntosTrayectoria = CalculoCaidaLibre.generarPuntosTrayectoria(h0, v0, g)

        val datosPosicion = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()
        val datosVelocidad = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()

        for (punto in puntosTrayectoria) {
            datosPosicion.add(XYChart.Data(punto.tiempo, punto.posicion))
            datosVelocidad.add(XYChart.Data(punto.tiempo, punto.velocidad))
        }

        serieTeoricaPosicion.data = datosPosicion
        serieTeoricaVelocidad.data = datosVelocidad

        actualizarMarcadoresActuales()
    }

    /**
     * Actualiza el marcador dinámico que indica la posición instantánea en ambos gráficos.
     */
    private fun actualizarMarcadoresActuales() {
        val t = gestorEstado.obtenerTiempoActual()
        val y = gestorEstado.obtenerAlturaActual()
        val v = gestorEstado.obtenerVelocidadActual()

        serieActualPosicion.data =
            FXCollections.observableArrayList(XYChart.Data<Number, Number>(t, y))
        serieActualVelocidad.data =
            FXCollections.observableArrayList(XYChart.Data<Number, Number>(t, v))
    }

    private fun configurarEje(eje: NumberAxis, etiqueta: String) {
        eje.label = etiqueta
        eje.animated = false
        eje.isAutoRanging = true
        eje.isForceZeroInRange = true
    }

    private fun crearGrafico(
        ejeX: NumberAxis,
        ejeY: NumberAxis,
        tituloGrafico: String
    ): LineChart<Number, Number> {
        val grafico = LineChart(ejeX, ejeY)
        grafico.title = tituloGrafico
        grafico.animated = false
        grafico.isLegendVisible = true
        grafico.createSymbols = true
        grafico.prefHeight = 300.0
        return grafico
    }
}
