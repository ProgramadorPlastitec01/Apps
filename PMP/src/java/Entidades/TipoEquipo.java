/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "tipo_equipo")
@NamedQueries({
    @NamedQuery(name = "TipoEquipo.findAll", query = "SELECT t FROM TipoEquipo t"),
    @NamedQuery(name = "TipoEquipo.findByIdTipoEquipo", query = "SELECT t FROM TipoEquipo t WHERE t.idTipoEquipo = :idTipoEquipo"),
    @NamedQuery(name = "TipoEquipo.findByTipoEquipo", query = "SELECT t FROM TipoEquipo t WHERE t.tipoEquipo = :tipoEquipo"),
    @NamedQuery(name = "TipoEquipo.findByFrecuenciaMtto", query = "SELECT t FROM TipoEquipo t WHERE t.frecuenciaMtto = :frecuenciaMtto"),
    @NamedQuery(name = "TipoEquipo.findByEstado", query = "SELECT t FROM TipoEquipo t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoEquipo.findByUsuarioRegistro", query = "SELECT t FROM TipoEquipo t WHERE t.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "TipoEquipo.findByFechaRegistro", query = "SELECT t FROM TipoEquipo t WHERE t.fechaRegistro = :fechaRegistro")})
public class TipoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_equipo")
    private Integer idTipoEquipo;
    @Column(name = "tipo_equipo")
    private String tipoEquipo;
    @Column(name = "frecuencia_mtto")
    private Integer frecuenciaMtto;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEquipo")
    private Collection<Equipo> equipoCollection;

    public TipoEquipo() {
    }

    public TipoEquipo(Integer idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public Integer getIdTipoEquipo() {
        return idTipoEquipo;
    }

    public void setIdTipoEquipo(Integer idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Integer getFrecuenciaMtto() {
        return frecuenciaMtto;
    }

    public void setFrecuenciaMtto(Integer frecuenciaMtto) {
        this.frecuenciaMtto = frecuenciaMtto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    public Collection<Equipo> getEquipoCollection() {
        return equipoCollection;
    }

    public void setEquipoCollection(Collection<Equipo> equipoCollection) {
        this.equipoCollection = equipoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEquipo != null ? idTipoEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEquipo)) {
            return false;
        }
        TipoEquipo other = (TipoEquipo) object;
        if ((this.idTipoEquipo == null && other.idTipoEquipo != null) || (this.idTipoEquipo != null && !this.idTipoEquipo.equals(other.idTipoEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoEquipo[idTipoEquipo=" + idTipoEquipo + "]";
    }

}
