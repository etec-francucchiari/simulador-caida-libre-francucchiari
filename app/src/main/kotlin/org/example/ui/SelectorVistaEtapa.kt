package org.example

import javafx.scene.Node

class SelectorVistaEtapa(
    desafio: DesafioAvanzado,
    fabrica: FabricaControlesUi,
    nodoAnimacion: Node,
    proveedor: ProveedorVistaEtapa = CreadorVistaEtapaInteractiva(desafio, fabrica)
) {
    /**
     * Resuelve y construye el componente visual correspondiente a la etapa
     * activa del desafío interactivo (SRP / DIP).
     */

    private val estadoSelector = Pair(
        Triple(desafio, fabrica, nodoAnimacion),
        proveedor
    )

    /**
     * Construye el nodo visual para la etapa actual del desafío e inyecta el
     * callback de avance hacia la siguiente etapa.
     */
    fun construir(alAvanzar: () -> Unit): Node {
        val etapa = estadoSelector.first.first.etapa()
        return when {
            etapa.esFormula() -> estadoSelector.second.crearVistaFormula(alAvanzar)
            etapa.esSustitucion() -> estadoSelector.second.crearVistaSustitucion(alAvanzar)
            else -> VistaEtapaValidacion(estadoSelector.first.first, estadoSelector.first.second, estadoSelector.first.third).construir()
        }
    }

    /**
     * Indica si la etapa actual corresponde a la validación experimental final.
     */
    fun esEtapaValidacion(): Boolean {
        return estadoSelector.first.first.etapa().esValidacion()
    }
}
