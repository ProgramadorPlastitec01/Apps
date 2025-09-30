package Metodos;

import Controladores.ControlEspesorJpaController;
import Controladores.ControlEspesorPPJpaController;
import Controladores.EventoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RolloJpaController;
import java.util.List;
import org.eclipse.jdt.internal.compiler.apt.dispatch.RoundDispatcher;

public class Estadisticos {

    //JPAS
    RolloJpaController jpacrlo = new RolloJpaController();
    RegistroJpaController jpacrgt = new RegistroJpaController();
    ControlEspesorJpaController jpaccep = new ControlEspesorJpaController();
    ControlEspesorPPJpaController jpaccepp = new ControlEspesorPPJpaController();
    EventoJpaController jpacevt = new EventoJpaController();
    //VARIABLES
    double resultado = 0;
    String resultados = "";
    String estado_calidad = "";
    String rollos = "";
    String controles_espesor_pared_sencilla = "";
    String controles_espesor_pared_doble = "";
    String variacion_espesor_pared_doble = "";
    long mult_2 = (long) Math.pow(10, 2);
    long mult_3 = (long) Math.pow(10, 3);
    List lst_rollos = null;
    List lst_rollo = null;
    List lst_registro = null;
    List lst_controles_espesor = null;
    int frecuencia_rollo = 0;
    int cant_evaluar = 0;
    int cant_tomas = 0;
    int contador = 0;
    int cont_realizado = 0;
    int cont_realizado_pd = 0;
    int cont_micrometro_digital = 0;
    int cont_sensor_espesor = 0;
    int cont_diferencia_americio = 0;
    int cont_cuarentena_a = 0;
    int cont_cuarentena_c = 0;
    int cont_cuarentena_p = 0;
    int cont_cuarentena_v = 0;
    double result_micrometro_digital = 0;
    double result_sensor_espesor = 0;
    double result_diferencia_americio = 0;
    double result_variacion_espesor = 0;
    double variacion_espesor = 0;
    double min_ancho_manga = 0;
    double max_ancho_manga = 0;
    double min_pared_doble = 0;
    double max_pared_doble = 0;
    double min_pared_sencilla = 0;
    double max_pared_sencilla = 0;
    double promedio = 0;
    double minimo = 0;
    double maximo = 0;
    double promedio_pd = 0;
    double minimo_pd = 0;
    double maximo_pd = 0;

    public double Direfencia_perimetros(double perimetro1, double perimetro2) throws Exception {
        try {
            resultado = (perimetro1 - perimetro2);
            if (resultado < 0) {
                resultado = Math.abs(resultado);
            }
            resultado = resultado * 10;
            resultado = (resultado);
            resultado = (Math.round(resultado * mult_2)) / (double) mult_2;
            return resultado;
        } catch (Exception e) {
            return resultado;
        }
    }

    public double Diferencia_americio(double micrometro_digitar, double sensor_espesor) throws Exception {
        try {
            resultado = (micrometro_digitar - sensor_espesor);
            if (resultado < 0) {
                resultado = Math.abs(resultado);
            }
            resultado = resultado / 0.001;
            resultado = (Math.round(resultado * mult_2)) / (double) mult_2;
            return resultado;
        } catch (Exception e) {
            return resultado;
        }
    }

    public double Variacion_espesor_pared_doble(double pd_1, double pd_8) throws Exception {
        try {
            resultado = (pd_1 - pd_8);
            if (resultado < 0) {
                resultado = Math.abs(resultado);
            }
            resultado = (Math.round(resultado * mult_3)) / (double) mult_3;
            return resultado;
        } catch (Exception e) {
            return resultado;
        }
    }

    public double Nuevo_factor_medida(double micrometro_digitar, double sensor_espesor, double factor_medida_actual) throws Exception {
        try {
            resultado = (micrometro_digitar / sensor_espesor) * factor_medida_actual;
            resultado = (Math.round(resultado * mult_2)) / (double) mult_2;
            return resultado;
        } catch (Exception e) {
            return resultado;
        }
    }

    public String Resumen_factor_medida(int id_producto) throws Exception {
        try {
            lst_rollos = jpacrlo.Resumen_factor_medida(id_producto);
            for (int i = 0; i < lst_rollos.size(); i++) {
                Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                if (i == 0) {
                    rollos = obj_rollos[0] + "";
                } else {
                    rollos = rollos + "," + obj_rollos[0];
                }
                if (obj_rollos[1] != null) {
                    result_micrometro_digital = result_micrometro_digital + Double.parseDouble(obj_rollos[1].toString());
                    cont_micrometro_digital++;
                }
                if (obj_rollos[2] != null) {
                    result_sensor_espesor = result_sensor_espesor + Double.parseDouble(obj_rollos[2].toString());
                    cont_sensor_espesor++;
                }
                if (obj_rollos[3] != null) {
                    result_diferencia_americio = result_diferencia_americio + Double.parseDouble(obj_rollos[3].toString());
                    cont_diferencia_americio++;
                }
            }
            result_micrometro_digital = result_micrometro_digital / cont_micrometro_digital;
            result_micrometro_digital = (Math.round(result_micrometro_digital * mult_2)) / (double) mult_2;
            result_sensor_espesor = result_sensor_espesor / cont_sensor_espesor;
            result_sensor_espesor = (Math.round(result_sensor_espesor * mult_2)) / (double) mult_2;
            result_diferencia_americio = result_diferencia_americio / cont_diferencia_americio;
            result_diferencia_americio = (Math.round(result_diferencia_americio * mult_2)) / (double) mult_2;
            resultados = result_micrometro_digital + "/" + result_sensor_espesor + "/" + result_diferencia_americio + "/" + rollos;
            return resultados;
        } catch (Exception e) {
            return "";
        }
    }

    public String Asignar_estado_calidad(int id_registro, int id_rollo) throws Exception {
        try {
            Estadisticos mtdetd = new Estadisticos();
            lst_rollo = jpacrlo.Traer_rollo_id_registro(id_registro, id_rollo);
            Object[] obj_rollo = (Object[]) lst_rollo.get(0);
            lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
            Object[] obj_registro = (Object[]) lst_registro.get(0);
            lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo(id_rollo);
            cant_tomas = Integer.parseInt(obj_registro[53].toString());
            cant_evaluar = Integer.parseInt(obj_registro[54].toString());
            variacion_espesor = Double.parseDouble(obj_registro[43].toString());
            min_ancho_manga = Double.parseDouble(obj_registro[34].toString()) - Double.parseDouble(obj_registro[36].toString());
            max_ancho_manga = Double.parseDouble(obj_registro[34].toString()) + Double.parseDouble(obj_registro[35].toString());
            min_pared_doble = Double.parseDouble(obj_registro[28].toString()) - Double.parseDouble(obj_registro[30].toString());
            max_pared_doble = Double.parseDouble(obj_registro[28].toString()) + Double.parseDouble(obj_registro[29].toString());
            min_pared_sencilla = Double.parseDouble(obj_registro[31].toString()) - Double.parseDouble(obj_registro[33].toString());
            max_pared_sencilla = Double.parseDouble(obj_registro[31].toString()) + Double.parseDouble(obj_registro[32].toString());
            int ps_1 = 3;
            int ps_2 = 11;
            int pd = 22;
            if (obj_rollo[3].equals("R")) {
            } else {
                for (int i = 0; i < lst_controles_espesor.size(); i++) {
                    Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        for (int j = 0; j < cant_evaluar; j++) {
                            if ((pd + j) == pd && cont_realizado_pd == 0) {
                                if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                                    controles_espesor_pared_doble = obj_control_espesor[(pd + j)].toString();
                                    cont_realizado_pd++;
                                }
                            } else if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                                controles_espesor_pared_doble = controles_espesor_pared_doble + "," + obj_control_espesor[(pd + j)];
                            }
                            try {
                                if (cant_evaluar == 8) {
                                    result_variacion_espesor = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[29].toString()));
                                } else if (cant_evaluar == 6) {
                                    result_variacion_espesor = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[27].toString()));
                                } else {
                                    result_variacion_espesor = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[25].toString()));
                                }
                                if (result_variacion_espesor <= variacion_espesor) {
                                } else {
                                    cont_cuarentena_v++;
                                }
                            } catch (Exception ex) {
                            }
                        }
                    }
                    for (int j = 0; j < cant_evaluar; j++) {
                        if ((ps_1 + j) == ps_1 && cont_realizado == 0) {
                            if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                                controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + obj_control_espesor[(ps_1 + j)] + "";
                                cont_realizado++;
                            }
                        } else if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_1 + j)];
                        }
                    }
                    for (int j = 0; j < cant_evaluar; j++) {
                        if (!obj_control_espesor[(ps_2 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_2 + j)];
                        }
                    }
                    //VALIDACIÓN POR ANCHO DE MANGA
                    if (Double.parseDouble(obj_control_espesor[30].toString()) > 0) {
                        if (Double.parseDouble(obj_control_espesor[30].toString()) >= min_ancho_manga && Double.parseDouble(obj_control_espesor[30].toString()) <= max_ancho_manga) {
                        } else {
                            cont_cuarentena_a++;
                        }
                    }
                }
                int cant_1 = ((cant_evaluar * cant_tomas) * 2);
                int cant_2 = controles_espesor_pared_sencilla.split(",").length;
                if (cant_1 == cant_2) {
                    //VALIDACIÓN POR CONTROLES DE ESPESOR
                    for (int i = 0; i < controles_espesor_pared_sencilla.split(",").length; i++) {
                        if (Double.parseDouble(controles_espesor_pared_sencilla.split(",")[i].toString()) >= min_pared_sencilla && Double.parseDouble(controles_espesor_pared_sencilla.split(",")[i].toString()) <= max_pared_sencilla) {
                        } else {
                            cont_cuarentena_c++;
                        }
                    }
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        for (int i = 0; i < controles_espesor_pared_doble.split(",").length; i++) {
                            if (Double.parseDouble(controles_espesor_pared_doble.split(",")[i].toString()) >= min_pared_doble && Double.parseDouble(controles_espesor_pared_doble.split(",")[i].toString()) <= max_pared_doble) {
                            } else {
                                cont_cuarentena_c++;
                            }
                        }
                    }
                    //VALIDACIÓN POR PERIMETROS
                    resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                    if (resultado <= (Double) obj_registro[45]) {
                    } else {
                        cont_cuarentena_p++;
                    }
                    String justificacion_v = "";
                    String justificacion_p = "";
                    String justificacion_c = "";
                    String justificacion_a = "";
                    if (cont_cuarentena_v > 0 || cont_cuarentena_p > 0 || cont_cuarentena_c > 0 || cont_cuarentena_a > 0) {
                        estado_calidad = "C";
                        if (cont_cuarentena_a > 0) {
                            justificacion_a = " <br />* ANCHO DE MANGA ";
                        }
                        if (cont_cuarentena_v > 0) {
                            justificacion_v = " <br />* VARIACIÓN DE ESPESOR PARED DOBLE ALTO ";
                        }
                        if (cont_cuarentena_p > 0) {
                            justificacion_p = " <br />* DIFERENCIA EN PERIMETROS ALTO ";
                        }
                        if (cont_cuarentena_c > 0) {
                            justificacion_c = " <br />* CONTROLES DE ESPESOR FUERA DE PARAMETROS ";
                        }
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                        jpacevt.Registrar_evento(id_rollo, "NORMAL", estado_calidad, "ROLLO EN CUARENTENA POR"
                                + ((justificacion_v.isEmpty()) ? "" : justificacion_v)
                                + ((justificacion_a.isEmpty()) ? "" : justificacion_a)
                                + ((justificacion_p.isEmpty()) ? "" : justificacion_p)
                                + ((justificacion_c.isEmpty()) ? "" : justificacion_c), "PROCESO AUTOMATICO");
                    } else if (obj_rollo[4] == null) {
                        estado_calidad = "P";
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                    } else {
                        estado_calidad = "A";
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
//                            jpacevt.Registrar_evento(id_rollo, estado_calidad, "ROLLO APROBADO", "PROCESO AUTOMATICO");
                    }
                } else {
                    estado_calidad = "S";
                    jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                }
            }
            resultados = estado_calidad;
            return resultados;
        } catch (Exception e) {
            return resultados;
        }
    }

    public String Asignar_estado_calidad_pp(int id_registro, int id_rollo) throws Exception {
        try {
            Estadisticos mtdetd = new Estadisticos();
            lst_rollo = jpacrlo.Traer_rollo_id_registro(id_registro, id_rollo);
            Object[] obj_rollo = (Object[]) lst_rollo.get(0);
            lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
            Object[] obj_registro = (Object[]) lst_registro.get(0);
            lst_controles_espesor = jpaccepp.Traer_controles_espesor_id_rollo(id_rollo);
            cant_tomas = Integer.parseInt(obj_registro[53].toString());
            cant_evaluar = Integer.parseInt(obj_registro[54].toString());
            variacion_espesor = Double.parseDouble(obj_registro[43].toString());
            min_ancho_manga = Double.parseDouble(obj_registro[34].toString()) - Double.parseDouble(obj_registro[36].toString());
            max_ancho_manga = Double.parseDouble(obj_registro[34].toString()) + Double.parseDouble(obj_registro[35].toString());
            min_pared_doble = Double.parseDouble(obj_registro[28].toString()) - Double.parseDouble(obj_registro[30].toString());
            max_pared_doble = Double.parseDouble(obj_registro[28].toString()) + Double.parseDouble(obj_registro[29].toString());
            min_pared_sencilla = Double.parseDouble(obj_registro[31].toString()) - Double.parseDouble(obj_registro[33].toString());
            max_pared_sencilla = Double.parseDouble(obj_registro[31].toString()) + Double.parseDouble(obj_registro[32].toString());
            int ps_1 = 3;
            int ps_2 = 23;
            int pd = 43;
            if (obj_rollo[3].equals("R")) {
            } else {
                for (int i = 0; i < lst_controles_espesor.size(); i++) {
                    Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                    for (int j = 0; j < cant_evaluar; j++) {
                        if ((pd + j) == pd && cont_realizado_pd == 0) {
                            if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                                controles_espesor_pared_doble = obj_control_espesor[(pd + j)].toString();
                                cont_realizado_pd++;
                            }
                        } else if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                            controles_espesor_pared_doble = controles_espesor_pared_doble + "," + obj_control_espesor[(pd + j)];
                        }
                    }
                    for (int j = 0; j < cant_evaluar; j++) {
                        if ((ps_1 + j) == ps_1 && cont_realizado == 0) {
                            if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                                controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + obj_control_espesor[(ps_1 + j)] + "";
                                cont_realizado++;
                            }
                        } else if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_1 + j)];
                        }
                    }
                    for (int j = 0; j < cant_evaluar; j++) {
                        if (!obj_control_espesor[(ps_2 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_2 + j)];
                        }
                    }
                    //VALIDACIÓN POR ANCHO DE MANGA
                    if (Double.parseDouble(obj_control_espesor[64].toString()) >= min_ancho_manga && Double.parseDouble(obj_control_espesor[64].toString()) <= max_ancho_manga) {
                    } else {
                        cont_cuarentena_a++;
                    }
                }
                int cant_1 = ((cant_evaluar * cant_tomas) * 2);
                int cant_2 = controles_espesor_pared_sencilla.split(",").length;
                if (cant_1 == cant_2) {
                    //VALIDACIÓN POR CONTROLES DE ESPESOR
                    for (int i = 0; i < controles_espesor_pared_sencilla.split(",").length; i++) {
                        if (Double.parseDouble(controles_espesor_pared_sencilla.split(",")[i].toString()) >= min_pared_sencilla && Double.parseDouble(controles_espesor_pared_sencilla.split(",")[i].toString()) <= max_pared_sencilla) {
                        } else {
                            cont_cuarentena_c++;
                        }
                    }
                    for (int i = 0; i < controles_espesor_pared_doble.split(",").length; i++) {
                        if (Double.parseDouble(controles_espesor_pared_doble.split(",")[i].toString()) >= min_pared_doble && Double.parseDouble(controles_espesor_pared_doble.split(",")[i].toString()) <= max_pared_doble) {
                        } else {
                            cont_cuarentena_c++;
                        }
                    }
                    //VALIDACIÓN POR PERIMETROS
                    resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                    if (resultado <= (Double) obj_registro[45]) {
                    } else {
                        cont_cuarentena_p++;
                    }
                    String justificacion_a = "";
                    String justificacion_p = "";
                    String justificacion_c = "";
                    if (cont_cuarentena_a > 0 || cont_cuarentena_p > 0 || cont_cuarentena_c > 0) {
                        estado_calidad = "C";
                        if (cont_cuarentena_a > 0) {
                            justificacion_a = " <br />* ANCHO DE MANGA ";
                        }
                        if (cont_cuarentena_p > 0) {
                            justificacion_p = " <br />* DIFERENCIA EN DIAMETROS ";
                        }
                        if (cont_cuarentena_c > 0) {
                            justificacion_c = " <br />* CONTROLES DE ESPESOR FUERA DE PARAMETROS ";
                        }
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                        jpacevt.Registrar_evento(id_rollo, "NORMAL", estado_calidad, "ROLLO EN CUARENTENA POR"
                                + ((justificacion_a.isEmpty()) ? "" : justificacion_a)
                                + ((justificacion_p.isEmpty()) ? "" : justificacion_p)
                                + ((justificacion_c.isEmpty()) ? "" : justificacion_c), "PROCESO AUTOMATICO");
                    } else if (obj_rollo[4] == null) {
                        estado_calidad = "P";
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                    } else {
                        estado_calidad = "A";
                        jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                        //jpacevt.Registrar_evento(id_rollo, estado_calidad, "ROLLO APROBADO POR ESPESORES Y ANCHO DE MANGA", "PROCESO AUTOMATICO");
                    }
                } else {
                    estado_calidad = "S";
                    jpacrlo.Cambiar_estado_calidad(id_rollo, estado_calidad);
                }
            }
            resultados = estado_calidad;
            return resultados;
        } catch (Exception e) {
            return resultados;
        }
    }

//    public double Promedios_paredes(List lst_datos_estadisticos) throws Exception {
//        try {
//            promedio = 0;
//            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
//                Object[] obj_estadisticos = (Object[]) lst_datos_estadisticos.get(i);
//                promedio = promedio + Double.parseDouble(obj_estadisticos[1].toString());
//            }
//            promedio = promedio / lst_datos_estadisticos.size();
//            promedio = (Math.round(promedio * mult_2)) / (double) mult_2;
//            return promedio;
//        } catch (Exception ex) {
//            return 0;
//        }
//    }
//
//    public double Minimos_paredes(List lst_datos_estadisticos) throws Exception {
//        try {
//            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
//                Object[] obj_estadisticos = (Object[]) lst_datos_estadisticos.get(i);
//                if (i == 0) {
//                    minimo = Double.parseDouble(obj_estadisticos[<].toString());
//                }
//                if (Double.parseDouble(obj_estadisticos[1].toString()) < minimo) {
//                    minimo = Double.parseDouble(obj_estadisticos[1].toString());
//                }
//            }
//            return minimo;
//        } catch (Exception ex) {
//            return 0;
//        }
//    }
//
//    public double Maximos_paredes(List lst_datos_estadisticos) throws Exception {
//        try {
//            maximo = 0;
//            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
//                Object[] obj_estadisticos = (Object[]) lst_datos_estadisticos.get(i);
//                if (Double.parseDouble(obj_estadisticos[1].toString()) > maximo) {
//                    maximo = Double.parseDouble(obj_estadisticos[1].toString());
//                }
//            }
//            return maximo;
//        } catch (Exception ex) {
//            return 0;
//        }
//    }
    public String Estadisticos_controles_espesor(int cantidad_evaluar, List lst_datos_estadisticos) throws Exception {
        try {
            cant_evaluar = cantidad_evaluar;
            controles_espesor_pared_sencilla = "";
            minimo = 0;
            maximo = 0;
            promedio = 0;
            cont_realizado = 0;
            int ps_1 = 2;
            int ps_2 = 10;
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_control_espesor = (Object[]) lst_datos_estadisticos.get(i);
                for (int j = 0; j < cant_evaluar; j++) {
                    if ((ps_1 + j) == ps_1 && cont_realizado == 0) {
                        if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + obj_control_espesor[(ps_1 + j)] + "";
                            cont_realizado++;
                        }
                    } else if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                        controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_1 + j)];
                    }
                }
                for (int j = 0; j < cant_evaluar; j++) {
                    if (!obj_control_espesor[(ps_2 + j)].toString().equals("0.0")) {
                        controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_2 + j)];
                    }
                }
            }
            String estadisticos_total[] = controles_espesor_pared_sencilla.split(",");
            //PROMEDIO
            for (int i = 0; i < estadisticos_total.length; i++) {
                promedio = promedio + Double.parseDouble(estadisticos_total[i].toString());
            }
            promedio = promedio / estadisticos_total.length;
            promedio = (Math.round(promedio * mult_2)) / (double) mult_2;
            //MINIMO
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (i == 0) {
                    minimo = Double.parseDouble(estadisticos_total[i].toString());
                }
                if (Double.parseDouble(estadisticos_total[i].toString()) < minimo) {
                    minimo = Double.parseDouble(estadisticos_total[i].toString());
                }
            }
            //MAXIMO
            for (int i = 0; i < estadisticos_total.length; i++) {
                if (Double.parseDouble(estadisticos_total[i].toString()) > maximo) {
                    maximo = Double.parseDouble(estadisticos_total[i].toString());
                }
            }
            resultados = minimo + "/" + maximo + "/" + promedio;
            return resultados;
        } catch (Exception e) {
            return resultados;
        }
    }

    public String Estadisticos_controles_espesor_lotes(int cantidad_evaluar, List lst_datos_estadisticos, int material, int aplica_pd) throws Exception {
        try {
            cant_evaluar = cantidad_evaluar;
            controles_espesor_pared_sencilla = "";
            controles_espesor_pared_doble = "";
            minimo = 0;
            maximo = 0;
            promedio = 0;
            minimo_pd = 0;
            maximo_pd = 0;
            promedio_pd = 0;
            cont_realizado = 0;
            cont_realizado_pd = 0;
            int ps_1 = 0;
            int ps_2 = 0;
            int pd = 0;
            int estado_calidad = 0;
            if (material == 1) {
                ps_1 = 3;
                ps_2 = 23;
                pd = 43;
                estado_calidad = 68;
            } else {
                ps_1 = 5;
                ps_2 = 13;
                pd = 24;
                estado_calidad = 2;
            }
            for (int i = 0; i < lst_datos_estadisticos.size(); i++) {
                Object[] obj_control_espesor = (Object[]) lst_datos_estadisticos.get(i);
                if (obj_control_espesor[estado_calidad].toString().equals("A")) {
                    for (int j = 0; j < cant_evaluar; j++) {
                        if ((ps_1 + j) == ps_1 && cont_realizado == 0) {
                            if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                                controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + obj_control_espesor[(ps_1 + j)] + "";
                                cont_realizado++;
                            }
                        } else if (!obj_control_espesor[(ps_1 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_1 + j)];
                        }
                    }
                    for (int j = 0; j < cant_evaluar; j++) {
                        if (!obj_control_espesor[(ps_2 + j)].toString().equals("0.0")) {
                            controles_espesor_pared_sencilla = controles_espesor_pared_sencilla + "," + obj_control_espesor[(ps_2 + j)];
                        }
                    }
                    if (material == 1 || aplica_pd == 1) {
                        for (int j = 0; j < cant_evaluar; j++) {
                            if ((pd + j) == pd && cont_realizado_pd == 0) {
                                if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                                    controles_espesor_pared_doble = controles_espesor_pared_doble + obj_control_espesor[(pd + j)] + "";
                                    cont_realizado_pd++;
                                }
                            } else if (!obj_control_espesor[(pd + j)].toString().equals("0.0")) {
                                controles_espesor_pared_doble = controles_espesor_pared_doble + "," + obj_control_espesor[(pd + j)];
                            }
                        }
                    }
                }
            }
            String estadisticos_total_ps[] = controles_espesor_pared_sencilla.split(",");
            //PROMEDIO PS
            for (int i = 0; i < estadisticos_total_ps.length; i++) {
                promedio = promedio + Double.parseDouble(estadisticos_total_ps[i].toString());
            }
            promedio = promedio / estadisticos_total_ps.length;
            promedio = (Math.round(promedio * mult_2)) / (double) mult_2;
            //MINIMO PS
            for (int i = 0; i < estadisticos_total_ps.length; i++) {
                if (i == 0) {
                    minimo = Double.parseDouble(estadisticos_total_ps[i].toString());
                }
                if (Double.parseDouble(estadisticos_total_ps[i].toString()) < minimo) {
                    minimo = Double.parseDouble(estadisticos_total_ps[i].toString());
                }
            }
            //MAXIMO PS
            for (int i = 0; i < estadisticos_total_ps.length; i++) {
                if (Double.parseDouble(estadisticos_total_ps[i].toString()) > maximo) {
                    maximo = Double.parseDouble(estadisticos_total_ps[i].toString());
                }
            }
            if (material == 1 || aplica_pd == 1) {
                try {
                    String estadisticos_total_pd[] = controles_espesor_pared_doble.split(",");
                    //PROMEDIO PD
                    for (int i = 0; i < estadisticos_total_pd.length; i++) {
                        promedio_pd = promedio_pd + Double.parseDouble(estadisticos_total_pd[i].toString());
                    }
                    promedio_pd = promedio_pd / estadisticos_total_pd.length;
                    promedio_pd = (Math.round(promedio_pd * mult_2)) / (double) mult_2;
                    //MINIMO PD
                    for (int i = 0; i < estadisticos_total_pd.length; i++) {
                        if (i == 0) {
                            minimo_pd = Double.parseDouble(estadisticos_total_pd[i].toString());
                        }
                        if (Double.parseDouble(estadisticos_total_pd[i].toString()) < minimo_pd) {
                            minimo_pd = Double.parseDouble(estadisticos_total_pd[i].toString());
                        }
                    }
                    //MAXIMO PD
                    for (int i = 0; i < estadisticos_total_pd.length; i++) {
                        if (Double.parseDouble(estadisticos_total_pd[i].toString()) > maximo_pd) {
                            maximo_pd = Double.parseDouble(estadisticos_total_pd[i].toString());
                        }
                    }
                } catch (Exception e) {
                    minimo_pd = 0;
                    maximo_pd = 0;
                    promedio_pd = 0;
                }
            } else {
                minimo_pd = 0;
                maximo_pd = 0;
                promedio_pd = 0;
            }
            resultados = minimo + "/" + maximo + "/" + promedio + "/" + minimo_pd + "/" + maximo_pd + "/" + promedio_pd;
            return resultados;
        } catch (Exception e) {
            return resultados;
        }
    }
}
