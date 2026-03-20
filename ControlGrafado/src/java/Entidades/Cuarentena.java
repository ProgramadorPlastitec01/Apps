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
@Table(name = "cuarentena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuarentena.findAll", query = "SELECT c FROM Cuarentena c"),
    @NamedQuery(name = "Cuarentena.findByIdCuarentena", query = "SELECT c FROM Cuarentena c WHERE c.idCuarentena = :idCuarentena"),
    @NamedQuery(name = "Cuarentena.findByFechaRegistro", query = "SELECT c FROM Cuarentena c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Cuarentena.findByCuarentena", query = "SELECT c FROM Cuarentena c WHERE c.cuarentena = :cuarentena")})
public class Cuarentena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuarentena")
    private Integer idCuarentena;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "cuarentena")
    private String cuarentena;
    @Lob
    @Column(name = "aprobacion")
    private String aprobacion;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_dimensional_c", referencedColumnName = "id_dimensional_c")
    @ManyToOne
    private ControlDmsC idDimensionalC;

    public Cuarentena() {
    }

    public Cuarentena(Integer idCuarentena) {
        this.idCuarentena = idCuarentena;
    }

    public Cuarentena(Integer idCuarentena, Date fechaRegistro) {
        this.idCuarentena = idCuarentena;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdCuarentena() {
        return idCuarentena;
    }

    public void setIdCuarentena(Integer idCuarentena) {
        this.idCuarentena = idCuarentena;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCuarentena() {
        return cuarentena;
    }

    public void setCuarentena(String cuarentena) {
        this.cuarentena = cuarentena;
    }

    public String getAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(String aprobacion) {
        this.aprobacion = aprobacion;
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
        hash += (idCuarentena != null ? idCuarentena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuarentena)) {
            return false;
        }
        Cuarentena other = (Cuarentena) object;
        if ((this.idCuarentena == null && other.idCuarentena != null) || (this.idCuarentena != null && !this.idCuarentena.equals(other.idCuarentena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cuarentena[ idCuarentena=" + idCuarentena + " ]";
    }
    
}
