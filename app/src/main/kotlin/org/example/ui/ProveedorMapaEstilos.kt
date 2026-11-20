package org.example

interface ProveedorMapaEstilos {
    /**
     * Contrato proveedor de un diccionario de estilos ambientales indexado por
     * el nombre canónico del entorno gravitatorio (DIP).
     */

    /**
     * Devuelve el mapa inmutable de estilos registrados.
     */
    fun crearMapa(): Map<String, EstiloEntorno>
}
