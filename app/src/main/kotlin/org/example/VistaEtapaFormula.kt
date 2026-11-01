package org.example

import javafx.scene.Node
import javafx.scene.control.*

class VistaEtapaFormula(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val onAvanzar: () -> Unit
) {
    /**
     * Vista de la Etapa 1 del desafío: selección de la fórmula correcta.
     *
     * Recibe por constructor el contrato [DesafioAvanzado], la fábrica de
     * controles y una acción a ejecutar cuando el usuario acierta.
     */

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()
        val titulo = Label("Etapa 1 · Elige la fórmula correcta")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 16px; -fx-font-weight: bold;"
        val instruccion = Label("Objetivo: ${desafio.ejercicio().objetivo().descripcion()}.")
        instruccion.isWrapText = true
        instruccion.style = "-fx-text-fill: #475569;"
        val etiquetaFeedback = fabrica.etiquetaFeedback()
        columna.children.addAll(titulo, instruccion)
        desafio.opcionesFormula().forEachIndexed { indice, opcion ->
            columna.children.add(crearBotonOpcion(indice, opcion, etiquetaFeedback))
        }
        columna.children.add(etiquetaFeedback)
        return columna
    }

    private fun crearBotonOpcion(indice: Int, opcion: OpcionFormula, etiquetaFeedback: Label): Button {
        val boton = Button(opcion.texto())
        boton.prefWidth = 740.0
        boton.style = "${fabrica.estiloOpcion()} -fx-alignment: center-left; -fx-font-family: Monospaced; -fx-font-size: 14px;"
        boton.setOnAction {
            val retroalimentacion = desafio.elegirFormula(indice)
            if (retroalimentacion.esCorrecta()) {
                onAvanzar()
            } else {
                fabrica.aplicarFeedback(etiquetaFeedback, retroalimentacion)
            }
        }
        return boton
    }
}
