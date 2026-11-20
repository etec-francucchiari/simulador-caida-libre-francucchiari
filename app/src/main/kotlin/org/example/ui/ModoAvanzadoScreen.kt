package org.example

import javafx.scene.control.ScrollPane
import javafx.scene.layout.*

class ModoAvanzadoScreen(
    componentes: FabricaComponentesAvanzados
) : BorderPane() {
    /**
     * Pantalla del Modo Avanzado: desafío interactivo de cálculo manual más
     * exportación de resultados.
     *
     * Orquesta las tres etapas del desafío delegando en componentes inyectados
     * por constructor cumpliendo SRP, bajo acoplamiento (CBO) y DIP.
     */

    private val vista = Pair(
        Triple(
            componentes,
            EncabezadoDesafioAvanzado(componentes.nucleo().desafio(), componentes.controles()),
            VBox(14.0)
        ),
        Triple(
            componentes.crearSelectorEtapa(),
            componentes.crearSincronizador(),
            arrayOfNulls<BarraExportacion>(1)
        )
    )

    /** Configura las barras de la pantalla y vincula la acción de cambio de modo. */
    fun configurar(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        val nuc = vista.first.first.nucleo()
        val exportacion = FabricaBarraExportacion(nuc.contexto(), nuc.calculo()).crear { scene?.window }
        vista.second.third[0] = exportacion
        style = "-fx-background-color: #f8fafc;"
        vista.first.second.configurarAcciones({ iniciarNuevoEjercicio() }, onCambiarModo)
        top = vista.first.second
        center = construirCentro()
        bottom = vista.second.second.crearBarraControles()
        vista.second.second.sincronizar()
        refrescar()
        return this
    }

    private fun construirCentro(): ScrollPane {
        vista.first.third.style = "-fx-padding: 18; -fx-alignment: top-center;"
        val scroll = ScrollPane(vista.first.third)
        scroll.isFitToWidth = true
        scroll.style = "-fx-background: #f8fafc; -fx-background-color: #f8fafc;"
        return scroll
    }

    private fun refrescar() {
        vista.first.second.actualizar()
        vista.first.third.children.setAll(listOf(vista.second.first.construir { refrescar() }))
    }

    private fun iniciarNuevoEjercicio() {
        vista.second.second.iniciarNuevoEjercicio()
        vista.second.third[0]?.reiniciarMensaje()
        refrescar()
    }

    /** Reaplica al estado de la simulación el escenario del ejercicio activo. */
    fun sincronizarEscenario() {
        vista.second.second.sincronizar()
    }
}
