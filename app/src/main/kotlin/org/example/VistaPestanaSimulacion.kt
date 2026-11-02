package org.example

import javafx.scene.layout.*

class VistaPestanaSimulacion(
    private val estado: EstadoSimulacion,
    private val series: SeriesCinematica,
    private val renderizador: RenderizadorAnimacionCaida
) {
    /**
     * Construye el contenido de la pestaña de simulación en tiempo real del Modo
     * Principiante, integrando los valores instantáneos, el lienzo de animación y
     * las curvas cinemáticas.
     *
     * Depende exclusivamente de los contratos [EstadoSimulacion], [SeriesCinematica]
     * y [RenderizadorAnimacionCaida] inyectados por constructor (DIP).
     */

    /**
     * Construye y devuelve el contenedor vertical principal de la pestaña de
     * simulación interactiva.
     */
    fun construir(): VBox {
        val contenedor = VBox(12.0)
        contenedor.style = "-fx-padding: 14;"
        val panelValores = PanelValoresInstantaneos(estado)
        contenedor.children.addAll(panelValores, construirFilaVisualizacion())
        return contenedor
    }

    /**
     * Construye la fila central que dispone en paralelo el lienzo de caída libre
     * y el panel de gráficos cinemáticos.
     */
    private fun construirFilaVisualizacion(): HBox {
        val panelAnimacion = PanelAnimacionCaida(estado, renderizador)
        panelAnimacion.prefWidth = 300.0
        panelAnimacion.minWidth = 280.0
        val panelGraficos = PanelGraficosSimulacion(estado, series)
        panelGraficos.prefWidth = 760.0
        val filaVisualizacion = HBox(12.0, panelAnimacion, panelGraficos)
        filaVisualizacion.style = "-fx-alignment: top-center;"
        return filaVisualizacion
    }
}
