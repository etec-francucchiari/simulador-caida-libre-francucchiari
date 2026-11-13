package org.example

class NucleoSimulacion {
    /**
     * Contenedor de las instancias centrales de dominio y estado reactivo de la
     * aplicación.
     *
     * Coordina la creación única de la configuración física, la calculadora de
     * caída libre, el catálogo de entornos, el contexto reactivo, el controlador
     * de reproducción y el desafío avanzado, exponiéndolos mediante métodos de
     * acceso encapsulados para los ensambladores de pantallas y las pruebas.
     */

    private val configuracionInterna = ConfiguracionFisica()
    private val calculoInterno = CalculoCaidaLibre()
    private val catalogoInterno = CatalogoPlanetas()
    private val contextoInterno = FabricaContextoSimulacion(configuracionInterna, calculoInterno)
        .crear(catalogoInterno.predefinidos().first())
    private val controlInterno = ControladorSimulacion(contextoInterno)
    private val desafioInterno = FabricaDesafioAvanzado(catalogoInterno, calculoInterno).crear()

    /**
     * Devuelve la configuración física activa del simulador.
     */
    fun configuracion() = configuracionInterna

    /**
     * Devuelve el servicio de cálculo cinemático de caída libre.
     */
    fun calculo() = calculoInterno

    /**
     * Devuelve el catálogo de entornos gravitatorios disponibles.
     */
    fun catalogo() = catalogoInterno

    /**
     * Devuelve el contexto reactivo de estado y comandos de la simulación.
     */
    fun contexto() = contextoInterno

    /**
     * Devuelve el controlador del bucle de reproducción de la simulación.
     */
    fun control() = controlInterno

    /**
     * Devuelve el controlador del desafío interactivo del Modo Avanzado.
     */
    fun desafio() = desafioInterno

    /**
     * Construye un nuevo renderizador JavaFX con el catálogo de estilos por defecto.
     */
    fun crearRenderizador(): RenderizadorCaidaJavaFx {
        return RenderizadorCaidaJavaFx(CatalogoEstilosEntorno())
    }

    /**
     * Reinicia el controlador de reproducción activo antes de cambiar de pantalla.
     */
    fun reiniciarControl() {
        controlInterno.reiniciar()
    }
}
