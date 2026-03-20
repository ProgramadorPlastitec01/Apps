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
 * @author prog.sistemas2
 */
@Entity
@Table(name = "control_dms_d")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlDmsD.findAll", query = "SELECT c FROM ControlDmsD c"),
    @NamedQuery(name = "ControlDmsD.findByIdDimensionalD", query = "SELECT c FROM ControlDmsD c WHERE c.idDimensionalD = :idDimensionalD"),
    @NamedQuery(name = "ControlDmsD.findByFecha", query = "SELECT c FROM ControlDmsD c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ControlDmsD.findByEstacion", query = "SELECT c FROM ControlDmsD c WHERE c.estacion = :estacion"),
    @NamedQuery(name = "ControlDmsD.findByCabidad", query = "SELECT c FROM ControlDmsD c WHERE c.cabidad = :cabidad"),
    @NamedQuery(name = "ControlDmsD.findByAltura", query = "SELECT c FROM ControlDmsD c WHERE c.altura = :altura"),
    @NamedQuery(name = "ControlDmsD.findByY2", query = "SELECT c FROM ControlDmsD c WHERE c.y2 = :y2"),
    @NamedQuery(name = "ControlDmsD.findByX1", query = "SELECT c FROM ControlDmsD c WHERE c.x1 = :x1"),
    @NamedQuery(name = "ControlDmsD.findByY1", query = "SELECT c FROM ControlDmsD c WHERE c.y1 = :y1"),
    @NamedQuery(name = "ControlDmsD.findByX2", query = "SELECT c FROM ControlDmsD c WHERE c.x2 = :x2"),
    @NamedQuery(name = "ControlDmsD.findByX3", query = "SELECT c FROM ControlDmsD c WHERE c.x3 = :x3"),
    @NamedQuery(name = "ControlDmsD.findByTipo", query = "SELECT c FROM ControlDmsD c WHERE c.tipo = :tipo")})
public class ControlDmsD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dimensional_d")
    private Integer idDimensionalD;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estacion")
    private String estacion;
    @Column(name = "cabidad")
    private Integer cabidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "altura")
    private Double altura;
    @Column(name = "y2")
    private Double y2;
    @Column(name = "x1")
    private Double x1;
    @Column(name = "y1")
    private Double y1;
    @Column(name = "x2")
    private Double x2;
    @Column(name = "x3")
    private Double x3;
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_dimensional_c", referencedColumnName = "id_dimensional_c")
    @ManyToOne
    private ControlDmsC idDimensionalC;

    public ControlDmsD() {
    }

    public ControlDmsD(Integer idDimensionalD) {
        this.idDimensionalD = idDimensionalD;
    }

    public Integer getIdDimensionalD() {
        return idDimensionalD;
    }

    public void setIdDimensionalD(Integer idDimensionalD) {
        this.idDimensionalD = idDimensionalD;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public Integer getCabidad() {
        return cabidad;
    }

    public void setCabidad(Integer cabidad) {
        this.cabidad = cabidad;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getX3() {
        return x3;
    }

    public void setX3(Double x3) {
        this.x3 = x3;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public ControlDmsC getIdDimensionalC() {
        return idDimensionalC;
    }

    public void setIdDimensionalC(ControlDmsC idDimensionalC) {
        this.idDimensionalC = idDimensionalC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDimensionalD != null ? idDimensionalD.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlDmsD)) {
            return false;
        }
        ControlDmsD other = (ControlDmsD) object;
        if ((this.idDimensionalD == null && other.idDimensionalD != null) || (this.idDimensionalD != null && !this.idDimensionalD.equals(other.idDimensionalD))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ControlDmsD[ idDimensionalD=" + idDimensionalD + " ]";
    }
    
}
