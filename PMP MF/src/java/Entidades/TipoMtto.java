/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "tipo_mtto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMtto.findAll", query = "SELECT t FROM TipoMtto t"),
    @NamedQuery(name = "TipoMtto.findByIdTipoMtto", query = "SELECT t FROM TipoMtto t WHERE t.idTipoMtto = :idTipoMtto"),
    @NamedQuery(name = "TipoMtto.findByNombre", query = "SELECT t FROM TipoMtto t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoMtto.findByNumeroOt", query = "SELECT t FROM TipoMtto t WHERE t.numeroOt = :numeroOt")})
public class TipoMtto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_mtto")
    private Integer idTipoMtto;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "numero_ot")
    private Integer numeroOt;

    public TipoMtto() {
    }

    public TipoMtto(Integer idTipoMtto) {
        this.idTipoMtto = idTipoMtto;
    }

    public Integer getIdTipoMtto() {
        return idTipoMtto;
    }

    public void setIdTipoMtto(Integer idTipoMtto) {
        this.idTipoMtto = idTipoMtto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroOt() {
        return numeroOt;
    }

    public void setNumeroOt(Integer numeroOt) {
        this.numeroOt = numeroOt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoMtto != null ? idTipoMtto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMtto)) {
            return false;
        }
        TipoMtto other = (TipoMtto) object;
        if ((this.idTipoMtto == null && other.idTipoMtto != null) || (this.idTipoMtto != null && !this.idTipoMtto.equals(other.idTipoMtto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoMtto[ idTipoMtto=" + idTipoMtto + " ]";
    }
    
}
