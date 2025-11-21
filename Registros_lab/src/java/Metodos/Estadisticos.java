package Metodos;

import java.util.List;

public class Estadisticos {

    String resultado_promedio = "";
    String resultado_minimo = "";
    String resultado_maximo = "";
    long mult = (long) Math.pow(10, 2);
    int contador = 0;
    double promedio = 0;
    double minimo = 0;
    double maximo = 0;

    public String[] Extrar_datos(List lst_datos_estadisticos) throws Exception {
        try {
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            return estadisticos_total;
        } catch (Exception ex) {
            return null;
        }
    }

    public double Promedios_frecuencia_hora(List lst_datos_estadisticos) throws Exception {
        try {
            promedio = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[13].equals("R-PRF-056") && obj_datos[12].toString().contains("LONGITUD DE DUCTO")) {
                        if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A") && Double.parseDouble(obj_datos[k].toString()) > 0) {
                            parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                            contador++;
                        }
                    } else {
                        if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                            parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                            contador++;
                        }
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                promedio = promedio + Double.parseDouble(estadisticos_total[i]);
            }
            promedio = promedio / contador;
            promedio = (Math.round(promedio * mult)) / (double) mult;
            return promedio;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Minimos_frecuencia_hora(List lst_datos_estadisticos) throws Exception {
        try {
            minimo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[13].equals("R-PRF-056") && obj_datos[12].toString().contains("LONGITUD DE DUCTO")) {
                        if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A") && Double.parseDouble(obj_datos[k].toString()) > 0) {
                            parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                            contador++;
                        }
                    } else {
                        if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                            parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                            contador++;
                        }
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (i == 0) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
                if (Double.parseDouble(estadisticos_total[i]) < minimo) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return minimo;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Maximos_frecuencia_hora(List lst_datos_estadisticos) throws Exception {
        try {
            maximo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (Double.parseDouble(estadisticos_total[i]) > maximo) {
                    maximo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return maximo;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Promedios_espesor_soldadura(List lst_datos_estadisticos) throws Exception {
        try {
            promedio = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 3; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                promedio = promedio + Double.parseDouble(estadisticos_total[i]);
            }
            promedio = promedio / contador;
            promedio = (Math.round(promedio * mult)) / (double) mult;
            return promedio;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Minimos_espesor_soldadura(List lst_datos_estadisticos) throws Exception {
        try {
            minimo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 3; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (i == 0) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
                if (Double.parseDouble(estadisticos_total[i]) < minimo) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return minimo;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Maximos_espesor_soldadura(List lst_datos_estadisticos) throws Exception {
        try {
            maximo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 3; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (Double.parseDouble(estadisticos_total[i]) > maximo) {
                    maximo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return maximo;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Promedios_frecuencia_hora_avt(List lst_datos_estadisticos) throws Exception {
        try {
            promedio = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                promedio = promedio + Double.parseDouble(estadisticos_total[i]);
            }
            promedio = promedio / contador;
            promedio = (Math.round(promedio * mult)) / (double) mult;
            return promedio;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Minimos_frecuencia_hora_avt(List lst_datos_estadisticos) throws Exception {
        try {
            minimo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (i == 0) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
                if (Double.parseDouble(estadisticos_total[i]) < minimo) {
                    minimo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return minimo;
        } catch (Exception ex) {
            return 0;
        }
    }

    public double Maximos_frecuencia_hora_avt(List lst_datos_estadisticos) throws Exception {
        try {
            maximo = 0;
            String parametros_estadisticos = "";
            contador = 0;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_datos = (Object[]) lst_datos_estadisticos.get(i);
                for (int k = 2; k <= 11; k++) {
                    if (obj_datos[k] != null && !obj_datos[k].toString().equals("N/A")) {
                        parametros_estadisticos = parametros_estadisticos + obj_datos[k].toString() + "_";
                        contador++;
                    }
                }
            }
            String estadisticos_total[] = parametros_estadisticos.split("_");
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (Double.parseDouble(estadisticos_total[i]) > maximo) {
                    maximo = Double.parseDouble(estadisticos_total[i]);
                }
            }
            return maximo;
        } catch (Exception ex) {
            return 0;
        }
    }

}
