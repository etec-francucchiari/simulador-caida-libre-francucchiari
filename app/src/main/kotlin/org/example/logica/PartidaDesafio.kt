package org.example

class PartidaDesafio(
    generadorEjercicios: GeneradorDeEjercicios,
    generadorOpciones: GeneradorOpcionesDesafio = GeneradorOpcionesDesafio()
) {
    /**
     * Encapsula el estado activo de una partida del desafío interactivo del
     * Modo Avanzado junto con sus generadores de dominio (SRP / LCOM / CBO).
     */

    private val estado = crearContenedor(generadorEjercicios, generadorOpciones)

    private fun crearContenedor(
        genEj: GeneradorDeEjercicios,
        genOp: GeneradorOpcionesDesafio
    ): Pair<Pair<GeneradorDeEjercicios, GeneradorOpcionesDesafio>, Array<Any>> {
        val ej = genEj.generar()
        val datos = arrayOf<Any>(
            ej,
            genOp.generarFormulas(ej),
            genOp.generarRespuestas(ej),
            EtapaDesafio.entries.first(),
            false
        )
        return Pair(Pair(genEj, genOp), datos)
    }

    fun ejercicio(): EjercicioCaidaLibre = estado.second[0] as EjercicioCaidaLibre

    @Suppress("UNCHECKED_CAST")
    fun opcionesFormula(): List<OpcionFormula> = estado.second[1] as List<OpcionFormula>

    @Suppress("UNCHECKED_CAST")
    fun opcionesRespuesta(): List<OpcionRespuesta> = estado.second[2] as List<OpcionRespuesta>

    fun etapa(): EtapaDesafio = estado.second[3] as EtapaDesafio

    fun estaResuelto(): Boolean = estado.second[4] as Boolean

    fun avanzarEtapa() {
        estado.second[3] = etapa().siguiente()
    }

    fun marcarResuelto() {
        estado.second[3] = etapa().siguiente()
        estado.second[4] = true
    }

    fun reiniciar() {
        val nuevo = estado.first.first.generar()
        estado.second[0] = nuevo
        estado.second[1] = estado.first.second.generarFormulas(nuevo)
        estado.second[2] = estado.first.second.generarRespuestas(nuevo)
        estado.second[3] = etapa().reiniciar()
        estado.second[4] = false
    }
}
