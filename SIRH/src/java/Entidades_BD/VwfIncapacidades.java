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
@Table(name = "vwf_incapacidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfIncapacidades.findAll", query = "SELECT v FROM VwfIncapacidades v"),
    @NamedQuery(name = "VwfIncapacidades.findByDocumento", query = "SELECT v FROM VwfIncapacidades v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfIncapacidades.findByApellidos", query = "SELECT v FROM VwfIncapacidades v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfIncapacidades.findByNombre", query = "SELECT v FROM VwfIncapacidades v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfIncapacidades.findByFechaingreso", query = "SELECT v FROM VwfIncapacidades v WHERE v.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "VwfIncapacidades.findByGenero", query = "SELECT v FROM VwfIncapacidades v WHERE v.genero = :genero"),
    @NamedQuery(name = "VwfIncapacidades.findByTipoContrato", query = "SELECT v FROM VwfIncapacidades v WHERE v.tipoContrato = :tipoContrato"),
    @NamedQuery(name = "VwfIncapacidades.findByCargo", query = "SELECT v FROM VwfIncapacidades v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfIncapacidades.findByArea", query = "SELECT v FROM VwfIncapacidades v WHERE v.area = :area"),
    @NamedQuery(name = "VwfIncapacidades.findByFecha", query = "SELECT v FROM VwfIncapacidades v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfIncapacidades.findByClasificaci\u00f3n", query = "SELECT v FROM VwfIncapacidades v WHERE v.clasificaci\u00f3n = :clasificaci\u00f3n"),
    @NamedQuery(name = "VwfIncapacidades.findByTipo", query = "SELECT v FROM VwfIncapacidades v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "VwfIncapacidades.findByHoras", query = "SELECT v FROM VwfIncapacidades v WHERE v.horas = :horas"),
    @NamedQuery(name = "VwfIncapacidades.findByDias", query = "SELECT v FROM VwfIncapacidades v WHERE v.dias = :dias"),
    @NamedQuery(name = "VwfIncapacidades.findByFechaNacimiento", query = "SELECT v FROM VwfIncapacidades v WHERE v.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "VwfIncapacidades.findByEdad", query = "SELECT v FROM VwfIncapacidades v WHERE v.edad = :edad")})
public class VwfIncapacidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Fecha_ingreso")
    private String fechaingreso;
    @Basic(optional = false)
    @Column(name = "Genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "Tipo Contrato")
    private String tipoContrato;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Clasificaci\u00f3n")
    private String clasificación;
    @Column(name = "Tipo")
    private String tipo;
    @Column(name = "# Horas")
    private String horas;
    @Lob
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "# Dias")
    private String dias;
    @Column(name = "Fecha_Nacimiento")
    private String fechaNacimiento;
    @Column(name = "Edad")
    private String edad;

    public VwfIncapacidades() {
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

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClasificación() {
        return clasificación;
    }

    public void setClasificación(String clasificación) {
        this.clasificación = clasificación;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
    
}
