/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
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

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "pnc")
@NamedQueries({
    @NamedQuery(name = "Pnc.findAll", query = "SELECT p FROM Pnc p"),
    @NamedQuery(name = "Pnc.findByIdPnc", query = "SELECT p FROM Pnc p WHERE p.idPnc = :idPnc"),
    @NamedQuery(name = "Pnc.findByNombre", query = "SELECT p FROM Pnc p WHERE p.nombre = :nombre")})
public class Pnc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pnc")
    private Integer idPnc;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria categoria;

    public Pnc() {
    }

    public Pnc(Integer idPnc) {
        this.idPnc = idPnc;
    }

    public Integer getIdPnc() {
        return idPnc;
    }

    public void setIdPnc(Integer idPnc) {
        this.idPnc = idPnc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPnc != null ? idPnc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pnc)) {
            return false;
        }
        Pnc other = (Pnc) object;
        if ((this.idPnc == null && other.idPnc != null) || (this.idPnc != null && !this.idPnc.equals(other.idPnc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pnc[idPnc=" + idPnc + "]";
    }

}
