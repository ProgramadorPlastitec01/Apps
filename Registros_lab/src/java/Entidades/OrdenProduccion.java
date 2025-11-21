/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
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

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "orden_produccion")
@NamedQueries({
    @NamedQuery(name = "OrdenProduccion.findAll", query = "SELECT o FROM OrdenProduccion o"),
    @NamedQuery(name = "OrdenProduccion.findByIdOrdenProduccion", query = "SELECT o FROM OrdenProduccion o WHERE o.idOrdenProduccion = :idOrdenProduccion"),
    @NamedQuery(name = "OrdenProduccion.findByNumero", query = "SELECT o FROM OrdenProduccion o WHERE o.numero = :numero"),
    @NamedQuery(name = "OrdenProduccion.findByCliente", query = "SELECT o FROM OrdenProduccion o WHERE o.cliente = :cliente"),
    @NamedQuery(name = "OrdenProduccion.findByEstado", query = "SELECT o FROM OrdenProduccion o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenProduccion.findByUsuarioRegistro", query = "SELECT o FROM OrdenProduccion o WHERE o.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "OrdenProduccion.findByFechaRegistro", query = "SELECT o FROM OrdenProduccion o WHERE o.fechaRegistro = :fechaRegistro")})
public class OrdenProduccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_produccion")
    private Integer idOrdenProduccion;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "cliente")
    private String cliente;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "ordenProduccion")
    private Collection<Producto> productoCollection;

    public OrdenProduccion() {
    }

    public OrdenProduccion(Integer idOrdenProduccion) {
        this.idOrdenProduccion = idOrdenProduccion;
    }

    public Integer getIdOrdenProduccion() {
        return idOrdenProduccion;
    }

    public void setIdOrdenProduccion(Integer idOrdenProduccion) {
        this.idOrdenProduccion = idOrdenProduccion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenProduccion != null ? idOrdenProduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenProduccion)) {
            return false;
        }
        OrdenProduccion other = (OrdenProduccion) object;
        if ((this.idOrdenProduccion == null && other.idOrdenProduccion != null) || (this.idOrdenProduccion != null && !this.idOrdenProduccion.equals(other.idOrdenProduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OrdenProduccion[idOrdenProduccion=" + idOrdenProduccion + "]";
    }

}
