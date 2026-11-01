package org.example

import javafx.collections.*
import javafx.scene.chart.XYChart

class ConstructorSeriesCinematica(
    private val calculo: CalculadoraCaidaLibre
) : SeriesCinematica {
    /**
     * Implementación instanciable de [SeriesCinematica] encargada de transformar
     * la secuencia de [PuntoTrayectoria] calculada por [CalculadoraCaidaLibre]
     * en listas observables [ObservableList] de nodos [XYChart.Data] para los
     * gráficos de líneas de JavaFX.
     *
     * Recibe su colaborador físico por constructor cumpliendo con el Principio
     * de Inversión de Dependencias (DIP).
     */

    /**
     * Construye la serie observable de pares `(tiempo, posicion)` que describe
     * la parábola de caída libre `y(t)` desde la altura inicial hasta el suelo.
     */
    override fun posicion(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): ObservableList<XYChart.Data<Number, Number>> {
        val datos = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()
        for (punto in calcularPuntos(alturaInicial, velocidadInicial, gravedad)) {
            datos.add(XYChart.Data(punto.tiempo(), punto.posicion()))
        }
        return datos
    }

    /**
     * Construye la serie observable de pares `(tiempo, velocidad)` que describe
     * la evolución lineal de la rapidez `v(t)` durante el descenso.
     */
    override fun velocidad(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): ObservableList<XYChart.Data<Number, Number>> {
        val datos = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()
        for (punto in calcularPuntos(alturaInicial, velocidadInicial, gravedad)) {
            datos.add(XYChart.Data(punto.tiempo(), punto.velocidad()))
        }
        return datos
    }

    /**
     * Solicita al servicio físico la discretización de la trayectoria completa.
     */
    private fun calcularPuntos(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): List<PuntoTrayectoria> {
        return calculo.generarPuntosTrayectoria(alturaInicial, velocidadInicial, gravedad)
    }
}
