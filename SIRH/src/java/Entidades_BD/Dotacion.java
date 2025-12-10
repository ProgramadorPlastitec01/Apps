/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "dotacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dotacion.findAll", query = "SELECT d FROM Dotacion d"),
    @NamedQuery(name = "Dotacion.findByIdDotacion", query = "SELECT d FROM Dotacion d WHERE d.idDotacion = :idDotacion"),
    @NamedQuery(name = "Dotacion.findByDocumento", query = "SELECT d FROM Dotacion d WHERE d.documento = :documento"),
    @NamedQuery(name = "Dotacion.findByFecha", query = "SELECT d FROM Dotacion d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Dotacion.findByEstado", query = "SELECT d FROM Dotacion d WHERE d.estado = :estado"),
    @NamedQuery(name = "Dotacion.findByFechaRegistro", query = "SELECT d FROM Dotacion d WHERE d.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Dotacion.findByUsuarioRegistro", query = "SELECT d FROM Dotacion d WHERE d.usuarioRegistro = :usuarioRegistro")})
public class Dotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dotacion")
    private Integer idDotacion;
    @Column(name = "documento")
    private BigInteger documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(name = "entrega")
    private String entrega;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Dotacion() {
    }

    public Dotacion(Integer idDotacion) {
        this.idDotacion = idDotacion;
    }

    public Integer getIdDotacion() {
        return idDotacion;
    }

    public void setIdDotacion(Integer idDotacion) {
        this.idDotacion = idDotacion;
    }

    public BigInteger getDocumento() {
        return documento;
    }

    public void setDocumento(BigInteger documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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
        hash += (idDotacion != null ? idDotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dotacion)) {
            return false;
        }
        Dotacion other = (Dotacion) object;
        if ((this.idDotacion == null && other.idDotacion != null) || (this.idDotacion != null && !this.idDotacion.equals(other.idDotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Dotacion[ idDotacion=" + idDotacion + " ]";
    }
    
}
