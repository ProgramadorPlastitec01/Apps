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
@Table(name = "vw_salario_personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwSalarioPersonal.findAll", query = "SELECT v FROM VwSalarioPersonal v"),
    @NamedQuery(name = "VwSalarioPersonal.findByDocumento", query = "SELECT v FROM VwSalarioPersonal v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwSalarioPersonal.findBySalario", query = "SELECT v FROM VwSalarioPersonal v WHERE v.salario = :salario"),
    @NamedQuery(name = "VwSalarioPersonal.findByApellidos", query = "SELECT v FROM VwSalarioPersonal v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwSalarioPersonal.findByNombre", query = "SELECT v FROM VwSalarioPersonal v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwSalarioPersonal.findByArea", query = "SELECT v FROM VwSalarioPersonal v WHERE v.area = :area"),
    @NamedQuery(name = "VwSalarioPersonal.findByCargo", query = "SELECT v FROM VwSalarioPersonal v WHERE v.cargo = :cargo")})
public class VwSalarioPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Salario")
    private String salario;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;

    public VwSalarioPersonal() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}
