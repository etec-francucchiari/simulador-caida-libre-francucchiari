package org.example

class FabricaDesafioAvanzado(
    private val recursos: RecursosEntornoSimulacion
) {
    /**
     * Fábrica que ensambla el controlador del desafío interactivo del Modo Avanzado
     * inyectando sus colaboradores de dominio (DIP / CBO).
     */

    /**
     * Construye una instancia configurada de [DesafioAvanzado] lista para gestionar
     * el flujo de tres etapas del desafío.
     */
    fun crear(): DesafioAvanzado {
        return ControladorDesafio(GeneradorEjercicios(recursos = recursos))
    }
}
