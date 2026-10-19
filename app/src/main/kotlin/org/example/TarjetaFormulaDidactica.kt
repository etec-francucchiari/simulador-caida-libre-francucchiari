package org.example

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Tarjeta visual reutilizable que muestra una fórmula del MRUV con su
 * reemplazo de variables, cálculo intermedio, resultado y aclaración.
 *
 * Encapsula sus etiquetas y expone [nodo] para insertarse en el panel y
 * [actualizar] para refrescar su contenido.
 */
class TarjetaFormulaDidactica(
    private val titulo: String,
    private val colorBorde: String
) {

    private val etiquetaValores = Label()
    private val etiquetaSustitucion = Label()
    private val etiquetaIntermedia = Label()
    private val etiquetaResultado = Label()
    private val etiquetaAclaracion = Label()

    private val tarjeta: VBox = construir()

    /** Nodo raíz de la tarjeta. */
    fun nodo(): VBox = tarjeta

    /** Actualiza todos los textos con el detalle indicado. */
    fun actualizar(detalle: DetallePasoFormula) {
        etiquetaValores.text = detalle.formulaConValores()
        etiquetaSustitucion.text = "Paso 1: ${detalle.sustitucionInstante()}"
        etiquetaIntermedia.text = "Paso 2: ${detalle.evaluacionIntermedia()}"
        etiquetaResultado.text = "Resultado: ${detalle.resultadoFinal()}"
        etiquetaAclaracion.text = "💡 ${detalle.aclaracionDidactica()}"
    }

    private fun construir(): VBox {
        val contenedor = VBox(5.0)
        contenedor.padding = Insets(8.0, 10.0, 8.0, 12.0)
        contenedor.style = "-fx-background-color: #f8fafc; " +
            "-fx-background-radius: 6; " +
            "-fx-border-color: $colorBorde; " +
            "-fx-border-width: 0 0 0 4; " +
            "-fx-border-radius: 0 6 6 0;"

        val labelTitulo = Label(titulo)
        labelTitulo.font = Font.font("System", FontWeight.BOLD, 13.0)
        labelTitulo.style = "-fx-text-fill: #0f172a;"

        val contenedorValores = VBox(2.0)
        val etiquetaPrefijoValores = Label("• Reemplazo con tus parámetros:")
        etiquetaPrefijoValores.font = Font.font("System", FontWeight.BOLD, 11.0)
        etiquetaPrefijoValores.style = "-fx-text-fill: #475569;"

        etiquetaValores.font = Font.font("Monospaced", 11.5)
        etiquetaValores.style = "-fx-text-fill: #0369a1; -fx-padding: 0 0 0 10;"

        contenedorValores.children.addAll(etiquetaPrefijoValores, etiquetaValores)

        val contenedorCalculo = VBox(2.0)
        val etiquetaPrefijoCalculo = Label("• Cálculo numérico en el instante actual:")
        etiquetaPrefijoCalculo.font = Font.font("System", FontWeight.BOLD, 11.0)
        etiquetaPrefijoCalculo.style = "-fx-text-fill: #475569;"

        etiquetaSustitucion.font = Font.font("Monospaced", 11.0)
        etiquetaSustitucion.style = "-fx-text-fill: #334155; -fx-padding: 0 0 0 10;"

        etiquetaIntermedia.font = Font.font("Monospaced", 11.0)
        etiquetaIntermedia.style = "-fx-text-fill: #334155; -fx-padding: 0 0 0 10;"

        etiquetaResultado.font = Font.font("System", FontWeight.BOLD, 12.0)
        etiquetaResultado.style = "-fx-text-fill: #166534; -fx-padding: 0 0 0 10;"

        contenedorCalculo.children.addAll(
            etiquetaPrefijoCalculo,
            etiquetaSustitucion,
            etiquetaIntermedia,
            etiquetaResultado
        )

        etiquetaAclaracion.font = Font.font("System", 10.5)
        etiquetaAclaracion.style = "-fx-text-fill: #64748b; -fx-wrap-text: true;"

        contenedor.children.addAll(
            labelTitulo,
            contenedorValores,
            contenedorCalculo,
            etiquetaAclaracion
        )

        return contenedor
    }
}
