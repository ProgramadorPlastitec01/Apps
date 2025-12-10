/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
@Table(name = "retiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retiro.findAll", query = "SELECT r FROM Retiro r"),
    @NamedQuery(name = "Retiro.findByIdRetiro", query = "SELECT r FROM Retiro r WHERE r.idRetiro = :idRetiro"),
    @NamedQuery(name = "Retiro.findByDocumento", query = "SELECT r FROM Retiro r WHERE r.documento = :documento"),
    @NamedQuery(name = "Retiro.findByFecha", query = "SELECT r FROM Retiro r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Retiro.findByIdCargo", query = "SELECT r FROM Retiro r WHERE r.idCargo = :idCargo"),
    @NamedQuery(name = "Retiro.findByTipo", query = "SELECT r FROM Retiro r WHERE r.tipo = :tipo"),
    @NamedQuery(name = "Retiro.findByEstado", query = "SELECT r FROM Retiro r WHERE r.estado = :estado"),
    @NamedQuery(name = "Retiro.findByVigencia", query = "SELECT r FROM Retiro r WHERE r.vigencia = :vigencia"),
    @NamedQuery(name = "Retiro.findByFechaRegistro", query = "SELECT r FROM Retiro r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Retiro.findByUsuarioRegistro", query = "SELECT r FROM Retiro r WHERE r.usuarioRegistro = :usuarioRegistro")})
public class Retiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_retiro")
    private Integer idRetiro;
    @Column(name = "documento")
    private Integer documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "tipo")
    private String tipo;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "vigencia")
    private Integer vigencia;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Retiro() {
    }

    public Retiro(Integer idRetiro) {
        this.idRetiro = idRetiro;
    }

    public Integer getIdRetiro() {
        return idRetiro;
    }

    public void setIdRetiro(Integer idRetiro) {
        this.idRetiro = idRetiro;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
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
        hash += (idRetiro != null ? idRetiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retiro)) {
            return false;
        }
        Retiro other = (Retiro) object;
        if ((this.idRetiro == null && other.idRetiro != null) || (this.idRetiro != null && !this.idRetiro.equals(other.idRetiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Retiro[ idRetiro=" + idRetiro + " ]";
    }
    
}
