package org.example

import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Pruebas del controlador del desafío interactivo (Modo Avanzado).
 */
class ControladorDesafioTest {

    private fun crearControlador(): ControladorDesafio =
        ControladorDesafio(
            GeneradorEjercicios(CatalogoPlanetas(), CalculoCaidaLibre(), Random(7)),
            GeneradorOpcionesFormula(Random(11)),
            GeneradorOpcionesRespuesta(Random(13))
        )

    @Test
    fun comienzaEnLaEtapaDeFormulaConTresOpciones() {
        val controlador = crearControlador()

        assertEquals(EtapaDesafio.FORMULA, controlador.etapa())
        assertEquals(3, controlador.opcionesFormula().size)
        assertFalse(controlador.estaResuelto())
    }

    @Test
    fun elegirLaFormulaIncorrectaNoAvanzaDeEtapa() {
        val controlador = crearControlador()
        val indiceIncorrecto = controlador.opcionesFormula().indexOfFirst { !it.esCorrecta() }

        val retroalimentacion = controlador.elegirFormula(indiceIncorrecto)

        assertFalse(retroalimentacion.esCorrecta())
        assertTrue(retroalimentacion.tieneDetalle())
        assertEquals(EtapaDesafio.FORMULA, controlador.etapa())
    }

    @Test
    fun elegirLaFormulaCorrectaAvanzaALaEtapaDeSustitucion() {
        val controlador = crearControlador()
        val indiceCorrecto = controlador.opcionesFormula().indexOfFirst { it.esCorrecta() }

        val retroalimentacion = controlador.elegirFormula(indiceCorrecto)

        assertTrue(retroalimentacion.esCorrecta())
        assertEquals(EtapaDesafio.SUSTITUCION, controlador.etapa())
    }

    @Test
    fun comprobarSustitucionConLosValoresCorrectosEsExitosa() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })

        val valores = controlador.camposSustitucion().map { it.valorEsperado() }
        val retroalimentacion = controlador.comprobarSustitucion(valores)

        assertTrue(retroalimentacion.esCorrecta())
    }

    @Test
    fun comprobarSustitucionConValoresErroneosFalla() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })

        val valores = controlador.camposSustitucion().map { it.valorEsperado() + 10.0 }
        val retroalimentacion = controlador.comprobarSustitucion(valores)

        assertFalse(retroalimentacion.esCorrecta())
    }

    @Test
    fun responderCorrectamenteAvanzaALaValidacion() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        val indiceCorrecto = controlador.opcionesRespuesta().indexOfFirst { it.esCorrecta() }

        val retroalimentacion = controlador.responder(indiceCorrecto)

        assertTrue(retroalimentacion.esCorrecta())
        assertTrue(controlador.estaResuelto())
        assertEquals(EtapaDesafio.VALIDACION, controlador.etapa())
    }

    @Test
    fun responderAntesDeElegirFormulaFalla() {
        val controlador = crearControlador()

        val retroalimentacion = controlador.responder(0)

        assertFalse(retroalimentacion.esCorrecta())
        assertEquals(EtapaDesafio.FORMULA, controlador.etapa())
    }

    @Test
    fun nuevoEjercicioReiniciaElDesafio() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        controlador.responder(controlador.opcionesRespuesta().indexOfFirst { it.esCorrecta() })

        controlador.nuevoEjercicio()

        assertEquals(EtapaDesafio.FORMULA, controlador.etapa())
        assertFalse(controlador.estaResuelto())
        assertEquals(3, controlador.opcionesFormula().size)
    }
}
