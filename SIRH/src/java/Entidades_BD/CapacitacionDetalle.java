/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "capacitacion_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CapacitacionDetalle.findAll", query = "SELECT c FROM CapacitacionDetalle c"),
    @NamedQuery(name = "CapacitacionDetalle.findByIdCapacitacionDetalle", query = "SELECT c FROM CapacitacionDetalle c WHERE c.idCapacitacionDetalle = :idCapacitacionDetalle"),
    @NamedQuery(name = "CapacitacionDetalle.findByIdCapacitacion", query = "SELECT c FROM CapacitacionDetalle c WHERE c.idCapacitacion = :idCapacitacion"),
    @NamedQuery(name = "CapacitacionDetalle.findByDocumento", query = "SELECT c FROM CapacitacionDetalle c WHERE c.documento = :documento"),
    @NamedQuery(name = "CapacitacionDetalle.findByNombre", query = "SELECT c FROM CapacitacionDetalle c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CapacitacionDetalle.findByCargo", query = "SELECT c FROM CapacitacionDetalle c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "CapacitacionDetalle.findBySalarioHora", query = "SELECT c FROM CapacitacionDetalle c WHERE c.salarioHora = :salarioHora"),
    @NamedQuery(name = "CapacitacionDetalle.findByFechaRegistro", query = "SELECT c FROM CapacitacionDetalle c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CapacitacionDetalle.findByUsuarioRegistro", query = "SELECT c FROM CapacitacionDetalle c WHERE c.usuarioRegistro = :usuarioRegistro")})
public class CapacitacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_capacitacion_detalle")
    private Integer idCapacitacionDetalle;
    @Basic(optional = false)
    @Column(name = "id_capacitacion")
    private int idCapacitacion;
    @Basic(optional = false)
    @Column(name = "documento")
    private long documento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "salario_hora")
    private String salarioHora;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public CapacitacionDetalle() {
    }

    public CapacitacionDetalle(Integer idCapacitacionDetalle) {
        this.idCapacitacionDetalle = idCapacitacionDetalle;
    }

    public CapacitacionDetalle(Integer idCapacitacionDetalle, int idCapacitacion, long documento, String nombre, String cargo, String salarioHora, Date fechaRegistro, String usuarioRegistro) {
        this.idCapacitacionDetalle = idCapacitacionDetalle;
        this.idCapacitacion = idCapacitacion;
        this.documento = documento;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salarioHora = salarioHora;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdCapacitacionDetalle() {
        return idCapacitacionDetalle;
    }

    public void setIdCapacitacionDetalle(Integer idCapacitacionDetalle) {
        this.idCapacitacionDetalle = idCapacitacionDetalle;
    }

    public int getIdCapacitacion() {
        return idCapacitacion;
    }

    public void setIdCapacitacion(int idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(String salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapacitacionDetalle != null ? idCapacitacionDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapacitacionDetalle)) {
            return false;
        }
        CapacitacionDetalle other = (CapacitacionDetalle) object;
        if ((this.idCapacitacionDetalle == null && other.idCapacitacionDetalle != null) || (this.idCapacitacionDetalle != null && !this.idCapacitacionDetalle.equals(other.idCapacitacionDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.CapacitacionDetalle[ idCapacitacionDetalle=" + idCapacitacionDetalle + " ]";
    }
    
}
