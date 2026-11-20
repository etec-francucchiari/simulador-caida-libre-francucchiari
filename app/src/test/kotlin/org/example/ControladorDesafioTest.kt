package org.example

import java.util.Random
import kotlin.test.*

class ControladorDesafioTest {
    /**
     * Pruebas unitarias exhaustivas del flujo interactivo de tres etapas en
     * [ControladorDesafio] (Modo Avanzado).
     */

    private val semilla = 7L

    /**
     * Construye una instancia determinista de [ControladorDesafio] con semilla
     * fija para asegurar la reproducibilidad de las pruebas.
     */
    private fun crearControlador() =
        ControladorDesafio(GeneradorEjercicios(Random(semilla)))

    @Test
    fun comienzaEnLaEtapaDeFormulaConTresOpciones() {
        val controlador = crearControlador()
        assertTrue(controlador.etapa().esFormula())
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
        assertTrue(controlador.etapa().esFormula())
    }

    @Test
    fun elegirLaFormulaCorrectaAvanzaALaEtapaDeSustitucion() {
        val controlador = crearControlador()
        val indiceCorrecto = controlador.opcionesFormula().indexOfFirst { it.esCorrecta() }
        val retroalimentacion = controlador.elegirFormula(indiceCorrecto)
        assertTrue(retroalimentacion.esCorrecta())
        assertTrue(controlador.etapa().esSustitucion())
    }

    @Test
    fun comprobarSustitucionValidaValoresCorrectosYErroneos() {
        val controlador = crearControlador()
        controlador.elegirFormula(controlador.opcionesFormula().indexOfFirst { it.esCorrecta() })
        val correctos = controlador.camposSustitucion().map { it.valorEsperado() }
        assertTrue(controlador.comprobarSustitucion(correctos).esCorrecta())
        val erroneos = controlador.camposSustitucion().map { it.valorEsperado() + 10.0 }
        assertFalse(controlador.comprobarSustitucion(erroneos).esCorrecta())
    }

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
