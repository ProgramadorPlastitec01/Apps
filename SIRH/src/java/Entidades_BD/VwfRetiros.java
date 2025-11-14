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
@Table(name = "vwf_retiros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfRetiros.findAll", query = "SELECT v FROM VwfRetiros v"),
    @NamedQuery(name = "VwfRetiros.findByDocumento", query = "SELECT v FROM VwfRetiros v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfRetiros.findByApellidos", query = "SELECT v FROM VwfRetiros v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfRetiros.findByNombre", query = "SELECT v FROM VwfRetiros v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfRetiros.findByFecha", query = "SELECT v FROM VwfRetiros v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfRetiros.findByTipo", query = "SELECT v FROM VwfRetiros v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "VwfRetiros.findByCargo", query = "SELECT v FROM VwfRetiros v WHERE v.cargo = :cargo")})
public class VwfRetiros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Tipo")
    private String tipo;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;

    public VwfRetiros() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}
