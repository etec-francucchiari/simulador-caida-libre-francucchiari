package org.example

interface ServiciosAplicacion {
    /**
     * Contrato de la fachada de servicios y ensambladores de pantallas de la
     * aplicación (Principio de Inversión de Dependencias - DIP).
     *
     * Permite a [NavegadorPantallas] coordinar las transiciones entre vistas y
     * acceder al catálogo de planetas, al contexto reactivo, al control de
     * reproducción y al desafío avanzado sin acoplarse a [ComposicionAplicacion].
     */

    /**
     * Devuelve el catálogo de entornos gravitatorios disponibles.
     */
    fun catalogo(): CatalogoEntornos

    /**
     * Devuelve el contexto reactivo que unifica lecturas y comandos de estado.
     */
    fun contexto(): ContextoSimulacion

    /**
     * Devuelve el controlador de reproducción temporal de la simulación.
     */
    fun control(): ControlReproduccion

    /**
     * Devuelve el controlador del desafío interactivo del Modo Avanzado.
     */
    fun desafio(): DesafioAvanzado

    /**
     * Construye y devuelve la pantalla completa de simulación del Modo
     * Principiante con los callbacks de navegación indicados.
     */
    fun crearPantallaPrincipiante(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit): PrincipianteSimulationScreen

    /**
     * Construye y devuelve la pantalla interactiva del Modo Avanzado vinculada
     * a la acción de retorno al selector de modo.
     */
    fun crearPantallaAvanzada(onCambiarModo: () -> Unit): ModoAvanzadoScreen
}
