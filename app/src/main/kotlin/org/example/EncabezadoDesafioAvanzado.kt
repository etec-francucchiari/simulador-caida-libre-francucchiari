package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class EncabezadoDesafioAvanzado(
    private val desafio: DesafioAvanzado,
    private val fabrica: FabricaControlesUi
) : VBox(8.0) {
    /**
     * Cabecera de la pantalla del Modo Avanzado con título, botones de acción,
     * indicador de etapa actual y tarjeta con el enunciado del problema activo.
     *
     * Depende por constructor de las abstracciones [DesafioAvanzado] y
     * [FabricaControlesUi] (DIP), manteniendo encapsulados todos sus controles.
     */

    private val etiquetaEtapa = Label()
    private val etiquetaEnunciado = Label()
    private val botonNuevo = fabrica.botonSecundario("Nuevo Ejercicio")
    private val botonCambiar = fabrica.botonSecundario("Cambiar Modo")

    init {
        inicializar()
    }

    /**
     * Construye la jerarquía visual del encabezado y aplica los estilos del tema.
     */
    private fun inicializar() {
        style = "-fx-padding: 14 20; -fx-background-color: #f5f3ff; -fx-border-color: #ddd6fe; -fx-border-width: 0 0 1 0;"
        val fila = HBox(12.0)
        fila.style = "-fx-alignment: center-left;"
        val titulo = Label("Modo Avanzado · Desafío Interactivo")
        titulo.style = "-fx-text-fill: #4c1d95; -fx-font-size: 20px; -fx-font-weight: bold;"
        titulo.prefWidth = 740.0
        fila.children.addAll(titulo, botonNuevo, botonCambiar)
        etiquetaEtapa.style = "-fx-text-fill: #6d28d9; -fx-font-size: 13px; -fx-font-weight: bold;"
        etiquetaEnunciado.isWrapText = true
        etiquetaEnunciado.style = "-fx-text-fill: #1e293b; -fx-font-size: 14px; -fx-background-color: white; " +
            "-fx-padding: 10; -fx-background-radius: 8; -fx-border-color: #e9d5ff; -fx-border-radius: 8;"
        children.addAll(fila, etiquetaEtapa, etiquetaEnunciado)
    }

    /**
     * Vincula las acciones de generación de un nuevo ejercicio y de regreso a la
     * pantalla de selección de modo.
     */
    fun configurarAcciones(onNuevo: () -> Unit, onCambiarModo: () -> Unit) {
        botonNuevo.setOnAction { onNuevo() }
        botonCambiar.setOnAction { onCambiarModo() }
    }

    /**
     * Refresca los textos de la etapa activa y del enunciado del ejercicio en curso.
     */
    fun actualizar() {
        etiquetaEtapa.text = desafio.etapa().toString()
        etiquetaEnunciado.text = desafio.ejercicio().enunciado()
    }
}
