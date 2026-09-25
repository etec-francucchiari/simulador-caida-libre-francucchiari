package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Pruebas del [ValidadorParametrosIniciales]: formato numérico y
 * restricciones físicas de los parámetros iniciales.
 */
class ValidadorParametrosInicialesTest {

    private val validador = ValidadorParametrosIniciales()

    @Test
    fun alturaConPuntoDecimalEsValida() {
        assertNull(validador.validarAltura("100.5"))
    }

    @Test
    fun alturaConComaDecimalEsValida() {
        assertNull(validador.validarAltura("10,5"))
    }

    @Test
    fun alturaVaciaEsInvalida() {
        assertNotNull(validador.validarAltura(""))
    }

    @Test
    fun alturaNegativaEsInvalida() {
        assertEquals(
            "La altura inicial no puede ser negativa.",
            validador.validarAltura("-5")
        )
    }

    @Test
    fun alturaConTextoNoNumericoEsInvalida() {
        assertNotNull(validador.validarAltura("abc"))
    }

    @Test
    fun velocidadCeroEsValida() {
        assertNull(validador.validarVelocidad("0"))
    }

    @Test
    fun velocidadNegativaEsInvalida() {
        assertEquals(
            "La velocidad inicial no puede ser negativa.",
            validador.validarVelocidad("-1")
        )
    }

    @Test
    fun valoresNoFinitosSonInvalidos() {
        assertNotNull(validador.validarAltura("NaN"))
        assertNotNull(validador.validarVelocidad("Infinity"))
    }

    @Test
    fun dosValoresValidosRetornanExitoso() {
        val resultado = validador.validar("100", "5.5")

        assertTrue(resultado.esValido())
        val exitoso = resultado as ValidadorParametrosIniciales.ResultadoValidacion.Exitoso
        assertEquals(100.0, exitoso.parametros.alturaInicial)
        assertEquals(5.5, exitoso.parametros.velocidadInicial)
    }

    @Test
    fun alturaInvalidaRetornaFallido() {
        val resultado = validador.validar("-2", "5")

        assertFalse(resultado.esValido())
        val fallido = resultado as ValidadorParametrosIniciales.ResultadoValidacion.Fallido
        assertEquals(1, fallido.errores.size)
        assertNotNull(fallido.errores.firstOrNull { it.contains("altura", ignoreCase = true) })
    }
}