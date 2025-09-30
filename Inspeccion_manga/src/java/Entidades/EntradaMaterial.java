/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "materia_prima")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaPrima.findAll", query = "SELECT m FROM MateriaPrima m"),
    @NamedQuery(name = "MateriaPrima.findByIdMateriaPrima", query = "SELECT m FROM MateriaPrima m WHERE m.idMateriaPrima = :idMateriaPrima"),
    @NamedQuery(name = "MateriaPrima.findByFechaRegistro", query = "SELECT m FROM MateriaPrima m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "MateriaPrima.findByMpProceso", query = "SELECT m FROM MateriaPrima m WHERE m.mpProceso = :mpProceso"),
    @NamedQuery(name = "MateriaPrima.findByLoteMpProceso", query = "SELECT m FROM MateriaPrima m WHERE m.loteMpProceso = :loteMpProceso"),
    @NamedQuery(name = "MateriaPrima.findByCantidadProceso", query = "SELECT m FROM MateriaPrima m WHERE m.cantidadProceso = :cantidadProceso"),
    @NamedQuery(name = "MateriaPrima.findByResponsableProceso", query = "SELECT m FROM MateriaPrima m WHERE m.responsableProceso = :responsableProceso"),
    @NamedQuery(name = "MateriaPrima.findByMpEntrante", query = "SELECT m FROM MateriaPrima m WHERE m.mpEntrante = :mpEntrante"),
    @NamedQuery(name = "MateriaPrima.findByLoteMpEntrante", query = "SELECT m FROM MateriaPrima m WHERE m.loteMpEntrante = :loteMpEntrante"),
    @NamedQuery(name = "MateriaPrima.findByCantidadEntrante", query = "SELECT m FROM MateriaPrima m WHERE m.cantidadEntrante = :cantidadEntrante"),
    @NamedQuery(name = "MateriaPrima.findByResponsableEntrante", query = "SELECT m FROM MateriaPrima m WHERE m.responsableEntrante = :responsableEntrante"),
    @NamedQuery(name = "MateriaPrima.findByColor", query = "SELECT m FROM MateriaPrima m WHERE m.color = :color")})
public class EntradaMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_materia_prima")
    private Integer idMateriaPrima;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "mp_proceso")
    private String mpProceso;
    @Column(name = "lote_mp_proceso")
    private String loteMpProceso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_proceso")
    private Double cantidadProceso;
    @Column(name = "responsable_proceso")
    private String responsableProceso;
    @Column(name = "mp_entrante")
    private String mpEntrante;
    @Column(name = "lote_mp_entrante")
    private String loteMpEntrante;
    @Column(name = "cantidad_entrante")
    private Double cantidadEntrante;
    @Column(name = "responsable_entrante")
    private String responsableEntrante;
    @Column(name = "color")
    private String color;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;

    public EntradaMaterial() {
    }

    public EntradaMaterial(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getMpProceso() {
        return mpProceso;
    }

    public void setMpProceso(String mpProceso) {
        this.mpProceso = mpProceso;
    }

    public String getLoteMpProceso() {
        return loteMpProceso;
    }

    public void setLoteMpProceso(String loteMpProceso) {
        this.loteMpProceso = loteMpProceso;
    }

    public Double getCantidadProceso() {
        return cantidadProceso;
    }

    public void setCantidadProceso(Double cantidadProceso) {
        this.cantidadProceso = cantidadProceso;
    }

    public String getResponsableProceso() {
        return responsableProceso;
    }

    public void setResponsableProceso(String responsableProceso) {
        this.responsableProceso = responsableProceso;
    }

    public String getMpEntrante() {
        return mpEntrante;
    }

    public void setMpEntrante(String mpEntrante) {
        this.mpEntrante = mpEntrante;
    }

    public String getLoteMpEntrante() {
        return loteMpEntrante;
    }

    public void setLoteMpEntrante(String loteMpEntrante) {
        this.loteMpEntrante = loteMpEntrante;
    }

    public Double getCantidadEntrante() {
        return cantidadEntrante;
    }

    public void setCantidadEntrante(Double cantidadEntrante) {
        this.cantidadEntrante = cantidadEntrante;
    }

    public String getResponsableEntrante() {
        return responsableEntrante;
    }

    public void setResponsableEntrante(String responsableEntrante) {
        this.responsableEntrante = responsableEntrante;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateriaPrima != null ? idMateriaPrima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradaMaterial)) {
            return false;
        }
        EntradaMaterial other = (EntradaMaterial) object;
        if ((this.idMateriaPrima == null && other.idMateriaPrima != null) || (this.idMateriaPrima != null && !this.idMateriaPrima.equals(other.idMateriaPrima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MateriaPrima[ idMateriaPrima=" + idMateriaPrima + " ]";
    }
    
}
