package org.example

interface ProveedorDominioFisico : RecursosEntornoSimulacion {
    /**
     * Contrato que agrupa las abstracciones de configuración física, cálculo
     * cinemático y catálogo de entornos del dominio (DIP).
     */

    /**
     * Devuelve la configuración física activa del simulador.
     */
    fun configuracion(): ConfiguracionSimulacion
}
