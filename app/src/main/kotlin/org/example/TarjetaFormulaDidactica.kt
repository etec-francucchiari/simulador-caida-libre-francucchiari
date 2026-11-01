package org.example

import javafx.scene.control.Label
import javafx.scene.layout.VBox

class TarjetaFormulaDidactica(
    private val titulo: String,
    private val colorBorde: String
) {
    /**
     * Tarjeta visual reutilizable que muestra una fórmula del MRUV con su
     * reemplazo de variables, cálculo intermedio, resultado y aclaración.
     */

    private val etiquetaValores = Label()
    private val etiquetaSustitucion = Label()
    private val etiquetaIntermedia = Label()
    private val etiquetaResultado = Label()
    private val tarjeta: VBox = construir()

    /** Nodo raíz de la tarjeta. */
    fun nodo(): VBox = tarjeta

    /** Actualiza todos los textos con el detalle indicado. */
    fun actualizar(detalle: DetallePasoFormula) {
        etiquetaValores.text = detalle.formulaConValores()
        etiquetaSustitucion.text = "Paso 1: ${detalle.sustitucionInstante()}"
        etiquetaIntermedia.text = "Paso 2: ${detalle.evaluacionIntermedia()}"
        etiquetaResultado.text = "Resultado: ${detalle.resultadoFinal()}"
    }

    private fun construir(): VBox {
        val contenedor = VBox(5.0)
        contenedor.style = "-fx-padding: 8 10 8 12; -fx-background-color: #f8fafc; -fx-background-radius: 6; " +
            "-fx-border-color: $colorBorde; -fx-border-width: 0 0 0 4; -fx-border-radius: 0 6 6 0;"
        val labelTitulo = Label(titulo)
        labelTitulo.style = "-fx-text-fill: #0f172a; -fx-font-size: 13px; -fx-font-weight: bold;"
        val prefijoValores = Label("• Reemplazo con tus parámetros:")
        prefijoValores.style = "-fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold;"
        val prefijoCalculo = Label("• Cálculo numérico en el instante actual:")
        prefijoCalculo.style = "-fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold;"
        aplicarEstilosEtiquetas()
        val bloqueValores = VBox(2.0, prefijoValores, etiquetaValores)
        val bloqueCalculo = VBox(2.0, prefijoCalculo, etiquetaSustitucion, etiquetaIntermedia, etiquetaResultado)
        contenedor.children.addAll(labelTitulo, bloqueValores, bloqueCalculo)
        return contenedor
    }

    private fun aplicarEstilosEtiquetas() {
        etiquetaValores.style = "-fx-font-family: Monospaced; -fx-font-size: 11.5px; -fx-text-fill: #0369a1; -fx-padding: 0 0 0 10;"
        etiquetaSustitucion.style = "-fx-font-family: Monospaced; -fx-font-size: 11px; -fx-text-fill: #334155; -fx-padding: 0 0 0 10;"
        etiquetaIntermedia.style = "-fx-font-family: Monospaced; -fx-font-size: 11px; -fx-text-fill: #334155; -fx-padding: 0 0 0 10;"
        etiquetaResultado.style = "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #166534; -fx-padding: 0 0 0 10;"
    }
}
