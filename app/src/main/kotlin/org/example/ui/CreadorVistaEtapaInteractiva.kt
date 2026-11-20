package org.example

import javafx.scene.Node

class CreadorVistaEtapaInteractiva(
    desafio: DesafioAvanzado,
    fabrica: FabricaControlesUi
) : ProveedorVistaEtapa {
    /**
     * Implementación del proveedor de vistas de las dos primeras etapas del
     * desafío interactivo ([VistaEtapaFormula] y [VistaEtapaSustitucion]).
     *
     * Extraído de [SelectorVistaEtapa] para desacoplar la instanciación de las
     * vistas concretas por etapa (SRP / DIP).
     */

    private val base = Pair(desafio, fabrica)

    override fun crearVistaFormula(alAvanzar: () -> Unit): Node {
        return VistaEtapaFormula(base.first, base.second, alAvanzar).construir()
    }

    override fun crearVistaSustitucion(alAvanzar: () -> Unit): Node {
        return VistaEtapaSustitucion(base.first, base.second, alAvanzar).construir()
    }
}
