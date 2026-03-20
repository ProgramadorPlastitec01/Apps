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
 * @author prog.sistemas2
 */
@Entity
@Table(name = "dimensional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dimensional.findAll", query = "SELECT d FROM Dimensional d"),
    @NamedQuery(name = "Dimensional.findByIdDimensional", query = "SELECT d FROM Dimensional d WHERE d.idDimensional = :idDimensional"),
    @NamedQuery(name = "Dimensional.findByFecha", query = "SELECT d FROM Dimensional d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Dimensional.findByEstacion", query = "SELECT d FROM Dimensional d WHERE d.estacion = :estacion"),
    @NamedQuery(name = "Dimensional.findByCabidad", query = "SELECT d FROM Dimensional d WHERE d.cabidad = :cabidad"),
    @NamedQuery(name = "Dimensional.findByAltura", query = "SELECT d FROM Dimensional d WHERE d.altura = :altura"),
    @NamedQuery(name = "Dimensional.findByY2", query = "SELECT d FROM Dimensional d WHERE d.y2 = :y2"),
    @NamedQuery(name = "Dimensional.findByX1", query = "SELECT d FROM Dimensional d WHERE d.x1 = :x1"),
    @NamedQuery(name = "Dimensional.findByY1", query = "SELECT d FROM Dimensional d WHERE d.y1 = :y1"),
    @NamedQuery(name = "Dimensional.findByX2", query = "SELECT d FROM Dimensional d WHERE d.x2 = :x2"),
    @NamedQuery(name = "Dimensional.findByX3", query = "SELECT d FROM Dimensional d WHERE d.x3 = :x3")})
public class Dimensional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dimensional")
    private Integer idDimensional;
    @Basic(optional = false)
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
    @Lob
    @Column(name = "justificacion")
    private String justificacion;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_dimensional_c", referencedColumnName = "id_dimensional_c")
    @ManyToOne
    private ControlDmsC idDimensionalC;

    public Dimensional() {
    }

    public Dimensional(Integer idDimensional) {
        this.idDimensional = idDimensional;
    }

    public Dimensional(Integer idDimensional, Date fecha) {
        this.idDimensional = idDimensional;
        this.fecha = fecha;
    }

    public Integer getIdDimensional() {
        return idDimensional;
    }

    public void setIdDimensional(Integer idDimensional) {
        this.idDimensional = idDimensional;
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

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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
        hash += (idDimensional != null ? idDimensional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dimensional)) {
            return false;
        }
        Dimensional other = (Dimensional) object;
        if ((this.idDimensional == null && other.idDimensional != null) || (this.idDimensional != null && !this.idDimensional.equals(other.idDimensional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Dimensional[ idDimensional=" + idDimensional + " ]";
    }
    
}
