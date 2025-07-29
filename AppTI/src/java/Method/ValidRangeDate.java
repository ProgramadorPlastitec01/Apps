package Method;

import java.time.LocalDate;
import java.time.YearMonth;

public class ValidRangeDate {

    public static String validarRango(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDate hoy = LocalDate.now();
        YearMonth mesActual = YearMonth.from(hoy);
        YearMonth mesAnterior = mesActual.minusMonths(1);
        LocalDate primerDiaMesAnterior = mesAnterior.atDay(1); // Convertir a LocalDate
        YearMonth rangoInicio = YearMonth.from(fechaInicio);
        YearMonth rangoFin = YearMonth.from(fechaFin);

        if (fechaFin.isAfter(hoy)) {
            return "Error";
        }

        if (rangoInicio.equals(mesActual) && rangoFin.equals(mesActual)) {
            return "ThisMonth";
        }

        if (rangoInicio.equals(mesAnterior) && rangoFin.equals(mesAnterior)) {
            return "Mes anterior";
        }

        if (rangoInicio.equals(mesAnterior) && !fechaFin.isAfter(hoy)) {
            return "Mes anterior hasta hoy";
        }

        if (fechaInicio.isBefore(primerDiaMesAnterior) && rangoFin.equals(mesAnterior)) {
            return "Histórico - Mes anterior";
        }

        if (fechaInicio.isBefore(primerDiaMesAnterior) && !fechaFin.isAfter(hoy)) {
            return "Histórico - Mes anterior - Mes actual";
        }

        return "Histórico";
    }

    
}
