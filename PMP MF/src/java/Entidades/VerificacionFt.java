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
@Table(name = "verificacion_ft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VerificacionFt.findAll", query = "SELECT v FROM VerificacionFt v"),
    @NamedQuery(name = "VerificacionFt.findByIdVerificacionFt", query = "SELECT v FROM VerificacionFt v WHERE v.idVerificacionFt = :idVerificacionFt"),
    @NamedQuery(name = "VerificacionFt.findByIdEquipo", query = "SELECT v FROM VerificacionFt v WHERE v.idEquipo = :idEquipo"),
    @NamedQuery(name = "VerificacionFt.findByUsuarioRegistro", query = "SELECT v FROM VerificacionFt v WHERE v.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "VerificacionFt.findByFechaRegistro", query = "SELECT v FROM VerificacionFt v WHERE v.fechaRegistro = :fechaRegistro")})
public class VerificacionFt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_verificacion_ft")
    private Integer idVerificacionFt;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public VerificacionFt() {
    }

    public VerificacionFt(Integer idVerificacionFt) {
        this.idVerificacionFt = idVerificacionFt;
    }

    public Integer getIdVerificacionFt() {
        return idVerificacionFt;
    }

    public void setIdVerificacionFt(Integer idVerificacionFt) {
        this.idVerificacionFt = idVerificacionFt;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVerificacionFt != null ? idVerificacionFt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificacionFt)) {
            return false;
        }
        VerificacionFt other = (VerificacionFt) object;
        if ((this.idVerificacionFt == null && other.idVerificacionFt != null) || (this.idVerificacionFt != null && !this.idVerificacionFt.equals(other.idVerificacionFt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VerificacionFt[ idVerificacionFt=" + idVerificacionFt + " ]";
    }
    
}
