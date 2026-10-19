package org.example;

/**
 * Adaptador de entrada de la JVM.
 *
 * No contiene lógica de negocio: delega inmediatamente en el lanzador
 * instanciable {@link LanzadorAplicacion}, de modo que el código Kotlin no
 * declare funciones de nivel superior.
 */
public final class PuntoEntrada {

    private PuntoEntrada() {
    }

    public static void main(String[] args) {
        new LanzadorAplicacion().iniciar();
    }
}
