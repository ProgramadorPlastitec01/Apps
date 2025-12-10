/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "mc_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McGrupo.findAll", query = "SELECT m FROM McGrupo m"),
    @NamedQuery(name = "McGrupo.findByIdMcGrupo", query = "SELECT m FROM McGrupo m WHERE m.idMcGrupo = :idMcGrupo"),
    @NamedQuery(name = "McGrupo.findByNombre", query = "SELECT m FROM McGrupo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "McGrupo.findByPocision", query = "SELECT m FROM McGrupo m WHERE m.pocision = :pocision"),
    @NamedQuery(name = "McGrupo.findByEstado", query = "SELECT m FROM McGrupo m WHERE m.estado = :estado"),
    @NamedQuery(name = "McGrupo.findBySst", query = "SELECT m FROM McGrupo m WHERE m.sst = :sst")})
public class McGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_grupo")
    private Integer idMcGrupo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "pocision")
    private int pocision;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "sst")
    private int sst;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMcGrupo")
    private Collection<McSstDefinicion> mcSstDefinicionCollection;

    public McGrupo() {
    }

    public McGrupo(Integer idMcGrupo) {
        this.idMcGrupo = idMcGrupo;
    }

    public McGrupo(Integer idMcGrupo, String nombre, int pocision, int estado, int sst) {
        this.idMcGrupo = idMcGrupo;
        this.nombre = nombre;
        this.pocision = pocision;
        this.estado = estado;
        this.sst = sst;
    }

    public Integer getIdMcGrupo() {
        return idMcGrupo;
    }

    public void setIdMcGrupo(Integer idMcGrupo) {
        this.idMcGrupo = idMcGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPocision() {
        return pocision;
    }

    public void setPocision(int pocision) {
        this.pocision = pocision;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getSst() {
        return sst;
    }

    public void setSst(int sst) {
        this.sst = sst;
    }

    @XmlTransient
    public Collection<McSstDefinicion> getMcSstDefinicionCollection() {
        return mcSstDefinicionCollection;
    }

    public void setMcSstDefinicionCollection(Collection<McSstDefinicion> mcSstDefinicionCollection) {
        this.mcSstDefinicionCollection = mcSstDefinicionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcGrupo != null ? idMcGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McGrupo)) {
            return false;
        }
        McGrupo other = (McGrupo) object;
        if ((this.idMcGrupo == null && other.idMcGrupo != null) || (this.idMcGrupo != null && !this.idMcGrupo.equals(other.idMcGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McGrupo[ idMcGrupo=" + idMcGrupo + " ]";
    }
    
}
