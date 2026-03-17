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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "tipo_plantilla")
@NamedQueries({
    @NamedQuery(name = "TipoPlantilla.findAll", query = "SELECT t FROM TipoPlantilla t")})
public class TipoPlantilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_plantilla")
    private Integer idTipoPlantilla;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "tipoPlantilla")
    private Collection<Plantilla> plantillaCollection;

    public TipoPlantilla() {
    }

    public TipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public Integer getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setIdTipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<Plantilla> getPlantillaCollection() {
        return plantillaCollection;
    }

    public void setPlantillaCollection(Collection<Plantilla> plantillaCollection) {
        this.plantillaCollection = plantillaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPlantilla != null ? idTipoPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlantilla)) {
            return false;
        }
        TipoPlantilla other = (TipoPlantilla) object;
        if ((this.idTipoPlantilla == null && other.idTipoPlantilla != null) || (this.idTipoPlantilla != null && !this.idTipoPlantilla.equals(other.idTipoPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoPlantilla[ idTipoPlantilla=" + idTipoPlantilla + " ]";
    }
    
}
