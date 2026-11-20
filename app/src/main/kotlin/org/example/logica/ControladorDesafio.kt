package org.example

class ControladorDesafio(
    partidaInterna: PartidaDesafio,
    evaluadorInterno: EvaluadorSustitucion = EvaluadorSustitucionFormula()
) : DesafioAvanzado {
    constructor(
        generadorEjercicios: GeneradorDeEjercicios
    ) : this(PartidaDesafio(generadorEjercicios))

    /**
     * Controlador instanciable del desafío interactivo (Modo Avanzado).
     *
     * Orquesta la lógica pedagógica de las tres etapas (fórmula, sustitución y
     * validación) delegando el estado de la partida en [PartidaDesafio] y la
     * verificación algebraica en [EvaluadorSustitucion] (SRP / DIP / CBO).
     */

    private val sesion = Pair(partidaInterna, evaluadorInterno)

    override fun etapa() = sesion.first.etapa()

    override fun ejercicio() = sesion.first.ejercicio()

    override fun opcionesFormula() = sesion.first.opcionesFormula()

    override fun opcionesRespuesta() = sesion.first.opcionesRespuesta()

    override fun estaResuelto(): Boolean = sesion.first.estaResuelto()

    override fun elegirFormula(indice: Int): Retroalimentacion {
        if (!sesion.first.etapa().esFormula()) {
            return sesion.second.crearRetroalimentacion(false, "Ya superaste la etapa de selección de fórmula.")
        }
        val opcion = sesion.first.opcionesFormula().getOrNull(indice)
            ?: return sesion.second.crearRetroalimentacion(false, "Opción no válida.")
        if (opcion.esCorrecta()) {
            sesion.first.avanzarEtapa()
        }
        return opcion.evaluarSeleccion()
    }

    override fun comprobarSustitucion(valoresIngresados: List<Double>): Retroalimentacion {
        return sesion.second.comprobar(sesion.first.ejercicio().camposSustitucion(), valoresIngresados)
    }

    override fun responder(indice: Int): Retroalimentacion {
        if (!sesion.first.etapa().esSustitucion()) {
            return sesion.second.crearRetroalimentacion(false, "Primero debes elegir la fórmula correcta.")
        }
        val opcion = sesion.first.opcionesRespuesta().getOrNull(indice)
            ?: return sesion.second.crearRetroalimentacion(false, "Opción no válida.")
        if (opcion.esCorrecta()) {
            sesion.first.marcarResuelto()
        }
        return opcion.evaluarRespuesta()
    }

    override fun nuevoEjercicio() {
        sesion.first.reiniciar()
    }
}
