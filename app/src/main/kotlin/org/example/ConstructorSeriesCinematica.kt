package org.example

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart

/**
 * Implementación de [SeriesCinematica] que obtiene los puntos de la trayectoria
 * desde el servicio de física inyectado por constructor (DIP).
 */
class ConstructorSeriesCinematica(
    private val calculo: CalculadoraCaidaLibre
) : SeriesCinematica {

    override fun posicion(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double
    ): ObservableList<XYChart.Data<Number, Number>> {
        val datos = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()
        for (punto in calcularPuntos(alturaInicial, velocidadInicial, gravedad)) {
            datos.add(XYChart.Data(punto.tiempo(), punto.posicion()))
        }
        return datos
    }

    override fun velocidad(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double
    ): ObservableList<XYChart.Data<Number, Number>> {
        val datos = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()
        for (punto in calcularPuntos(alturaInicial, velocidadInicial, gravedad)) {
            datos.add(XYChart.Data(punto.tiempo(), punto.velocidad()))
        }
        return datos
    }

    private fun calcularPuntos(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double
    ): List<PuntoTrayectoria> =
        calculo.generarPuntosTrayectoria(alturaInicial, velocidadInicial, gravedad)
}
