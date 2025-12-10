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
@Table(name = "epp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Epp.findAll", query = "SELECT e FROM Epp e"),
    @NamedQuery(name = "Epp.findByIdEpp", query = "SELECT e FROM Epp e WHERE e.idEpp = :idEpp"),
    @NamedQuery(name = "Epp.findByDocumento", query = "SELECT e FROM Epp e WHERE e.documento = :documento"),
    @NamedQuery(name = "Epp.findByFecha", query = "SELECT e FROM Epp e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Epp.findByEstado", query = "SELECT e FROM Epp e WHERE e.estado = :estado"),
    @NamedQuery(name = "Epp.findByFechaRegistro", query = "SELECT e FROM Epp e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Epp.findByUsuarioRegistro", query = "SELECT e FROM Epp e WHERE e.usuarioRegistro = :usuarioRegistro")})
public class Epp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_epp")
    private Integer idEpp;
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

    public Epp() {
    }

    public Epp(Integer idEpp) {
        this.idEpp = idEpp;
    }

    public Integer getIdEpp() {
        return idEpp;
    }

    public void setIdEpp(Integer idEpp) {
        this.idEpp = idEpp;
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
        hash += (idEpp != null ? idEpp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Epp)) {
            return false;
        }
        Epp other = (Epp) object;
        if ((this.idEpp == null && other.idEpp != null) || (this.idEpp != null && !this.idEpp.equals(other.idEpp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Epp[ idEpp=" + idEpp + " ]";
    }
    
}
