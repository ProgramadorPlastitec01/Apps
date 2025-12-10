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
@Table(name = "vwf_calificacion_de_competencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findAll", query = "SELECT v FROM VwfCalificacionDeCompetencias v"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByDocumento", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByNombre", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByApellidos", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByCargo", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByArea", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.area = :area"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByJefe", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.jefe = :jefe"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByFecha", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByRegistro", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.registro = :registro"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByVersion", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.version = :version"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByCalificacion100", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.calificacion100 = :calificacion100"),
    @NamedQuery(name = "VwfCalificacionDeCompetencias.findByCalificacion5", query = "SELECT v FROM VwfCalificacionDeCompetencias v WHERE v.calificacion5 = :calificacion5")})
public class VwfCalificacionDeCompetencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Column(name = "Jefe")
    private String jefe;
    @Lob
    @Column(name = "Evaluadores")
    private String evaluadores;
    @Column(name = "Fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "Registro")
    private String registro;
    @Basic(optional = false)
    @Column(name = "Version")
    private String version;
    @Lob
    @Column(name = "Grupos_calificacion")
    private String gruposcalificacion;
    @Column(name = "Calificacion 100%")
    private String calificacion100;
    @Column(name = "Calificacion 5%")
    private String calificacion5;
    @Lob
    @Column(name = "Recomendacion")
    private String recomendacion;

    public VwfCalificacionDeCompetencias() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public String getEvaluadores() {
        return evaluadores;
    }

    public void setEvaluadores(String evaluadores) {
        this.evaluadores = evaluadores;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGruposcalificacion() {
        return gruposcalificacion;
    }

    public void setGruposcalificacion(String gruposcalificacion) {
        this.gruposcalificacion = gruposcalificacion;
    }

    public String getCalificacion100() {
        return calificacion100;
    }

    public void setCalificacion100(String calificacion100) {
        this.calificacion100 = calificacion100;
    }

    public String getCalificacion5() {
        return calificacion5;
    }

    public void setCalificacion5(String calificacion5) {
        this.calificacion5 = calificacion5;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
    
}
