package org.example

import java.util.Random
import kotlin.test.*

class ControladorDesafioTest {
    /**
     * Pruebas unitarias exhaustivas del flujo interactivo de tres etapas en
     * [ControladorDesafio] (Modo Avanzado):
     * - Etapa 1: Selección de la ecuación cinemática correcta ([EtapaDesafio.FORMULA]).
     * - Etapa 2: Sustitución de variables conocidas y elección del resultado
     *   numérico ([EtapaDesafio.SUSTITUCION]).
     * - Etapa 3: Validación experimental con la simulación desbloqueada
     *   ([EtapaDesafio.VALIDACION]).
     */

    private val semilla = 7L

    /**
     * Construye una instancia determinista de [ControladorDesafio] con semillas
     * fijas para asegurar la reproducibilidad de las pruebas.
     */
    private fun crearControlador() =
        ControladorDesafio(
            GeneradorEjercicios(CatalogoPlanetas(), CalculoCaidaLibre(), Random(semilla)),
            GeneradorOpcionesFormula(Random(11)),
            GeneradorOpcionesRespuesta(Random(13))
        )

    /**
     * Al inicializar el controlador, debe situarse en la etapa de selección de
     * fórmula, ofrecer exactamente tres opciones y figurar como no resuelto.
     */
    @Test
    fun comienzaEnLaEtapaDeFormulaConTresOpciones() {
        val controlador = crearControlador()
        assertTrue(controlador.etapa().esFormula())
        assertEquals(3, controlador.opcionesFormula().size)
        assertFalse(controlador.estaResuelto())
    }

    /**
     * Seleccionar un distractor algebraico en la Etapa 1 debe devolver una
     * retroalimentación negativa con detalle explicativo sin avanzar de etapa.
     */
    @Test
    fun elegirLaFormulaIncorrectaNoAvanzaDeEtapa() {
        val controlador = crearControlador()
        val indiceIncorrecto = controlador.opcionesFormula().indexOfFirst { !it.esCorrecta() }
        val retroalimentacion = controlador.elegirFormula(indiceIncorrecto)
        assertFalse(retroalimentacion.esCorrecta())
        assertTrue(retroalimentacion.tieneDetalle())
        assertTrue(controlador.etapa().esFormula())
    }

    /**
     * Seleccionar la ecuación cinemática correcta en la Etapa 1 debe devolver
     * retroalimentación positiva y avanzar el flujo a la Etapa 2 (Sustitución).
     */
    @Test
    fun elegirLaFormulaCorrectaAvanzaALaEtapaDeSustitucion() {
        val controlador = crearControlador()
        val indiceCorrecto = controlador.opcionesFormula().indexOfFirst { it.esCorrecta() }
        val retroalimentacion = controlador.elegirFormula(indiceCorrecto)
        assertTrue(retroalimentacion.esCorrecta())
        assertTrue(controlador.etapa().esSustitucion())
    }

    /**
     * En la Etapa 2, la verificación de campos de sustitución debe aprobar los
     * valores esperados del ejercicio y rechazar sustituciones alteradas.
     */
    @Test
    fun comprobarSustitucionValidaValoresCorrectosYErroneos() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        val correctos = controlador.camposSustitucion().map { it.valorEsperado() }
        assertTrue(controlador.comprobarSustitucion(correctos).esCorrecta())
        val erroneos = controlador.camposSustitucion().map { it.valorEsperado() + 10.0 }
        assertFalse(controlador.comprobarSustitucion(erroneos).esCorrecta())
    }

    /**
     * Intentar responder antes de superar la Etapa 1 falla; una vez en la
     * Etapa 2, elegir la respuesta numérica correcta marca el desafío como
     * resuelto y avanza a la Etapa 3 (Validación).
     */
    @Test
    fun responderCorrectamenteAvanzaALaValidacion() {
        val controlador = crearControlador()
        assertFalse(controlador.responder(0).esCorrecta())
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        val indiceCorrecto = controlador.opcionesRespuesta().indexOfFirst { it.esCorrecta() }
        val retroalimentacion = controlador.responder(indiceCorrecto)
        assertTrue(retroalimentacion.esCorrecta())
        assertTrue(controlador.estaResuelto())
        assertTrue(controlador.etapa().esValidacion())
    }

    /**
     * Invocar `nuevoEjercicio()` tras haber completado el desafío debe generar
     * un nuevo problema, restablecer la etapa a Fórmula y bloquear la validación.
     */
    @Test
    fun nuevoEjercicioReiniciaElDesafio() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        controlador.responder(controlador.opcionesRespuesta().indexOfFirst { it.esCorrecta() })
        controlador.nuevoEjercicio()
        assertTrue(controlador.etapa().esFormula())
        assertFalse(controlador.estaResuelto())
        assertEquals(3, controlador.opcionesFormula().size)
    }
}
