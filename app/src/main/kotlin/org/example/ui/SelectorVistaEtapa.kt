package org.example

import javafx.scene.Node

class SelectorVistaEtapa(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val nodoAnimacion: Node
) {
    /**
     * Resuelve y construye el componente visual correspondiente a la etapa
     * activa del desafío interactivo (SRP / DIP).
     *
     * Desacopla a [ModoAvanzadoScreen] de las vistas concretas de cada etapa
     * ([VistaEtapaFormula], [VistaEtapaSustitucion] y [VistaEtapaValidacion]),
     * consultando los predicados de dominio de [EtapaDesafio].
     */

    /**
     * Construye el nodo visual para la etapa actual del desafío e inyecta el
     * callback de avance hacia la siguiente etapa.
     *
     * @param alAvanzar acción invocada cuando el estudiante supera la etapa activa.
     */
    fun construir(alAvanzar: () -> Unit): Node {
        val etapa = desafio.etapa()
        return when {
            etapa.esFormula() -> VistaEtapaFormula(desafio, fabrica, alAvanzar).construir()
            etapa.esSustitucion() -> VistaEtapaSustitucion(desafio, fabrica, alAvanzar).construir()
            else -> VistaEtapaValidacion(desafio, fabrica, nodoAnimacion).construir()
        }
    }

    /**
     * Indica si la etapa actual corresponde a la validación experimental final.
     */
    fun esEtapaValidacion(): Boolean {
        return desafio.etapa().esValidacion()
    }
}
