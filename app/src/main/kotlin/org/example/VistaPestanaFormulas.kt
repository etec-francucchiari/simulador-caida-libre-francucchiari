package org.example

import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox

class VistaPestanaFormulas(
    private val contexto: ContextoSimulacion,
    private val calculo: CalculadoraCaidaLibre,
    private val configuracion: ConfiguracionSimulacion
) {
    /**
     * Construye el contenido desplazable de la pestaña didáctica de fórmulas paso a paso
     * del Modo Principiante.
     *
     * Recibe por constructor únicamente abstracciones ([ContextoSimulacion],
     * [CalculadoraCaidaLibre] y [ConfiguracionSimulacion]) cumpliendo con el principio de
     * inversión de dependencias (DIP) y liberando a [PrincipianteSimulationScreen]
     * de conocer los paneles internos de configuración y desglose algebraico.
     */

    /**
     * Construye y devuelve el contenedor con desplazamiento vertical para la pestaña
     * de fórmulas y configuración de parámetros iniciales.
     */
    fun construir(): ScrollPane {
        val scroll = ScrollPane(construirContenedor())
        scroll.isFitToWidth = true
        scroll.style = "-fx-background-color: #ffffff; -fx-background: #ffffff;"
        return scroll
    }

    /**
     * Ensambla verticalmente el panel de parámetros iniciales y el panel de
     * explicaciones físicas paso a paso.
     */
    private fun construirContenedor(): VBox {
        val panelParametros = PanelParametrosIniciales(contexto, configuracion)
        val panelExplicacion = PanelExplicacionFisica(contexto, calculo)
        val contenedor = VBox(16.0, panelParametros, panelExplicacion)
        contenedor.style = "-fx-padding: 18; -fx-alignment: top-center;"
        contenedor.maxWidth = 720.0
        return contenedor
    }
}
