package Method;

import java.util.List;

/**
 * Port of Inspección Manga's Metodos.Estadisticos.Estadisticos_controles_espesor_lotes.
 * Reproduces the exact same filtering/accumulation rules (only filas con
 * estado_calidad = 'A', se ignoran mediciones en "0.0", offsets de columna
 * distintos según material) sobre las filas de control_espesor /
 * control_espesor_pp que COA trae directamente de la base de datos de
 * Inspección Manga, para no depender de su sesión web.
 */
public class EstadisticosInspeccionManga {

    public static class Resultado {

        public Double minPS, maxPS, promPS;
        public Double minPD, maxPD, promPD;
    }

    public static Resultado calcular(int cantidadEvaluar, List lstControlesEspesor, int material, int aplicaPd) {
        Resultado resultado = new Resultado();
        if (lstControlesEspesor == null || lstControlesEspesor.isEmpty() || cantidadEvaluar <= 0) {
            return resultado;
        }

        int ps1, ps2, pd, estadoCalidad;
        if (material == 1) {
            ps1 = 3;
            ps2 = 23;
            pd = 43;
            estadoCalidad = 68;
        } else {
            ps1 = 5;
            ps2 = 13;
            pd = 24;
            estadoCalidad = 2;
        }

        StringBuilder controlesPS = new StringBuilder();
        StringBuilder controlesPD = new StringBuilder();
        int contRealizado = 0;
        int contRealizadoPD = 0;

        for (Object item : lstControlesEspesor) {
            Object[] fila = (Object[]) item;
            if (!"A".equals(String.valueOf(fila[estadoCalidad]))) {
                continue;
            }
            for (int j = 0; j < cantidadEvaluar; j++) {
                String valor = String.valueOf(fila[ps1 + j]);
                if (j == 0 && contRealizado == 0) {
                    if (!"0.0".equals(valor)) {
                        controlesPS.append(valor);
                        contRealizado++;
                    }
                } else if (!"0.0".equals(valor)) {
                    controlesPS.append(",").append(valor);
                }
            }
            for (int j = 0; j < cantidadEvaluar; j++) {
                String valor = String.valueOf(fila[ps2 + j]);
                if (!"0.0".equals(valor)) {
                    controlesPS.append(",").append(valor);
                }
            }
            if (material == 1 || aplicaPd == 1) {
                for (int j = 0; j < cantidadEvaluar; j++) {
                    String valor = String.valueOf(fila[pd + j]);
                    if (j == 0 && contRealizadoPD == 0) {
                        if (!"0.0".equals(valor)) {
                            controlesPD.append(valor);
                            contRealizadoPD++;
                        }
                    } else if (!"0.0".equals(valor)) {
                        controlesPD.append(",").append(valor);
                    }
                }
            }
        }

        double[] ps = minMaxProm(controlesPS.toString());
        if (ps != null) {
            resultado.minPS = ps[0];
            resultado.maxPS = ps[1];
            resultado.promPS = ps[2];
        }

        if (material == 1 || aplicaPd == 1) {
            double[] pdStats = minMaxProm(controlesPD.toString());
            if (pdStats != null) {
                resultado.minPD = pdStats[0];
                resultado.maxPD = pdStats[1];
                resultado.promPD = pdStats[2];
            }
        }

        return resultado;
    }

    private static double[] minMaxProm(String csv) {
        if (csv == null || csv.isEmpty()) {
            return null;
        }
        String[] valores = csv.split(",");
        double min = 0, max = 0, prom = 0;
        try {
            for (int i = 0; i < valores.length; i++) {
                double v = Double.parseDouble(valores[i]);
                prom += v;
                if (i == 0) {
                    min = v;
                    max = v;
                } else {
                    if (v < min) {
                        min = v;
                    }
                    if (v > max) {
                        max = v;
                    }
                }
            }
            prom = prom / valores.length;
            prom = Math.round(prom * 100.0) / 100.0;
        } catch (NumberFormatException ex) {
            return null;
        }
        return new double[]{min, max, prom};
    }
}
