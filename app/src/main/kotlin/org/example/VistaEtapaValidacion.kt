package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale

/**
 * Vista de la Etapa 3 del desafío: resultado validado y simulación en vivo.
 */
class VistaEtapaValidacion(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi,
    private val nodoAnimacion: Node
) {

    /** Construye y devuelve el nodo de la etapa. */
    fun construir(): Node {
        val columna = fabrica.columnaBase()

        val titulo = Label("Etapa 3 · ¡Ejercicio resuelto! 🎉")
        titulo.font = Font.font("System", FontWeight.BOLD, 16.0)
        titulo.style = "-fx-text-fill: #166534;"

        val ejercicio = desafio.ejercicio()
        val respuestaCorrecta = desafio.opcionesRespuesta().firstOrNull { it.esCorrecta() }
        val valorTexto = respuestaCorrecta?.texto()
            ?: String.format(
                Locale.ROOT,
                "%.2f %s",
                ejercicio.valorCorrecto(),
                ejercicio.unidadResultado()
            )

        val tarjeta = VBox(6.0)
        tarjeta.padding = Insets(14.0)
        tarjeta.style =
            "-fx-background-color: #f0fdf4; -fx-background-radius: 10; " +
                "-fx-border-color: #bbf7d0; -fx-border-radius: 10;"

        val lineaObjetivo = Label("Objetivo: ${ejercicio.objetivo().descripcion()}.")
        val lineaResultado = Label("Resultado correcto: $valorTexto")
        lineaResultado.font = Font.font("System", FontWeight.BOLD, 15.0)
        lineaResultado.style = "-fx-text-fill: #166534;"
        val lineaFormula = Label("Fórmula aplicada: ${ejercicio.formulaSimbolica()}")
        lineaFormula.font = Font.font("Monospaced", 13.0)

        val lineaDesarrollo = Label("Sustitución: ${ejercicio.expresionConValores()}")
        lineaDesarrollo.font = Font.font("Monospaced", 13.0)
        lineaDesarrollo.isWrapText = true
        lineaDesarrollo.maxWidth = 720.0

        tarjeta.children.addAll(
            lineaObjetivo,
            lineaResultado,
            lineaFormula,
            lineaDesarrollo
        )

        val instruccion = Label(
            "Pulsa «Iniciar Simulación» en la barra inferior para contrastar el " +
                "resultado teórico con la animación física en tiempo real."
        )
        instruccion.isWrapText = true
        instruccion.style = "-fx-text-fill: #475569;"

        val contenedorAnimacion = HBox(nodoAnimacion)
        contenedorAnimacion.alignment = Pos.CENTER

        columna.children.addAll(titulo, tarjeta, instruccion, contenedorAnimacion)
        return columna
    }
}
