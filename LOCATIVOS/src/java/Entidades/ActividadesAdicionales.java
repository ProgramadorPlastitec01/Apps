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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Aprendiz.Sena1
 */
@Entity
@Table(name = "actividades_adicionales")
@NamedQueries({
    @NamedQuery(name = "ActividadesAdicionales.findAll", query = "SELECT a FROM ActividadesAdicionales a"),
    @NamedQuery(name = "ActividadesAdicionales.findByIdActividadAdicional", query = "SELECT a FROM ActividadesAdicionales a WHERE a.idActividadAdicional = :idActividadAdicional")})
public class ActividadesAdicionales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_adicional")
    private Integer idActividadAdicional;
    @Lob
    @Column(name = "ubicacion")
    private String ubicacion;
    @Lob
    @Column(name = "actividad_adicional")
    private String actividadAdicional;
    @JoinColumn(name = "id_prorgamacion", referencedColumnName = "id_programacion")
    @ManyToOne
    private Programacion programacion;

    public ActividadesAdicionales() {
    }

    public ActividadesAdicionales(Integer idActividadAdicional) {
        this.idActividadAdicional = idActividadAdicional;
    }

    public Integer getIdActividadAdicional() {
        return idActividadAdicional;
    }

    public void setIdActividadAdicional(Integer idActividadAdicional) {
        this.idActividadAdicional = idActividadAdicional;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getActividadAdicional() {
        return actividadAdicional;
    }

    public void setActividadAdicional(String actividadAdicional) {
        this.actividadAdicional = actividadAdicional;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadAdicional != null ? idActividadAdicional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadesAdicionales)) {
            return false;
        }
        ActividadesAdicionales other = (ActividadesAdicionales) object;
        if ((this.idActividadAdicional == null && other.idActividadAdicional != null) || (this.idActividadAdicional != null && !this.idActividadAdicional.equals(other.idActividadAdicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ActividadesAdicionales[idActividadAdicional=" + idActividadAdicional + "]";
    }

}
