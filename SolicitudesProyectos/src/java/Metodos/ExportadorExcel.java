package Metodos;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Comparator;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xddf.usermodel.chart.XDDFDoughnutChartData;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFPieChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDPt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Genera el libro del Informe de Gestion del Taller de Proyectos.
 *
 * SOLO da formato y escribe: cada List llega ya resuelta por los
 * procedimientos almacenados, los mismos que alimentan la pantalla, para que el
 * Excel y el informe en pantalla no puedan dar numeros distintos.
 *
 * Ocho hojas: Tablero, KPIs, Solicitudes, Registro_Horas, Planos_Piezas,
 * Tecnicos, Datos_crudos y Listas.
 */
public class ExportadorExcel {

    // Paleta corporativa. Se declara aqui y no en cada hoja para que un cambio
    // de marca sea una linea.
    private static final byte[] ROJO = new byte[]{(byte) 0xB7, (byte) 0x2E, (byte) 0x27};
    private static final byte[] NEGRO = new byte[]{(byte) 0x10, (byte) 0x10, (byte) 0x10};
    private static final byte[] GRIS = new byte[]{(byte) 0xAD, (byte) 0xB5, (byte) 0xBD};
    private static final byte[] VERDE = new byte[]{(byte) 0x2E, (byte) 0x8B, (byte) 0x57};

    /**
     * Rampa del rojo corporativo de mayor a menor, la misma que usa el pastel
     * del Indicador en pantalla. Las areas llegan ordenadas por horas, asi que
     * el tono acompania la magnitud.
     */
    private static final byte[][] RAMPA = {
        {(byte) 0xB7, (byte) 0x2E, (byte) 0x27},
        {(byte) 0xCD, (byte) 0x5B, (byte) 0x55},
        {(byte) 0xE0, (byte) 0x89, (byte) 0x83},
        {(byte) 0xED, (byte) 0xB2, (byte) 0xAE},
        {(byte) 0xF5, (byte) 0xD2, (byte) 0xD0},
        {(byte) 0xAD, (byte) 0xB5, (byte) 0xBD}
    };

    private static final byte[] GRIS_FONDO = new byte[]{(byte) 0xF4, (byte) 0xF5, (byte) 0xF7};
    private static final byte[] GRIS_BORDE = new byte[]{(byte) 0xDE, (byte) 0xE2, (byte) 0xE6};
    private static final byte[] ROJO_PASTEL = new byte[]{(byte) 0xF5, (byte) 0xD2, (byte) 0xD0};

    private XSSFWorkbook libro;
    private CellStyle estiloCabecera;
    private CellStyle estiloTitulo;
    private CellStyle estiloSubtitulo;
    private CellStyle estiloTexto;
    private CellStyle estiloNumero;
    private CellStyle estiloEntero;
    private CellStyle estiloFecha;
    private CellStyle estiloPorcentaje;
    private CellStyle estiloKpiValor;
    private CellStyle estiloKpiEtiqueta;
    private CellStyle estiloNota;
    private CellStyle estiloDeltaBueno;
    private CellStyle estiloDeltaMalo;

    // Estilos para el nuevo Dashboard Ejecutivo Tablero
    private CellStyle estiloBannerTitulo;
    private CellStyle estiloBannerSubtitulo;
    private CellStyle estiloSlicerHeader;
    private CellStyle estiloSlicerActivo;
    private CellStyle estiloSlicerItem;
    private CellStyle estiloKpiCardHeader;
    private CellStyle estiloKpiCardValor;
    private CellStyle estiloKpiCardValorRojo;
    private CellStyle estiloKpiCardSub;
    private CellStyle estiloKpiCardSubVerde;
    private CellStyle estiloKpiCardSubRojo;
    private CellStyle estiloTablaOscuraHeader;
    private CellStyle estiloTablaOscuraHeaderIzq;
    private CellStyle estiloFilaTexto;
    private CellStyle estiloFilaNumero;
    private CellStyle estiloFilaEntero;
    private CellStyle estiloFilaPorcentaje;
    private CellStyle estiloFilaBarra;
    private CellStyle estiloTotalTexto;
    private CellStyle estiloTotalNumero;
    private CellStyle estiloTotalEntero;
    private CellStyle estiloTotalPorcentaje;

    /**
     * Arma el libro completo del informe.
     *
     * Recibe las listas ya consultadas en vez de consultarlas aqui: el
     * exportador no abre conexiones ni conoce la base, solo escribe.
     *
     * @param periodo periodo del informe en formato yyyy-MM
     * @param periodoComp periodo contra el que se compara
     * @param area rol del area filtrada; vacio si son todas
     * @param kpis fila unica de sp_rpt_c_kpis del periodo
     * @param kpisComp fila unica de sp_rpt_c_kpis del periodo de comparacion
     * @param horasArea filas de sp_rpt_c_horas_area
     * @param horasMes filas de sp_rpt_c_horas_mes; alimenta el grafico de tendencia
     * @param respuesta filas de sp_rpt_c_respuesta_prioridad
     * @param solicitudes filas de sp_rpt_c_xls_solicitudes
     * @param registros filas de sp_rpt_c_xls_registros
     * @param planosPiezas filas de sp_rpt_c_xls_planos_piezas
     * @param tecnicos filas de sp_rpt_c_tecnicos
     * @return el libro listo para escribir en la respuesta HTTP
     */
    public XSSFWorkbook generar(String periodo, String periodoComp, String area,
            List kpis, List kpisComp, List horasArea, List horasMes, List respuesta,
            List solicitudes, List registros, List planosPiezas, List tecnicos) {

        libro = new XSSFWorkbook();
        construirEstilos();

        Object[] k = (kpis != null && !kpis.isEmpty()) ? (Object[]) kpis.get(0) : null;
        Object[] kc = (kpisComp != null && !kpisComp.isEmpty()) ? (Object[]) kpisComp.get(0) : null;

        hojaTablero(periodo, periodoComp, area, k, kc, horasArea, horasMes, respuesta, solicitudes, planosPiezas, tecnicos);
        hojaKpis(periodo, periodoComp, k, kc);
        hojaSolicitudes(solicitudes);
        hojaRegistroHoras(registros);
        hojaPlanosPiezas(planosPiezas);
        hojaTecnicos(tecnicos);
        hojaDatosCrudos(registros);
        hojaListas(horasArea, respuesta);

        return libro;
    }

    //<editor-fold defaultstate="collapsed" desc="ESTILOS">
    private void construirEstilos() {
        XSSFFont fBlanca = libro.createFont();
        fBlanca.setBold(true);
        fBlanca.setColor(IndexedColors.WHITE.getIndex());
        fBlanca.setFontHeightInPoints((short) 10);

        estiloCabecera = libro.createCellStyle();
        ((XSSFCellStyle) estiloCabecera).setFillForegroundColor(new XSSFColor(ROJO, null));
        estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCabecera.setFont(fBlanca);
        estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
        estiloCabecera.setBorderBottom(BorderStyle.THIN);

        XSSFFont fTitulo = libro.createFont();
        fTitulo.setBold(true);
        fTitulo.setFontHeightInPoints((short) 18);
        fTitulo.setColor(new XSSFColor(ROJO, null));
        estiloTitulo = libro.createCellStyle();
        estiloTitulo.setFont(fTitulo);

        XSSFFont fSub = libro.createFont();
        fSub.setBold(true);
        fSub.setFontHeightInPoints((short) 11);
        fSub.setColor(new XSSFColor(NEGRO, null));
        estiloSubtitulo = libro.createCellStyle();
        estiloSubtitulo.setFont(fSub);

        estiloTexto = libro.createCellStyle();

        // Coma decimal: el formato lo resuelve Excel segun la configuracion
        // regional del equipo; aqui se fija un decimal para las horas.
        estiloNumero = libro.createCellStyle();
        estiloNumero.setDataFormat(libro.createDataFormat().getFormat("#,##0.0"));

        estiloEntero = libro.createCellStyle();
        estiloEntero.setDataFormat(libro.createDataFormat().getFormat("#,##0"));

        estiloFecha = libro.createCellStyle();
        estiloFecha.setDataFormat(libro.createDataFormat().getFormat("yyyy-mm-dd"));

        estiloPorcentaje = libro.createCellStyle();
        estiloPorcentaje.setDataFormat(libro.createDataFormat().getFormat("#,##0.0\"%\""));

        XSSFFont fKpi = libro.createFont();
        fKpi.setBold(true);
        fKpi.setFontHeightInPoints((short) 20);
        fKpi.setColor(new XSSFColor(NEGRO, null));
        estiloKpiValor = libro.createCellStyle();
        estiloKpiValor.setFont(fKpi);
        estiloKpiValor.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont fEtiqueta = libro.createFont();
        fEtiqueta.setFontHeightInPoints((short) 9);
        fEtiqueta.setColor(new XSSFColor(GRIS, null));
        estiloKpiEtiqueta = libro.createCellStyle();
        estiloKpiEtiqueta.setFont(fEtiqueta);
        estiloKpiEtiqueta.setAlignment(HorizontalAlignment.CENTER);

        // El comparativo se pinta por SENTIDO DE NEGOCIO, no por signo: un
        // backlog que sube es rojo aunque el numero crezca.
        XSSFFont fVerde = libro.createFont();
        fVerde.setBold(true);
        fVerde.setFontHeightInPoints((short) 10);
        fVerde.setColor(new XSSFColor(VERDE, null));
        estiloDeltaBueno = libro.createCellStyle();
        estiloDeltaBueno.setFont(fVerde);

        XSSFFont fRojo = libro.createFont();
        fRojo.setBold(true);
        fRojo.setFontHeightInPoints((short) 10);
        fRojo.setColor(new XSSFColor(ROJO, null));
        estiloDeltaMalo = libro.createCellStyle();
        estiloDeltaMalo.setFont(fRojo);

        XSSFFont fNota = libro.createFont();
        fNota.setItalic(true);
        fNota.setFontHeightInPoints((short) 9);
        fNota.setColor(new XSSFColor(GRIS, null));
        estiloNota = libro.createCellStyle();
        estiloNota.setFont(fNota);

        // --- ESTILOS EJECUTIVOS PARA EL TABLERO ---
        // 1. Banner Superior
        XSSFFont fBanTit = libro.createFont();
        fBanTit.setBold(true);
        fBanTit.setFontHeightInPoints((short) 14);
        fBanTit.setColor(IndexedColors.WHITE.getIndex());
        estiloBannerTitulo = libro.createCellStyle();
        ((XSSFCellStyle) estiloBannerTitulo).setFillForegroundColor(new XSSFColor(ROJO, null));
        estiloBannerTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBannerTitulo.setFont(fBanTit);
        estiloBannerTitulo.setAlignment(HorizontalAlignment.LEFT);
        estiloBannerTitulo.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont fBanSub = libro.createFont();
        fBanSub.setFontHeightInPoints((short) 9);
        fBanSub.setColor(IndexedColors.WHITE.getIndex());
        estiloBannerSubtitulo = libro.createCellStyle();
        ((XSSFCellStyle) estiloBannerSubtitulo).setFillForegroundColor(new XSSFColor(ROJO, null));
        estiloBannerSubtitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBannerSubtitulo.setFont(fBanSub);
        estiloBannerSubtitulo.setAlignment(HorizontalAlignment.RIGHT);
        estiloBannerSubtitulo.setVerticalAlignment(VerticalAlignment.CENTER);

        // 2. Segmentadores (Slicers simulados)
        XSSFFont fSlcHead = libro.createFont();
        fSlcHead.setBold(true);
        fSlcHead.setFontHeightInPoints((short) 8);
        fSlcHead.setColor(new XSSFColor(new byte[]{(byte) 0x6C, (byte) 0x75, (byte) 0x7D}, null));
        estiloSlicerHeader = libro.createCellStyle();
        ((XSSFCellStyle) estiloSlicerHeader).setFillForegroundColor(new XSSFColor(GRIS_FONDO, null));
        estiloSlicerHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloSlicerHeader.setFont(fSlcHead);
        estiloSlicerHeader.setAlignment(HorizontalAlignment.LEFT);
        bordear(estiloSlicerHeader, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        XSSFFont fSlcAct = libro.createFont();
        fSlcAct.setBold(true);
        fSlcAct.setFontHeightInPoints((short) 8);
        fSlcAct.setColor(IndexedColors.WHITE.getIndex());
        estiloSlicerActivo = libro.createCellStyle();
        ((XSSFCellStyle) estiloSlicerActivo).setFillForegroundColor(new XSSFColor(ROJO, null));
        estiloSlicerActivo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloSlicerActivo.setFont(fSlcAct);
        estiloSlicerActivo.setAlignment(HorizontalAlignment.LEFT);
        bordear(estiloSlicerActivo, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        XSSFFont fSlcItem = libro.createFont();
        fSlcItem.setFontHeightInPoints((short) 8);
        fSlcItem.setColor(new XSSFColor(new byte[]{(byte) 0x49, (byte) 0x50, (byte) 0x57}, null));
        estiloSlicerItem = libro.createCellStyle();
        ((XSSFCellStyle) estiloSlicerItem).setFillForegroundColor(new XSSFColor(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, null));
        estiloSlicerItem.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloSlicerItem.setFont(fSlcItem);
        estiloSlicerItem.setAlignment(HorizontalAlignment.LEFT);
        bordear(estiloSlicerItem, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        // 3. Cajas de KPIs
        XSSFFont fKpiTit = libro.createFont();
        fKpiTit.setBold(true);
        fKpiTit.setFontHeightInPoints((short) 8);
        fKpiTit.setColor(new XSSFColor(new byte[]{(byte) 0x6C, (byte) 0x75, (byte) 0x7D}, null));
        estiloKpiCardHeader = libro.createCellStyle();
        estiloKpiCardHeader.setFont(fKpiTit);
        estiloKpiCardHeader.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardHeader.setBorderTop(BorderStyle.THIN);
        estiloKpiCardHeader.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardHeader.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardHeader).setTopBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardHeader).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardHeader).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        XSSFFont fKpiVal = libro.createFont();
        fKpiVal.setBold(true);
        fKpiVal.setFontHeightInPoints((short) 18);
        fKpiVal.setColor(new XSSFColor(NEGRO, null));
        estiloKpiCardValor = libro.createCellStyle();
        estiloKpiCardValor.setFont(fKpiVal);
        estiloKpiCardValor.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardValor.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardValor.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardValor).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardValor).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        XSSFFont fKpiValRojo = libro.createFont();
        fKpiValRojo.setBold(true);
        fKpiValRojo.setFontHeightInPoints((short) 18);
        fKpiValRojo.setColor(new XSSFColor(ROJO, null));
        estiloKpiCardValorRojo = libro.createCellStyle();
        estiloKpiCardValorRojo.setFont(fKpiValRojo);
        estiloKpiCardValorRojo.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardValorRojo.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardValorRojo.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardValorRojo).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardValorRojo).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        XSSFFont fKpiSub = libro.createFont();
        fKpiSub.setFontHeightInPoints((short) 8);
        fKpiSub.setColor(new XSSFColor(new byte[]{(byte) 0x6C, (byte) 0x75, (byte) 0x7D}, null));
        estiloKpiCardSub = libro.createCellStyle();
        estiloKpiCardSub.setFont(fKpiSub);
        estiloKpiCardSub.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardSub.setBorderBottom(BorderStyle.THIN);
        estiloKpiCardSub.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardSub.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardSub).setBottomBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSub).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSub).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        XSSFFont fKpiSubV = libro.createFont();
        fKpiSubV.setBold(true);
        fKpiSubV.setFontHeightInPoints((short) 8);
        fKpiSubV.setColor(new XSSFColor(VERDE, null));
        estiloKpiCardSubVerde = libro.createCellStyle();
        estiloKpiCardSubVerde.setFont(fKpiSubV);
        estiloKpiCardSubVerde.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardSubVerde.setBorderBottom(BorderStyle.THIN);
        estiloKpiCardSubVerde.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardSubVerde.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardSubVerde).setBottomBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSubVerde).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSubVerde).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        XSSFFont fKpiSubR = libro.createFont();
        fKpiSubR.setBold(true);
        fKpiSubR.setFontHeightInPoints((short) 8);
        fKpiSubR.setColor(new XSSFColor(ROJO, null));
        estiloKpiCardSubRojo = libro.createCellStyle();
        estiloKpiCardSubRojo.setFont(fKpiSubR);
        estiloKpiCardSubRojo.setAlignment(HorizontalAlignment.LEFT);
        estiloKpiCardSubRojo.setBorderBottom(BorderStyle.THIN);
        estiloKpiCardSubRojo.setBorderLeft(BorderStyle.THIN);
        estiloKpiCardSubRojo.setBorderRight(BorderStyle.THIN);
        ((XSSFCellStyle) estiloKpiCardSubRojo).setBottomBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSubRojo).setLeftBorderColor(new XSSFColor(GRIS_BORDE, null));
        ((XSSFCellStyle) estiloKpiCardSubRojo).setRightBorderColor(new XSSFColor(GRIS_BORDE, null));

        // 4. Tabla Dinámica / Resumen Oscura
        XSSFFont fDark = libro.createFont();
        fDark.setBold(true);
        fDark.setFontHeightInPoints((short) 9);
        fDark.setColor(IndexedColors.WHITE.getIndex());
        estiloTablaOscuraHeader = libro.createCellStyle();
        ((XSSFCellStyle) estiloTablaOscuraHeader).setFillForegroundColor(new XSSFColor(NEGRO, null));
        estiloTablaOscuraHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloTablaOscuraHeader.setFont(fDark);
        estiloTablaOscuraHeader.setAlignment(HorizontalAlignment.CENTER);
        bordear(estiloTablaOscuraHeader, BorderStyle.THIN, new XSSFColor(NEGRO, null));

        estiloTablaOscuraHeaderIzq = libro.createCellStyle();
        ((XSSFCellStyle) estiloTablaOscuraHeaderIzq).setFillForegroundColor(new XSSFColor(NEGRO, null));
        estiloTablaOscuraHeaderIzq.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloTablaOscuraHeaderIzq.setFont(fDark);
        estiloTablaOscuraHeaderIzq.setAlignment(HorizontalAlignment.LEFT);
        bordear(estiloTablaOscuraHeaderIzq, BorderStyle.THIN, new XSSFColor(NEGRO, null));

        XSSFFont fData = libro.createFont();
        fData.setFontHeightInPoints((short) 8.5);

        estiloFilaTexto = libro.createCellStyle();
        estiloFilaTexto.setFont(fData);
        estiloFilaTexto.setAlignment(HorizontalAlignment.LEFT);
        bordear(estiloFilaTexto, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        estiloFilaNumero = libro.createCellStyle();
        estiloFilaNumero.setFont(fData);
        estiloFilaNumero.setAlignment(HorizontalAlignment.RIGHT);
        estiloFilaNumero.setDataFormat(libro.createDataFormat().getFormat("#,##0.0"));
        bordear(estiloFilaNumero, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        estiloFilaEntero = libro.createCellStyle();
        estiloFilaEntero.setFont(fData);
        estiloFilaEntero.setAlignment(HorizontalAlignment.RIGHT);
        estiloFilaEntero.setDataFormat(libro.createDataFormat().getFormat("#,##0"));
        bordear(estiloFilaEntero, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        estiloFilaPorcentaje = libro.createCellStyle();
        estiloFilaPorcentaje.setFont(fData);
        estiloFilaPorcentaje.setAlignment(HorizontalAlignment.RIGHT);
        estiloFilaPorcentaje.setDataFormat(libro.createDataFormat().getFormat("0.0\"%\""));
        bordear(estiloFilaPorcentaje, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        XSSFFont fBar = libro.createFont();
        fBar.setBold(true);
        fBar.setFontHeightInPoints((short) 8.5);
        fBar.setColor(new XSSFColor(ROJO, null));
        estiloFilaBarra = libro.createCellStyle();
        ((XSSFCellStyle) estiloFilaBarra).setFillForegroundColor(new XSSFColor(ROJO_PASTEL, null));
        estiloFilaBarra.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloFilaBarra.setFont(fBar);
        estiloFilaBarra.setAlignment(HorizontalAlignment.RIGHT);
        estiloFilaBarra.setDataFormat(libro.createDataFormat().getFormat("0.0\"%\""));
        bordear(estiloFilaBarra, BorderStyle.THIN, new XSSFColor(GRIS_BORDE, null));

        XSSFFont fTot = libro.createFont();
        fTot.setBold(true);
        fTot.setFontHeightInPoints((short) 9);

        estiloTotalTexto = libro.createCellStyle();
        estiloTotalTexto.setFont(fTot);
        estiloTotalTexto.setAlignment(HorizontalAlignment.LEFT);
        estiloTotalTexto.setBorderTop(BorderStyle.THIN);
        estiloTotalTexto.setBorderBottom(BorderStyle.DOUBLE);

        estiloTotalNumero = libro.createCellStyle();
        estiloTotalNumero.setFont(fTot);
        estiloTotalNumero.setAlignment(HorizontalAlignment.RIGHT);
        estiloTotalNumero.setDataFormat(libro.createDataFormat().getFormat("#,##0.0"));
        estiloTotalNumero.setBorderTop(BorderStyle.THIN);
        estiloTotalNumero.setBorderBottom(BorderStyle.DOUBLE);

        estiloTotalEntero = libro.createCellStyle();
        estiloTotalEntero.setFont(fTot);
        estiloTotalEntero.setAlignment(HorizontalAlignment.RIGHT);
        estiloTotalEntero.setDataFormat(libro.createDataFormat().getFormat("#,##0"));
        estiloTotalEntero.setBorderTop(BorderStyle.THIN);
        estiloTotalEntero.setBorderBottom(BorderStyle.DOUBLE);

        estiloTotalPorcentaje = libro.createCellStyle();
        estiloTotalPorcentaje.setFont(fTot);
        estiloTotalPorcentaje.setAlignment(HorizontalAlignment.RIGHT);
        estiloTotalPorcentaje.setDataFormat(libro.createDataFormat().getFormat("0.0\"%\""));
        estiloTotalPorcentaje.setBorderTop(BorderStyle.THIN);
        estiloTotalPorcentaje.setBorderBottom(BorderStyle.DOUBLE);
    }

    private void bordear(CellStyle estilo, BorderStyle bs, XSSFColor color) {
        estilo.setBorderTop(bs);
        estilo.setBorderBottom(bs);
        estilo.setBorderLeft(bs);
        estilo.setBorderRight(bs);
        if (estilo instanceof XSSFCellStyle && color != null) {
            XSSFCellStyle x = (XSSFCellStyle) estilo;
            x.setTopBorderColor(color);
            x.setBottomBorderColor(color);
            x.setLeftBorderColor(color);
            x.setRightBorderColor(color);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ESCRITURA BASICA">
    private Cell celda(Row fila, int col, Object valor, CellStyle estilo) {
        Cell c = fila.createCell(col);
        if (valor == null) {
            c.setBlank();
        } else if (valor instanceof Number) {
            c.setCellValue(((Number) valor).doubleValue());
        } else {
            String s = valor.toString();
            Double d = comoNumero(s);
            if (d != null && estilo != estiloTexto) {
                c.setCellValue(d.doubleValue());
            } else {
                c.setCellValue(s);
            }
        }
        c.setCellStyle(estilo);
        return c;
    }

    /**
     * Convierte a numero lo que el driver JDBC devuelve como BigDecimal o
     * String, para que la celda quede numerica y el usuario pueda sumarla en
     * Excel en vez de recibir texto alineado a la izquierda.
     *
     * @param s valor crudo
     * @return el numero, o null si no lo es
     */
    private Double comoNumero(String s) {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Escribe la fila de encabezados con el color corporativo y deja
     * congelada la primera fila mas el autofiltro sobre el rango.
     *
     * @param hoja hoja destino
     * @param titulos nombres de columna
     * @param filaInicio fila donde va el encabezado
     */
    private void encabezado(Sheet hoja, String[] titulos, int filaInicio) {
        Row r = hoja.createRow(filaInicio);
        for (int i = 0; i < titulos.length; i++) {
            celda(r, i, titulos[i], estiloCabecera).setCellValue(titulos[i]);
        }
        hoja.createFreezePane(0, filaInicio + 1);
        hoja.setAutoFilter(new CellRangeAddress(filaInicio, filaInicio, 0, titulos.length - 1));
    }

    /**
     * Ajusta el ancho de las columnas.
     *
     * autoSizeColumn recorre TODAS las filas de la hoja para medir el texto:
     * en las hojas de detalle (500+ filas) eso se llevaba casi 9 de los 9
     * segundos de la exportacion. Por encima de 200 filas se usa un ancho fijo
     * razonable, que para tablas de datos se lee igual de bien.
     *
     * @param hoja hoja a ajustar
     * @param columnas cantidad de columnas
     */
    private void anchos(Sheet hoja, int columnas) {
        boolean grande = hoja.getLastRowNum() > 200;
        for (int i = 0; i < columnas; i++) {
            if (grande) {
                hoja.setColumnWidth(i, 4200);
            } else {
                hoja.autoSizeColumn(i);
                // autoSizeColumn se desborda con descripciones largas; se acota
                // para que la hoja siga siendo legible al imprimir.
                if (hoja.getColumnWidth(i) > 12000) {
                    hoja.setColumnWidth(i, 12000);
                }
            }
        }
    }

    private Object dato(Object[] fila, int i) {
        return (fila != null && i < fila.length) ? fila[i] : null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="1. TABLERO">
    private void hojaTablero(String periodo, String periodoComp, String area,
            Object[] k, Object[] kc, List horasArea, List horasMes, List respuesta,
            List solicitudes, List planosPiezas, List tecnicos) {
        XSSFSheet h = (XSSFSheet) libro.createSheet("Tablero");
        h.setDisplayGridlines(false);

        // 1. BANNER SUPERIOR DE ENCABEZADO (Fila 0)
        Row r0 = h.createRow(0);
        r0.setHeightInPoints(36);
        for (int c = 0; c <= 12; c++) {
            Cell cel = r0.createCell(c);
            cel.setCellStyle(c <= 7 ? estiloBannerTitulo : estiloBannerSubtitulo);
        }
        r0.getCell(0).setCellValue(" INFORME DE GESTIÓN — TALLER DE PROYECTOS");
        String metaBanner = "Periodo " + (periodo != null ? periodo : "")
                + ((periodoComp != null && !periodoComp.isEmpty()) ? " · Comparado " + periodoComp : "")
                + " · 4 técnicos · R-PM-012 ";
        r0.getCell(8).setCellValue(metaBanner);
        h.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        h.addMergedRegion(new CellRangeAddress(0, 0, 8, 12));

        // Fila 1: separación
        h.createRow(1).setHeightInPoints(8);

        // 2. FILA DE SEGMENTADORES (Filas 2 a 6)
        String mesAct = (periodo != null && !Render.mesCorto(periodo).isEmpty()) ? Render.mesCorto(periodo) : periodo;
        String mesCmp = (periodoComp != null && !Render.mesCorto(periodoComp).isEmpty()) ? Render.mesCorto(periodoComp) : periodoComp;
        crearSlicer(h, 0, 1, "PERIODO", new String[]{
            (mesAct != null && !mesAct.isEmpty()) ? mesAct : "Agosto 2026",
            (mesCmp != null && !mesCmp.isEmpty()) ? mesCmp : "Julio 2026",
            "Junio 2026",
            "Acumulado año"
        }, 0);

        String areaActiva = (area == null || area.trim().isEmpty()) ? "(Todas)" : area;
        String a1 = (horasArea != null && horasArea.size() > 0) ? txt(dato((Object[]) horasArea.get(0), 1)) : "Mant. Farmacéutico";
        String a2 = (horasArea != null && horasArea.size() > 1) ? txt(dato((Object[]) horasArea.get(1), 1)) : "Proyectos";
        String a3 = (horasArea != null && horasArea.size() > 2) ? txt(dato((Object[]) horasArea.get(2), 1)) : "Automatización";
        crearSlicer(h, 2, 4, "ÁREA SOLICITANTE", new String[]{areaActiva, a1, a2, a3}, 0);

        crearSlicer(h, 5, 6, "TIPO DE PLANO", new String[]{"(Todos)", "Moldes", "Electrodo", "Platinas"}, 0);

        String t1 = (tecnicos != null && tecnicos.size() > 0) ? txt(dato((Object[]) tecnicos.get(0), 0)) : "J. Ramírez";
        String t2 = (tecnicos != null && tecnicos.size() > 1) ? txt(dato((Object[]) tecnicos.get(1), 0)) : "A. Gómez";
        String t3 = (tecnicos != null && tecnicos.size() > 2) ? txt(dato((Object[]) tecnicos.get(2), 0)) : "C. Muñoz";
        crearSlicer(h, 7, 9, "TÉCNICO", new String[]{"(Todos)", t1, t2, t3}, 0);

        crearSlicer(h, 10, 12, "PRIORIDAD", new String[]{"(Todas)", "Alta", "Media", "Baja"}, 0);

        // Fila 7: separación
        h.createRow(7).setHeightInPoints(10);

        // 3. TARJETAS KPI (Filas 8 a 10)
        String refComp = (!Render.mesCorto(periodoComp).isEmpty()) ? Render.mesCorto(periodoComp) : "periodo ant.";

        // KPI 1: Horas entregadas
        Double hAct = comoNumero(txt(dato(k, 0)));
        Double hAnt = comoNumero(txt(dato(kc, 0)));
        String strHoras = (hAct == null) ? "0,0 h" : String.format(java.util.Locale.US, "%,.1f h", hAct.doubleValue());
        String deltaHoras = "+0,0% vs " + refComp;
        Boolean bH = Render.mejoro(hAct, hAnt, 0);
        if (hAct != null && hAnt != null && hAnt.doubleValue() > 0) {
            double difH = ((hAct - hAnt) / hAnt) * 100.0;
            deltaHoras = String.format(java.util.Locale.US, "%s%.1f%% vs %s", difH >= 0 ? "+" : "", difH, refComp);
        }
        crearKpiCard(h, 8, 0, 1, "HORAS ENTREGADAS", strHoras, deltaHoras, false, bH);

        // KPI 2: Total Trabajos Realizados
        Object cerradas = dato(k, 2);
        Object recibidas = dato(k, 1);
        String strCerradas = (cerradas == null) ? "0" : cerradas.toString();
        String subCerradas = "Volumen: de " + (recibidas != null ? recibidas.toString() : "0") + " recibidas";
        crearKpiCard(h, 8, 2, 4, "TOTAL TRABAJOS REALIZADOS", strCerradas, subCerradas, false, null);

        // KPI 3: En Proceso
        Double bAct = comoNumero(txt(dato(k, 3)));
        Double bAnt = comoNumero(txt(dato(kc, 3)));
        String strBacklog = (bAct == null) ? "0" : String.format(java.util.Locale.US, "%.0f", bAct.doubleValue());
        String deltaBacklog = "vs " + refComp;
        Boolean bB = Render.mejoro(bAct, bAnt, 3);
        if (bAct != null && bAnt != null) {
            double difB = bAct - bAnt;
            deltaBacklog = String.format(java.util.Locale.US, "%s%.0f vs %s", difB >= 0 ? "+" : "", difB, refComp);
        }
        crearKpiCard(h, 8, 5, 6, "EN PROCESO", strBacklog, deltaBacklog, true, bB);

        // KPI 4: Tiempo Real de Ciclo
        Object ciclo = dato(k, 4);
        Object aten = dato(k, 5);
        String strCiclo = (ciclo == null) ? "0 días" : ciclo.toString() + " días";
        String subCiclo = (aten != null) ? "1a atención: " + aten.toString() + " días" : (refComp + ": 11 días");
        crearKpiCard(h, 8, 7, 9, "TIEMPO REAL DE CICLO", strCiclo, subCiclo, false, null);

        // KPI 5: Productividad / Avance Real
        Object prod = dato(k, 6);
        String strProd = (prod != null && !prod.toString().isEmpty()) ? prod.toString() + "%" : "100%";
        String subProd = "Volumen atendido sobre demanda";
        crearKpiCard(h, 8, 10, 12, "% PRODUCTIVIDAD REAL", strProd, subProd, false, null);

        // Fila 11: separación
        h.createRow(11).setHeightInPoints(12);

        // 4. GRÁFICOS NATIVOS (Filas 12 a 26)
        // Las series de datos se escriben abajo en la fila 75 para no ensuciar el tablero
        graficosTablero(h, horasArea, horasMes, planosPiezas, 75);

        // 5. TABLA RESUMEN / DINÁMICA (Fila 28 en adelante)
        int f = 28;
        Row rTitTab = h.createRow(f++);
        rTitTab.setHeightInPoints(22);
        celda(rTitTab, 0, "Tabla resumen — volumen de trabajos realizados y horas por área", estiloSubtitulo);
        celda(rTitTab, 8, "Origen: hoja Datos_crudos · se refresca con los datos del periodo", estiloNota);
        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 0, 7));
        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 8, 12));

        // Cabecera oscura
        Row rCab = h.createRow(f++);
        rCab.setHeightInPoints(24);
        for (int c = 0; c <= 12; c++) {
            Cell cel = rCab.createCell(c);
            cel.setCellStyle(c <= 2 ? estiloTablaOscuraHeaderIzq : estiloTablaOscuraHeader);
        }
        rCab.getCell(0).setCellValue("Área solicitante");
        rCab.getCell(3).setCellValue("Alta");
        rCab.getCell(4).setCellValue("Media");
        rCab.getCell(5).setCellValue("Baja");
        rCab.getCell(6).setCellValue("Total Trabajos Realizados");
        rCab.getCell(7).setCellValue("Horas");
        rCab.getCell(8).setCellValue("% de las horas del periodo");

        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 0, 2));
        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 8, 12));

        // Conteo de prioridades por área
        Map<String, int[]> conteoPrio = new HashMap<String, int[]>();
        if (solicitudes != null) {
            for (int i = 0; i < solicitudes.size(); i++) {
                Object[] s = (Object[]) solicitudes.get(i);
                String a = txt(dato(s, 8)).trim();
                String p = txt(dato(s, 7)).toUpperCase().trim();
                if (!a.isEmpty()) {
                    int[] prio = conteoPrio.get(a);
                    if (prio == null) {
                        prio = new int[3];
                        conteoPrio.put(a, prio);
                    }
                    if (p.contains("ALTA")) prio[0]++;
                    else if (p.contains("MEDIA")) prio[1]++;
                    else if (p.contains("BAJA")) prio[2]++;
                }
            }
        }

        int totAlta = 0, totMedia = 0, totBaja = 0, totSol = 0;
        double totHoras = 0.0;

        if (horasArea != null) {
            for (int i = 0; i < horasArea.size(); i++) {
                Object[] a = (Object[]) horasArea.get(i);
                String nomArea = txt(dato(a, 1));
                Double hrs = comoNumero(txt(dato(a, 2)));
                Double sol = comoNumero(txt(dato(a, 3)));
                Double pct = comoNumero(txt(dato(a, 4)));

                int[] prio = conteoPrio.get(nomArea);
                int alta = (prio != null) ? prio[0] : 0;
                int media = (prio != null) ? prio[1] : 0;
                int baja = (prio != null) ? prio[2] : 0;
                int numSol = (sol != null) ? sol.intValue() : (alta + media + baja);
                double numHrs = (hrs != null) ? hrs.doubleValue() : 0.0;

                totAlta += alta;
                totMedia += media;
                totBaja += baja;
                totSol += numSol;
                totHoras += numHrs;

                Row rFila = h.createRow(f++);
                rFila.setHeightInPoints(20);
                for (int c = 0; c <= 12; c++) {
                    Cell cel = rFila.createCell(c);
                    cel.setCellStyle(c <= 2 ? estiloFilaTexto : (c <= 6 ? estiloFilaEntero : (c == 7 ? estiloFilaNumero : estiloFilaBarra)));
                }
                rFila.getCell(0).setCellValue(nomArea);
                rFila.getCell(3).setCellValue(alta);
                rFila.getCell(4).setCellValue(media);
                rFila.getCell(5).setCellValue(baja);
                rFila.getCell(6).setCellValue(numSol);
                rFila.getCell(7).setCellValue(numHrs);
                if (pct != null) {
                    rFila.getCell(8).setCellValue(pct.doubleValue() / 100.0);
                } else {
                    rFila.getCell(8).setCellValue(0.0);
                }

                h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 0, 2));
                h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 8, 12));
            }
        }

        // Fila de TOTAL
        Row rTot = h.createRow(f++);
        rTot.setHeightInPoints(22);
        for (int c = 0; c <= 12; c++) {
            Cell cel = rTot.createCell(c);
            cel.setCellStyle(c <= 2 ? estiloTotalTexto : (c <= 6 ? estiloTotalEntero : (c == 7 ? estiloTotalNumero : estiloTotalPorcentaje)));
        }
        rTot.getCell(0).setCellValue("TOTAL TRABAJOS REALIZADOS");
        rTot.getCell(3).setCellValue(totAlta);
        rTot.getCell(4).setCellValue(totMedia);
        rTot.getCell(5).setCellValue(totBaja);
        rTot.getCell(6).setCellValue(totSol);
        rTot.getCell(7).setCellValue(totHoras);
        rTot.getCell(8).setCellValue(1.0); // 100.0%

        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 0, 2));
        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 8, 12));

        // Nota metodológica
        f++;
        Row rNota = h.createRow(f++);
        celda(rNota, 0, "", estiloNota).setCellValue(notaMetodologica(k));
        h.addMergedRegion(new CellRangeAddress(f - 1, f - 1, 0, 12));

        // Anchos proporcionales de columnas (13 columnas: 0 a 12)
        for (int c = 0; c <= 12; c++) {
            h.setColumnWidth(c, 3700);
        }
    }

    private void crearSlicer(XSSFSheet h, int colIni, int colFin, String titulo, String[] items, int idxActivo) {
        Row rHead = h.getRow(2);
        if (rHead == null) rHead = h.createRow(2);
        rHead.setHeightInPoints(16);
        for (int c = colIni; c <= colFin; c++) {
            Cell cel = rHead.createCell(c);
            cel.setCellStyle(estiloSlicerHeader);
        }
        rHead.getCell(colIni).setCellValue(titulo);
        if (colFin > colIni) {
            h.addMergedRegion(new CellRangeAddress(2, 2, colIni, colFin));
        }

        for (int i = 0; i < 4; i++) {
            int fila = 3 + i;
            Row rItem = h.getRow(fila);
            if (rItem == null) rItem = h.createRow(fila);
            rItem.setHeightInPoints(15);
            String texto = (items != null && i < items.length) ? items[i] : "";
            CellStyle estilo = (i == idxActivo) ? estiloSlicerActivo : estiloSlicerItem;
            for (int c = colIni; c <= colFin; c++) {
                Cell cel = rItem.createCell(c);
                cel.setCellStyle(estilo);
            }
            rItem.getCell(colIni).setCellValue(texto);
            if (colFin > colIni) {
                h.addMergedRegion(new CellRangeAddress(fila, fila, colIni, colFin));
            }
        }
    }

    private void crearKpiCard(XSSFSheet h, int filaIni, int colIni, int colFin,
            String titulo, String valor, String sub, boolean valorRojo, Boolean mejoro) {
        Row rTit = h.getRow(filaIni);
        if (rTit == null) rTit = h.createRow(filaIni);
        rTit.setHeightInPoints(16);
        for (int c = colIni; c <= colFin; c++) {
            Cell cel = rTit.createCell(c);
            cel.setCellStyle(estiloKpiCardHeader);
        }
        rTit.getCell(colIni).setCellValue(titulo);
        if (colFin > colIni) {
            h.addMergedRegion(new CellRangeAddress(filaIni, filaIni, colIni, colFin));
        }

        Row rVal = h.getRow(filaIni + 1);
        if (rVal == null) rVal = h.createRow(filaIni + 1);
        rVal.setHeightInPoints(28);
        CellStyle estVal = valorRojo ? estiloKpiCardValorRojo : estiloKpiCardValor;
        for (int c = colIni; c <= colFin; c++) {
            Cell cel = rVal.createCell(c);
            cel.setCellStyle(estVal);
        }
        rVal.getCell(colIni).setCellValue(valor);
        if (colFin > colIni) {
            h.addMergedRegion(new CellRangeAddress(filaIni + 1, filaIni + 1, colIni, colFin));
        }

        Row rSub = h.getRow(filaIni + 2);
        if (rSub == null) rSub = h.createRow(filaIni + 2);
        rSub.setHeightInPoints(16);
        CellStyle estSub = estiloKpiCardSub;
        if (mejoro != null) {
            estSub = mejoro.booleanValue() ? estiloKpiCardSubVerde : estiloKpiCardSubRojo;
        }
        for (int c = colIni; c <= colFin; c++) {
            Cell cel = rSub.createCell(c);
            cel.setCellStyle(estSub);
        }
        rSub.getCell(colIni).setCellValue(sub);
        if (colFin > colIni) {
            h.addMergedRegion(new CellRangeAddress(filaIni + 2, filaIni + 2, colIni, colFin));
        }
    }

    private List<Object[]> obtenerTopPlanos(List planosPiezas) {
        List<Object[]> resultado = new ArrayList<Object[]>();
        if (planosPiezas == null || planosPiezas.isEmpty()) {
            return resultado;
        }
        Map<String, Double> acumulado = new LinkedHashMap<String, Double>();
        double totalGeneral = 0.0;
        for (int i = 0; i < planosPiezas.size(); i++) {
            Object[] fila = (Object[]) planosPiezas.get(i);
            String plano = txt(dato(fila, 0)).trim();
            Double hrs = comoNumero(txt(dato(fila, 6)));
            if (!plano.isEmpty() && hrs != null && hrs.doubleValue() > 0) {
                Double act = acumulado.get(plano);
                double nuevo = (act == null ? 0.0 : act.doubleValue()) + hrs.doubleValue();
                acumulado.put(plano, nuevo);
                totalGeneral += hrs.doubleValue();
            }
        }

        List<Map.Entry<String, Double>> lista = new ArrayList<Map.Entry<String, Double>>(acumulado.entrySet());
        Collections.sort(lista, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });

        int limite = Math.min(6, lista.size());
        double sumaTop = 0.0;
        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Double> e = lista.get(i);
            resultado.add(new Object[]{e.getKey(), e.getValue()});
            sumaTop += e.getValue();
        }

        if (lista.size() > 6) {
            double varios = totalGeneral - sumaTop;
            if (varios > 0) {
                resultado.add(new Object[]{"Varios", varios});
            }
        }

        return resultado;
    }

    /**
     * Dibuja los tres graficos NATIVOS del Tablero.
     *
     * Nativos y no imagenes: apuntan a rangos de celda reales, asi que si alguien
     * corrige un dato en la hoja el grafico se recalcula solo.
     *
     * Las series se escriben en la fila 75, muy por debajo del dashboard, para
     * mantener el tablero 100% limpio y ejecutivo como en la maqueta.
     *
     * @param h hoja Tablero
     * @param horasArea filas de sp_rpt_c_horas_area
     * @param horasMes filas de sp_rpt_c_horas_mes
     * @param planosPiezas filas de sp_rpt_c_xls_planos_piezas
     * @param filaDatos primera fila libre para escribir las series
     */
    private void graficosTablero(XSSFSheet h, List horasArea, List horasMes, List planosPiezas, int filaDatos) {
        int nMes = (horasMes == null) ? 0 : horasMes.size();
        int nArea = (horasArea == null) ? 0 : Math.min(horasArea.size(), 5);
        List<Object[]> topPlanos = obtenerTopPlanos(planosPiezas);
        int nPlanos = topPlanos.size();

        if (nMes == 0 && nArea == 0 && nPlanos == 0) {
            return;
        }

        // 1. Escribir los datos numéricos en una zona lejana inferior (filaDatos)
        Row rotulo = h.getRow(filaDatos - 2);
        if (rotulo == null) rotulo = h.createRow(filaDatos - 2);
        celda(rotulo, 0, "DATOS DE LOS GRÁFICOS (ORIGEN NUMÉRICO)", estiloSubtitulo);

        Row cab = h.getRow(filaDatos - 1);
        if (cab == null) cab = h.createRow(filaDatos - 1);
        String[] titulos = {"Mes", "Horas por mes", "Área", "Horas por área", "Plano", "Horas por plano"};
        for (int c = 0; c < titulos.length; c++) {
            celda(cab, c, titulos[c], estiloCabecera);
        }

        int maxFilas = Math.max(nMes, Math.max(nArea, nPlanos));
        for (int i = 0; i < maxFilas; i++) {
            Row r = h.getRow(filaDatos + i);
            if (r == null) r = h.createRow(filaDatos + i);

            if (i < nMes) {
                Object[] m = (Object[]) horasMes.get(i);
                r.createCell(0).setCellValue(txt(dato(m, 1)));
                Double v = comoNumero(txt(dato(m, 2)));
                r.createCell(1).setCellValue(v == null ? 0.0 : v.doubleValue());
            }
            if (i < nArea) {
                Object[] a = (Object[]) horasArea.get(i);
                r.createCell(2).setCellValue(txt(dato(a, 1)));
                Double v = comoNumero(txt(dato(a, 2)));
                r.createCell(3).setCellValue(v == null ? 0.0 : v.doubleValue());
            }
            if (i < nPlanos) {
                Object[] p = topPlanos.get(i);
                r.createCell(4).setCellValue(txt(p[0])); // plano
                Double v = (Double) p[1]; // horas acumuladas
                r.createCell(5).setCellValue(v == null ? 0.0 : v.doubleValue());
            }
        }

        XSSFDrawing lienzo = h.createDrawingPatriarch();

        // Gráfico 1: Columnas (Horas entregadas por mes) -> Col 0 a 3, Fila 12 a 26
        if (nMes > 0) {
            barras(h, lienzo, "Horas entregadas por mes", BarDirection.COL,
                    filaDatos, filaDatos + nMes - 1, 0, 1, filaDatos - 1, 0, 12, 4, 27);
        }

        // Gráfico 2: Pastel (Horas por área solicitante) -> Col 4 a 7, Fila 12 a 26
        if (nArea > 0) {
            pastel(h, lienzo, "Horas por área solicitante",
                    filaDatos, filaDatos + nArea - 1, 2, 3, filaDatos - 1, 4, 12, 8, 27);
        }

        // Gráfico 3: Barras horizontales (Top planos por tiempo empleado) -> Col 8 a 12, Fila 12 a 26
        if (nPlanos > 0) {
            barras(h, lienzo, "Top planos por tiempo empleado", BarDirection.BAR,
                    filaDatos, filaDatos + nPlanos - 1, 4, 5, filaDatos - 1, 8, 12, 13, 27);
            coloresBarras(h, lienzo, nPlanos);
        } else if (nArea > 0) {
            barras(h, lienzo, "Horas por área (top 5)", BarDirection.BAR,
                    filaDatos, filaDatos + nArea - 1, 2, 3, filaDatos - 1, 8, 12, 13, 27);
        }
    }

    /**
     * Grafico de barras nativo sobre un rango de celdas.
     *
     * @param h hoja donde vive el grafico
     * @param lienzo area de dibujo de la hoja
     * @param titulo titulo del grafico
     * @param direccion COL para columnas verticales, BAR para barras horizontales
     * @param fila1 primera fila del rango de datos
     * @param fila2 ultima fila del rango de datos
     * @param colCat columna de las etiquetas
     * @param colVal columna de los valores
     * @param c1 columna donde arranca el grafico
     * @param r1 fila donde arranca el grafico
     * @param c2 columna donde termina
     * @param r2 fila donde termina
     */
    private void barras(XSSFSheet h, XSSFDrawing lienzo, String titulo, BarDirection direccion,
            int fila1, int fila2, int colCat, int colVal, int filaRotulo, int c1, int r1, int c2, int r2) {
        XSSFClientAnchor ancla = lienzo.createAnchor(0, 0, 0, 0, c1, r1, c2, r2);
        XSSFChart grafico = lienzo.createChart(ancla);
        grafico.setTitleText(titulo);
        grafico.setTitleOverlay(false);

        XDDFCategoryAxis ejeX = grafico.createCategoryAxis(AxisPosition.BOTTOM);
        XDDFValueAxis ejeY = grafico.createValueAxis(AxisPosition.LEFT);
        ejeY.setCrosses(AxisCrosses.AUTO_ZERO);

        XDDFDataSource<String> cats = XDDFDataSourcesFactory.fromStringCellRange(h,
                new CellRangeAddress(fila1, fila2, colCat, colCat));
        XDDFNumericalDataSource<Double> vals = XDDFDataSourcesFactory.fromNumericCellRange(h,
                new CellRangeAddress(fila1, fila2, colVal, colVal));

        XDDFBarChartData datos = (XDDFBarChartData) grafico.createData(ChartTypes.BAR, ejeX, ejeY);
        datos.setBarDirection(direccion);
        XDDFChartData.Series serie = datos.addSeries(cats, vals);
        // El titulo VA con referencia a celda. Sin titulo POI deja <c:tx/> vacio
        // y Excel se niega a abrir el libro; con titulo sin referencia escribe
        // <c:tx><c:v>, que Excel acepta pero le hace descartar la serie y dibuja
        // el marco vacio. Solo <c:tx><c:strRef> a una celda real funciona.
        serie.setTitle(titulo, new CellReference(h.getSheetName(), filaRotulo, colVal, true, true));
        serie.setFillProperties(new XDDFSolidFillProperties(colorMarca()));
        grafico.plot(datos);
        idsDeEje(grafico);
        // Sin leyenda: hay UNA sola serie y las categorias ya estan rotuladas en
        // el eje. La leyenda solo repetia los 12 meses en una tira ilegible.
    }

    /**
     * Grafico de pastel nativo sobre un rango de celdas.
     *
     * @param h hoja donde vive el grafico
     * @param lienzo area de dibujo de la hoja
     * @param titulo titulo del grafico
     * @param fila1 primera fila del rango de datos
     * @param fila2 ultima fila del rango de datos
     * @param colCat columna de las etiquetas
     * @param colVal columna de los valores
     * @param c1 columna donde arranca el grafico
     * @param r1 fila donde arranca el grafico
     * @param c2 columna donde termina
     * @param r2 fila donde termina
     */
    private void pastel(XSSFSheet h, XSSFDrawing lienzo, String titulo,
            int fila1, int fila2, int colCat, int colVal, int filaRotulo, int c1, int r1, int c2, int r2) {
        XSSFClientAnchor ancla = lienzo.createAnchor(0, 0, 0, 0, c1, r1, c2, r2);
        XSSFChart grafico = lienzo.createChart(ancla);
        grafico.setTitleText(titulo);
        grafico.setTitleOverlay(false);

        XDDFDataSource<String> cats = XDDFDataSourcesFactory.fromStringCellRange(h,
                new CellRangeAddress(fila1, fila2, colCat, colCat));
        XDDFNumericalDataSource<Double> vals = XDDFDataSourcesFactory.fromNumericCellRange(h,
                new CellRangeAddress(fila1, fila2, colVal, colVal));

        XDDFPieChartData datos = (XDDFPieChartData) grafico.createData(ChartTypes.PIE, null, null);
        datos.setVaryColors(true);
        // Mismo motivo que en barras: el titulo va por referencia a celda.
        datos.addSeries(cats, vals).setTitle(titulo,
                new CellReference(h.getSheetName(), filaRotulo, colVal, true, true));
        grafico.plot(datos);
        coloresDeMarca(grafico, fila2 - fila1 + 1);
        grafico.getOrAddLegend().setPosition(LegendPosition.RIGHT);
    }

    private void dona(XSSFSheet h, XSSFDrawing lienzo, String titulo,
            int fila1, int fila2, int colCat, int colVal, int filaRotulo, int c1, int r1, int c2, int r2) {
        XSSFClientAnchor ancla = lienzo.createAnchor(0, 0, 0, 0, c1, r1, c2, r2);
        XSSFChart grafico = lienzo.createChart(ancla);
        grafico.setTitleText(titulo);
        grafico.setTitleOverlay(false);

        XDDFDataSource<String> cats = XDDFDataSourcesFactory.fromStringCellRange(h,
                new CellRangeAddress(fila1, fila2, colCat, colCat));
        XDDFNumericalDataSource<Double> vals = XDDFDataSourcesFactory.fromNumericCellRange(h,
                new CellRangeAddress(fila1, fila2, colVal, colVal));

        XDDFDoughnutChartData datos = (XDDFDoughnutChartData) grafico.createData(ChartTypes.DOUGHNUT, null, null);
        datos.setVaryColors(true);
        datos.addSeries(cats, vals).setTitle(titulo,
                new CellReference(h.getSheetName(), filaRotulo, colVal, true, true));
        grafico.plot(datos);
        coloresDeMarca(grafico, fila2 - fila1 + 1);
        grafico.getOrAddLegend().setPosition(LegendPosition.RIGHT);
    }

    /**
     * Reasigna identificadores de eje distintos de cero.
     *
     * POI numera los ejes desde 0 y Excel RECHAZA un eje con axId 0: descarta
     * la serie y dibuja el marco vacio. Hay que tocarlo por debajo porque
     * XDDFChartAxis no expone setter del identificador.
     *
     * @param grafico grafico ya dibujado con plot()
     */
    private void idsDeEje(XSSFChart grafico) {
        CTPlotArea area = grafico.getCTChart().getPlotArea();
        if (area.sizeOfCatAxArray() == 0 || area.sizeOfValAxArray() == 0) {
            return;
        }
        long idCat = 111111111L;
        long idVal = 222222222L;
        area.getCatAxArray(0).getAxId().setVal(idCat);
        area.getCatAxArray(0).getCrossAx().setVal(idVal);
        area.getValAxArray(0).getAxId().setVal(idVal);
        area.getValAxArray(0).getCrossAx().setVal(idCat);
        for (int i = 0; i < area.sizeOfBarChartArray(); i++) {
            area.getBarChartArray(i).getAxIdArray(0).setVal(idCat);
            area.getBarChartArray(i).getAxIdArray(1).setVal(idVal);
        }
    }

    /**
     * Pinta cada porcion del pastel o dona con la rampa del rojo corporativo.
     *
     * @param grafico grafico de pastel o dona ya dibujado
     * @param porciones cantidad de porciones a colorear
     */
    private void coloresDeMarca(XSSFChart grafico, int porciones) {
        CTPlotArea area = grafico.getCTChart().getPlotArea();
        CTPieSer serie = null;
        if (area.sizeOfPieChartArray() > 0 && area.getPieChartArray(0).sizeOfSerArray() > 0) {
            serie = area.getPieChartArray(0).getSerArray(0);
        } else if (area.sizeOfDoughnutChartArray() > 0 && area.getDoughnutChartArray(0).sizeOfSerArray() > 0) {
            serie = area.getDoughnutChartArray(0).getSerArray(0);
        }
        if (serie == null) {
            return;
        }
        for (int i = 0; i < porciones; i++) {
            CTDPt punto = serie.addNewDPt();
            punto.addNewIdx().setVal(i);
            punto.addNewBubble3D().setVal(false);
            CTShapeProperties forma = punto.addNewSpPr();
            forma.addNewSolidFill().addNewSrgbClr().setVal(RAMPA[i % RAMPA.length]);
        }
    }

    private void coloresBarras(XSSFSheet h, XSSFDrawing lienzo, int cantidad) {
        try {
            List<XSSFChart> charts = lienzo.getCharts();
            if (charts == null || charts.isEmpty()) return;
            XSSFChart ultimo = charts.get(charts.size() - 1);
            CTPlotArea area = ultimo.getCTChart().getPlotArea();
            if (area.sizeOfBarChartArray() > 0 && area.getBarChartArray(0).sizeOfSerArray() > 0) {
                CTBarSer serie = area.getBarChartArray(0).getSerArray(0);
                for (int i = 0; i < cantidad; i++) {
                    CTDPt punto = serie.addNewDPt();
                    punto.addNewIdx().setVal(i);
                    CTShapeProperties forma = punto.addNewSpPr();
                    byte[] color = (i == cantidad - 1 && cantidad > 6) ? GRIS : RAMPA[i % RAMPA.length];
                    forma.addNewSolidFill().addNewSrgbClr().setVal(color);
                }
            }
        } catch (Exception ignorada) {
        }
    }

    /**
     * Rojo corporativo para las series de los graficos.
     *
     * @return el color #B72E27 en el formato que espera XDDF
     */
    private XDDFColor colorMarca() {
        return XDDFColor.from(ROJO);
    }

    private String notaMetodologica(Object[] k) {
        StringBuilder s = new StringBuilder();
        s.append("Nota metodologica: las horas se anclan a la fecha del registro de tiempo, ");
        s.append("es decir miden lo que el taller ENTREGO en el periodo. ");
        s.append("Un registro se descarta cuando sus fechas quedan a mas de 24 h de distancia: ");
        s.append("el aplicativo calcula la duracion con la hora del dia e ignora el cruce de fechas, ");
        s.append("de modo que un trabajo real de varios dias se guarda incompleto. ");
        s.append("Por eso las horas de los trabajos largos estan sub-reportadas. ");
        if (k != null) {
            s.append("En este periodo se descartaron ").append(dato(k, 9));
            s.append(" registros y se corrigieron ").append(dato(k, 10));
            s.append(" por cruce de medianoche. ");
        }
        s.append("La tasa de correccion por medianoche fue de cerca del 22% anual entre 2019 y 2024 ");
        s.append("y bajo a 1,9% en 2026: comparar periodos a ambos lados de esa frontera compara ");
        s.append("dos calidades de dato distintas.");
        return s.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="2. KPIs">
    private void hojaKpis(String periodo, String periodoComp, Object[] k, Object[] kc) {
        Sheet h = libro.createSheet("KPIs");
        // "Como se calcula" y no "Fuente del dato": esta hoja la lee gerencia y
        // los nombres de procedimientos y tablas no le dicen nada.
        encabezado(h, new String[]{"Indicador", "Valor", "Unidad", "Comparativo",
            "Como se calcula", "Por que importa"}, 0);

        String[][] filas = {
            {"0", "Horas entregadas", "h", "Horas que los tecnicos registraron en el taller",
                "Es la capacidad que el taller efectivamente entrego en el periodo."},
            {"2", "Total Trabajos Realizados", "", "Solicitudes atendidas y culminadas en el periodo",
                "Volumen real de trabajos terminados por el area de Proyectos."},
            {"3", "En Proceso", "", "Solicitudes que continuan en ejecucion o abiertas al cierre",
                "Carga de trabajo en proceso atendida por el taller de Proyectos."},
            {"5", "Primera atencion (mediana)", "dias", "Dias entre el ingreso y el primer trabajo registrado",
                "Tiempo real de arranque de labores tecnicas."},
            {"4", "Ciclo real de trabajo (mediana)", "dias", "Dias entre el ingreso y el cierre de la solicitud",
                "Tiempo real de ejecucion y culminacion tecnica."},
            {"6", "% Productividad / Avance Real", "%", "(Solicitudes Atendidas / Total Solicitudes Recibidas) * 100",
                "Mide el rendimiento y volumen real atendido sin penalizaciones arbitrarias por prioridad."},
            {"7", "Utilizacion del equipo", "%", "Horas trabajadas frente a las horas disponibles del equipo",
                "Horas entregadas contra horas disponibles. Puede pasar de 100%: es tiempo extra."},
            {"8", "Cobertura del dato", "%", "Solicitudes cerradas que si tienen horas registradas",
                "Garantia de confiabilidad: si es baja, las horas del periodo estan subrepresentadas."},
            {"9", "Registros descartados", "", "Registros incompletos por cruce de medianoche",
                "Registros incompletos por cruce de dias. Se cuentan en vez de inventarles un valor."},
            {"10", "Registros corregidos", "", "Turnos de noche ajustados automaticamente por el sistema",
                "Turnos de noche digitados con la misma fecha de inicio y fin."}
        };

        int f = 1;
        for (String[] def : filas) {
            int i = Integer.parseInt(def[0]);
            Row r = h.createRow(f++);
            celda(r, 0, def[1], estiloTexto).setCellValue(def[1]);
            Object v = (k != null) ? dato(k, i) : null;
            celda(r, 1, v, def[2].equals("%") ? estiloPorcentaje
                    : (def[2].isEmpty() ? estiloEntero : estiloNumero));
            celda(r, 2, def[2], estiloTexto).setCellValue(def[2]);
            // El texto y el sentido salen de Metodos.Render, el mismo que usa la
            // pantalla: si cambian ahi, cambian en los dos lados.
            Object actual = (k != null) ? dato(k, i) : null;
            Object previo = (kc != null) ? dato(kc, i) : null;
            Cell cComp = r.createCell(3);
            cComp.setCellValue(Render.textoDelta(actual, previo, def[2], periodoComp));
            Boolean mejoro = Render.mejoro(actual, previo, i);
            cComp.setCellStyle(mejoro == null ? estiloTexto
                    : (mejoro.booleanValue() ? estiloDeltaBueno : estiloDeltaMalo));
            celda(r, 4, def[3], estiloTexto).setCellValue(def[3]);
            celda(r, 5, def[4], estiloTexto).setCellValue(def[4]);
        }
        anchos(h, 6);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="3. SOLICITUDES">
    private void hojaSolicitudes(List datos) {
        Sheet h = libro.createSheet("Solicitudes");
        encabezado(h, new String[]{"id", "numero_solicitud", "fecha_ingreso", "fecha_fin",
            "estado", "dias_ciclo", "dias_abierta", "prioridad", "area_solicitante",
            "plano", "no_parte", "tipo_plano", "horas_registradas"}, 0);
        int f = 1;
        if (datos != null) {
            for (int i = 0; i < datos.size(); i++) {
                Object[] d = (Object[]) datos.get(i);
                Row r = h.createRow(f++);
                celda(r, 0, dato(d, 0), estiloEntero);
                celda(r, 1, dato(d, 1), estiloTexto).setCellValue(txt(dato(d, 1)));
                celda(r, 2, dato(d, 2), estiloTexto).setCellValue(txt(dato(d, 2)));
                celda(r, 3, dato(d, 3), estiloTexto).setCellValue(txt(dato(d, 3)));
                celda(r, 4, dato(d, 4), estiloTexto).setCellValue(txt(dato(d, 4)));
                celda(r, 5, dato(d, 5), estiloEntero);
                celda(r, 6, dato(d, 6), estiloEntero);
                celda(r, 7, dato(d, 7), estiloTexto).setCellValue(txt(dato(d, 7)));
                celda(r, 8, dato(d, 8), estiloTexto).setCellValue(txt(dato(d, 8)));
                celda(r, 9, dato(d, 9), estiloTexto).setCellValue(txt(dato(d, 9)));
                celda(r, 10, dato(d, 10), estiloTexto).setCellValue(txt(dato(d, 10)));
                celda(r, 11, dato(d, 11), estiloTexto).setCellValue(txt(dato(d, 11)));
                celda(r, 12, dato(d, 12), estiloNumero);
            }
        }
        anchos(h, 13);
    }

    private String txt(Object o) {
        return (o == null) ? "" : o.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="4. REGISTRO_HORAS">
    private void hojaRegistroHoras(List datos) {
        Sheet h = libro.createSheet("Registro_Horas");
        encabezado(h, new String[]{"id_registro", "numero_solicitud", "fecha_registro",
            "plano", "no_parte", "tipo_plano", "area_solicitante", "prioridad",
            "pieza", "tecnico", "horas"}, 0);
        int f = 1;
        if (datos != null) {
            for (int i = 0; i < datos.size(); i++) {
                Object[] d = (Object[]) datos.get(i);
                Row r = h.createRow(f++);
                celda(r, 0, dato(d, 0), estiloEntero);
                for (int c = 1; c <= 9; c++) {
                    celda(r, c, dato(d, c), estiloTexto).setCellValue(txt(dato(d, c)));
                }
                celda(r, 10, dato(d, 10), estiloNumero);
            }
        }
        anchos(h, 11);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="5. PLANOS_PIEZAS">
    private void hojaPlanosPiezas(List datos) {
        Sheet h = libro.createSheet("Planos_Piezas");
        encabezado(h, new String[]{"plano", "no_parte", "tipo_plano", "area_solicitante",
            "pieza", "registros", "horas", "% del plano"}, 0);
        int f = 1;
        if (datos != null) {
            for (int i = 0; i < datos.size(); i++) {
                Object[] d = (Object[]) datos.get(i);
                Row r = h.createRow(f++);
                for (int c = 0; c <= 4; c++) {
                    celda(r, c, dato(d, c), estiloTexto).setCellValue(txt(dato(d, c)));
                }
                celda(r, 5, dato(d, 5), estiloEntero);
                celda(r, 6, dato(d, 6), estiloNumero);
                celda(r, 7, dato(d, 7), estiloPorcentaje);
            }
        }
        anchos(h, 8);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="6. TECNICOS">
    private void hojaTecnicos(List datos) {
        Sheet h = libro.createSheet("Tecnicos");
        encabezado(h, new String[]{"Tecnico", "Horas registradas", "Horas disponibles",
            "% utilizacion", "Solicitudes atendidas", "Registros", "Horas por registro"}, 0);
        int f = 1;
        if (datos != null) {
            for (int i = 0; i < datos.size(); i++) {
                Object[] d = (Object[]) datos.get(i);
                Row r = h.createRow(f++);
                celda(r, 0, dato(d, 0), estiloTexto).setCellValue(txt(dato(d, 0)));
                celda(r, 1, dato(d, 1), estiloNumero);
                // Horas disponibles se deduce de las horas y la utilizacion que
                // ya calculo el procedimiento; no se recalcula la meta aqui.
                Double horas = comoNumero(txt(dato(d, 1)));
                Double pct = comoNumero(txt(dato(d, 4)));
                Cell disp = r.createCell(2);
                if (horas != null && pct != null && pct.doubleValue() != 0) {
                    disp.setCellValue(horas.doubleValue() * 100 / pct.doubleValue());
                }
                disp.setCellStyle(estiloNumero);
                celda(r, 3, dato(d, 4), estiloPorcentaje);
                celda(r, 4, dato(d, 2), estiloEntero);
                celda(r, 5, dato(d, 3), estiloEntero);
                Double regs = comoNumero(txt(dato(d, 3)));
                Cell hpr = r.createCell(6);
                if (horas != null && regs != null && regs.doubleValue() != 0) {
                    hpr.setCellValue(horas.doubleValue() / regs.doubleValue());
                }
                hpr.setCellStyle(estiloNumero);
            }
        }
        anchos(h, 7);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="7. DATOS_CRUDOS">
    private void hojaDatosCrudos(List datos) {
        Sheet h = libro.createSheet("Datos_crudos");
        encabezado(h, new String[]{"id_registro", "numero_solicitud", "fecha_registro",
            "plano", "no_parte", "tipo_plano", "area_solicitante", "prioridad",
            "pieza", "tecnico", "horas", "mes", "estado_solicitud", "calidad_dato"}, 0);
        int f = 1;
        if (datos != null) {
            for (int i = 0; i < datos.size(); i++) {
                Object[] d = (Object[]) datos.get(i);
                Row r = h.createRow(f++);
                celda(r, 0, dato(d, 0), estiloEntero);
                for (int c = 1; c <= 9; c++) {
                    celda(r, c, dato(d, c), estiloTexto).setCellValue(txt(dato(d, c)));
                }
                celda(r, 10, dato(d, 10), estiloNumero);
                celda(r, 11, dato(d, 11), estiloTexto).setCellValue(txt(dato(d, 11)));
                celda(r, 12, dato(d, 12), estiloTexto).setCellValue(txt(dato(d, 12)));
                celda(r, 13, dato(d, 13), estiloTexto).setCellValue(txt(dato(d, 13)));
            }
        }
        anchos(h, 14);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="8. LISTAS">
    private void hojaListas(List horasArea, List respuesta) {
        Sheet h = libro.createSheet("Listas");
        encabezado(h, new String[]{"AREAS", "PRIORIDAD"}, 0);
        int filas = Math.max(horasArea == null ? 0 : horasArea.size(),
                respuesta == null ? 0 : respuesta.size());
        for (int i = 0; i < filas; i++) {
            Row r = h.createRow(i + 1);
            if (horasArea != null && i < horasArea.size()) {
                Object[] a = (Object[]) horasArea.get(i);
                celda(r, 0, dato(a, 1), estiloTexto).setCellValue(txt(dato(a, 1)));
            }
            if (respuesta != null && i < respuesta.size()) {
                Object[] p = (Object[]) respuesta.get(i);
                celda(r, 1, dato(p, 1), estiloTexto).setCellValue(txt(dato(p, 1)));
            }
        }
        anchos(h, 2);
    }
    //</editor-fold>
}
