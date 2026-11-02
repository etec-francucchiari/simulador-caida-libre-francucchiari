package org.example

interface DesafioAvanzado {
    /**
     * Contrato del desafío pedagógico interactivo en tres etapas del Modo
     * Avanzado (Principio de Inversión de Dependencias - DIP).
     *
     * Las vistas de etapa ([VistaEtapaFormula], [VistaEtapaSustitucion],
     * [VistaEtapaValidacion]) y el encabezado ([EncabezadoDesafioAvanzado])
     * dependen exclusivamente de esta abstracción y no de [ControladorDesafio].
     */

    /**
     * Devuelve la [EtapaDesafio] en la que se encuentra actualmente el flujo
     * (Fórmula, Sustitución o Validación).
     */
    fun etapa(): EtapaDesafio

    /**
     * Devuelve el [EjercicioCaidaLibre] activo que el estudiante está resolviendo.
     */
    fun ejercicio(): EjercicioCaidaLibre

    /**
     * Devuelve las tres opciones de fórmula simbólica generadas para la Etapa 1.
     */
    fun opcionesFormula(): List<OpcionFormula>

    /**
     * Evalúa la opción de fórmula seleccionada en la posición [indice],
     * avanzando a la Etapa 2 cuando la elección es correcta.
     */
    fun elegirFormula(indice: Int): Retroalimentacion

    /**
     * Devuelve los campos de sustitución requeridos en la Etapa 2 según el
     * ejercicio activo.
     */
    fun camposSustitucion(): List<CampoSustitucion> = ejercicio().camposSustitucion()

    /**
     * Verifica si la lista ordenada de [valoresIngresados] por el estudiante
     * coincide con los datos esperados de cada variable del problema.
     */
    fun comprobarSustitucion(valoresIngresados: List<Double>): Retroalimentacion

    /**
     * Devuelve las tres opciones de resultado numérico final para la Etapa 2.
     */
    fun opcionesRespuesta(): List<OpcionRespuesta>

    /**
     * Evalúa la opción de respuesta numérica elegida en la posición [indice],
     * marcando el desafío como resuelto y pasando a la Etapa 3 si es correcta.
     */
    fun responder(indice: Int): Retroalimentacion

    /**
     * Genera un nuevo ejercicio aleatorio y restablece el desafío a la Etapa 1.
     */
    fun nuevoEjercicio()

    /**
     * Indica si el estudiante ya superó las etapas algebraica y numérica,
     * habilitando la validación experimental con la simulación.
     */
    fun estaResuelto(): Boolean
}
