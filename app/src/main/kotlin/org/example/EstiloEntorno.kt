package org.example

import javafx.scene.canvas.GraphicsContext

/**
 * Contrato polimórfico de representación visual de un entorno gravitatorio.
 *
 * Cada entorno concreto aporta su propio fondo y suelo, de modo que el
 * renderizador no necesita comprobaciones de tipo ni `when` sobre planetas
 * (OCP).
 */
interface EstiloEntorno {

    /** Dibuja el fondo ambiental del entorno. */
    fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double)

    /** Dibuja la superficie del suelo del entorno. */
    fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double)
}
