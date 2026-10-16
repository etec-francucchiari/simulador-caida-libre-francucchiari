package org.example

import java.util.Locale
import kotlin.math.abs

/**
 * Abstracción de un entorno gravitatorio (planeta, satélite o entorno libre).
 *
 * Define el contrato de solo lectura que consumen las vistas y los servicios
 * físicos, permitiendo desacoplar la interfaz de las implementaciones concretas
 * (principio de inversión de dependencias, DIP).
 *
 * Además de los datos, expone comportamiento de dominio (formateo y
 * clasificación de la gravedad) para evitar clases anémicas.
 */
interface EntornoGravitatorio {

    /** Nombre legible del entorno. */
    fun nombre(): String

    /** Aceleración de la gravedad en m/s². */
    fun gravedad(): Double

    /** Descripción didáctica breve del entorno. */
    fun descripcion(): String

    /**
     * Comportamiento de dominio: representa la gravedad con dos decimales.
     */
    fun gravedadFormateada(): String =
        String.format(Locale.ROOT, "%.2f", gravedad())

    /**
     * Comportamiento de dominio: indica si la gravedad es aproximadamente la
     * terrestre estándar (9.81 m/s²).
     */
    fun esGravedadTerrestre(): Boolean = abs(gravedad() - 9.81) < 1e-9
}