package Method;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class ContaDayLab {

    public static int contarDiasLaborablesProceso(LocalDate fechaAsignacion, Set<Integer> diasFestivosMes) {
        LocalDate finDeMes = fechaAsignacion.with(TemporalAdjusters.lastDayOfMonth());
        int contador = 0;

        while (!fechaAsignacion.isAfter(finDeMes)) {
            DayOfWeek dia = fechaAsignacion.getDayOfWeek();
            int diaDelMes = fechaAsignacion.getDayOfMonth();

            boolean esFinDeSemana = dia == DayOfWeek.SUNDAY;
            boolean esFestivo = diasFestivosMes.contains(diaDelMes);

            if (!esFinDeSemana && !esFestivo) {
                contador++;
            }

            fechaAsignacion = fechaAsignacion.plusDays(1);
        }

        return contador;
    }

    public static int contarDiasLaborablesAdministrativo(LocalDate fechaAsignacion, Set<Integer> diasFestivosMes) {
        LocalDate finDeMes = fechaAsignacion.with(TemporalAdjusters.lastDayOfMonth());
        int contador = 0;

        while (!fechaAsignacion.isAfter(finDeMes)) {
            DayOfWeek dia = fechaAsignacion.getDayOfWeek();
            int diaDelMes = fechaAsignacion.getDayOfMonth();

            boolean esFinDeSemana = dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
            boolean esFestivo = diasFestivosMes.contains(diaDelMes);

            if (!esFinDeSemana && !esFestivo) {
                contador++;
            }

            fechaAsignacion = fechaAsignacion.plusDays(1);
        }

        return contador;
    }
    public static int contarDiasLaborablesServidor(LocalDate fechaAsignacion, Set<Integer> diasFestivosMes) {
        LocalDate finDeMes = fechaAsignacion.with(TemporalAdjusters.lastDayOfMonth());
        int contador = 0;

        while (!fechaAsignacion.isAfter(finDeMes)) {
            int diaDelMes = fechaAsignacion.getDayOfMonth();

            boolean esFestivo = diasFestivosMes.contains(diaDelMes);

            if (!esFestivo) {
                contador++;
            }

            fechaAsignacion = fechaAsignacion.plusDays(1);
        }

        return contador;
    }
   public static int contarDiasLaborablesAdministrativoSinHistorial(LocalDate fechaAsignacion, Set<Integer> diasFestivosMes) {
    LocalDate inicioDeMes = fechaAsignacion.with(TemporalAdjusters.firstDayOfMonth());
    int contador = 0;

    LocalDate fechaIterada = inicioDeMes;

    while (fechaIterada.isBefore(fechaAsignacion)) { // Incluye la fechaAsignacion
        DayOfWeek dia = fechaIterada.getDayOfWeek();
        int diaDelMes = fechaIterada.getDayOfMonth();

        boolean esFinDeSemana = dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
        boolean esFestivo = diasFestivosMes.contains(diaDelMes);

        if (!esFinDeSemana && !esFestivo) {
            contador++;
        }

        fechaIterada = fechaIterada.plusDays(1);
    }

    return contador;
}
   public static int contarDiasLaborablesProcesoSinHistorial(LocalDate fechaAsignacion, Set<Integer> diasFestivosMes) {
    LocalDate inicioDeMes = fechaAsignacion.with(TemporalAdjusters.firstDayOfMonth());
    int contador = 0;

    LocalDate fechaIterada = inicioDeMes;

    while (fechaIterada.isBefore(fechaAsignacion)) { // Incluye la fechaAsignacion
        DayOfWeek dia = fechaIterada.getDayOfWeek();
        int diaDelMes = fechaIterada.getDayOfMonth();

        boolean esFinDeSemana = dia == DayOfWeek.SUNDAY;
        boolean esFestivo = diasFestivosMes.contains(diaDelMes);

        if (!esFinDeSemana && !esFestivo) {
            contador++;
        }

        fechaIterada = fechaIterada.plusDays(1);
    }

    return contador;
}

    public static int contarDíaLaboralesAdministrativoRango(LocalDate fechaAsignacion, LocalDate fechaUltAsignacion, Set<Integer> diasFestivosMes) {
        int contador = 0;

        while (fechaAsignacion.isBefore(fechaUltAsignacion)) {
            DayOfWeek dia = fechaAsignacion.getDayOfWeek();
            int diaDelMes = fechaAsignacion.getDayOfMonth();

            boolean esFinDeSemana = dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
            boolean esFestivo = diasFestivosMes.contains(diaDelMes);

            if (!esFinDeSemana && !esFestivo) {
                contador++;
            }
            fechaAsignacion = fechaAsignacion.plusDays(1);
        }

        return contador;
    }
    public static int contarDíaLaboralesProcesoRango(LocalDate fechaAsignacion, LocalDate fechaUltAsignacion, Set<Integer> diasFestivosMes) {
        int contador = 0;

        while (fechaAsignacion.isBefore(fechaUltAsignacion)) {
            DayOfWeek dia = fechaAsignacion.getDayOfWeek();
            int diaDelMes = fechaAsignacion.getDayOfMonth();

            boolean esFinDeSemana = dia == DayOfWeek.SUNDAY;
            boolean esFestivo = diasFestivosMes.contains(diaDelMes);

            if (!esFinDeSemana && !esFestivo) {
                contador++;
            }
            fechaAsignacion = fechaAsignacion.plusDays(1);
        }

        return contador;
    }
}
