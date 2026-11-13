package org.example

import javafx.collections.ObservableList
import javafx.scene.chart.XYChart

interface SeriesCinematica {
    /**
     * Contrato del constructor de series de datos para los gráficos cinemáticos
     * en tiempo real (Principio de Inversión de Dependencias - DIP).
     *
     * Desacopla a [PanelGraficosSimulacion] de las fórmulas físicas y de la
     * conversión de [PuntoTrayectoria] a nodos [XYChart.Data], delegando esa
     * responsabilidad en [ConstructorSeriesCinematica].
     */

    /**
     * Construye la serie observable de pares `(t, y(t))` que representa la curva
     * teórica de altura en función del tiempo desde el lanzamiento hasta el suelo.
     */
    fun posicion(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): ObservableList<XYChart.Data<Number, Number>>

    /**
     * Construye la serie observable de pares `(t, v(t))` que representa la recta
     * teórica de rapidez en función del tiempo hasta el instante de impacto.
     */
    fun velocidad(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): ObservableList<XYChart.Data<Number, Number>>
}
