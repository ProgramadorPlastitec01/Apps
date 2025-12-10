/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c"),
    @NamedQuery(name = "Calificacion.findByIdCalificacion", query = "SELECT c FROM Calificacion c WHERE c.idCalificacion = :idCalificacion"),
    @NamedQuery(name = "Calificacion.findByNombre", query = "SELECT c FROM Calificacion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Calificacion.findByFrecuencia", query = "SELECT c FROM Calificacion c WHERE c.frecuencia = :frecuencia"),
    @NamedQuery(name = "Calificacion.findByIdTipoCalificacion", query = "SELECT c FROM Calificacion c WHERE c.idTipoCalificacion = :idTipoCalificacion"),
    @NamedQuery(name = "Calificacion.findByIdArea", query = "SELECT c FROM Calificacion c WHERE c.idArea = :idArea"),
    @NamedQuery(name = "Calificacion.findByIdSubgrupo", query = "SELECT c FROM Calificacion c WHERE c.idSubgrupo = :idSubgrupo"),
    @NamedQuery(name = "Calificacion.findByDocumento", query = "SELECT c FROM Calificacion c WHERE c.documento = :documento"),
    @NamedQuery(name = "Calificacion.findByEjecuta", query = "SELECT c FROM Calificacion c WHERE c.ejecuta = :ejecuta"),
    @NamedQuery(name = "Calificacion.findByRevisa", query = "SELECT c FROM Calificacion c WHERE c.revisa = :revisa"),
    @NamedQuery(name = "Calificacion.findByAprueba", query = "SELECT c FROM Calificacion c WHERE c.aprueba = :aprueba"),
    @NamedQuery(name = "Calificacion.findByFechaRegistro", query = "SELECT c FROM Calificacion c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Calificacion.findByUsuarioRegistro", query = "SELECT c FROM Calificacion c WHERE c.usuarioRegistro = :usuarioRegistro")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_calificacion")
    private Integer idCalificacion;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "frecuencia")
    private Double frecuencia;
    @Column(name = "id_tipo_calificacion")
    private Integer idTipoCalificacion;
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "id_subgrupo")
    private String idSubgrupo;
    @Column(name = "documento")
    private String documento;
    @Column(name = "ejecuta")
    private String ejecuta;
    @Column(name = "revisa")
    private String revisa;
    @Column(name = "aprueba")
    private String aprueba;
    @Lob
    @Column(name = "dependencia")
    private String dependencia;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Calificacion() {
    }

    public Calificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Double frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getIdTipoCalificacion() {
        return idTipoCalificacion;
    }

    public void setIdTipoCalificacion(Integer idTipoCalificacion) {
        this.idTipoCalificacion = idTipoCalificacion;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getIdSubgrupo() {
        return idSubgrupo;
    }

    public void setIdSubgrupo(String idSubgrupo) {
        this.idSubgrupo = idSubgrupo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEjecuta() {
        return ejecuta;
    }

    public void setEjecuta(String ejecuta) {
        this.ejecuta = ejecuta;
    }

    public String getRevisa() {
        return revisa;
    }

    public void setRevisa(String revisa) {
        this.revisa = revisa;
    }

    public String getAprueba() {
        return aprueba;
    }

    public void setAprueba(String aprueba) {
        this.aprueba = aprueba;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalificacion != null ? idCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.idCalificacion == null && other.idCalificacion != null) || (this.idCalificacion != null && !this.idCalificacion.equals(other.idCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Calificacion[ idCalificacion=" + idCalificacion + " ]";
    }
    
}
