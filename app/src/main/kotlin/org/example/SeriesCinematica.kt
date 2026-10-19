package org.example

import javafx.collections.ObservableList
import javafx.scene.chart.XYChart

/**
 * Contrato del constructor de series para los gráficos cinemáticos (DIP).
 *
 * Aísla a la vista de los cálculos físicos y del formato de datos de JavaFX.
 */
interface SeriesCinematica {

    /** Serie teórica de posición y(t). */
    fun posicion(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double
    ): ObservableList<XYChart.Data<Number, Number>>

    /** Serie teórica de velocidad v(t). */
    fun velocidad(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double
    ): ObservableList<XYChart.Data<Number, Number>>
}
