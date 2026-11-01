package org.example

class ControladorDesafio(
    private val generadorEjercicios: GeneradorDeEjercicios,
    private val generadorOpcionesFormula: GeneradorDeFormulas,
    private val generadorOpcionesRespuesta: GeneradorDeRespuestas
) : DesafioAvanzado {
    /**
     * Controlador instanciable del desafío interactivo (Modo Avanzado).
     *
     * Orquesta la lógica pedagógica de las tres etapas (fórmula, sustitución y
     * validación) delegando la generación de ejercicios y opciones en servicios
     * inyectados por constructor (DIP).
     */

    private val toleranciaSustitucion = 0.05
    private var ejercicioActual = generadorEjercicios.generar()
    private var opcionesFormulaActuales = generadorOpcionesFormula.generar(ejercicioActual)
    private var opcionesRespuestaActuales = generadorOpcionesRespuesta.generar(ejercicioActual)
    private var etapaActual = EtapaDesafio.entries.first()
    private var resuelto = false

    override fun etapa(): EtapaDesafio = etapaActual

    override fun ejercicio() = ejercicioActual

    override fun opcionesFormula() = opcionesFormulaActuales

    override fun opcionesRespuesta() = opcionesRespuestaActuales

    override fun estaResuelto(): Boolean = resuelto

    override fun elegirFormula(indice: Int): Retroalimentacion {
        if (!etapaActual.esFormula()) {
            return Retroalimentacion(false, "Ya superaste la etapa de selección de fórmula.")
        }
        val opcion = opcionesFormulaActuales.getOrNull(indice)
            ?: return Retroalimentacion(false, "Opción no válida.")
        if (opcion.esCorrecta()) {
            etapaActual = etapaActual.siguiente()
        }
        return opcion.evaluarSeleccion()
    }

    override fun comprobarSustitucion(valoresIngresados: List<Double>): Retroalimentacion {
        val campos = ejercicioActual.camposSustitucion()
        if (valoresIngresados.size != campos.size) {
            return Retroalimentacion(false, "Debes completar todos los campos de la fórmula.")
        }
        val incorrectos = campos.filterIndexed { indice, campo ->
            !campo.esAcierto(valoresIngresados[indice], toleranciaSustitucion)
        }
        if (incorrectos.isEmpty()) {
            return Retroalimentacion(true, "¡Sustitución correcta!", "Reemplazaste bien todos los datos en la fórmula.")
        }
        val detalle = "Revisa: " + incorrectos.joinToString(", ") { "${it.simbolo()} (esperado ${it.valorEsperado()} ${it.unidad()})" }
        return Retroalimentacion(false, "Hay ${incorrectos.size} valor(es) incorrecto(s).", detalle)
    }

    override fun responder(indice: Int): Retroalimentacion {
        if (!etapaActual.esSustitucion()) {
            return Retroalimentacion(false, "Primero debes elegir la fórmula correcta.")
        }
        val opcion = opcionesRespuestaActuales.getOrNull(indice)
            ?: return Retroalimentacion(false, "Opción no válida.")
        if (opcion.esCorrecta()) {
            etapaActual = etapaActual.siguiente()
            resuelto = true
        }
        return opcion.evaluarRespuesta()
    }

    override fun nuevoEjercicio() {
        ejercicioActual = generadorEjercicios.generar()
        opcionesFormulaActuales = generadorOpcionesFormula.generar(ejercicioActual)
        opcionesRespuestaActuales = generadorOpcionesRespuesta.generar(ejercicioActual)
        etapaActual = etapaActual.reiniciar()
        resuelto = false
    }
}
