package org.example

import javafx.scene.control.ScrollPane
import javafx.scene.layout.*

class ModoAvanzadoScreen(
    private val contexto: ContextoSimulacion,
    private val control: ControlReproduccion,
    desafio: DesafioAvanzado,
    private val componentes: FabricaComponentesAvanzados
) : BorderPane() {
    /**
     * Pantalla del Modo Avanzado: desafío interactivo de cálculo manual más
     * exportación de resultados.
     *
     * Orquesta las tres etapas del desafío delegando en componentes inyectados
     * por constructor cumpliendo SRP y DIP.
     */

    private val contenedorContenido = VBox(14.0)
    private val encabezado = EncabezadoDesafioAvanzado(desafio, componentes.controles())
    private val selectorEtapa = componentes.crearSelectorEtapa()
    private val sincronizador = componentes.crearSincronizador()
    private var barraExportacion: BarraExportacion? = null

    /** Configura las barras de la pantalla y vincula la acción de cambio de modo. */
    fun configurar(calculo: CalculadoraCaidaLibre, onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        val exportacion = FabricaBarraExportacion(contexto, calculo, componentes.controles()).crear { scene?.window }
        barraExportacion = exportacion
        style = "-fx-background-color: #f8fafc;"
        encabezado.configurarAcciones({ iniciarNuevoEjercicio() }, onCambiarModo)
        top = encabezado
        center = construirCentro()
        bottom = componentes.controles().crearBarraControles(contexto, control)
        sincronizador.sincronizar()
        refrescar()
        return this
    }

    private fun construirCentro(): ScrollPane {
        contenedorContenido.style = "-fx-padding: 18; -fx-alignment: top-center;"
        val scroll = ScrollPane(contenedorContenido)
        scroll.isFitToWidth = true
        scroll.style = "-fx-background: #f8fafc; -fx-background-color: #f8fafc;"
        return scroll
    }

    private fun refrescar() {
        encabezado.actualizar()
        contenedorContenido.children.setAll(listOf(selectorEtapa.construir { refrescar() }))
    }

    private fun iniciarNuevoEjercicio() {
        sincronizador.iniciarNuevoEjercicio()
        barraExportacion?.reiniciarMensaje()
        refrescar()
    }

    /** Reaplica al estado de la simulación el escenario del ejercicio activo. */
    fun sincronizarEscenario() {
        sincronizador.sincronizar()
    }
}
