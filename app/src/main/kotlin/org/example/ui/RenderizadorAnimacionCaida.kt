package org.example

import javafx.scene.canvas.GraphicsContext

interface RenderizadorAnimacionCaida {
    /**
     * Contrato del renderizador de la animación de caída libre (DIP).
     *
     * El panel visual depende de esta abstracción y no de la implementación
     * concreta JavaFX, lo que permite sustituirla en pruebas o reutilizarla.
     */

    /** Dibuja un fotograma completo sobre el contexto gráfico. */
    fun dibujar(gc: GraphicsContext, fotograma: FotogramaCaida)

    /** Describe el entorno activo para el subtítulo del panel. */
    fun descripcionEntorno(fotograma: FotogramaCaida): String

    /** Construye el fotograma instantáneo a partir del estado reactivo y las dimensiones del lienzo. */
    fun construirFotograma(estado: EstadoSimulacion, ancho: Double, alto: Double): FotogramaCaida =
        FotogramaCaida(
            anchoInterno = ancho,
            altoInterno = alto,
            entornoInterno = estado.obtenerPlaneta(),
            alturaInicialInterna = estado.obtenerAlturaInicial(),
            alturaActualInterna = estado.obtenerAlturaActual(),
            velocidadActualInterna = estado.obtenerVelocidadActual(),
            impactadoInterno = estado.haLlegadoAlSuelo() || estado.obtenerAlturaActual() <= 0.0
        )
}
