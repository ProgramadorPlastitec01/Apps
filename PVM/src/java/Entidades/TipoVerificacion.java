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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "tipo_verificacion")
@NamedQueries({
    @NamedQuery(name = "TipoVerificacion.findAll", query = "SELECT t FROM TipoVerificacion t")})
public class TipoVerificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_verificacion")
    private Integer idTipoVerificacion;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @OneToMany(mappedBy = "tipoVerificacion")
    private Collection<Verificacion> verificacionCollection;

    public TipoVerificacion() {
    }

    public TipoVerificacion(Integer idTipoVerificacion) {
        this.idTipoVerificacion = idTipoVerificacion;
    }

    public TipoVerificacion(Integer idTipoVerificacion, Date fchRegistro) {
        this.idTipoVerificacion = idTipoVerificacion;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdTipoVerificacion() {
        return idTipoVerificacion;
    }

    public void setIdTipoVerificacion(Integer idTipoVerificacion) {
        this.idTipoVerificacion = idTipoVerificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Collection<Verificacion> getVerificacionCollection() {
        return verificacionCollection;
    }

    public void setVerificacionCollection(Collection<Verificacion> verificacionCollection) {
        this.verificacionCollection = verificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoVerificacion != null ? idTipoVerificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVerificacion)) {
            return false;
        }
        TipoVerificacion other = (TipoVerificacion) object;
        if ((this.idTipoVerificacion == null && other.idTipoVerificacion != null) || (this.idTipoVerificacion != null && !this.idTipoVerificacion.equals(other.idTipoVerificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoVerificacion[ idTipoVerificacion=" + idTipoVerificacion + " ]";
    }
    
}
