/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "defecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Defecto.findAll", query = "SELECT d FROM Defecto d")
    , @NamedQuery(name = "Defecto.findByIdDefecto", query = "SELECT d FROM Defecto d WHERE d.idDefecto = :idDefecto")})
public class Defecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_defecto")
    private Integer idDefecto;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre_defecto")
    private String nombreDefecto;
    @OneToMany(mappedBy = "idDefecto")
    private Collection<Movimientos> movimientosCollection;

    public Defecto() {
    }

    public Defecto(Integer idDefecto) {
        this.idDefecto = idDefecto;
    }

    public Defecto(Integer idDefecto, String nombreDefecto) {
        this.idDefecto = idDefecto;
        this.nombreDefecto = nombreDefecto;
    }

    public Integer getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(Integer idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getNombreDefecto() {
        return nombreDefecto;
    }

    public void setNombreDefecto(String nombreDefecto) {
        this.nombreDefecto = nombreDefecto;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
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
