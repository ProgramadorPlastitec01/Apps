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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "parametro_orden")
@NamedQueries({
    @NamedQuery(name = "ParametroOrden.findAll", query = "SELECT p FROM ParametroOrden p"),
    @NamedQuery(name = "ParametroOrden.findByIdParametroOrden", query = "SELECT p FROM ParametroOrden p WHERE p.idParametroOrden = :idParametroOrden"),
    @NamedQuery(name = "ParametroOrden.findByToma1", query = "SELECT p FROM ParametroOrden p WHERE p.toma1 = :toma1"),
    @NamedQuery(name = "ParametroOrden.findByToma2", query = "SELECT p FROM ParametroOrden p WHERE p.toma2 = :toma2"),
    @NamedQuery(name = "ParametroOrden.findByToma3", query = "SELECT p FROM ParametroOrden p WHERE p.toma3 = :toma3"),
    @NamedQuery(name = "ParametroOrden.findByEstado", query = "SELECT p FROM ParametroOrden p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroOrden.findByUsuarioRegistro", query = "SELECT p FROM ParametroOrden p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ParametroOrden.findByFechaRegistro", query = "SELECT p FROM ParametroOrden p WHERE p.fechaRegistro = :fechaRegistro")})
public class ParametroOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro_orden")
    private Integer idParametroOrden;
    @Column(name = "toma1")
    private String toma1;
    @Column(name = "toma2")
    private String toma2;
    @Column(name = "toma3")
    private String toma3;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_parametro", referencedColumnName = "id_parametro")
    @ManyToOne
    private Parametro parametro;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne
    private OrdenTrabajo ordenTrabajo;

    public ParametroOrden() {
    }

    public ParametroOrden(Integer idParametroOrden) {
        this.idParametroOrden = idParametroOrden;
    }

    public Integer getIdParametroOrden() {
        return idParametroOrden;
    }

    public void setIdParametroOrden(Integer idParametroOrden) {
        this.idParametroOrden = idParametroOrden;
    }

    public String getToma1() {
        return toma1;
    }

    public void setToma1(String toma1) {
        this.toma1 = toma1;
    }

    public String getToma2() {
        return toma2;
    }

    public void setToma2(String toma2) {
        this.toma2 = toma2;
    }

    public String getToma3() {
        return toma3;
    }

    public void setToma3(String toma3) {
        this.toma3 = toma3;
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

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametroOrden != null ? idParametroOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroOrden)) {
            return false;
        }
        ParametroOrden other = (ParametroOrden) object;
        if ((this.idParametroOrden == null && other.idParametroOrden != null) || (this.idParametroOrden != null && !this.idParametroOrden.equals(other.idParametroOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametroOrden[idParametroOrden=" + idParametroOrden + "]";
    }

}
