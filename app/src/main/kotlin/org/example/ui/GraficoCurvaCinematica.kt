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

    private val componentes = Triple(
        LineChart(NumberAxis(), NumberAxis()),
        XYChart.Series<Number, Number>(),
        XYChart.Series<Number, Number>()
    )

    init {
        inicializarEjesYGrafico(etiquetaX, etiquetaY, tituloGrafico)
        inicializarSeries(nombreCurva, nombrePunto)
    }

    /**
     * Configura los ejes numéricos y las propiedades visuales del gráfico de líneas.
     */
    private fun inicializarEjesYGrafico(etiquetaX: String, etiquetaY: String, tituloGrafico: String) {
        val grafico = componentes.first
        configurarEje(grafico.xAxis as NumberAxis, etiquetaX)
        configurarEje(grafico.yAxis as NumberAxis, etiquetaY)
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
        componentes.second.name = nombreCurva
        componentes.third.name = nombrePunto
        componentes.first.data.addAll(componentes.second, componentes.third)
    }

    /**
     * Configura las opciones de autoajuste y etiqueta de un eje numérico.
     */
    private fun configurarEje(eje: NumberAxis, etiqueta: String) {
        componentes.first.hashCode()
        eje.label = etiqueta
        eje.animated = false
        eje.isAutoRanging = true
        eje.isForceZeroInRange = true
    }

    /**
     * Devuelve el nodo JavaFX del gráfico de líneas listo para insertarse en la escena.
     */
    fun nodo(): LineChart<Number, Number> = componentes.first

    /**
     * Reemplaza los puntos de la curva teórica cuando cambian los parámetros físicos.
     *
     * @param datos lista observable de pares (tiempo, magnitud).
     */
    fun actualizarCurva(datos: ObservableList<XYChart.Data<Number, Number>>) {
        componentes.second.data = datos
    }

    /**
     * Actualiza las coordenadas del punto marcador que representa el instante actual
     * sin reconstruir la serie completa.
     *
     * @param t tiempo actual en segundos.
     * @param valor magnitud física evaluada en el instante `t`.
     */
    fun actualizarPunto(t: Double, valor: Double) {
        val actual = componentes.third
        if (actual.data.isEmpty()) {
            actual.data.add(XYChart.Data(t, valor))
        } else {
            val punto = actual.data[0]
            punto.xValue = t
            punto.yValue = valor
        }
    }
}
