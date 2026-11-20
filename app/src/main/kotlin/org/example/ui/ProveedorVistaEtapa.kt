package org.example

import javafx.scene.Node

interface ProveedorVistaEtapa {
    /**
     * Contrato para la construcción de las vistas interactivas de selección de
     * fórmula y sustitución numérica del desafío avanzado (DIP).
     */

    fun crearVistaFormula(alAvanzar: () -> Unit): Node

    fun crearVistaSustitucion(alAvanzar: () -> Unit): Node
}
