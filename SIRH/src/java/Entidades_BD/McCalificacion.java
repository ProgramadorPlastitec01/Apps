/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "mc_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McCalificacion.findAll", query = "SELECT m FROM McCalificacion m"),
    @NamedQuery(name = "McCalificacion.findByIdMcCalificacion", query = "SELECT m FROM McCalificacion m WHERE m.idMcCalificacion = :idMcCalificacion"),
    @NamedQuery(name = "McCalificacion.findByDocumento", query = "SELECT m FROM McCalificacion m WHERE m.documento = :documento"),
    @NamedQuery(name = "McCalificacion.findByIdMcCargo", query = "SELECT m FROM McCalificacion m WHERE m.idMcCargo = :idMcCargo"),
    @NamedQuery(name = "McCalificacion.findByFecha", query = "SELECT m FROM McCalificacion m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "McCalificacion.findByCalificacion", query = "SELECT m FROM McCalificacion m WHERE m.calificacion = :calificacion"),
    @NamedQuery(name = "McCalificacion.findByEstado", query = "SELECT m FROM McCalificacion m WHERE m.estado = :estado"),
    @NamedQuery(name = "McCalificacion.findByUsuarioRegistro", query = "SELECT m FROM McCalificacion m WHERE m.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "McCalificacion.findByFechaRegistro", query = "SELECT m FROM McCalificacion m WHERE m.fechaRegistro = :fechaRegistro")})
public class McCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_calificacion")
    private Integer idMcCalificacion;
    @Column(name = "documento")
    private BigInteger documento;
    @Column(name = "id_mc_cargo")
    private Integer idMcCargo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(name = "evaluadores")
    private String evaluadores;
    @Lob
    @Column(name = "detalle_calificacion")
    private String detalleCalificacion;
    @Lob
    @Column(name = "grupos_calificacion")
    private String gruposCalificacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "calificacion")
    private Double calificacion;
    @Lob
    @Column(name = "recomendacion")
    private String recomendacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idMcCalificacion")
    private Collection<McSstCalificacion> mcSstCalificacionCollection;

    public McCalificacion() {
    }

    public McCalificacion(Integer idMcCalificacion) {
        this.idMcCalificacion = idMcCalificacion;
    }

    public Integer getIdMcCalificacion() {
        return idMcCalificacion;
    }

    public void setIdMcCalificacion(Integer idMcCalificacion) {
        this.idMcCalificacion = idMcCalificacion;
    }

    public BigInteger getDocumento() {
        return documento;
    }

    public void setDocumento(BigInteger documento) {
        this.documento = documento;
    }

    public Integer getIdMcCargo() {
        return idMcCargo;
    }

    public void setIdMcCargo(Integer idMcCargo) {
        this.idMcCargo = idMcCargo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEvaluadores() {
        return evaluadores;
    }

    public void setEvaluadores(String evaluadores) {
        this.evaluadores = evaluadores;
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

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    @XmlTransient
    public Collection<McSstCalificacion> getMcSstCalificacionCollection() {
        return mcSstCalificacionCollection;
    }

    public void setMcSstCalificacionCollection(Collection<McSstCalificacion> mcSstCalificacionCollection) {
        this.mcSstCalificacionCollection = mcSstCalificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcCalificacion != null ? idMcCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McCalificacion)) {
            return false;
        }
        McCalificacion other = (McCalificacion) object;
        if ((this.idMcCalificacion == null && other.idMcCalificacion != null) || (this.idMcCalificacion != null && !this.idMcCalificacion.equals(other.idMcCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McCalificacion[ idMcCalificacion=" + idMcCalificacion + " ]";
    }
    
}
