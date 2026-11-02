package org.example

class FabricaPantallaAvanzada(
    private val contexto: ContextoSimulacion,
    private val control: ControlReproduccion,
    private val desafio: DesafioAvanzado,
    private val componentes: FabricaComponentesAvanzados,
    private val fabricaBarra: FabricaBarraExportacion
) {
    /**
     * Fábrica que ensambla la pantalla completa del Modo Avanzado inyectando
     * todos sus colaboradores (DIP).
     *
     * Separa la construcción de los subcomponentes ([EncabezadoDesafioAvanzado],
     * [SelectorVistaEtapa], [SincronizadorEscenarioDesafio], [BarraExportacion] y
     * [BarraControlesSimulacion]) de la vista [ModoAvanzadoScreen].
     */

    /**
     * Construye una instancia configurada de [ModoAvanzadoScreen] lista para
     * presentarse en el contenedor raíz de navegación.
     *
     * @param onCambiarModo acción para regresar a la selección de modo.
     */
    fun crear(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        lateinit var pantalla: ModoAvanzadoScreen
        val barraExportacion = fabricaBarra.crear { pantalla.scene?.window }
        pantalla = ModoAvanzadoScreen(
            encabezado = crearEncabezado(),
            selectorEtapa = componentes.crearSelectorEtapa(),
            sincronizador = componentes.crearSincronizador(),
            barraExportacion = barraExportacion,
            barraControles = BarraControlesSimulacion(contexto, control),
            onCambiarModo = onCambiarModo
        )
        return pantalla
    }

    /**
     * Construye el encabezado interactivo del desafío avanzado.
     */
    private fun crearEncabezado(): EncabezadoDesafioAvanzado {
        return EncabezadoDesafioAvanzado(desafio, componentes.controles())
    }
}
