package org.example

import javafx.collections.ObservableList
import javafx.scene.chart.*

class GraficoCurvaCinematica(
    etiquetaX: String,
    etiquetaY: String,
    tituloGrafico: String,
    nombreCurva: String,
    nombrePunto: String
) {
    /**
     * Componente encapsulado para un gráfico cinemático individual que combina la
     * curva teórica completa del movimiento con el marcador del instante actual.
     *
     * Extraído de [PanelGraficosSimulacion] para eliminar la duplicación entre los
     * gráficos de posición y(t) y velocidad v(t), garantizando el principio de
     * responsabilidad única (SRP) y manteniendo todos los ejes y series privados.
     */

    private val ejeX = NumberAxis()
    private val ejeY = NumberAxis()
    private val grafico = LineChart(ejeX, ejeY)
    private val serieTeorica = XYChart.Series<Number, Number>()
    private val serieActual = XYChart.Series<Number, Number>()

    init {
        inicializarEjesYGrafico(etiquetaX, etiquetaY, tituloGrafico)
        inicializarSeries(nombreCurva, nombrePunto)
    }

    /**
     * Configura los ejes numéricos y las propiedades visuales del gráfico de líneas.
     */
    private fun inicializarEjesYGrafico(etiquetaX: String, etiquetaY: String, tituloGrafico: String) {
        configurarEje(ejeX, etiquetaX)
        configurarEje(ejeY, etiquetaY)
        grafico.title = tituloGrafico
        grafico.animated = false
        grafico.isLegendVisible = true
        grafico.createSymbols = true
        grafico.prefHeight = 300.0
    }

    /**
     * Asigna los nombres de leyenda a la serie teórica y al marcador instantáneo
     * y los registra en el gráfico.
     */
    private fun inicializarSeries(nombreCurva: String, nombrePunto: String) {
        serieTeorica.name = nombreCurva
        serieActual.name = nombrePunto
        grafico.data.addAll(serieTeorica, serieActual)
    }

    /**
     * Configura las opciones de autoajuste y etiqueta de un eje numérico.
     */
    private fun configurarEje(eje: NumberAxis, etiqueta: String) {
        eje.label = etiqueta
        eje.animated = false
        eje.isAutoRanging = true
        eje.isForceZeroInRange = true
    }

    /**
     * Devuelve el nodo JavaFX del gráfico de líneas listo para insertarse en la escena.
     */
    fun nodo(): LineChart<Number, Number> = grafico

    /**
     * Reemplaza los puntos de la curva teórica cuando cambian los parámetros físicos.
     *
     * @param datos lista observable de pares (tiempo, magnitud).
     */
    fun actualizarCurva(datos: ObservableList<XYChart.Data<Number, Number>>) {
        serieTeorica.data = datos
    }

    /**
     * Actualiza las coordenadas del punto marcador que representa el instante actual
     * sin reconstruir la serie completa.
     *
     * @param t tiempo actual en segundos.
     * @param valor magnitud física evaluada en el instante `t`.
     */
    fun actualizarPunto(t: Double, valor: Double) {
        if (serieActual.data.isEmpty()) {
            serieActual.data.add(XYChart.Data(t, valor))
        } else {
            val punto = serieActual.data[0]
            punto.xValue = t
            punto.yValue = valor
        }
    }
}
