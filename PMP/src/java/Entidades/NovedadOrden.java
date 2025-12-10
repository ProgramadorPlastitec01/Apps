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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "novedad_orden")
@NamedQueries({
    @NamedQuery(name = "NovedadOrden.findAll", query = "SELECT n FROM NovedadOrden n"),
    @NamedQuery(name = "NovedadOrden.findByIdNovedadOrden", query = "SELECT n FROM NovedadOrden n WHERE n.idNovedadOrden = :idNovedadOrden"),
    @NamedQuery(name = "NovedadOrden.findByAsunto", query = "SELECT n FROM NovedadOrden n WHERE n.asunto = :asunto"),
    @NamedQuery(name = "NovedadOrden.findByUsuarioRegistro", query = "SELECT n FROM NovedadOrden n WHERE n.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "NovedadOrden.findByFechaRegistro", query = "SELECT n FROM NovedadOrden n WHERE n.fechaRegistro = :fechaRegistro")})
public class NovedadOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_orden")
    private Integer idNovedadOrden;
    @Column(name = "asunto")
    private String asunto;
    @Lob
    @Column(name = "descripccion")
    private String descripccion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne
    private OrdenTrabajo ordenTrabajo;

    public NovedadOrden() {
    }

    public NovedadOrden(Integer idNovedadOrden) {
        this.idNovedadOrden = idNovedadOrden;
    }

    public Integer getIdNovedadOrden() {
        return idNovedadOrden;
    }

    public void setIdNovedadOrden(Integer idNovedadOrden) {
        this.idNovedadOrden = idNovedadOrden;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
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

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadOrden != null ? idNovedadOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadOrden)) {
            return false;
        }
        NovedadOrden other = (NovedadOrden) object;
        if ((this.idNovedadOrden == null && other.idNovedadOrden != null) || (this.idNovedadOrden != null && !this.idNovedadOrden.equals(other.idNovedadOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.NovedadOrden[idNovedadOrden=" + idNovedadOrden + "]";
    }

}
