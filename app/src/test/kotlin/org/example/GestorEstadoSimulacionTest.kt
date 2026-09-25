package org.example

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Pruebas de la reactividad del [GestorEstadoSimulacion]: al establecer
 * los parámetros iniciales se actualizan las propiedades observables y
 * se notifica a los listeners vinculados.
 */
class GestorEstadoSimulacionTest {

    @Test
    fun alturaInicialSePublicaEnLaPropiedadObservable() {
        val gestor = GestorEstadoSimulacion()
        var ultimoValorNotificado = Double.NaN

        gestor.alturaInicialProperty.addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }

        gestor.establecerAlturaInicial(245.5)

        assertEquals(245.5, gestor.obtenerAlturaInicial())
        assertEquals(245.5, ultimoValorNotificado)
    }

    @Test
    fun velocidadInicialSePublicaEnLaPropiedadObservable() {
        val gestor = GestorEstadoSimulacion()
        var ultimoValorNotificado = Double.NaN

        gestor.velocidadInicialProperty.addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }

        gestor.establecerVelocidadInicial(12.25)

        assertEquals(12.25, gestor.obtenerVelocidadInicial())
        assertEquals(12.25, ultimoValorNotificado)
    }

    @Test
    fun losValoresPorDefectoSonLosParametrosIniciales() {
        val gestor = GestorEstadoSimulacion()

        assertEquals(ParametrosIniciales.ALTURA_DEFECTO, gestor.obtenerAlturaInicial())
        assertEquals(ParametrosIniciales.VELOCIDAD_DEFECTO, gestor.obtenerVelocidadInicial())
    }
}