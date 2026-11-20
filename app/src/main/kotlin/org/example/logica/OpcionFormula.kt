package org.example

class OpcionFormula(
    textoInterno: String,
    correctaInterna: Boolean,
    retroalimentacionInterna: String
) {
    /**
     * Modelo de dominio inmutable que representa una opción de fórmula
     * algebraica dentro de la Etapa 1 del desafío interactivo ([VistaEtapaFormula]).
     *
     * Encapsula de manera privada la expresión simbólica, el indicador de
     * validez física y la explicación pedagógica del error en caso de tratarse
     * de un distractor, proporcionando comportamiento propio ([evaluarSeleccion])
     * para construir la [Retroalimentacion] resultante de su elección.
     */

    private val datos = Triple(textoInterno, correctaInterna, retroalimentacionInterna)

    /**
     * Devuelve la expresión algebraica que se presenta en el botón de opción.
     */
    fun texto(): String = datos.first

    /**
     * Comportamiento de dominio: indica si esta opción corresponde a la fórmula
     * físicamente correcta para el objetivo del ejercicio.
     */
    fun esCorrecta(): Boolean {
        return datos.second
    }

    /**
     * Comportamiento de dominio: devuelve el mensaje formativo que explica por
     * qué la fórmula es correcta o cuál es la falla conceptual del distractor.
     */
    fun retroalimentacion(): String {
        return datos.third
    }

    /**
     * Comportamiento de dominio: evalúa la selección de esta opción por parte
     * del estudiante y genera el objeto [Retroalimentacion] correspondiente.
     */
    fun evaluarSeleccion(): Retroalimentacion {
        if (datos.second) {
            return Retroalimentacion(true, "¡Fórmula correcta!", "Avanzas a la sustitución de valores.")
        }
        return Retroalimentacion(false, "Fórmula incorrecta.", datos.third)
    }

    /**
     * Devuelve el texto simbólico de la fórmula como representación por defecto.
     */
    override fun toString(): String {
        return datos.first
    }
}
