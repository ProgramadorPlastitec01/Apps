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
@Table(name = "informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Informe.findAll", query = "SELECT i FROM Informe i"),
    @NamedQuery(name = "Informe.findByIdInforme", query = "SELECT i FROM Informe i WHERE i.idInforme = :idInforme"),
    @NamedQuery(name = "Informe.findByInforme", query = "SELECT i FROM Informe i WHERE i.informe = :informe"),
    @NamedQuery(name = "Informe.findByIdTipoClasificacion", query = "SELECT i FROM Informe i WHERE i.idTipoClasificacion = :idTipoClasificacion"),
    @NamedQuery(name = "Informe.findByFechaHoraEjecucion", query = "SELECT i FROM Informe i WHERE i.fechaHoraEjecucion = :fechaHoraEjecucion"),
    @NamedQuery(name = "Informe.findByFechaHoraRevisa", query = "SELECT i FROM Informe i WHERE i.fechaHoraRevisa = :fechaHoraRevisa"),
    @NamedQuery(name = "Informe.findByFechaHoraAprueba", query = "SELECT i FROM Informe i WHERE i.fechaHoraAprueba = :fechaHoraAprueba"),
    @NamedQuery(name = "Informe.findByFechaRegistro", query = "SELECT i FROM Informe i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Informe.findByUsuarioRegistro", query = "SELECT i FROM Informe i WHERE i.usuarioRegistro = :usuarioRegistro")})
public class Informe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_informe")
    private Integer idInforme;
    @Column(name = "informe")
    private String informe;
    @Column(name = "id_tipo_clasificacion")
    private Integer idTipoClasificacion;
    @Lob
    @Column(name = "conclusion")
    private String conclusion;
    @Lob
    @Column(name = "desviacion")
    private String desviacion;
    @Lob
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_hora_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEjecucion;
    @Column(name = "fecha_hora_revisa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRevisa;
    @Column(name = "fecha_hora_aprueba")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraAprueba;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Informe() {
    }

    public Informe(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public Integer getIdTipoClasificacion() {
        return idTipoClasificacion;
    }

    public void setIdTipoClasificacion(Integer idTipoClasificacion) {
        this.idTipoClasificacion = idTipoClasificacion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(String desviacion) {
        this.desviacion = desviacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaHoraEjecucion() {
        return fechaHoraEjecucion;
    }

    public void setFechaHoraEjecucion(Date fechaHoraEjecucion) {
        this.fechaHoraEjecucion = fechaHoraEjecucion;
    }

    public Date getFechaHoraRevisa() {
        return fechaHoraRevisa;
    }

    public void setFechaHoraRevisa(Date fechaHoraRevisa) {
        this.fechaHoraRevisa = fechaHoraRevisa;
    }

    public Date getFechaHoraAprueba() {
        return fechaHoraAprueba;
    }

    public void setFechaHoraAprueba(Date fechaHoraAprueba) {
        this.fechaHoraAprueba = fechaHoraAprueba;
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
        hash += (idInforme != null ? idInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Informe)) {
            return false;
        }
        Informe other = (Informe) object;
        if ((this.idInforme == null && other.idInforme != null) || (this.idInforme != null && !this.idInforme.equals(other.idInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Informe[ idInforme=" + idInforme + " ]";
    }
    
}
