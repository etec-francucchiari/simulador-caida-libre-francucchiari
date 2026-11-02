package org.example

import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*

class ModoAvanzadoScreen(
    private val encabezado: EncabezadoDesafioAvanzado,
    private val selectorEtapa: SelectorVistaEtapa,
    private val sincronizador: SincronizadorEscenarioDesafio,
    private val barraExportacion: BarraExportacion,
    private val barraControles: Node,
    onCambiarModo: () -> Unit
) : BorderPane() {
    /**
     * Pantalla del Modo Avanzado: desafío interactivo de cálculo manual más
     * exportación de resultados.
     *
     * Orquesta las tres etapas del desafío delegando en componentes inyectados
     * por constructor ([EncabezadoDesafioAvanzado], [SelectorVistaEtapa],
     * [SincronizadorEscenarioDesafio], [BarraExportacion]) cumpliendo SRP y DIP.
     */

    private val contenedorContenido = VBox(14.0)

    init {
        inicializar(onCambiarModo)
    }

    private fun inicializar(onCambiarModo: () -> Unit) {
        style = "-fx-background-color: #f8fafc;"
        encabezado.configurarAcciones({ iniciarNuevoEjercicio() }, onCambiarModo)
        top = encabezado
        center = construirCentro()
        bottom = barraControles
        sincronizador.sincronizar()
        refrescar()
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
        barraExportacion.reiniciarMensaje()
        refrescar()
    }

    /** Reaplica al estado de la simulación el escenario del ejercicio activo. */
    fun sincronizarEscenario() {
        sincronizador.sincronizar()
    }
}
