package org.example

import kotlin.test.*

class ValidadorParametrosResultadoTest {
    /**
     * Pruebas unitarias de validación de velocidad inicial, valores no finitos
     * y construcción polimórfica de [ResultadoValidacion] ([ValidacionExitosa] y
     * [ValidacionFallida]) en [ValidadorParametrosIniciales].
     */

    private val validador = ValidadorParametrosIniciales(ConfiguracionFisica())

    /**
     * La velocidad inicial nula ("0") corresponde a caída desde el reposo y es válida.
     */
    @Test
    fun velocidadCeroEsValida() {
        assertNull(validador.validarVelocidad("0"))
    }

    /**
     * Una velocidad inicial negativa debe ser rechazada con el mensaje específico.
     */
    @Test
    fun velocidadNegativaEsInvalida() {
        assertEquals("La velocidad inicial no puede ser negativa.", validador.validarVelocidad("-1"))
    }

    /**
     * Valores especiales IEEE 754 como `NaN` o `Infinity` no son físicamente válidos.
     */
    @Test
    fun valoresNoFinitosSonInvalidos() {
        assertNotNull(validador.validarAltura("NaN"))
        assertNotNull(validador.validarVelocidad("Infinity"))
    }

    /**
     * Cuando altura y velocidad son válidas, `validar` devuelve un resultado
     * válido con los [ParametrosIniciales] parseados.
     */
    @Test
    fun dosValoresValidosRetornanExitoso() {
        val resultado = validador.validar("100", "5.5")
        assertTrue(resultado.esValido())
        val parametros = resultado.parametros()
        assertNotNull(parametros)
        assertEquals(100.0, parametros.alturaInicial())
        assertEquals(5.5, parametros.velocidadInicial())
    }

    /**
     * Cuando alguno de los campos es inválido, `validar` devuelve un resultado
     * fallido con la lista de mensajes de error correspondientes.
     */
    @Test
    fun alturaInvalidaRetornaFallido() {
        val resultado = validador.validar("-2", "5")
        assertFalse(resultado.esValido())
        val errores = resultado.errores()
        assertEquals(1, errores.size)
        assertNotNull(errores.firstOrNull { it.contains("altura", ignoreCase = true) })
    }
}
