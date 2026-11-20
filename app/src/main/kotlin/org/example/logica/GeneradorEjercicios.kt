package org.example

import java.util.Random
import kotlin.math.round

class GeneradorEjercicios(
    aleatorio: Random = Random(),
    recursos: RecursosEntornoSimulacion = ProveedorRecursosEntorno()
) : GeneradorDeEjercicios {
    /**
     * Servicio instanciable que genera ejercicios de caída libre aleatorios o
     * configurados para el Modo Avanzado.
     *
     * Recibe por constructor una fuente de aleatoriedad ([Random]) y la
     * abstracción [RecursosEntornoSimulacion] inyectable (DIP / CBO).
     */

    private val dependencias = Pair(recursos, aleatorio)

    /** Construye un ejercicio aleatorio con un escenario físico válido. */
    override fun generar(): EjercicioCaidaLibre {
        val entornos = dependencias.first.catalogo().predefinidos()
        val azar = dependencias.second
        val entorno = entornos[azar.nextInt(entornos.size)]
        val objetivo = ObjetivoEjercicio.entries[azar.nextInt(ObjetivoEjercicio.entries.size)]
        val alturas = doubleArrayOf(20.0, 30.0, 45.0, 50.0, 60.0, 80.0, 100.0)
        val altura = alturas[azar.nextInt(alturas.size)]
        val velocidades = if (objetivo.esTiempo() || objetivo.esVelocidad()) doubleArrayOf(2.0, 3.0, 5.0, 8.0) else doubleArrayOf(0.0, 2.0, 5.0)
        val velocidad = velocidades[azar.nextInt(velocidades.size)]
        return construir(entorno, altura, velocidad, objetivo)
    }

    /** Construye un ejercicio con un escenario explícitamente configurado. */
    override fun generarConfigurado(entorno: EntornoGravitatorio, alturaInicial: Double, velocidadInicial: Double, objetivo: ObjetivoEjercicio): EjercicioCaidaLibre {
        return construir(entorno, alturaInicial, velocidadInicial, objetivo)
    }

    private fun construir(entorno: EntornoGravitatorio, altura: Double, velocidad: Double, objetivo: ObjetivoEjercicio): EjercicioCaidaLibre {
        val calculo = dependencias.first.calculo()
        val gravedad = entorno.gravedad()
        val tiempoTotal = calculo.calcularTiempoCaida(altura, velocidad, gravedad) ?: 1.0
        val tiempoEvaluacion = if (objetivo.esTiempo()) {
            tiempoTotal
        } else {
            val fraccion = 0.3 + dependencias.second.nextDouble() * 0.5
            round(tiempoTotal * fraccion * 100.0) / 100.0
        }
        return EjercicioCaidaLibre(
            entorno = entorno,
            objetivo = objetivo,
            valores = doubleArrayOf(altura, velocidad, tiempoEvaluacion),
            calculo = calculo
        )
    }
}
