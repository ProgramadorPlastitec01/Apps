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
 * @author JoseForero
 */
@Entity
@Table(name = "resumen")
@NamedQueries({
    @NamedQuery(name = "Resumen.findAll", query = "SELECT r FROM Resumen r"),
    @NamedQuery(name = "Resumen.findByIdResumen", query = "SELECT r FROM Resumen r WHERE r.idResumen = :idResumen"),
    @NamedQuery(name = "Resumen.findByNumeroCertificado", query = "SELECT r FROM Resumen r WHERE r.numeroCertificado = :numeroCertificado"),
    @NamedQuery(name = "Resumen.findByOrden", query = "SELECT r FROM Resumen r WHERE r.orden = :orden"),
    @NamedQuery(name = "Resumen.findByProducto", query = "SELECT r FROM Resumen r WHERE r.producto = :producto"),
    @NamedQuery(name = "Resumen.findByLote", query = "SELECT r FROM Resumen r WHERE r.lote = :lote"),
    @NamedQuery(name = "Resumen.findByCantidadRegistros", query = "SELECT r FROM Resumen r WHERE r.cantidadRegistros = :cantidadRegistros"),
    @NamedQuery(name = "Resumen.findByFechaInicio", query = "SELECT r FROM Resumen r WHERE r.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Resumen.findByFechaFin", query = "SELECT r FROM Resumen r WHERE r.fechaFin = :fechaFin"),
    @NamedQuery(name = "Resumen.findByEstado", query = "SELECT r FROM Resumen r WHERE r.estado = :estado"),
    @NamedQuery(name = "Resumen.findByUsuarioRegistro", query = "SELECT r FROM Resumen r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Resumen.findByFechaRegistro", query = "SELECT r FROM Resumen r WHERE r.fechaRegistro = :fechaRegistro")})
public class Resumen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resumen")
    private Integer idResumen;
    @Column(name = "numero_certificado")
    private String numeroCertificado;
    @Column(name = "orden")
    private String orden;
    @Column(name = "producto")
    private String producto;
    @Column(name = "lote")
    private String lote;
    @Column(name = "cantidad_registros")
    private String cantidadRegistros;
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Resumen() {
    }

    public Resumen(Integer idResumen) {
        this.idResumen = idResumen;
    }

    public Integer getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(Integer idResumen) {
        this.idResumen = idResumen;
    }

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResumen != null ? idResumen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resumen)) {
            return false;
        }
        Resumen other = (Resumen) object;
        if ((this.idResumen == null && other.idResumen != null) || (this.idResumen != null && !this.idResumen.equals(other.idResumen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Resumen[idResumen=" + idResumen + "]";
    }

}
