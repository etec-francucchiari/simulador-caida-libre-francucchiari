package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas unitarias para el enumerador [ModoSimulacion].
 */
class ModoSimulacionTest {

    @Test
    fun modosDisponiblesContienenPrincipianteYEstandar() {
        val modos = ModoSimulacion.entries
        assertEquals(2, modos.size)
        assertTrue(modos.contains(ModoSimulacion.PRINCIPIANTE))
        assertTrue(modos.contains(ModoSimulacion.ESTANDAR))
    }

    @Test
    fun modoPrincipiantePoseeTituloYDescripcionAdecuados() {
        val modo = ModoSimulacion.PRINCIPIANTE
        assertTrue(modo.titulo().contains("Modo Principiante"))
        assertTrue(modo.titulo().contains("Fórmulas paso a paso"))
        assertTrue(modo.descripcion().contains("MRUV"))
        assertEquals(modo.titulo(), modo.toString())
    }

    @Test
    fun modoEstandarPoseeTituloYDescripcionAdecuados() {
        val modo = ModoSimulacion.ESTANDAR
        assertTrue(modo.titulo().contains("Avanzado") || modo.titulo().contains("Estándar"))
        assertEquals(modo.titulo(), modo.toString())
    }
}
