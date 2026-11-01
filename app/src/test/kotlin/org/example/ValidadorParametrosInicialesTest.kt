package org.example

import kotlin.test.*

class ValidadorParametrosInicialesTest {
    /**
     * Pruebas unitarias de validación del campo de altura inicial en
     * [ValidadorParametrosIniciales]: separadores decimales (punto y coma),
     * cadenas vacías, valores negativos y texto no numérico.
     */

    private val validador = ValidadorParametrosIniciales(ConfiguracionFisica())

    /**
     * Un número decimal positivo con punto ("100.5") debe ser aceptado sin error.
     */
    @Test
    fun alturaConPuntoDecimalEsValida() {
        assertNull(validador.validarAltura("100.5"))
    }

    /**
     * Un número decimal positivo con coma ("10,5") debe normalizarse y aceptarse.
     */
    @Test
    fun alturaConComaDecimalEsValida() {
        assertNull(validador.validarAltura("10,5"))
    }

    /**
     * Una cadena vacía debe devolver un mensaje de error de formato numérico.
     */
    @Test
    fun alturaVaciaEsInvalida() {
        assertNotNull(validador.validarAltura(""))
    }

    /**
     * Una altura negativa debe ser rechazada con el mensaje de dominio esperado.
     */
    @Test
    fun alturaNegativaEsInvalida() {
        assertEquals("La altura inicial no puede ser negativa.", validador.validarAltura("-5"))
    }

    /**
     * Cualquier texto alfabético no convertible a número real debe rechazarse.
     */
    @Test
    fun alturaConTextoNoNumericoEsInvalida() {
        assertNotNull(validador.validarAltura("abc"))
    }
}
