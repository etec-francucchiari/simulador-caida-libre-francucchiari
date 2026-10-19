package org.example

/**
 * Raíz de composición de la aplicación.
 *
 * Construye una única vez los servicios instanciables y los expone mediante el
 * contrato [ServiciosAplicacion]. Es el único lugar donde se eligen las
 * implementaciones concretas, aplicando inversión de dependencias (DIP).
 */
class ComposicionAplicacion : ServiciosAplicacion {

    private val configuracionInterna: ConfiguracionSimulacion = ConfiguracionFisica()
    private val calculoInterno: CalculadoraCaidaLibre = CalculoCaidaLibre()
    private val explicadorInterno: ExplicadorFisica = ExplicadorFisicaPasoAPaso(calculoInterno)
    private val validadorInterno: ValidadorParametros =
        ValidadorParametrosIniciales(configuracionInterna)
    private val catalogoInterno: CatalogoEntornos = CatalogoPlanetas()

    private val contextoInterno: ContextoSimulacion =
        GestorEstadoSimulacion(
            configuracionInterna,
            Planeta.TIERRA,
            MotorEstadoCaida(calculoInterno)
        )
    private val controlInterno: ControlReproduccion = ControladorSimulacion(contextoInterno)

    private val desafioInterno: DesafioAvanzado = ControladorDesafio(
        GeneradorEjercicios(catalogoInterno, calculoInterno),
        GeneradorOpcionesFormula(),
        GeneradorOpcionesRespuesta()
    )

    private val catalogoExportadoresInterno: CatalogoDeExportadores =
        CatalogoExportadores(listOf(ExportadorCsv(), ExportadorJson()))
    private val selectorArchivoInterno: SelectorArchivoExportacion = SelectorArchivoJavaFx()
    private val exportacionInterna: ExportacionDatos =
        ServicioExportacion(contextoInterno, calculoInterno)

    override fun configuracion(): ConfiguracionSimulacion = configuracionInterna

    override fun calculo(): CalculadoraCaidaLibre = calculoInterno

    override fun explicador(): ExplicadorFisica = explicadorInterno

    override fun validador(): ValidadorParametros = validadorInterno

    override fun catalogo(): CatalogoEntornos = catalogoInterno

    override fun contexto(): ContextoSimulacion = contextoInterno

    override fun control(): ControlReproduccion = controlInterno

    override fun desafio(): DesafioAvanzado = desafioInterno

    override fun exportacion(): ExportacionDatos = exportacionInterna

    override fun catalogoExportadores(): CatalogoDeExportadores = catalogoExportadoresInterno

    override fun selectorArchivo(): SelectorArchivoExportacion = selectorArchivoInterno
}
