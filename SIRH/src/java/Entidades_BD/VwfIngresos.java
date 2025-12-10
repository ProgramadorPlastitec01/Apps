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
@Table(name = "vwf_ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfIngresos.findAll", query = "SELECT v FROM VwfIngresos v"),
    @NamedQuery(name = "VwfIngresos.findByDocumento", query = "SELECT v FROM VwfIngresos v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfIngresos.findByNombres", query = "SELECT v FROM VwfIngresos v WHERE v.nombres = :nombres"),
    @NamedQuery(name = "VwfIngresos.findByApellidos", query = "SELECT v FROM VwfIngresos v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfIngresos.findByCodigo", query = "SELECT v FROM VwfIngresos v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VwfIngresos.findByCargo", query = "SELECT v FROM VwfIngresos v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfIngresos.findByFecha", query = "SELECT v FROM VwfIngresos v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfIngresos.findByFechacontrato", query = "SELECT v FROM VwfIngresos v WHERE v.fechacontrato = :fechacontrato"),
    @NamedQuery(name = "VwfIngresos.findByContrato", query = "SELECT v FROM VwfIngresos v WHERE v.contrato = :contrato")})
public class VwfIngresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Nombres")
    private String nombres;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Fecha_contrato")
    private String fechacontrato;
    @Basic(optional = false)
    @Column(name = "Contrato")
    private String contrato;

    public VwfIngresos() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechacontrato() {
        return fechacontrato;
    }

    public void setFechacontrato(String fechacontrato) {
        this.fechacontrato = fechacontrato;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
    
}
