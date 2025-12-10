/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vw_areas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwAreas.findAll", query = "SELECT v FROM VwAreas v"),
    @NamedQuery(name = "VwAreas.findById", query = "SELECT v FROM VwAreas v WHERE v.id = :id"),
    @NamedQuery(name = "VwAreas.findByArea", query = "SELECT v FROM VwAreas v WHERE v.area = :area"),
    @NamedQuery(name = "VwAreas.findBySigla", query = "SELECT v FROM VwAreas v WHERE v.sigla = :sigla"),
    @NamedQuery(name = "VwAreas.findByJefe", query = "SELECT v FROM VwAreas v WHERE v.jefe = :jefe"),
    @NamedQuery(name = "VwAreas.findByEstado", query = "SELECT v FROM VwAreas v WHERE v.estado = :estado")})
public class VwAreas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Basic(optional = false)
    @Column(name = "Sigla")
    private String sigla;
    @Column(name = "Jefe")
    private String jefe;
    @Lob
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;

    public VwAreas() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
