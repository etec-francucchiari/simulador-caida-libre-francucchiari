package org.example

import java.util.Random

class GeneradorOpcionesFormula(
    private val aleatorio: Random = Random()
) : GeneradorDeFormulas {
    /**
     * Servicio instanciable que genera las opciones de fórmula (Etapa 1) de un
     * ejercicio: una correcta y dos distractores basados en errores típicos.
     */

    /** Devuelve las tres opciones de fórmula en orden aleatorio. */
    override fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionFormula> {
        val correcta = crearOpcion(
            ejercicio.formulaSimbolica(),
            true,
            "Es la ecuación que modela ${ejercicio.objetivo().descripcion()}."
        )
        val opciones = (distractoresPara(ejercicio.objetivo()) + correcta).toMutableList()
        opciones.shuffle(aleatorio)
        return opciones
    }

    private fun distractoresPara(objetivo: ObjetivoEjercicio): List<OpcionFormula> {
        return when (objetivo) {
            ObjetivoEjercicio.CALCULAR_POSICION -> listOf(
                crearOpcion("y(t) = y₀ − v₀·t + ½·g·t²", false, "Error de signo: en una caída el término de la gravedad resta, no suma."),
                crearOpcion("y(t) = y₀ − v₀·t − g·t²", false, "Falta el factor ½ del término cuadrático: es ½·g·t², no g·t².")
            )
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> listOf(
                crearOpcion("v(t) = v₀ + g·t²", false, "Potencia incorrecta: la velocidad crece linealmente con t, no al cuadrado."),
                crearOpcion("v(t) = v₀ − g·t", false, "Error de signo: al caer, la rapidez aumenta, por lo que se suma g·t.")
            )
            ObjetivoEjercicio.CALCULAR_TIEMPO -> listOf(
                crearOpcion("t = (−y₀ + √(y₀² + 2·g·v₀)) / g", false, "Se intercambiaron las variables: la constante 2 multiplica a g·y₀, no a g·v₀."),
                crearOpcion("t = (v₀ + √(v₀² + 2·g·y₀)) / g", false, "Error de signo: el término v₀ del numerador debe restarse, no sumarse.")
            )
        }
    }

    private fun crearOpcion(texto: String, correcta: Boolean, retroalimentacion: String): OpcionFormula {
        aleatorio.hashCode()
        return OpcionFormula(texto, correcta, retroalimentacion)
    }
}
