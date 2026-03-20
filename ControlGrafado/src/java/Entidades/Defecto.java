/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prog.sistemas2
 */
@Entity
@Table(name = "defecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Defecto.findAll", query = "SELECT d FROM Defecto d"),
    @NamedQuery(name = "Defecto.findByIdDefecto", query = "SELECT d FROM Defecto d WHERE d.idDefecto = :idDefecto"),
    @NamedQuery(name = "Defecto.findByFechaRegistro", query = "SELECT d FROM Defecto d WHERE d.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Defecto.findByDefecto", query = "SELECT d FROM Defecto d WHERE d.defecto = :defecto"),
    @NamedQuery(name = "Defecto.findByEstado", query = "SELECT d FROM Defecto d WHERE d.estado = :estado")})
public class Defecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_defecto")
    private Integer idDefecto;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "defecto")
    private String defecto;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @OneToMany(mappedBy = "idDefecto")
    private Collection<Visual> visualCollection;

    public Defecto() {
    }

    public Defecto(Integer idDefecto) {
        this.idDefecto = idDefecto;
    }

    public Defecto(Integer idDefecto, Date fechaRegistro) {
        this.idDefecto = idDefecto;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(Integer idDefecto) {
        this.idDefecto = idDefecto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDefecto() {
        return defecto;
    }

    public void setDefecto(String defecto) {
        this.defecto = defecto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public Collection<Visual> getVisualCollection() {
        return visualCollection;
    }

    public void setVisualCollection(Collection<Visual> visualCollection) {
        this.visualCollection = visualCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDefecto != null ? idDefecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Defecto)) {
            return false;
        }
        Defecto other = (Defecto) object;
        if ((this.idDefecto == null && other.idDefecto != null) || (this.idDefecto != null && !this.idDefecto.equals(other.idDefecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Defecto[ idDefecto=" + idDefecto + " ]";
    }
    
}
