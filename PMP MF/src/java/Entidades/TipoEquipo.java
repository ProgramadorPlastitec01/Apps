/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "tipo_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEquipo.findAll", query = "SELECT t FROM TipoEquipo t"),
    @NamedQuery(name = "TipoEquipo.findByIdTipoEquipo", query = "SELECT t FROM TipoEquipo t WHERE t.idTipoEquipo = :idTipoEquipo"),
    @NamedQuery(name = "TipoEquipo.findByTipoEquipo", query = "SELECT t FROM TipoEquipo t WHERE t.tipoEquipo = :tipoEquipo"),
    @NamedQuery(name = "TipoEquipo.findByFrecuencia", query = "SELECT t FROM TipoEquipo t WHERE t.frecuencia = :frecuencia"),
    @NamedQuery(name = "TipoEquipo.findByFrecuenciaAlerta", query = "SELECT t FROM TipoEquipo t WHERE t.frecuenciaAlerta = :frecuenciaAlerta"),
    @NamedQuery(name = "TipoEquipo.findByCodigo", query = "SELECT t FROM TipoEquipo t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TipoEquipo.findByVersion", query = "SELECT t FROM TipoEquipo t WHERE t.version = :version"),
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
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @Column(name = "frecuencia_alerta")
    private Integer frecuenciaAlerta;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "version")
    private Integer version;
    @Lob
    @Column(name = "observaciones_generales")
    private String observacionesGenerales;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

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

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getFrecuenciaAlerta() {
        return frecuenciaAlerta;
    }

    public void setFrecuenciaAlerta(Integer frecuenciaAlerta) {
        this.frecuenciaAlerta = frecuenciaAlerta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
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
        return "Entidades.TipoEquipo[ idTipoEquipo=" + idTipoEquipo + " ]";
    }
    
}
