package org.example

import kotlin.test.*

class ModoSimulacionTest {
    /**
     * Pruebas unitarias para el enumerador de modos pedagógicos de operación
     * ([ModoSimulacion]).
     *
     * Verifica que existan las dos modalidades principales del simulador:
     * - [ModoSimulacion.PRINCIPIANTE]: exploración guiada con fórmulas paso a
     *   paso, gráficos cinemáticos en tiempo real y selección de planeta.
     * - [ModoSimulacion.ESTANDAR]: desafío interactivo en tres etapas con
     *   validación algebraica y exportación de datos.
     */

    /**
     * Comprueba que el enumerador contenga únicamente los modos Principiante y
     * Estándar/Avanzado.
     */
    @Test
    fun modosDisponiblesContienenPrincipianteYEstandar() {
        val modos = ModoSimulacion.entries
        assertEquals(2, modos.size)
        assertTrue(modos.contains(ModoSimulacion.PRINCIPIANTE))
        assertTrue(modos.contains(ModoSimulacion.ESTANDAR))
    }

    /**
     * Verifica el título descriptivo, la mención a las ecuaciones del MRUV y el
     * comportamiento de `toString()` para el Modo Principiante.
     */
    @Test
    fun modoPrincipiantePoseeTituloYDescripcionAdecuados() {
        val modo = ModoSimulacion.PRINCIPIANTE
        assertTrue(modo.titulo().contains("Modo Principiante"))
        assertTrue(modo.titulo().contains("Fórmulas paso a paso"))
        assertTrue(modo.descripcion().contains("MRUV"))
        assertEquals(modo.titulo(), modo.toString())
    }

    /**
     * Verifica el título descriptivo y la representación textual del Modo
     * Avanzado / Estándar.
     */
    @Test
    fun modoEstandarPoseeTituloYDescripcionAdecuados() {
        val modo = ModoSimulacion.ESTANDAR
        assertTrue(modo.titulo().contains("Avanzado") || modo.titulo().contains("Estándar"))
        assertEquals(modo.titulo(), modo.toString())
    }
}
