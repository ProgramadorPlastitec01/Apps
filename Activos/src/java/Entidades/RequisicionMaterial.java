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
 * @author APRENDIZ.SENA1
 */
@Entity
@Table(name = "requisicion_material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequisicionMaterial.findAll", query = "SELECT r FROM RequisicionMaterial r"),
    @NamedQuery(name = "RequisicionMaterial.findByIdRequisicion", query = "SELECT r FROM RequisicionMaterial r WHERE r.idRequisicion = :idRequisicion"),
    @NamedQuery(name = "RequisicionMaterial.findByFechaSolictud", query = "SELECT r FROM RequisicionMaterial r WHERE r.fechaSolictud = :fechaSolictud"),
    @NamedQuery(name = "RequisicionMaterial.findByCantidad", query = "SELECT r FROM RequisicionMaterial r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "RequisicionMaterial.findByMarca", query = "SELECT r FROM RequisicionMaterial r WHERE r.marca = :marca"),
    @NamedQuery(name = "RequisicionMaterial.findByDestino", query = "SELECT r FROM RequisicionMaterial r WHERE r.destino = :destino"),
    @NamedQuery(name = "RequisicionMaterial.findByFechaEstimada", query = "SELECT r FROM RequisicionMaterial r WHERE r.fechaEstimada = :fechaEstimada"),
    @NamedQuery(name = "RequisicionMaterial.findByPrioridad", query = "SELECT r FROM RequisicionMaterial r WHERE r.prioridad = :prioridad"),
    @NamedQuery(name = "RequisicionMaterial.findByUsuarioRegistro", query = "SELECT r FROM RequisicionMaterial r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RequisicionMaterial.findByFechaRegistro", query = "SELECT r FROM RequisicionMaterial r WHERE r.fechaRegistro = :fechaRegistro")})
public class RequisicionMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_requisicion")
    private Integer idRequisicion;
    @Column(name = "fecha_solictud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolictud;
    @Lob
    @Column(name = "elemento")
    private String elemento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "marca")
    private String marca;
    @Column(name = "destino")
    private String destino;
    @Column(name = "fecha_estimada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimada;
    @Column(name = "prioridad")
    private Integer prioridad;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "clasificacion", referencedColumnName = "id_clasificacion")
    @ManyToOne
    private Clasificacion clasificacion;
    public RequisicionMaterial() {
    }

    public RequisicionMaterial(Integer idRequisicion) {
        this.idRequisicion = idRequisicion;
    }

    public Integer getIdRequisicion() {
        return idRequisicion;
    }

    public void setIdRequisicion(Integer idRequisicion) {
        this.idRequisicion = idRequisicion;
    }

    public Date getFechaSolictud() {
        return fechaSolictud;
    }

    public void setFechaSolictud(Date fechaSolictud) {
        this.fechaSolictud = fechaSolictud;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
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

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequisicion != null ? idRequisicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequisicionMaterial)) {
            return false;
        }
        RequisicionMaterial other = (RequisicionMaterial) object;
        if ((this.idRequisicion == null && other.idRequisicion != null) || (this.idRequisicion != null && !this.idRequisicion.equals(other.idRequisicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RequisicionMaterial[ idRequisicion=" + idRequisicion + " ]";
    }
    
}
