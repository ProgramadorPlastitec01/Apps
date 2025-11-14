/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vwf_calificacion_sst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfCalificacionSst.findAll", query = "SELECT v FROM VwfCalificacionSst v"),
    @NamedQuery(name = "VwfCalificacionSst.findByFecha", query = "SELECT v FROM VwfCalificacionSst v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfCalificacionSst.findByDocumento", query = "SELECT v FROM VwfCalificacionSst v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfCalificacionSst.findByNombres", query = "SELECT v FROM VwfCalificacionSst v WHERE v.nombres = :nombres"),
    @NamedQuery(name = "VwfCalificacionSst.findByApellidos", query = "SELECT v FROM VwfCalificacionSst v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfCalificacionSst.findByCargo", query = "SELECT v FROM VwfCalificacionSst v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfCalificacionSst.findByArea", query = "SELECT v FROM VwfCalificacionSst v WHERE v.area = :area"),
    @NamedQuery(name = "VwfCalificacionSst.findByA100", query = "SELECT v FROM VwfCalificacionSst v WHERE v.a100 = :a100"),
    @NamedQuery(name = "VwfCalificacionSst.findByA5", query = "SELECT v FROM VwfCalificacionSst v WHERE v.a5 = :a5"),
    @NamedQuery(name = "VwfCalificacionSst.findByA15", query = "SELECT v FROM VwfCalificacionSst v WHERE v.a15 = :a15"),
    @NamedQuery(name = "VwfCalificacionSst.findByEvaluador", query = "SELECT v FROM VwfCalificacionSst v WHERE v.evaluador = :evaluador")})
public class VwfCalificacionSst implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Nombres")
    private String nombres;
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Column(name = "1 a 100")
    private String a100;
    @Column(name = "1 a 5")
    private String a5;
    @Column(name = "1 a 15")
    private String a15;
    @Column(name = "Evaluador")
    private String evaluador;

    public VwfCalificacionSst() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getA100() {
        return a100;
    }

    public void setA100(String a100) {
        this.a100 = a100;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public String getA15() {
        return a15;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public String getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(String evaluador) {
        this.evaluador = evaluador;
    }
    
}
