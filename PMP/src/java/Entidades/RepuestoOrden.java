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
 * @author asistemas2
 */
@Entity
@Table(name = "repuesto_orden")
@NamedQueries({
    @NamedQuery(name = "RepuestoOrden.findAll", query = "SELECT r FROM RepuestoOrden r"),
    @NamedQuery(name = "RepuestoOrden.findByIdRepuestoOrden", query = "SELECT r FROM RepuestoOrden r WHERE r.idRepuestoOrden = :idRepuestoOrden"),
    @NamedQuery(name = "RepuestoOrden.findByCodigo", query = "SELECT r FROM RepuestoOrden r WHERE r.codigo = :codigo"),
    @NamedQuery(name = "RepuestoOrden.findByReferencia", query = "SELECT r FROM RepuestoOrden r WHERE r.referencia = :referencia"),
    @NamedQuery(name = "RepuestoOrden.findByCumpleEspecificaciones", query = "SELECT r FROM RepuestoOrden r WHERE r.cumpleEspecificaciones = :cumpleEspecificaciones"),
    @NamedQuery(name = "RepuestoOrden.findByCantidad", query = "SELECT r FROM RepuestoOrden r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "RepuestoOrden.findByRequeridos", query = "SELECT r FROM RepuestoOrden r WHERE r.requeridos = :requeridos"),
    @NamedQuery(name = "RepuestoOrden.findByUtilizados", query = "SELECT r FROM RepuestoOrden r WHERE r.utilizados = :utilizados"),
    @NamedQuery(name = "RepuestoOrden.findByUsuarioRegistro", query = "SELECT r FROM RepuestoOrden r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RepuestoOrden.findByFechaRegistro", query = "SELECT r FROM RepuestoOrden r WHERE r.fechaRegistro = :fechaRegistro")})
public class RepuestoOrden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_repuesto_orden")
    private Integer idRepuestoOrden;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "cumple_especificaciones")
    private Short cumpleEspecificaciones;
    @Column(name = "cantidad")
    private String cantidad;
    @Column(name = "requeridos")
    private String requeridos;
    @Column(name = "utilizados")
    private String utilizados;
    @Lob
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne(optional = false)
    private OrdenTrabajo ordenTrabajo;

    public RepuestoOrden() {
    }

    public RepuestoOrden(Integer idRepuestoOrden) {
        this.idRepuestoOrden = idRepuestoOrden;
    }

    public Integer getIdRepuestoOrden() {
        return idRepuestoOrden;
    }

    public void setIdRepuestoOrden(Integer idRepuestoOrden) {
        this.idRepuestoOrden = idRepuestoOrden;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Short getCumpleEspecificaciones() {
        return cumpleEspecificaciones;
    }

    public void setCumpleEspecificaciones(Short cumpleEspecificaciones) {
        this.cumpleEspecificaciones = cumpleEspecificaciones;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getRequeridos() {
        return requeridos;
    }

    public void setRequeridos(String requeridos) {
        this.requeridos = requeridos;
    }

    public String getUtilizados() {
        return utilizados;
    }

    public void setUtilizados(String utilizados) {
        this.utilizados = utilizados;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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
        hash += (idRepuestoOrden != null ? idRepuestoOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepuestoOrden)) {
            return false;
        }
        RepuestoOrden other = (RepuestoOrden) object;
        if ((this.idRepuestoOrden == null && other.idRepuestoOrden != null) || (this.idRepuestoOrden != null && !this.idRepuestoOrden.equals(other.idRepuestoOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RepuestoOrden[idRepuestoOrden=" + idRepuestoOrden + "]";
    }

}
