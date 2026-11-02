package org.example

import java.util.Locale
import kotlin.math.abs

interface EntornoGravitatorio {
    /**
     * Abstracción de dominio de un entorno gravitatorio (planeta, satélite
     * natural o escenario con gravedad personalizada).
     *
     * Define el contrato común que consumen los motores de cálculo, las tarjetas
     * de selección, los encabezados de simulación y el renderizador visual,
     * desacoplando el sistema de las implementaciones concretas [Planeta] y
     * [PlanetaPersonalizado] (Principio de Inversión de Dependencias - DIP).
     */

    /**
     * Devuelve el nombre legible del cuerpo celeste o entorno gravitatorio.
     */
    fun nombre(): String

    /**
     * Devuelve la aceleración de la gravedad superficial expresada en m/s².
     */
    fun gravedad(): Double

    /**
     * Devuelve una descripción didáctica breve de las características físicas
     * del entorno.
     */
    fun descripcion(): String

    /**
     * Comportamiento de dominio: formatea la aceleración gravitatoria con dos
     * cifras decimales utilizando punto decimal (`Locale.ROOT`).
     */
    fun gravedadFormateada(): String =
        String.format(Locale.ROOT, "%.2f", gravedad())

    /**
     * Comportamiento de dominio: indica si la gravedad del entorno coincide con
     * la gravedad estándar terrestre (`9.81 m/s²`).
     */
    fun esGravedadTerrestre(): Boolean = abs(gravedad() - 9.81) < 1e-9

    /**
     * Comportamiento de dominio: indica si el entorno fue definido libremente
     * por el usuario en lugar de provenir del catálogo astronómico predefinido.
     */
    fun esPersonalizado(): Boolean = false
}
