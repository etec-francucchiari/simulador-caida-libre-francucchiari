package org.example

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Vista de la Etapa 2 del desafío: sustitución numérica y elección de la
 * respuesta final.
 */
class VistaEtapaSustitucion(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val onAvanzar: () -> Unit
) {

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()

        val titulo = Label("Etapa 2 · Sustituye los valores y elige el resultado")
        titulo.font = Font.font("System", FontWeight.BOLD, 16.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val formula = Label("Fórmula elegida:  ${desafio.ejercicio().formulaSimbolica()}")
        formula.font = Font.font("Monospaced", 14.0)
        formula.style = "-fx-text-fill: #1d4ed8;"

        val instruccion = Label(
            "Escribe el valor numérico correcto para cada variable de la fórmula."
        )
        instruccion.isWrapText = true
        instruccion.style = "-fx-text-fill: #475569;"

        val campos = desafio.camposSustitucion()
        val entradas = mutableListOf<TextField>()
        val cuadricula = GridPane()
        cuadricula.hgap = 12.0
        cuadricula.vgap = 8.0

        campos.forEachIndexed { indice, campo ->
            val etiqueta = Label("${campo.simbolo()}  (${campo.descripcion()}) [${campo.unidad()}]")
            etiqueta.style = "-fx-text-fill: #334155;"

            val entrada = TextField()
            entrada.promptText = campo.simbolo()
            entrada.prefColumnCount = 10
            entradas.add(entrada)

            cuadricula.add(etiqueta, 0, indice)
            cuadricula.add(entrada, 1, indice)
        }

        val botonComprobar = fabrica.botonPrimario("Comprobar sustitución")
        val etiquetaFeedbackSustitucion = fabrica.etiquetaFeedback()
        botonComprobar.setOnAction {
            val valores = entradas.map { fabrica.interpretarNumero(it.text) }
            fabrica.aplicarFeedback(
                etiquetaFeedbackSustitucion,
                desafio.comprobarSustitucion(valores)
            )
        }

        columna.children.addAll(
            titulo,
            formula,
            instruccion,
            cuadricula,
            botonComprobar,
            etiquetaFeedbackSustitucion,
            Separator()
        )

        val tituloRespuestas = Label("Selecciona la respuesta final:")
        tituloRespuestas.font = Font.font("System", FontWeight.BOLD, 14.0)
        tituloRespuestas.style = "-fx-text-fill: #1e293b;"
        columna.children.add(tituloRespuestas)

        val etiquetaFeedbackRespuesta = fabrica.etiquetaFeedback()

        desafio.opcionesRespuesta().forEachIndexed { indice, opcion ->
            val boton = Button(opcion.texto())
            boton.maxWidth = Double.MAX_VALUE
            boton.alignment = Pos.CENTER_LEFT
            boton.font = Font.font("System", FontWeight.BOLD, 14.0)
            boton.style = fabrica.estiloOpcion()
            boton.setOnAction {
                val retroalimentacion = desafio.responder(indice)
                if (retroalimentacion.esCorrecta()) {
                    onAvanzar()
                } else {
                    fabrica.aplicarFeedback(etiquetaFeedbackRespuesta, retroalimentacion)
                }
            }
            columna.children.add(boton)
        }

        columna.children.add(etiquetaFeedbackRespuesta)
        return columna
    }
}
