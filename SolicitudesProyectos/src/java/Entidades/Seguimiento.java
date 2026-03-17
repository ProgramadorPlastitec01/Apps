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
@Table(name = "seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguimiento.findAll", query = "SELECT s FROM Seguimiento s")
    , @NamedQuery(name = "Seguimiento.findByIdSeguimientos", query = "SELECT s FROM Seguimiento s WHERE s.idSeguimientos = :idSeguimientos")
    , @NamedQuery(name = "Seguimiento.findByFecha", query = "SELECT s FROM Seguimiento s WHERE s.fecha = :fecha")
    , @NamedQuery(name = "Seguimiento.findByEjecutor", query = "SELECT s FROM Seguimiento s WHERE s.ejecutor = :ejecutor")})
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSeguimientos")
    private Integer idSeguimientos;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Ejecutor")
    private String ejecutor;
    @Basic(optional = false)
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinColumn(name = "idSolicitud", referencedColumnName = "idSolicitud")
    @ManyToOne(optional = false)
    private Solicitud idSolicitud;

    public Seguimiento() {
    }

    public Seguimiento(Integer idSeguimientos) {
        this.idSeguimientos = idSeguimientos;
    }

    public Seguimiento(Integer idSeguimientos, Date fecha, String ejecutor, String descripcion) {
        this.idSeguimientos = idSeguimientos;
        this.fecha = fecha;
        this.ejecutor = ejecutor;
        this.descripcion = descripcion;
    }

    public Integer getIdSeguimientos() {
        return idSeguimientos;
    }

    public void setIdSeguimientos(Integer idSeguimientos) {
        this.idSeguimientos = idSeguimientos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(String ejecutor) {
        this.ejecutor = ejecutor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimientos != null ? idSeguimientos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguimiento)) {
            return false;
        }
        Seguimiento other = (Seguimiento) object;
        if ((this.idSeguimientos == null && other.idSeguimientos != null) || (this.idSeguimientos != null && !this.idSeguimientos.equals(other.idSeguimientos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Seguimiento[ idSeguimientos=" + idSeguimientos + " ]";
    }
    
}
