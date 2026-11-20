package org.example

import javafx.scene.control.Label
import javafx.scene.layout.VBox

class TarjetaFormulaDidactica(
    titulo: String,
    colorBorde: String
) {
    /**
     * Tarjeta visual reutilizable que muestra una fórmula del MRUV con su
     * reemplazo de variables, cálculo intermedio, resultado y aclaración.
     */

    private val componentes = Pair(
        Array(4) { Label() },
        VBox(5.0)
    )

    init {
        construir(titulo, colorBorde)
    }

    /** Nodo raíz de la tarjeta. */
    fun nodo(): VBox = componentes.second

    /** Actualiza todos los textos con el detalle indicado. */
    fun actualizar(detalle: DetallePasoFormula) {
        val etiquetas = componentes.first
        etiquetas[0].text = detalle.formulaConValores()
        etiquetas[1].text = "Paso 1: ${detalle.sustitucionInstante()}"
        etiquetas[2].text = "Paso 2: ${detalle.evaluacionIntermedia()}"
        etiquetas[3].text = "Resultado: ${detalle.resultadoFinal()}"
    }

    private fun construir(titulo: String, colorBorde: String) {
        val contenedor = componentes.second
        val etiquetas = componentes.first
        contenedor.style = "-fx-padding: 8 10 8 12; -fx-background-color: #f8fafc; -fx-background-radius: 6; " +
            "-fx-border-color: $colorBorde; -fx-border-width: 0 0 0 4; -fx-border-radius: 0 6 6 0;"
        val labelTitulo = Label(titulo)
        labelTitulo.style = "-fx-text-fill: #0f172a; -fx-font-size: 13px; -fx-font-weight: bold;"
        val prefijoValores = Label("• Reemplazo con tus parámetros:")
        prefijoValores.style = "-fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold;"
        val prefijoCalculo = Label("• Cálculo numérico en el instante actual:")
        prefijoCalculo.style = "-fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold;"
        etiquetas[0].style = "-fx-font-family: Monospaced; -fx-font-size: 11.5px; -fx-text-fill: #0369a1; -fx-padding: 0 0 0 10;"
        etiquetas[1].style = "-fx-font-family: Monospaced; -fx-font-size: 11px; -fx-text-fill: #334155; -fx-padding: 0 0 0 10;"
        etiquetas[2].style = "-fx-font-family: Monospaced; -fx-font-size: 11px; -fx-text-fill: #334155; -fx-padding: 0 0 0 10;"
        etiquetas[3].style = "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #166534; -fx-padding: 0 0 0 10;"
        val bloqueValores = VBox(2.0, prefijoValores, etiquetas[0])
        val bloqueCalculo = VBox(2.0, prefijoCalculo, etiquetas[1], etiquetas[2], etiquetas[3])
        contenedor.children.addAll(labelTitulo, bloqueValores, bloqueCalculo)
    }
}
