package org.example

interface CatalogoEstilos {
    /**
     * Contrato del catálogo de estilos visuales para los entornos gravitatorios
     * del simulador (Principio de Inversión de Dependencias - DIP).
     *
     * Permite que el renderizador gráfico ([RenderizadorCaidaJavaFx]) obtenga la
     * estrategia de pintura ([EstiloEntorno]) correspondiente a cualquier
     * [EntornoGravitatorio] sin acoplarse a la implementación del catálogo
     * ([CatalogoEstilosEntorno]) ni a los estilos concretos de cada planeta.
     */

    /**
     * Resuelve y devuelve la estrategia de estilo visual asociada al [entorno]
     * gravitatorio indicado, retornando un estilo neutro por defecto cuando se
     * trata de un entorno personalizado.
     *
     * @param entorno entorno gravitatorio activo de la simulación.
     * @return implementación de [EstiloEntorno] encargada de pintar fondo y suelo.
     */
    fun para(entorno: EntornoGravitatorio): EstiloEntorno
}
