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
 * @author aprendiz.sena1
 */
@Entity
@Table(name = "area")
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByIdArea", query = "SELECT a FROM Area a WHERE a.idArea = :idArea"),
    @NamedQuery(name = "Area.findByUsuRegistro", query = "SELECT a FROM Area a WHERE a.usuRegistro = :usuRegistro"),
    @NamedQuery(name = "Area.findByFchRegistro", query = "SELECT a FROM Area a WHERE a.fchRegistro = :fchRegistro"),
    @NamedQuery(name = "Area.findBySiglatura", query = "SELECT a FROM Area a WHERE a.siglatura = :siglatura"),
    @NamedQuery(name = "Area.findByArea", query = "SELECT a FROM Area a WHERE a.area = :area"),
    @NamedQuery(name = "Area.findByEstado", query = "SELECT a FROM Area a WHERE a.estado = :estado")})
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "siglatura")
    private String siglatura;
    @Column(name = "area")
    private String area;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private Collection<Notas> notasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private Collection<Maquina> maquinaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private Collection<Cargo> cargoCollection;

    public Area() {
    }

    public Area(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getSiglatura() {
        return siglatura;
    }

    public void setSiglatura(String siglatura) {
        this.siglatura = siglatura;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Collection<Notas> getNotasCollection() {
        return notasCollection;
    }

    public void setNotasCollection(Collection<Notas> notasCollection) {
        this.notasCollection = notasCollection;
    }

    public Collection<Maquina> getMaquinaCollection() {
        return maquinaCollection;
    }

    public void setMaquinaCollection(Collection<Maquina> maquinaCollection) {
        this.maquinaCollection = maquinaCollection;
    }

    public Collection<Cargo> getCargoCollection() {
        return cargoCollection;
    }

    public void setCargoCollection(Collection<Cargo> cargoCollection) {
        this.cargoCollection = cargoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Servlets.Area[idArea=" + idArea + "]";
    }

}
