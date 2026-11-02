package org.example

import javafx.scene.canvas.GraphicsContext

interface EstiloEntorno {
    /**
     * Contrato polimórfico de representación visual de un entorno gravitatorio
     * sobre el lienzo de animación (Principio Abierto/Cerrado - OCP).
     *
     * Cada planeta o entorno concreto ([EstiloTierra], [EstiloLuna],
     * [EstiloMarte], [EstiloJupiter], [EstiloNeutro]) implementa este contrato
     * aportando su propio gradiente atmosférico, cuerpos celestes decorativos y
     * textura de superficie, evitando condicionales por tipo en el renderizador.
     */

    /**
     * Pinta el fondo ambiental (cielo, atmósfera o espacio exterior) sobre el
     * contexto gráfico [gc] cubriendo las dimensiones [ancho] × [alto].
     */
    fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double)

    /**
     * Pinta la franja de superficie y la línea de nivel cero (`y = 0 m`) a partir
     * de la coordenada vertical [ySuelo] hasta el borde inferior [alto].
     */
    fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double)
}
