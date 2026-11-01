package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

interface FabricaControlesUi {
    /**
     * Contrato de la fábrica de controles visuales y utilidades de formato
     * compartidas por las vistas de etapa del Modo Avanzado (Principio de
     * Inversión de Dependencias - DIP).
     *
     * Desacopla a [EncabezadoDesafioAvanzado], [VistaEtapaFormula],
     * [VistaEtapaSustitucion], [VistaEtapaValidacion] y [BarraExportacion] de la
     * creación y estilizado manual de botones, contenedores y etiquetas,
     * centralizando esa responsabilidad en [FabricaControles].
     */

    /**
     * Construye un contenedor vertical [VBox] con el espaciado y ancho máximo
     * estandarizados para las tarjetas de etapa del desafío.
     */
    fun columnaBase(): VBox

    /**
     * Construye un botón de acción primaria con el estilo destacado del tema
     * violeta del Modo Avanzado.
     */
    fun botonPrimario(texto: String): Button

    /**
     * Construye un botón de acción secundaria para navegación o reinicio.
     */
    fun botonSecundario(texto: String): Button

    /**
     * Construye una etiqueta [Label] con ajuste de línea preparada para exhibir
     * mensajes de retroalimentación formativa.
     */
    fun etiquetaFeedback(): Label

    /**
     * Actualiza el texto y el color semántico (éxito o error) de la [etiqueta]
     * según el resultado contenido en [retroalimentacion].
     */
    fun aplicarFeedback(etiqueta: Label, retroalimentacion: Retroalimentacion)

    /**
     * Devuelve la cadena CSS reutilizable para estilizar los `RadioButton` de
     * selección de fórmulas y respuestas numéricas.
     */
    fun estiloOpcion(): String

    /**
     * Convierte una cadena de [texto] numérico ingresada por el estudiante en
     * un `Double`, admitiendo tanto punto como coma decimal y devolviendo
     * `Double.NaN` cuando el formato no es válido.
     */
    fun interpretarNumero(texto: String): Double
}
