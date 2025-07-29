package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.MoveItemJpaController;
import Controller.ItemJpaController;
import Controller.ReferenceControllerJpa;

import SQL.ConnectionFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class Tag_moveItem extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        ConnectionFactory FactoryJpa = new ConnectionFactory();
        MoveItemJpaController MoveJpa = new MoveItemJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();
        ReferenceControllerJpa ReferenceJpa = new ReferenceControllerJpa();
        List lst_factory = null;
        List lst_move = null;
        List lst_item = null;
        List lst_ref = null;
        JspWriter out = pageContext.getOut();
        String ref = "", dtIn = "", dtFn = "", numEnt = "", moveDate = "", moveValid = "";
        int id_ref = 0;
        try {
            ref = pageContext.getRequest().getAttribute("ref").toString();
        } catch (Exception e) {
            ref = "";
        }
        try {
            numEnt = pageContext.getRequest().getAttribute("numEnt").toString();
        } catch (Exception e) {
            numEnt = "";
        }
        try {
            moveValid = pageContext.getRequest().getAttribute("moveValid").toString();
        } catch (Exception e) {
            moveValid = "";
        }
        try {
            dtIn = pageContext.getRequest().getAttribute("DateIni").toString();
            dtFn = pageContext.getRequest().getAttribute("DateFin").toString();
        } catch (Exception e) {
            dtIn = "";
            dtFn = "";
        }
        try {
            moveDate = pageContext.getRequest().getAttribute("dateMove").toString();
        } catch (Exception e) {
            moveDate = "";
        }
        try {

            if (!numEnt.equals("") && !ref.equals("")) {
                //<editor-fold defaultstate="collapsed" desc="MOVE ITEMS">

                if (!moveValid.equals("")) {
                    //<editor-fold defaultstate="collapsed" desc="COMPLETE DATA">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block; z-index: 2000;'>");
                    out.print("<div class='contGeneral' style='width: 65%; right: 8%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Completar datos de movimiento </h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    String[] dataMove = moveValid.split(",");

                    out.print("<form action='MoveItem?opt=3&numEnt=" + numEnt + "&dateMove=" + moveDate + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&txt_ref=" + ref + "' method='post' class='needs-validation' novalidate=''>");

                    out.print("<div class='d-flex' style='justify-content: center;align-items: center;box-shadow: 0px 0px 6px 0px #a5a4a4;padding: 10px;border-radius: 3px;'>");
                    out.print("<div class='col-lg-4 d-flex text-center align-items-baseline'>");
                    out.print("<h4>Movimiento:</h4> <span style='font-weight: bold;font-size: 19px;color: #33bf98;'>&nbsp;" + numEnt + "</span>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 d-flex text-center align-items-baseline'>");
                    out.print("<h4>Fecha:</h4> <span style='font-weight: bold;font-size: 19px;color: #33bf98;'>&nbsp;" + moveDate + "</span>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div id='showItemsList' class='showItemsList'>");
                    int count = 0;
                    for (String dta : dataMove) {
                        count++;
                        String idItem = dta.split(" / ")[0];
                        String numItm = dta.split(" / ")[1];
                        out.print("<div class='d-flex mt-2' style='justify-content: space-evenly'>");
                        out.print("<input type='text' class='form-control' name='txtIdItem" + count + "' id=''  value='" + idItem + "'>");
                        out.print("<div class=''>");
                        out.print("<span>Item</span>");
                        out.print("<input type='text' class='form-control disabled' name='' id='' value='" + numItm + "'>");
                        out.print("</div>");
                        out.print("<div class='col-lg-5'>");
                        out.print("<span>Ubicaciones</span>");
                        out.print("<input type='text' class='form-control' name='txtLocation" + count + "' id='' placeholder='Ubicacion' value='' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-5'>");
                        out.print("<span>Observaciones</span>");
                        out.print("<input type='text' class='form-control' name='txtObs" + count + "' id='' placeholder='Observaciones' value='' required>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");

                    out.print("<input type='text' class='form-control' name='txtCountData' id='' value='" + count + "'>");

                    out.print("<div class='text-center mt-2'>");
                    out.print("<button class='btn btn-green'>Confirmar movimiento</button>");
                    out.print("</div>");
                    out.print("</form>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }

                //<editor-fold defaultstate="collapsed" desc="FORM REGISTER NEW ITEM">
                lst_ref = ReferenceJpa.ConsultReferencesFact(ref);
                if (lst_ref != null) {
                    Object[] ObjRef = (Object[]) lst_ref.get(0);
                    id_ref = Integer.parseInt(ObjRef[0].toString());
                }
                lst_item = ItemJpa.ConsultLastItem();
                int lstItem = 0;
                if (lst_item != null) {
                    Object[] ObjIt = (Object[]) lst_item.get(0);
                    int temp = Integer.parseInt(ObjIt[1].toString());
                    lstItem = temp + 1;
                } else {
                    lstItem = 1;
                }
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none; z-index: 2050;'>");
                out.print("<div class='contGeneral' style='width: 52%; top: 4%; right: 16%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Item</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='d-flex' style='justify-content: center;'>");
                out.print("<form action='MoveItem?opt=2&id_ref=" + id_ref + "&TypeMov=ENT&numMov=" + numEnt.replace("ENT", "").trim() + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class=''>");
                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class=''>Referencia</span>");
                out.print("<input type='text' class='form-control disabled' name='txt_ref' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ref + "'>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class=''>Item</span>");
                out.print("<input type='text' class='form-control ' name='txt_numItem' id='' data-toggle='tooltip' data-placement='top' title='' value='" + lstItem + "'>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class=''>Fecha</span>");
                out.print("<input type='date' class='form-control' name='txt_date' id='' data-toggle='tooltip' data-placement='top' title='' value='" + moveDate + "' required>");
                out.print("</div>");
                out.print("</div>");
                
                out.print("<div class='d-flex'>");
                
                out.print("<div class='col-lg-4 mt-2'>");
                out.print("<span class=''>Ubicación</span>");
                out.print("<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                
                out.print("<div class='col-lg-4 mt-2'>");
                out.print("<span class=''>Modelo</span>");
                out.print("<input type='text' class='form-control' name='txt_model' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                
                out.print("<div class='col-lg-4 mt-2'>");
                out.print("<span class=''>Serial</span>");
                out.print("<input type='text' class='form-control' name='txt_serial' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                
                
                out.print("</div>");
                
                
                out.print("<div class='col-lg-12 mt-2'>");
                out.print("<span class=''>Observaciones</span>");
                out.print("<textarea class='form-control' style='margin: auto; margin-left: 12px;' name='txt_Obs' placeholder='' required></textarea>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='text-center mt-2'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
                out.print("</div>");
                
                
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 50%; right: 17%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Movimiento item - " + numEnt + "</h4>");
                out.print("<div class=''>");
                if (numEnt.contains("ENT")) {
                    out.print("<button class='btn btn-green mr-2' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-plus'></i></button>");
                    out.print("<button class='btn btn-success mr-2' onclick='formToMove()' style='height: 30px;padding: 3px;width: 30px;' data-toggle='tooltip' data-placement='top' title='Generar entrada'><i class=\"fas fa-reply\"></i></button>");
                } else if (numEnt.contains("SAL")) {
                    out.print("<button class='btn btn-info mr-2' onclick='formToMove()' style='height: 30px;padding: 3px;width: 30px;' data-toggle='tooltip' data-placement='top' title='Generar salida'><i class=\"fas fa-share\"></i></button>");
                }
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_item = ItemJpa.ConsultItemsByReferences(ref);
                if (lst_item != null) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULT ITEMS BY REFERENCES CODE">
                    out.print("<table class='table table-bordered tableRef' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Item</th>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Ultimo mov</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_item.size(); i++) {
                        Object[] ObjItem = (Object[]) lst_item.get(i);
                        String lastMove = ObjItem[8].toString();
                        if ((lastMove.contains("ENT") && numEnt.contains("ENT")) || (lastMove.contains("SAL") && numEnt.contains("SAL"))) {
                            out.print("<tr class='disabled'>");
                        } else {
                            out.print("<tr data-id='" + ObjItem[0] + " / " + ObjItem[1] + "' style='cursor: pointer;'>");
                        }
                        out.print("<td>" + ObjItem[1] + "</td>");
                        out.print("<td>" + ObjItem[3] + "</td>");
                        out.print("<td>" + ObjItem[4] + "</td>");
                        out.print("<td>" + ObjItem[8] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<form action='MoveItem?opt=1&txt_ref=" + ref + "&numEnt=" + numEnt + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&dateMove=" + moveDate + "' method='post' class='needs-validation' novalidate='' id='formDataMove'>");
                    out.print("<input type='hidden' class='form-control' name='txt_fieldMove' id='FieldMove' value=''>");
                    out.print("</form>");
                } else {
                    out.print("<div class=''>");
                    out.print("<div class='text-center'>");
                    out.print("<h5>No se han encontrado items relacionados a esta referencia! </h5>");
                    out.print("<h5>'" + ref + "'</h5>");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    out.print("<h6>¿Desea registrar primera entrada con este movimiento? </h6>");
                    out.print("<span class=''>El siguiente consecutivo para los items es: <b>" + lstItem + "</b></span>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="MOVE ITEM MODULE">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h2>Movimiento de items</h2>");
            out.print("</div>");
            out.print("<div class='card-body text-center'>");
            //<editor-fold defaultstate="collapsed" desc="FORM SEARCH DATA">
            out.print("<h4>Buscar movimiento de factory: </h4>");
            out.print("<form action='MoveItem?opt=1' method='post' class='needs-validation' novalidate='' id='FormMove'>");
            out.print("<div class='d-flex' style='justify-content: center;'>");
            out.print("<div class='col-lg-4'>");
            out.print("<label for='startDate' class='form-label mt-2'><b>Referencia:</b></label>");
            out.print("<input type='text' class='form-control mr-2' name='txt_ref' id='idRef' data-toggle='tooltip' data-placement='top' title='Referencia' placeholder='Referencia...' value='" + ref + "' autocomplete='off'>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<label for='startDate' class='form-label mt-2'><b>Fecha de inicio:</b></label>");
            out.print("<input type='date' class='form-control mr-2' name='txtDateIni' id='startDate' data-toggle='tooltip' data-placement='top' title='' value='" + dtIn + "'>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<label for='endDate' class='form-label mt-2'><b>Fecha de fin:</b></label>");
            out.print("<input type='date' class='form-control' name='txtDateFin' id='endDate' data-toggle='tooltip' data-placement='top' title='' value='" + dtFn + "'>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='text-center mt-2'>");
            out.print("<button type='button' class='btn btn-green' onclick='SearchItems()'>Buscar.. <i class='fas fa-search'></i></button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            //</editor-fold>

            lst_ref = ReferenceJpa.ConsultReferencesFact(ref);
            if (lst_ref != null) {
                if (!ref.equals("")) {
                    //<editor-fold defaultstate="collapsed" desc="RESULT OF SEARCH">
                    //<editor-fold defaultstate="collapsed" desc="TABLE ENT">
                    out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                    out.print("<div class='card col-lg-5' style='box-shadow: 0px 0px 20px 1px #e7e7e7;'>");
                    out.print("<div class='card-header' style='justify-content: center;'>");
                    out.print("<h3><b class='text-success'>ENTRADAS</b></h3>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    out.print("<div id='divFixedR5' style='max-height: 373px;overflow-y: scroll;'>");
                    out.print("<table class='table table-sm' id=''>");
                    out.print("<thead  class='sticky-top'>");
                    out.print("<tr>");
                    out.print("<th>COD. REF</th>");
                    out.print("<th>ENT</th>");
                    out.print("<th>FECHA</th>");
                    out.print("<th>OBS</th>");
                    out.print("<th>-</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    //<editor-fold defaultstate="collapsed" desc="VALID ENT">
                    lst_factory = FactoryJpa.ConsulReferenceENT(ref, dtIn, dtFn);
                    if (lst_factory.size() > 0) {
                        try {
                            List<String> entNums = new ArrayList<>();
                            String[] arrEnt = lst_factory.toString().replace("[", "").replace("]", "").split("---");
                            for (String item : arrEnt) {
                                String[] entDat = item.split(" / ");
                                if (entDat.length > 2) {
                                    entNums.add(entDat[1].replace("ENT", ""));
                                }
                            }
                            lst_move = MoveJpa.compareItems(entNums, dtIn, dtFn);
                            Set<String> misIDs = new HashSet<>();
                            if (lst_move != null) {
                                //<editor-fold defaultstate="collapsed" desc="LIST OF ITEMS BY REFERENCE">
                                for (Object obj : lst_move) {
                                    Object[] ObjVl = (Object[]) obj;
                                    misIDs.add(ObjVl[3].toString());
                                }
                                for (String item : arrEnt) {
                                    String[] entDat = item.split(" / ");
                                    String valid = entDat[1].replace("ENT", "");
                                    if (entDat.length > 2 && !misIDs.contains(valid)) {
                                        out.print("<tr style='font-size: 12px;'>");
                                        out.print("<td>" + entDat[0].replace(",", "") + "</td>");
                                        out.print("<td>" + entDat[1] + "</td>");
                                        out.print("<td>" + entDat[2] + "</td>");
                                        out.print("<td>" + entDat[3] + "</td>");
                                        out.print("<td><button class='btn btn-success btn-sm' onclick='window.location.href=\"MoveItem?opt=1&txt_ref=" + ref + "&numEnt=" + entDat[1] + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&dateMove=" + entDat[2] + "\"'><i class=\"fas fa-reply\"></i></button></td>");
                                        out.print("</tr>");
                                    }
                                }
                            } else {
                                for (String item : arrEnt) {
                                    String[] entDat = item.split(" / ");
                                    out.print("<tr style='font-size: 12px;'>");
                                    out.print("<td>" + entDat[0].replace(",", "") + "</td>");
                                    out.print("<td>" + entDat[1] + "</td>");
                                    out.print("<td>" + entDat[2] + "</td>");
                                    out.print("<td>" + entDat[3] + "</td>");
                                    out.print("<td><button class='btn btn-success btn-sm' onclick='window.location.href=\"MoveItem?opt=1&txt_ref=" + ref + "&numEnt=" + entDat[1] + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&dateMove=" + entDat[2] + "\"'><i class=\"fas fa-reply\"></i></button></td>");
                                    out.print("</tr>");
                                }
                                //</editor-fold>
                            }
                        } catch (Exception e) {
                            out.print("<tr>");
                            out.print("<td colspan='5' class='text-center'>No se ha encontrado ENTRADAS de esta referencia! </td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td colspan='5' class='text-center'>No se ha encontrado ENTRADAS de esta referencia! </td>");
                        out.print("</tr>");
                    }
                    //</editor-fold>
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="TABLE SAL">

                    out.print("<div class='card col-lg-5' style='box-shadow: 0px 0px 20px 1px #e7e7e7;'>");
                    out.print("<div class='card-header' style='justify-content: center;'>");
                    out.print("<h3><b class='text-info'>SALIDAS</b></h3>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    out.print("<div id='divFixedR5' style='max-height: 373px;overflow-y: scroll;'>");
                    out.print("<table class='table table-sm' id=''>");
                    out.print("<thead class='sticky-top'>");
                    out.print("<tr>");
                    out.print("<th>COD. REF</th>");
                    out.print("<th>ENT</th>");
                    out.print("<th>FECHA</th>");
                    out.print("<th>OBS</th>");
                    out.print("<th>-</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    //<editor-fold defaultstate="collapsed" desc="VALID SAL">

                    lst_factory = FactoryJpa.ConsulReferenceSAL(ref, dtIn, dtFn);
                    if (lst_factory.size() > 0) {
                        List<String> salNums = new ArrayList<>();
                        String[] arrSal = lst_factory.toString().replace("[", "").replace("]", "").split("---");

                        for (String item : arrSal) {
                            String[] salDat = item.split(" / ");
                            if (salDat.length > 2) {
                                salNums.add(salDat[1].replace("SAL", ""));
                            }
                        }
                        lst_move = MoveJpa.compareItems(salNums, dtIn, dtFn);
                        Set<String> misIds = new HashSet<>();
                        if (lst_move != null) {
                            //<editor-fold defaultstate="collapsed" desc="LIST OF ITEMS BY REFERENCE">
                            for (Object obj : lst_move) {
                                Object[] ObjVl = (Object[]) obj;
                                misIds.add(ObjVl[3].toString());
                            }
                            for (String item : arrSal) {
                                String[] salDat = item.split(" / ");
                                if (salDat.length > 2 && !misIds.contains(salDat[1].replace("SAL", ""))) {
                                    out.print("<tr style='font-size: 12px;'>");
                                    out.print("<td>" + salDat[0].replace(",", "") + "</td>");
                                    out.print("<td>" + salDat[1] + "</td>");
                                    out.print("<td>" + salDat[2] + "</td>");
                                    out.print("<td>" + salDat[3] + "</td>");
                                    out.print("<td><button class='btn btn-info btn-sm' onclick='window.location.href=\"MoveItem?opt=1&txt_ref=" + ref + "&numEnt=" + salDat[1] + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&dateMove=" + salDat[2] + "\"'><i class=\"fas fa-share\"></i></button></td>");
                                    out.print("</tr>");
                                }
                            }
                        } else {
                            for (String item : arrSal) {
                                String[] salDat = item.split(" / ");
                                out.print("<tr style='font-size: 12px;'>");
                                out.print("<td>" + salDat[0].replace(",", "") + "</td>");
                                out.print("<td>" + salDat[1] + "</td>");
                                out.print("<td>" + salDat[2] + "</td>");
                                out.print("<td>" + salDat[3] + "</td>");
                                out.print("<td><button class='btn btn-info btn-sm' onclick='window.location.href=\"MoveItem?opt=1&txt_ref=" + ref + "&numEnt=" + salDat[1] + "&txtDateIni=" + dtIn + "&txtDateFin=" + dtFn + "&dateMove=" + salDat[2] + "\"'><i class=\"fas fa-share\"></i></button></td>");
                                out.print("</tr>");
                            }
                        }
                        //</editor-fold>
                    } else {
                        out.print("<tr>");
                        out.print("<td colspan='5' class='text-center'>No se ha encontrado SALIDAS de esta referencia! </td>");
                        out.print("</tr>");
                    }
                    //</editor-fold>
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //</editor-fold>
                }
            } else {
                out.print("<script>");
                out.print("$(\"#toastr-2\").ready(function () {\n"
                        + "     iziToast.warning({\n"
                        + "         title: 'Atención!',\n"
                        + "         message: 'No se ha encontrado referencia en el sistema, recuerde que primero se debe ingresar al sistema, antes de hacer movimientos!.',\n"
                        + "         position: 'topRight'\n"
                        + "     });\n"
                        + " });");
                out.print("</script>");
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_moveItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_moveItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
