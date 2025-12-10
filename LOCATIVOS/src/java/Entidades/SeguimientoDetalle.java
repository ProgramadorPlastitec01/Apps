/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Aprendiz.Sena1
 */
@Entity
@Table(name = "seguimiento_detalle")
@NamedQueries({
    @NamedQuery(name = "SeguimientoDetalle.findAll", query = "SELECT s FROM SeguimientoDetalle s"),
    @NamedQuery(name = "SeguimientoDetalle.findByIdSeguimientoDetalle", query = "SELECT s FROM SeguimientoDetalle s WHERE s.idSeguimientoDetalle = :idSeguimientoDetalle"),
    @NamedQuery(name = "SeguimientoDetalle.findByIdSeguimiento", query = "SELECT s FROM SeguimientoDetalle s WHERE s.idSeguimiento = :idSeguimiento"),
    @NamedQuery(name = "SeguimientoDetalle.findByFechaInico", query = "SELECT s FROM SeguimientoDetalle s WHERE s.fechaInico = :fechaInico"),
    @NamedQuery(name = "SeguimientoDetalle.findByFechaFin", query = "SELECT s FROM SeguimientoDetalle s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SeguimientoDetalle.findByEstado", query = "SELECT s FROM SeguimientoDetalle s WHERE s.estado = :estado"),
    @NamedQuery(name = "SeguimientoDetalle.findByFechaRegistro", query = "SELECT s FROM SeguimientoDetalle s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SeguimientoDetalle.findByUsuarioRegistro", query = "SELECT s FROM SeguimientoDetalle s WHERE s.usuarioRegistro = :usuarioRegistro")})
public class SeguimientoDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguimiento_detalle")
    private Integer idSeguimientoDetalle;
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;
    @Column(name = "fecha_inico")
    @Temporal(TemporalType.DATE)
    private Date fechaInico;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    private Integer fechaRegistro;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;

    public SeguimientoDetalle() {
    }

    public SeguimientoDetalle(Integer idSeguimientoDetalle) {
        this.idSeguimientoDetalle = idSeguimientoDetalle;
    }

    public Integer getIdSeguimientoDetalle() {
        return idSeguimientoDetalle;
    }

    public void setIdSeguimientoDetalle(Integer idSeguimientoDetalle) {
        this.idSeguimientoDetalle = idSeguimientoDetalle;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Date getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(Date fechaInico) {
        this.fechaInico = fechaInico;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Integer fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Integer usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimientoDetalle != null ? idSeguimientoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeguimientoDetalle)) {
            return false;
        }
        SeguimientoDetalle other = (SeguimientoDetalle) object;
        if ((this.idSeguimientoDetalle == null && other.idSeguimientoDetalle != null) || (this.idSeguimientoDetalle != null && !this.idSeguimientoDetalle.equals(other.idSeguimientoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SeguimientoDetalle[idSeguimientoDetalle=" + idSeguimientoDetalle + "]";
    }

}
