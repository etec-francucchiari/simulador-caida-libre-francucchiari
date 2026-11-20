package org.example

class GeneradorOpcionesDesafio(
    formulas: GeneradorDeFormulas = GeneradorOpcionesFormula(),
    respuestas: GeneradorDeRespuestas = GeneradorOpcionesRespuesta()
) {
    /**
     * Colaborador que coordina la generación de opciones de fórmula (Etapa 1) y
     * de respuesta numérica (Etapa 2) para un ejercicio de caída libre (SRP / DIP).
     *
     * Extraído de [PartidaDesafio] para reducir el acoplamiento eferente (CBO).
     */

    private val generadores = Pair(formulas, respuestas)

    fun generarFormulas(ejercicio: EjercicioCaidaLibre) =
        generadores.first.generar(ejercicio)

    fun generarRespuestas(ejercicio: EjercicioCaidaLibre) =
        generadores.second.generar(ejercicio)
}
