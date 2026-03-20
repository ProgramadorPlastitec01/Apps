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
@Table(name = "visual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visual.findAll", query = "SELECT v FROM Visual v"),
    @NamedQuery(name = "Visual.findByIdVisual", query = "SELECT v FROM Visual v WHERE v.idVisual = :idVisual"),
    @NamedQuery(name = "Visual.findByFechaRegistro", query = "SELECT v FROM Visual v WHERE v.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Visual.findByCantidadCalidad", query = "SELECT v FROM Visual v WHERE v.cantidadCalidad = :cantidadCalidad"),
    @NamedQuery(name = "Visual.findByCantidadProduccion", query = "SELECT v FROM Visual v WHERE v.cantidadProduccion = :cantidadProduccion")})
public class Visual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_visual")
    private Integer idVisual;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "cantidad_calidad")
    private String cantidadCalidad;
    @Column(name = "cantidad_produccion")
    private String cantidadProduccion;
    @Lob
    @Column(name = "rastreo")
    private String rastreo;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_control_dms_c", referencedColumnName = "id_dimensional_c")
    @ManyToOne
    private ControlDmsC idControlDmsC;
    @JoinColumn(name = "id_defecto", referencedColumnName = "id_defecto")
    @ManyToOne
    private Defecto idDefecto;

    public Visual() {
    }

    public Visual(Integer idVisual) {
        this.idVisual = idVisual;
    }

    public Visual(Integer idVisual, Date fechaRegistro) {
        this.idVisual = idVisual;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdVisual() {
        return idVisual;
    }

    public void setIdVisual(Integer idVisual) {
        this.idVisual = idVisual;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCantidadCalidad() {
        return cantidadCalidad;
    }

    public void setCantidadCalidad(String cantidadCalidad) {
        this.cantidadCalidad = cantidadCalidad;
    }

    public String getCantidadProduccion() {
        return cantidadProduccion;
    }

    public void setCantidadProduccion(String cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    public String getRastreo() {
        return rastreo;
    }

    public void setRastreo(String rastreo) {
        this.rastreo = rastreo;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public ControlDmsC getIdControlDmsC() {
        return idControlDmsC;
    }

    public void setIdControlDmsC(ControlDmsC idControlDmsC) {
        this.idControlDmsC = idControlDmsC;
    }

    public Defecto getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(Defecto idDefecto) {
        this.idDefecto = idDefecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisual != null ? idVisual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visual)) {
            return false;
        }
        Visual other = (Visual) object;
        if ((this.idVisual == null && other.idVisual != null) || (this.idVisual != null && !this.idVisual.equals(other.idVisual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Visual[ idVisual=" + idVisual + " ]";
    }
    
}
