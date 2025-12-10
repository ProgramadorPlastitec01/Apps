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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "mc_sst_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McSstCalificacion.findAll", query = "SELECT m FROM McSstCalificacion m"),
    @NamedQuery(name = "McSstCalificacion.findByIdMcSstCalificacion", query = "SELECT m FROM McSstCalificacion m WHERE m.idMcSstCalificacion = :idMcSstCalificacion"),
    @NamedQuery(name = "McSstCalificacion.findByIdMcSstRendicion", query = "SELECT m FROM McSstCalificacion m WHERE m.idMcSstRendicion = :idMcSstRendicion"),
    @NamedQuery(name = "McSstCalificacion.findByCalificacion", query = "SELECT m FROM McSstCalificacion m WHERE m.calificacion = :calificacion"),
    @NamedQuery(name = "McSstCalificacion.findByEstado", query = "SELECT m FROM McSstCalificacion m WHERE m.estado = :estado"),
    @NamedQuery(name = "McSstCalificacion.findByFechaRegistro", query = "SELECT m FROM McSstCalificacion m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "McSstCalificacion.findByUsuarioRegistro", query = "SELECT m FROM McSstCalificacion m WHERE m.usuarioRegistro = :usuarioRegistro")})
public class McSstCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_sst_calificacion")
    private Integer idMcSstCalificacion;
    @Column(name = "id_mc_sst_rendicion")
    private Integer idMcSstRendicion;
    @Lob
    @Column(name = "detalle_calificacion")
    private String detalleCalificacion;
    @Lob
    @Column(name = "grupos_calificacion")
    private String gruposCalificacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "calificacion")
    private Double calificacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @JoinColumn(name = "id_mc_calificacion", referencedColumnName = "id_mc_calificacion")
    @ManyToOne
    private McCalificacion idMcCalificacion;

    public McSstCalificacion() {
    }

    public McSstCalificacion(Integer idMcSstCalificacion) {
        this.idMcSstCalificacion = idMcSstCalificacion;
    }

    public Integer getIdMcSstCalificacion() {
        return idMcSstCalificacion;
    }

    public void setIdMcSstCalificacion(Integer idMcSstCalificacion) {
        this.idMcSstCalificacion = idMcSstCalificacion;
    }

    public Integer getIdMcSstRendicion() {
        return idMcSstRendicion;
    }

    public void setIdMcSstRendicion(Integer idMcSstRendicion) {
        this.idMcSstRendicion = idMcSstRendicion;
    }

    public String getDetalleCalificacion() {
        return detalleCalificacion;
    }

    public void setDetalleCalificacion(String detalleCalificacion) {
        this.detalleCalificacion = detalleCalificacion;
    }

    public String getGruposCalificacion() {
        return gruposCalificacion;
    }

    public void setGruposCalificacion(String gruposCalificacion) {
        this.gruposCalificacion = gruposCalificacion;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
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

    public McCalificacion getIdMcCalificacion() {
        return idMcCalificacion;
    }

    public void setIdMcCalificacion(McCalificacion idMcCalificacion) {
        this.idMcCalificacion = idMcCalificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcSstCalificacion != null ? idMcSstCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McSstCalificacion)) {
            return false;
        }
        McSstCalificacion other = (McSstCalificacion) object;
        if ((this.idMcSstCalificacion == null && other.idMcSstCalificacion != null) || (this.idMcSstCalificacion != null && !this.idMcSstCalificacion.equals(other.idMcSstCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McSstCalificacion[ idMcSstCalificacion=" + idMcSstCalificacion + " ]";
    }
    
}
