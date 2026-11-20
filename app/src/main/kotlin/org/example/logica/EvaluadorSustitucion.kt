package org.example

interface EvaluadorSustitucion {
    /**
     * Contrato para la evaluación de los campos de sustitución algebraica y la
     * construcción de retroalimentación pedagógica en el Modo Avanzado (DIP).
     */

    /**
     * Verifica que los valores ingresados coincidan con los campos esperados.
     */
    fun comprobar(campos: List<CampoSustitucion>, valoresIngresados: List<Double>): Retroalimentacion

    /**
     * Construye un mensaje de retroalimentación formativa.
     */
    fun crearRetroalimentacion(correcta: Boolean, mensaje: String, detalle: String = ""): Retroalimentacion
}
