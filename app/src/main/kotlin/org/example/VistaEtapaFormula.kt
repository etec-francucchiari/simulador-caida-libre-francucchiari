package org.example

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Vista de la Etapa 1 del desafío: selección de la fórmula correcta.
 *
 * Recibe por constructor el contrato [DesafioAvanzado], la fábrica de
 * controles y una acción a ejecutar cuando el usuario acierta.
 */
class VistaEtapaFormula(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val onAvanzar: () -> Unit
) {

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()

        val titulo = Label("Etapa 1 · Elige la fórmula correcta")
        titulo.font = Font.font("System", FontWeight.BOLD, 16.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val instruccion = Label(
            "Objetivo: ${desafio.ejercicio().objetivo().descripcion()}."
        )
        instruccion.isWrapText = true
        instruccion.style = "-fx-text-fill: #475569;"

        val etiquetaFeedback = fabrica.etiquetaFeedback()

        columna.children.addAll(titulo, instruccion)

        desafio.opcionesFormula().forEachIndexed { indice, opcion ->
            val boton = Button(opcion.texto())
            boton.maxWidth = Double.MAX_VALUE
            boton.alignment = Pos.CENTER_LEFT
            boton.font = Font.font("Monospaced", 14.0)
            boton.style = fabrica.estiloOpcion()
            boton.setOnAction {
                val retroalimentacion = desafio.elegirFormula(indice)
                if (retroalimentacion.esCorrecta()) {
                    onAvanzar()
                } else {
                    fabrica.aplicarFeedback(etiquetaFeedback, retroalimentacion)
                }
            }
            columna.children.add(boton)
        }

        columna.children.add(etiquetaFeedback)
        return columna
    }
}
