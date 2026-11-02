package org.example

import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.*

class VistaEtapaValidacion(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val nodoAnimacion: Node
) {
    /**
     * Vista de la Etapa 3 del desafío: resultado validado y simulación en vivo.
     */

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()
        val titulo = Label("Etapa 3 · ¡Ejercicio resuelto! 🎉")
        titulo.style = "-fx-text-fill: #166534; -fx-font-size: 16px; -fx-font-weight: bold;"
        val contenedorAnimacion = HBox(nodoAnimacion)
        contenedorAnimacion.style = "-fx-alignment: center;"
        columna.children.addAll(titulo, construirTarjetaResumen(), contenedorAnimacion)
        return columna
    }

    private fun construirTarjetaResumen(): VBox {
        val ejercicio = desafio.ejercicio()
        val respuestaCorrecta = desafio.opcionesRespuesta().firstOrNull { it.esCorrecta() }
        val valorTexto = respuestaCorrecta?.texto() ?: ejercicio.textoResultado()
        val tarjeta = VBox(6.0)
        tarjeta.style = "-fx-padding: 14; -fx-background-color: #f0fdf4; -fx-background-radius: 10; " +
            "-fx-border-color: #bbf7d0; -fx-border-radius: 10;"
        val lineaObjetivo = Label("Objetivo: ${ejercicio.objetivo().descripcion()}.")
        val lineaResultado = Label("Resultado correcto: $valorTexto")
        lineaResultado.style = "-fx-text-fill: #166534; -fx-font-size: 15px; -fx-font-weight: bold;"
        val lineaFormula = Label("Fórmula aplicada: ${ejercicio.formulaSimbolica()}")
        lineaFormula.style = "-fx-font-family: Monospaced; -fx-font-size: 13px;"
        val lineaDesarrollo = Label("Sustitución: ${ejercicio.expresionConValores()}")
        lineaDesarrollo.style = "-fx-font-family: Monospaced; -fx-font-size: 13px;"
        lineaDesarrollo.isWrapText = true
        lineaDesarrollo.maxWidth = 720.0
        tarjeta.children.addAll(lineaObjetivo, lineaResultado, lineaFormula, lineaDesarrollo)
        return tarjeta
    }
}
