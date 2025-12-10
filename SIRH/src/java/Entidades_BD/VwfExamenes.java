/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vwf_examenes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfExamenes.findAll", query = "SELECT v FROM VwfExamenes v"),
    @NamedQuery(name = "VwfExamenes.findByDocumento", query = "SELECT v FROM VwfExamenes v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfExamenes.findByApellidos", query = "SELECT v FROM VwfExamenes v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfExamenes.findByNombre", query = "SELECT v FROM VwfExamenes v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfExamenes.findByFecha", query = "SELECT v FROM VwfExamenes v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfExamenes.findByConcepto", query = "SELECT v FROM VwfExamenes v WHERE v.concepto = :concepto"),
    @NamedQuery(name = "VwfExamenes.findByCentroMedico", query = "SELECT v FROM VwfExamenes v WHERE v.centroMedico = :centroMedico")})
public class VwfExamenes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Concepto")
    private String concepto;
    @Column(name = "Centro Medico")
    private String centroMedico;
    @Lob
    @Column(name = "Recomendaciones")
    private String recomendaciones;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;
    @Lob
    @Column(name = "Compromiso")
    private String compromiso;
    @Lob
    @Column(name = "Restricciones")
    private String restricciones;
    @Lob
    @Column(name = "Examenes")
    private String examenes;

    public VwfExamenes() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCentroMedico() {
        return centroMedico;
    }

    public void setCentroMedico(String centroMedico) {
        this.centroMedico = centroMedico;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public String getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(String compromiso) {
        this.compromiso = compromiso;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public String getExamenes() {
        return examenes;
    }

    public void setExamenes(String examenes) {
        this.examenes = examenes;
    }
    
}
