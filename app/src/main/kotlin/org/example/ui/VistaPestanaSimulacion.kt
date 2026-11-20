package org.example

import javafx.scene.layout.VBox

class VistaPestanaSimulacion(
    estado: EstadoSimulacion,
    calculo: CalculadoraCaidaLibre
) {
    /**
     * Construye el contenido de la pestaña de simulación en tiempo real del Modo
     * Principiante, integrando los valores instantáneos y la fila de visualización
     * cinemática (SRP / DIP).
     */

    private val base = Pair(estado, FilaVisualizacionSimulacion(estado, calculo))

    /**
     * Construye y devuelve el contenedor vertical principal de la pestaña de
     * simulación interactiva.
     */
    fun construir(): VBox {
        val contenedor = VBox(12.0)
        contenedor.style = "-fx-padding: 14;"
        val panelValores = PanelValoresInstantaneos(base.first)
        contenedor.children.addAll(panelValores, base.second.construir())
        return contenedor
    }
}
