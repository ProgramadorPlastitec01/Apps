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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "verificacion_metraje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VerificacionMetraje.findAll", query = "SELECT v FROM VerificacionMetraje v")
    , @NamedQuery(name = "VerificacionMetraje.findByIdVerificacion", query = "SELECT v FROM VerificacionMetraje v WHERE v.idVerificacion = :idVerificacion")
    , @NamedQuery(name = "VerificacionMetraje.findByUsuarioRegistro", query = "SELECT v FROM VerificacionMetraje v WHERE v.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "VerificacionMetraje.findByFechaRegistros", query = "SELECT v FROM VerificacionMetraje v WHERE v.fechaRegistros = :fechaRegistros")})
public class VerificacionMetraje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_verificacion")
    private Integer idVerificacion;
    @Lob
    @Column(name = "items")
    private String items;
    @Lob
    @Column(name = "turnos")
    private String turnos;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registros")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistros;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro idRegistro;

    public VerificacionMetraje() {
    }

    public VerificacionMetraje(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public Integer getIdVerificacion() {
        return idVerificacion;
    }

    public void setIdVerificacion(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTurnos() {
        return turnos;
    }

    public void setTurnos(String turnos) {
        this.turnos = turnos;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistros() {
        return fechaRegistros;
    }

    public void setFechaRegistros(Date fechaRegistros) {
        this.fechaRegistros = fechaRegistros;
    }

    public Registro getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Registro idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVerificacion != null ? idVerificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificacionMetraje)) {
            return false;
        }
        VerificacionMetraje other = (VerificacionMetraje) object;
        if ((this.idVerificacion == null && other.idVerificacion != null) || (this.idVerificacion != null && !this.idVerificacion.equals(other.idVerificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VerificacionMetraje[ idVerificacion=" + idVerificacion + " ]";
    }
    
}
