package org.example

interface ProveedorNucleoSimulacion {
    /**
     * Contrato de las dependencias centrales del núcleo de simulación para los
     * ensambladores de pantallas y la raíz de composición (DIP).
     */

    fun configuracion(): ConfiguracionSimulacion

    fun calculo(): CalculadoraCaidaLibre

    fun catalogo(): CatalogoEntornos

    fun contexto(): ContextoSimulacion

    fun control(): ControlReproduccion

    fun desafio(): DesafioAvanzado
}
