package org.example

class FabricaComponentesAvanzados(
    private val contexto: ContextoSimulacion,
    private val control: ControlReproduccion,
    private val desafio: DesafioAvanzado,
    private val renderizador: RenderizadorAnimacionCaida,
    private val fabricaControles: FabricaControlesUi
) {
    /**
     * Fábrica de los colaboradores de selección de etapa y sincronización de
     * escenario del Modo Avanzado.
     *
     * Agrupa las dependencias del desafío interactivo para simplificar el
     * ensamblado en [FabricaPantallaAvanzada] manteniendo bajo el acoplamiento (CBO).
     */

    /**
     * Construye el [SelectorVistaEtapa] junto con el lienzo de animación que se
     * exhibe en la tercera etapa de validación experimental.
     */
    fun crearSelectorEtapa(): SelectorVistaEtapa {
        val animacion = PanelAnimacionCaida(contexto, renderizador)
        return SelectorVistaEtapa(desafio, fabricaControles, animacion)
    }

    /**
     * Construye el [SincronizadorEscenarioDesafio] encargado de reflejar los datos
     * del ejercicio activo en el contexto físico.
     */
    fun crearSincronizador(): SincronizadorEscenarioDesafio {
        return SincronizadorEscenarioDesafio(desafio, contexto, control)
    }

    /**
     * Devuelve la fábrica de controles UI asociada a los componentes del desafío.
     */
    fun controles(): FabricaControlesUi = fabricaControles
}
