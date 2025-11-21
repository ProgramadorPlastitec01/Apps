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
@Table(name = "parada_maquina")
@NamedQueries({
    @NamedQuery(name = "ParadaMaquina.findAll", query = "SELECT p FROM ParadaMaquina p"),
    @NamedQuery(name = "ParadaMaquina.findByIdParadaMaquina", query = "SELECT p FROM ParadaMaquina p WHERE p.idParadaMaquina = :idParadaMaquina"),
    @NamedQuery(name = "ParadaMaquina.findByNombre", query = "SELECT p FROM ParadaMaquina p WHERE p.nombre = :nombre")})
public class ParadaMaquina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parada_maquina")
    private Integer idParadaMaquina;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria categoria;

    public ParadaMaquina() {
    }

    public ParadaMaquina(Integer idParadaMaquina) {
        this.idParadaMaquina = idParadaMaquina;
    }

    public Integer getIdParadaMaquina() {
        return idParadaMaquina;
    }

    public void setIdParadaMaquina(Integer idParadaMaquina) {
        this.idParadaMaquina = idParadaMaquina;
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
        hash += (idParadaMaquina != null ? idParadaMaquina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParadaMaquina)) {
            return false;
        }
        ParadaMaquina other = (ParadaMaquina) object;
        if ((this.idParadaMaquina == null && other.idParadaMaquina != null) || (this.idParadaMaquina != null && !this.idParadaMaquina.equals(other.idParadaMaquina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParadaMaquina[idParadaMaquina=" + idParadaMaquina + "]";
    }

}
