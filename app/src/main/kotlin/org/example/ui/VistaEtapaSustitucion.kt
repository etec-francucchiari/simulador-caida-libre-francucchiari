package org.example

import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.*

class VistaEtapaSustitucion(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val onAvanzar: () -> Unit
) {
    /**
     * Vista de la Etapa 2 del desafío: sustitución numérica y elección de la
     * respuesta final.
     */

    private val entradas = mutableListOf<TextField>()

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()
        val titulo = Label("Etapa 2 · Sustituye los valores y elige el resultado")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 16px; -fx-font-weight: bold;"
        val formula = Label("Fórmula elegida:  ${desafio.ejercicio().formulaSimbolica()}")
        formula.style = "-fx-text-fill: #1d4ed8; -fx-font-family: Monospaced; -fx-font-size: 14px;"
        val instruccion = Label("Escribe el valor numérico correcto para cada variable de la fórmula.")
        instruccion.isWrapText = true
        instruccion.style = "-fx-text-fill: #475569;"
        val cuadricula = construirCuadricula()
        val botonComprobar = fabrica.botonPrimario("Comprobar sustitución")
        val feedbackSustitucion = fabrica.etiquetaFeedback()
        botonComprobar.setOnAction {
            val valores = entradas.map { fabrica.interpretarNumero(it.text) }
            fabrica.aplicarFeedback(feedbackSustitucion, desafio.comprobarSustitucion(valores))
        }
        columna.children.addAll(titulo, formula, instruccion, cuadricula, botonComprobar, feedbackSustitucion)
        agregarSeccionRespuestas(columna)
        return columna
    }

    private fun construirCuadricula(): GridPane {
        entradas.clear()
        val cuadricula = GridPane()
        cuadricula.hgap = 12.0
        cuadricula.vgap = 8.0
        desafio.camposSustitucion().forEachIndexed { indice, campo ->
            val etiqueta = Label("${campo.simbolo()}  (${campo.descripcion()}) [${campo.unidad()}]")
            etiqueta.style = "-fx-text-fill: #334155;"
            val entrada = TextField()
            entrada.promptText = campo.simbolo()
            entrada.prefColumnCount = 10
            entradas.add(entrada)
            cuadricula.add(etiqueta, 0, indice)
            cuadricula.add(entrada, 1, indice)
        }
        return cuadricula
    }

    private fun agregarSeccionRespuestas(columna: VBox) {
        val tituloRespuestas = Label("Selecciona la respuesta final:")
        tituloRespuestas.style = "-fx-text-fill: #1e293b; -fx-font-size: 14px; -fx-font-weight: bold;"
        columna.children.add(tituloRespuestas)
        val feedbackRespuesta = fabrica.etiquetaFeedback()
        desafio.opcionesRespuesta().forEachIndexed { indice, opcion ->
            val boton = Button(opcion.texto())
            boton.prefWidth = 740.0
            boton.style = "${fabrica.estiloOpcion()} -fx-alignment: center-left; -fx-font-size: 14px; -fx-font-weight: bold;"
            boton.setOnAction {
                val resultado = desafio.responder(indice)
                if (resultado.esCorrecta()) onAvanzar() else fabrica.aplicarFeedback(feedbackRespuesta, resultado)
            }
            columna.children.add(boton)
        }
        columna.children.add(feedbackRespuesta)
    }
}
