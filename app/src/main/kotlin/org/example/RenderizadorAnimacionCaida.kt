package org.example

import javafx.scene.canvas.GraphicsContext

/**
 * Contrato del renderizador de la animación de caída libre (DIP).
 *
 * El panel visual depende de esta abstracción y no de la implementación
 * concreta JavaFX, lo que permite sustituirla en pruebas o reutilizarla.
 */
interface RenderizadorAnimacionCaida {

    /** Dibuja un fotograma completo sobre el contexto gráfico. */
    fun dibujar(gc: GraphicsContext, fotograma: FotogramaCaida)

    /** Describe el entorno activo para el subtítulo del panel. */
    fun descripcionEntorno(fotograma: FotogramaCaida): String
}
