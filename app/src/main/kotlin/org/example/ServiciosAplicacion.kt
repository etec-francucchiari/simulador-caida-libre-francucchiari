package org.example

/**
 * Contrato de acceso a los servicios de la aplicación (DIP).
 *
 * La raíz de composición construye las implementaciones concretas y el
 * navegador de pantallas consume únicamente esta abstracción, de modo que la
 * navegación no queda acoplada a las clases concretas de infraestructura.
 */
interface ServiciosAplicacion {

    /** Configuración física del simulador. */
    fun configuracion(): ConfiguracionSimulacion

    /** Servicio de cálculo físico. */
    fun calculo(): CalculadoraCaidaLibre

    /** Servicio de explicaciones paso a paso. */
    fun explicador(): ExplicadorFisica

    /** Validador de parámetros iniciales. */
    fun validador(): ValidadorParametros

    /** Catálogo de entornos gravitatorios. */
    fun catalogo(): CatalogoEntornos

    /** Estado y comandos de la simulación. */
    fun contexto(): ContextoSimulacion

    /** Control de reproducción de la simulación. */
    fun control(): ControlReproduccion

    /** Controlador del desafío del Modo Avanzado. */
    fun desafio(): DesafioAvanzado

    /** Servicio de exportación de datos. */
    fun exportacion(): ExportacionDatos

    /** Catálogo de formatos de exportación. */
    fun catalogoExportadores(): CatalogoDeExportadores

    /** Selector de archivo de destino. */
    fun selectorArchivo(): SelectorArchivoExportacion
}
