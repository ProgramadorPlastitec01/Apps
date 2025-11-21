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
 * @author asistemas2
 */
@Entity
@Table(name = "registro_entrada_material")
@NamedQueries({
    @NamedQuery(name = "RegistroEntradaMaterial.findAll", query = "SELECT r FROM RegistroEntradaMaterial r"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByIdRegistrosEntradaMaterial", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.idRegistrosEntradaMaterial = :idRegistrosEntradaMaterial"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByProductoProceso", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.productoProceso = :productoProceso"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByLoteProceso", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.loteProceso = :loteProceso"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByUsuarioProceso", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.usuarioProceso = :usuarioProceso"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByFechaProceso", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByProductoEntrante", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.productoEntrante = :productoEntrante"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByLoteEntrante", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.loteEntrante = :loteEntrante"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByCantidad", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByUnidad", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.unidad = :unidad"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByUsuarioEntrante", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.usuarioEntrante = :usuarioEntrante"),
    @NamedQuery(name = "RegistroEntradaMaterial.findByFechaEntrante", query = "SELECT r FROM RegistroEntradaMaterial r WHERE r.fechaEntrante = :fechaEntrante")})
public class RegistroEntradaMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registros_entrada_material")
    private Integer idRegistrosEntradaMaterial;
    @Column(name = "producto_proceso")
    private String productoProceso;
    @Column(name = "lote_proceso")
    private String loteProceso;
    @Column(name = "usuario_proceso")
    private String usuarioProceso;
    @Column(name = "fecha_proceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "producto_entrante")
    private String productoEntrante;
    @Column(name = "lote_entrante")
    private String loteEntrante;
    @Column(name = "cantidad")
    private String cantidad;
    @Column(name = "unidad")
    private String unidad;
    @Column(name = "usuario_entrante")
    private String usuarioEntrante;
    @Column(name = "fecha_entrante")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrante;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro registro;

    public RegistroEntradaMaterial() {
    }

    public RegistroEntradaMaterial(Integer idRegistrosEntradaMaterial) {
        this.idRegistrosEntradaMaterial = idRegistrosEntradaMaterial;
    }

    public Integer getIdRegistrosEntradaMaterial() {
        return idRegistrosEntradaMaterial;
    }

    public void setIdRegistrosEntradaMaterial(Integer idRegistrosEntradaMaterial) {
        this.idRegistrosEntradaMaterial = idRegistrosEntradaMaterial;
    }

    public String getProductoProceso() {
        return productoProceso;
    }

    public void setProductoProceso(String productoProceso) {
        this.productoProceso = productoProceso;
    }

    public String getLoteProceso() {
        return loteProceso;
    }

    public void setLoteProceso(String loteProceso) {
        this.loteProceso = loteProceso;
    }

    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getProductoEntrante() {
        return productoEntrante;
    }

    public void setProductoEntrante(String productoEntrante) {
        this.productoEntrante = productoEntrante;
    }

    public String getLoteEntrante() {
        return loteEntrante;
    }

    public void setLoteEntrante(String loteEntrante) {
        this.loteEntrante = loteEntrante;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUsuarioEntrante() {
        return usuarioEntrante;
    }

    public void setUsuarioEntrante(String usuarioEntrante) {
        this.usuarioEntrante = usuarioEntrante;
    }

    public Date getFechaEntrante() {
        return fechaEntrante;
    }

    public void setFechaEntrante(Date fechaEntrante) {
        this.fechaEntrante = fechaEntrante;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistrosEntradaMaterial != null ? idRegistrosEntradaMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEntradaMaterial)) {
            return false;
        }
        RegistroEntradaMaterial other = (RegistroEntradaMaterial) object;
        if ((this.idRegistrosEntradaMaterial == null && other.idRegistrosEntradaMaterial != null) || (this.idRegistrosEntradaMaterial != null && !this.idRegistrosEntradaMaterial.equals(other.idRegistrosEntradaMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroEntradaMaterial[idRegistrosEntradaMaterial=" + idRegistrosEntradaMaterial + "]";
    }

}
