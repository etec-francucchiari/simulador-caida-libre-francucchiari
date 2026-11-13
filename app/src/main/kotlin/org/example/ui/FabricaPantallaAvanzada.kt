package org.example

class FabricaPantallaAvanzada(
    private val nucleo: NucleoSimulacion,
    private val fabricaControles: FabricaControlesUi
) {
    /**
     * Fábrica que ensambla la pantalla completa del Modo Avanzado inyectando
     * todos sus colaboradores (DIP).
     */

    /**
     * Construye una instancia configurada de [ModoAvanzadoScreen] lista para
     * presentarse en el contenedor raíz de navegación.
     *
     * @param onCambiarModo acción para regresar a la selección de modo.
     */
    fun crear(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        val pantalla = ModoAvanzadoScreen(
            contexto = nucleo.contexto(),
            control = nucleo.control(),
            desafio = nucleo.desafio(),
            componentes = crearComponentes()
        )
        return pantalla.configurar(nucleo.calculo(), onCambiarModo)
    }

    /**
     * Construye la fábrica de componentes específicos del desafío avanzado.
     */
    private fun crearComponentes(): FabricaComponentesAvanzados {
        return FabricaComponentesAvanzados(
            contexto = nucleo.contexto(),
            control = nucleo.control(),
            desafio = nucleo.desafio(),
            renderizador = nucleo.crearRenderizador(),
            fabricaControles = fabricaControles
        )
    }
}
