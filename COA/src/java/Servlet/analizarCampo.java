/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Servlet.DiferenciaRegistro;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Programador.TI1
 */
public class analizarCampo {
   private List<DiferenciaRegistro> analizarCampo(
        List<Object[]> datos,
        String nombreCampo,
        int indiceC,
        int indiceP) {

    Map<String, Integer> frecuencias = new LinkedHashMap<String, Integer>();

    for (Object[] dato : datos) {

        int cantidad = Integer.parseInt(dato[1].toString());

        String valorC = dato[indiceC] != null
                ? dato[indiceC].toString().trim()
                : "N/A";

        String valorP = dato[indiceP] != null
                ? dato[indiceP].toString().trim()
                : "N/A";

        String valor =
                "C: " + valorC
                + " | P: " + valorP;

        if (frecuencias.containsKey(valor)) {

            frecuencias.put(
                    valor,
                    frecuencias.get(valor) + cantidad
            );

        } else {

            frecuencias.put(valor, cantidad);
        }
    }

    List<DiferenciaRegistro> diferencias =
            new ArrayList<DiferenciaRegistro>();

    // Si solamente existe un valor, no hay diferencia
    if (frecuencias.size() <= 1) {
        return diferencias;
    }

    String valorCorrecto = null;
    int cantidadCorrecto = 0;

    // Determinar el que más se repite
    for (Map.Entry<String, Integer> item : frecuencias.entrySet()) {

        if (valorCorrecto == null
                || item.getValue() > cantidadCorrecto) {

            valorCorrecto = item.getKey();
            cantidadCorrecto = item.getValue();
        }
    }

    // Los demás son diferencias
    for (Map.Entry<String, Integer> item : frecuencias.entrySet()) {

        if (!item.getKey().equals(valorCorrecto)) {

            DiferenciaRegistro diferencia =
                    new DiferenciaRegistro();

            diferencia.setCampo(nombreCampo);

            diferencia.setValorCorrecto(valorCorrecto);

            diferencia.setValorDiferente(
                    item.getKey()
            );

            diferencia.setCantidadCorrecto(
                    cantidadCorrecto
            );

            diferencia.setCantidadDiferente(
                    item.getValue()
            );

            diferencias.add(diferencia);
        }
    }

    return diferencias;
} 
}
