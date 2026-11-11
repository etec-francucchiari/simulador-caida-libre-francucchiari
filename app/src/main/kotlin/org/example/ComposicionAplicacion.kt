package org.example

class ComposicionAplicacion(
    private val nucleo: NucleoSimulacion
) : ServiciosAplicacion {
    /**
     * Raíz de composición (`Composition Root`) de la aplicación JavaFX.
     *
     * Implementa el contrato [ServiciosAplicacion] coordinando las instancias
     * centrales contenidas en [NucleoSimulacion] junto con los ensambladores
     * especializados de cada modalidad ([EnsambladorPrincipiante] y
     * [EnsambladorAvanzado]), garantizando que todas las pantallas y vistas
     * reciban sus colaboradores por constructor (Principio de Inversión de
     * Dependencias - DIP).
     */

    private val ensambladorPrincipiante = EnsambladorPrincipiante(nucleo)
    private val ensambladorAvanzado = EnsambladorAvanzado(nucleo)

    /**
     * Expone el catálogo de entornos gravitatorios del núcleo.
     */
    override fun catalogo() = nucleo.catalogo()

    /**
     * Expone el contexto reactivo de estado y comandos del núcleo.
     */
    override fun contexto() = nucleo.contexto()

    /**
     * Expone el controlador de reproducción temporal del núcleo.
     */
    override fun control() = nucleo.control()

    /**
     * Expone el controlador del desafío interactivo del Modo Avanzado.
     */
    override fun desafio() = nucleo.desafio()

    /**
     * Delega en [EnsambladorPrincipiante] la construcción de la pantalla de
     * simulación guiada del Modo Principiante.
     */
    override fun crearPantallaPrincipiante(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): PrincipianteSimulationScreen {
        return ensambladorPrincipiante.construir(onCambiarPlaneta, onCambiarModo)
    }

    /**
     * Delega en [EnsambladorAvanzado] la construcción de la pantalla de desafío
     * interactivo del Modo Avanzado.
     */
    override fun crearPantallaAvanzada(onCambiarModo: () -> Unit): ModoAvanzadoScreen {
        return ensambladorAvanzado.construir(onCambiarModo)
    }
}
