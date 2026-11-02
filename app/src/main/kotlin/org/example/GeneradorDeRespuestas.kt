package org.example

interface GeneradorDeRespuestas {
    /**
     * Contrato del generador de opciones de respuesta numérica para la Etapa 2
     * del desafío interactivo en el Modo Avanzado (DIP).
     *
     * Permite que [ControladorDesafio] dependa de una abstracción pura en lugar
     * de la implementación concreta [GeneradorOpcionesRespuesta].
     */

    /**
     * Construye una colección de tres [OpcionRespuesta] numéricas para el
     * [ejercicio] recibido, incluyendo el resultado físico verdadero y dos
     * valores distractores derivados de errores de cálculo frecuentes.
     *
     * @param ejercicio escenario de caída libre cuyos datos se evalúan.
     * @return lista de tres opciones numéricas con su unidad y diagnóstico.
     */
    fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionRespuesta>
}
