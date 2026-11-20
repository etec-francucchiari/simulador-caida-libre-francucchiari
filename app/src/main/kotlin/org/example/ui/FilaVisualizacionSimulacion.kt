package org.example

import javafx.scene.layout.HBox

class FilaVisualizacionSimulacion(
    estado: EstadoSimulacion,
    calculo: CalculadoraCaidaLibre
) {
    /**
     * Construye la fila central que dispone en paralelo el lienzo de caída libre
     * ([PanelAnimacionCaida]) y el panel de gráficos cinemáticos
     * ([PanelGraficosSimulacion]) del Modo Principiante (SRP / DIP).
     */

    private val parametros = Pair(estado, calculo)

    /**
     * Construye y devuelve el contenedor horizontal con el lienzo y los gráficos.
     */
    fun construir(): HBox {
        val panelAnimacion = PanelAnimacionCaida(parametros.first)
        panelAnimacion.prefWidth = 300.0
        panelAnimacion.minWidth = 280.0
        val panelGraficos = PanelGraficosSimulacion(parametros.first, parametros.second)
        panelGraficos.prefWidth = 760.0
        val fila = HBox(12.0, panelAnimacion, panelGraficos)
        fila.style = "-fx-alignment: top-center;"
        return fila
    }
}
