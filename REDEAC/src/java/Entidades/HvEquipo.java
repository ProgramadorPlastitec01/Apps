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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "hv_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HvEquipo.findAll", query = "SELECT h FROM HvEquipo h")
    , @NamedQuery(name = "HvEquipo.findByIdHvEquipo", query = "SELECT h FROM HvEquipo h WHERE h.idHvEquipo = :idHvEquipo")
    , @NamedQuery(name = "HvEquipo.findByIdEquipo", query = "SELECT h FROM HvEquipo h WHERE h.idEquipo = :idEquipo")
    , @NamedQuery(name = "HvEquipo.findByCodigo", query = "SELECT h FROM HvEquipo h WHERE h.codigo = :codigo")
    , @NamedQuery(name = "HvEquipo.findByNombre", query = "SELECT h FROM HvEquipo h WHERE h.nombre = :nombre")
    , @NamedQuery(name = "HvEquipo.findByVersion", query = "SELECT h FROM HvEquipo h WHERE h.version = :version")
    , @NamedQuery(name = "HvEquipo.findByFchAdjunto", query = "SELECT h FROM HvEquipo h WHERE h.fchAdjunto = :fchAdjunto")
    , @NamedQuery(name = "HvEquipo.findByFchRegistro", query = "SELECT h FROM HvEquipo h WHERE h.fchRegistro = :fchRegistro")})
public class HvEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hv_equipo")
    private Integer idHvEquipo;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "version")
    private Integer version;
    @Lob
    @Column(name = "plantilla")
    private String plantilla;
    @Column(name = "fch_adjunto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchAdjunto;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;

    public HvEquipo() {
    }

    public HvEquipo(Integer idHvEquipo) {
        this.idHvEquipo = idHvEquipo;
    }

    public Integer getIdHvEquipo() {
        return idHvEquipo;
    }

    public void setIdHvEquipo(Integer idHvEquipo) {
        this.idHvEquipo = idHvEquipo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Date getFchAdjunto() {
        return fchAdjunto;
    }

    public void setFchAdjunto(Date fchAdjunto) {
        this.fchAdjunto = fchAdjunto;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHvEquipo != null ? idHvEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HvEquipo)) {
            return false;
        }
        HvEquipo other = (HvEquipo) object;
        if ((this.idHvEquipo == null && other.idHvEquipo != null) || (this.idHvEquipo != null && !this.idHvEquipo.equals(other.idHvEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HvEquipo[ idHvEquipo=" + idHvEquipo + " ]";
    }
    
}
