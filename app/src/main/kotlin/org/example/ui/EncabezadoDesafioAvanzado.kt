package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class EncabezadoDesafioAvanzado(
    desafio: DesafioAvanzado,
    fabrica: FabricaControlesUi
) : VBox(8.0) {
    /**
     * Cabecera de la pantalla del Modo Avanzado con título, botones de acción,
     * indicador de etapa actual y tarjeta con el enunciado del problema activo.
     */

    private val vista = Pair(
        desafio,
        Pair(
            Pair(Label(), Label()),
            Pair(fabrica.botonSecundario("Nuevo Ejercicio"), fabrica.botonSecundario("Cambiar Modo"))
        )
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-padding: 14 20; -fx-background-color: #f5f3ff; -fx-border-color: #ddd6fe; -fx-border-width: 0 0 1 0;"
        val fila = HBox(12.0)
        fila.style = "-fx-alignment: center-left;"
        val titulo = Label("Modo Avanzado · Desafío Interactivo")
        titulo.style = "-fx-text-fill: #4c1d95; -fx-font-size: 20px; -fx-font-weight: bold;"
        titulo.prefWidth = 740.0
        val botones = vista.second.second
        val etiquetas = vista.second.first
        fila.children.addAll(titulo, botones.first, botones.second)
        etiquetas.first.style = "-fx-text-fill: #6d28d9; -fx-font-size: 13px; -fx-font-weight: bold;"
        etiquetas.second.isWrapText = true
        etiquetas.second.style = "-fx-text-fill: #1e293b; -fx-font-size: 14px; -fx-background-color: white; " +
            "-fx-padding: 10; -fx-background-radius: 8; -fx-border-color: #e9d5ff; -fx-border-radius: 8;"
        children.addAll(fila, etiquetas.first, etiquetas.second)
    }

    /**
     * Vincula las acciones de generación de un nuevo ejercicio y de regreso a la
     * pantalla de selección de modo.
     */
    fun configurarAcciones(onNuevo: () -> Unit, onCambiarModo: () -> Unit) {
        vista.second.second.first.setOnAction { onNuevo() }
        vista.second.second.second.setOnAction { onCambiarModo() }
    }

    /**
     * Refresca los textos de la etapa activa y del enunciado del ejercicio en curso.
     */
    fun actualizar() {
        vista.second.first.first.text = vista.first.etapa().toString()
        vista.second.first.second.text = vista.first.ejercicio().enunciado()
    }
}
