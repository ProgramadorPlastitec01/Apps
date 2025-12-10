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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vw_cargos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwCargos.findAll", query = "SELECT v FROM VwCargos v"),
    @NamedQuery(name = "VwCargos.findByCargo", query = "SELECT v FROM VwCargos v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwCargos.findByArea", query = "SELECT v FROM VwCargos v WHERE v.area = :area"),
    @NamedQuery(name = "VwCargos.findBySigla", query = "SELECT v FROM VwCargos v WHERE v.sigla = :sigla"),
    @NamedQuery(name = "VwCargos.findByEstado", query = "SELECT v FROM VwCargos v WHERE v.estado = :estado"),
    @NamedQuery(name = "VwCargos.findByEspecialidad", query = "SELECT v FROM VwCargos v WHERE v.especialidad = :especialidad")})
public class VwCargos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Basic(optional = false)
    @Column(name = "Sigla")
    private String sigla;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "Especialidad")
    private String especialidad;

    public VwCargos() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
}
