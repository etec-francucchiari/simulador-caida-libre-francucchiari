package org.example

interface GeneradorDeFormulas {
    /**
     * Contrato del generador de opciones de fórmula algebraica para la Etapa 1
     * del desafío interactivo en el Modo Avanzado (DIP).
     *
     * Desacopla a [ControladorDesafio] de la lógica concreta de construcción de
     * distractores simbólicos ([GeneradorOpcionesFormula]), facilitando su
     * sustitución y prueba unitaria aislada.
     */

    /**
     * Genera una lista barajada de tres [OpcionFormula] para el [ejercicio]
     * indicado, garantizando que exista exactamente una opción verdadera y dos
     * distractores con retroalimentación formativa sobre el error algebraico.
     *
     * @param ejercicio problema de caída libre activo.
     * @return lista inmutable de tres opciones de fórmula.
     */
    fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionFormula>
}
