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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "tipo_soporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSoporte.findAll", query = "SELECT t FROM TipoSoporte t")
    , @NamedQuery(name = "TipoSoporte.findByIdTipoSoporte", query = "SELECT t FROM TipoSoporte t WHERE t.idTipoSoporte = :idTipoSoporte")
    , @NamedQuery(name = "TipoSoporte.findByNombre", query = "SELECT t FROM TipoSoporte t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoSoporte.findByUsuarioRegistro", query = "SELECT t FROM TipoSoporte t WHERE t.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "TipoSoporte.findByFechaRegistro", query = "SELECT t FROM TipoSoporte t WHERE t.fechaRegistro = :fechaRegistro")})
public class TipoSoporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_soporte")
    private Integer idTipoSoporte;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private Rol idRol;
    @OneToMany(mappedBy = "idTipoSoporte")
    private Collection<Caso> casoCollection;

    public TipoSoporte() {
    }

    public TipoSoporte(Integer idTipoSoporte) {
        this.idTipoSoporte = idTipoSoporte;
    }

    public TipoSoporte(Integer idTipoSoporte, String nombre, String usuarioRegistro, Date fechaRegistro) {
        this.idTipoSoporte = idTipoSoporte;
        this.nombre = nombre;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdTipoSoporte() {
        return idTipoSoporte;
    }

    public void setIdTipoSoporte(Integer idTipoSoporte) {
        this.idTipoSoporte = idTipoSoporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @XmlTransient
    public Collection<Caso> getCasoCollection() {
        return casoCollection;
    }

    public void setCasoCollection(Collection<Caso> casoCollection) {
        this.casoCollection = casoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSoporte != null ? idTipoSoporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSoporte)) {
            return false;
        }
        TipoSoporte other = (TipoSoporte) object;
        if ((this.idTipoSoporte == null && other.idTipoSoporte != null) || (this.idTipoSoporte != null && !this.idTipoSoporte.equals(other.idTipoSoporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoSoporte[ idTipoSoporte=" + idTipoSoporte + " ]";
    }
    
}
