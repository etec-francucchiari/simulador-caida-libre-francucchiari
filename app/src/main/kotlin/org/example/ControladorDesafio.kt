package org.example

/**
 * Controlador instanciable del desafío interactivo (Modo Avanzado).
 *
 * Orquesta la lógica pedagógica de las tres etapas (fórmula, sustitución y
 * validación) delegando la generación de ejercicios y opciones en servicios
 * inyectados por constructor (DIP). Mantiene un estado encapsulado con campos
 * [private] y lo expone a través del contrato [DesafioAvanzado].
 */
class ControladorDesafio(
    private val generadorEjercicios: GeneradorDeEjercicios,
    private val generadorOpcionesFormula: GeneradorDeFormulas,
    private val generadorOpcionesRespuesta: GeneradorDeRespuestas
) : DesafioAvanzado {

    private val toleranciaSustitucion = 0.05

    private var ejercicioActual: EjercicioCaidaLibre
    private var opcionesFormulaActuales: List<OpcionFormula>
    private var opcionesRespuestaActuales: List<OpcionRespuesta>
    private var etapaActual: EtapaDesafio
    private var resuelto: Boolean

    init {
        ejercicioActual = generadorEjercicios.generar()
        opcionesFormulaActuales = generadorOpcionesFormula.generar(ejercicioActual)
        opcionesRespuestaActuales = generadorOpcionesRespuesta.generar(ejercicioActual)
        etapaActual = EtapaDesafio.FORMULA
        resuelto = false
    }

    override fun etapa(): EtapaDesafio = etapaActual

    override fun ejercicio(): EjercicioCaidaLibre = ejercicioActual

    override fun opcionesFormula(): List<OpcionFormula> = opcionesFormulaActuales

    override fun opcionesRespuesta(): List<OpcionRespuesta> = opcionesRespuestaActuales

    override fun estaResuelto(): Boolean = resuelto

    override fun elegirFormula(indice: Int): Retroalimentacion {
        if (!etapaActual.esFormula()) {
            return Retroalimentacion(false, "Ya superaste la etapa de selección de fórmula.")
        }

        val opcion = opcionesFormulaActuales.getOrNull(indice)
            ?: return Retroalimentacion(false, "Opción no válida.")

        return if (opcion.esCorrecta()) {
            etapaActual = EtapaDesafio.SUSTITUCION
            Retroalimentacion(
                correctaInterna = true,
                mensajeInterno = "¡Fórmula correcta!",
                detalleInterno = "Avanzas a la sustitución de valores."
            )
        } else {
            Retroalimentacion(
                correctaInterna = false,
                mensajeInterno = "Fórmula incorrecta.",
                detalleInterno = opcion.retroalimentacion()
            )
        }
    }

    override fun camposSustitucion(): List<CampoSustitucion> = ejercicioActual.camposSustitucion()

    override fun comprobarSustitucion(valoresIngresados: List<Double>): Retroalimentacion {
        val campos = ejercicioActual.camposSustitucion()

        if (valoresIngresados.size != campos.size) {
            return Retroalimentacion(
                false,
                "Debes completar todos los campos de la fórmula."
            )
        }

        val incorrectos = campos.filterIndexed { indice, campo ->
            !campo.esAcierto(valoresIngresados[indice], toleranciaSustitucion)
        }

        return if (incorrectos.isEmpty()) {
            Retroalimentacion(
                correctaInterna = true,
                mensajeInterno = "¡Sustitución correcta!",
                detalleInterno = "Reemplazaste bien todos los datos en la fórmula."
            )
        } else {
            Retroalimentacion(
                correctaInterna = false,
                mensajeInterno = "Hay ${incorrectos.size} valor(es) incorrecto(s).",
                detalleInterno = "Revisa: " + incorrectos.joinToString(", ") {
                    "${it.simbolo()} (esperado ${it.valorEsperado()} ${it.unidad()})"
                }
            )
        }
    }

    override fun responder(indice: Int): Retroalimentacion {
        if (!etapaActual.esSustitucion()) {
            return Retroalimentacion(false, "Primero debes elegir la fórmula correcta.")
        }

        val opcion = opcionesRespuestaActuales.getOrNull(indice)
            ?: return Retroalimentacion(false, "Opción no válida.")

        return if (opcion.esCorrecta()) {
            etapaActual = EtapaDesafio.VALIDACION
            resuelto = true
            Retroalimentacion(
                correctaInterna = true,
                mensajeInterno = "¡Resultado correcto!",
                detalleInterno = "El valor ${opcion.texto()} coincide con el cálculo teórico. " +
                    "Ahora puedes simular para comprobarlo."
            )
        } else {
            Retroalimentacion(
                correctaInterna = false,
                mensajeInterno = "Resultado incorrecto.",
                detalleInterno = opcion.retroalimentacion()
            )
        }
    }

    override fun nuevoEjercicio() {
        ejercicioActual = generadorEjercicios.generar()
        opcionesFormulaActuales = generadorOpcionesFormula.generar(ejercicioActual)
        opcionesRespuestaActuales = generadorOpcionesRespuesta.generar(ejercicioActual)
        etapaActual = EtapaDesafio.FORMULA
        resuelto = false
    }
}
