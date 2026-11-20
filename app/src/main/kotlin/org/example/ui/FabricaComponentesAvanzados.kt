package org.example

class FabricaComponentesAvanzados(
    nucleo: ProveedorNucleoSimulacion,
    fabricaControles: FabricaControlesUi
) {
    /**
     * Fábrica de los colaboradores de selección de etapa y sincronización de
     * escenario del Modo Avanzado.
     *
     * Agrupa las dependencias del desafío interactivo para simplificar el
     * ensamblado en [FabricaPantallaAvanzada] manteniendo bajo el acoplamiento (CBO).
     */

    private val base = Pair(nucleo, fabricaControles)

    /**
     * Construye el [SelectorVistaEtapa] junto con el lienzo de animación que se
     * exhibe en la tercera etapa de validación experimental.
     */
    fun crearSelectorEtapa(): SelectorVistaEtapa {
        val animacion = base.second.crearPanelAnimacion(base.first.contexto())
        return SelectorVistaEtapa(base.first.desafio(), base.second, animacion)
    }

    /**
     * Construye el [SincronizadorEscenarioDesafio] encargado de reflejar los datos
     * del ejercicio activo en el contexto físico.
     */
    fun crearSincronizador(): SincronizadorEscenarioDesafio {
        return SincronizadorEscenarioDesafio(base.first.desafio(), base.first.contexto(), base.first.control())
    }

    /**
     * Devuelve el proveedor del núcleo asociado a los componentes.
     */
    fun nucleo(): ProveedorNucleoSimulacion = base.first

    /**
     * Devuelve la fábrica de controles UI asociada a los componentes del desafío.
     */
    fun controles(): FabricaControlesUi = base.second
}
