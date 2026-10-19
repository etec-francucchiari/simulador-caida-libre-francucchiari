package org.example

/**
 * Contrato del desafío interactivo del Modo Avanzado (DIP).
 *
 * Las vistas dependen de esta abstracción y no del controlador concreto, de
 * modo que la lógica pedagógica puede sustituirse o probarse de forma aislada.
 */
interface DesafioAvanzado {

    /** Etapa actual del desafío. */
    fun etapa(): EtapaDesafio

    /** Ejercicio activo. */
    fun ejercicio(): EjercicioCaidaLibre

    /** Opciones de fórmula de la Etapa 1. */
    fun opcionesFormula(): List<OpcionFormula>

    /**
     * Registra la elección de una fórmula.
     *
     * @return retroalimentación; si es correcta avanza a la Etapa 2.
     */
    fun elegirFormula(indice: Int): Retroalimentacion

    /** Campos de sustitución de la Etapa 2. */
    fun camposSustitucion(): List<CampoSustitucion>

    /**
     * Comprueba la sustitución numérica realizada por el estudiante.
     */
    fun comprobarSustitucion(valoresIngresados: List<Double>): Retroalimentacion

    /** Opciones de respuesta final de la Etapa 2. */
    fun opcionesRespuesta(): List<OpcionRespuesta>

    /**
     * Registra la respuesta final elegida.
     *
     * @return retroalimentación; si es correcta avanza a la Etapa 3.
     */
    fun responder(indice: Int): Retroalimentacion

    /** Genera un nuevo ejercicio y reinicia el desafío. */
    fun nuevoEjercicio()

    /** Indica si el ejercicio ya fue resuelto correctamente. */
    fun estaResuelto(): Boolean
}
