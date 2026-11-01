package org.example

class EnsambladorAvanzado(
    private val nucleo: NucleoSimulacion
) {
    /**
     * Ensambla la pantalla del Modo Avanzado a partir de las dependencias
     * provistas por el [NucleoSimulacion].
     *
     * Coordina [FabricaComponentesAvanzados], [FabricaBarraExportacion] y
     * [FabricaPantallaAvanzada] para inyectar cada colaborador por constructor (DIP).
     */

    private val fabricaControles = FabricaControles()

    /**
     * Construye la pantalla completa del Modo Avanzado vinculada a la acción de
     * cambio de modo indicada.
     *
     * @param onCambiarModo callback para regresar a la selección de modo.
     */
    fun construir(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        val fabrica = FabricaPantallaAvanzada(
            contexto = nucleo.contexto(),
            control = nucleo.control(),
            desafio = nucleo.desafio(),
            componentes = crearComponentes(),
            fabricaBarra = FabricaBarraExportacion(nucleo.contexto(), nucleo.calculo(), fabricaControles)
        )
        return fabrica.crear(onCambiarModo)
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
