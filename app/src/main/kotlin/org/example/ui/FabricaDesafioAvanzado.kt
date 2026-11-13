package org.example

class FabricaDesafioAvanzado(
    private val catalogo: CatalogoEntornos,
    private val calculo: CalculadoraCaidaLibre
) {
    /**
     * Fábrica que ensambla el controlador del desafío interactivo del Modo Avanzado
     * inyectando sus tres generadores de dominio (DIP).
     *
     * Aísla la instanciación de [GeneradorEjercicios], [GeneradorOpcionesFormula]
     * y [GeneradorOpcionesRespuesta] para mantener bajo el acoplamiento (CBO) en
     * el núcleo de composición.
     */

    /**
     * Construye una instancia configurada de [DesafioAvanzado] lista para gestionar
     * el flujo de tres etapas del desafío.
     */
    fun crear(): DesafioAvanzado {
        return ControladorDesafio(
            generadorEjercicios = crearGeneradorEjercicios(),
            generadorOpcionesFormula = GeneradorOpcionesFormula(),
            generadorOpcionesRespuesta = GeneradorOpcionesRespuesta()
        )
    }

    /**
     * Construye el generador de ejercicios aleatorios con el catálogo de entornos
     * y el servicio de cálculo físico inyectados.
     */
    private fun crearGeneradorEjercicios(): GeneradorEjercicios {
        return GeneradorEjercicios(catalogo, calculo)
    }
}
