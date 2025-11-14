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
@Table(name = "vwf_personal_activo_calificado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfPersonalActivoCalificado.findAll", query = "SELECT v FROM VwfPersonalActivoCalificado v"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByDocumento", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByApellidos", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByNombre", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByGenero", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.genero = :genero"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByCodigoFirma", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.codigoFirma = :codigoFirma"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByFechadenacimiento", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.fechadenacimiento = :fechadenacimiento"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByArea", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.area = :area"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByCargo", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByFechadeingreso", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.fechadeingreso = :fechadeingreso"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByContrato", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.contrato = :contrato"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByGruposanguineo", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.gruposanguineo = :gruposanguineo"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByNiveleducativo", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.niveleducativo = :niveleducativo"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByIdCargo", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.idCargo = :idCargo"),
    @NamedQuery(name = "VwfPersonalActivoCalificado.findByIdArea", query = "SELECT v FROM VwfPersonalActivoCalificado v WHERE v.idArea = :idArea")})
public class VwfPersonalActivoCalificado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Genero")
    private String genero;
    @Column(name = "codigo_firma")
    private String codigoFirma;
    @Column(name = "Fecha de nacimiento")
    private String fechadenacimiento;
    @Basic(optional = false)
    @Column(name = "Area")
    private String area;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Column(name = "Fecha de ingreso")
    private String fechadeingreso;
    @Basic(optional = false)
    @Column(name = "Contrato")
    private String contrato;
    @Column(name = "Grupo sanguineo")
    private String gruposanguineo;
    @Column(name = "Nivel educativo")
    private String niveleducativo;
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private String idCargo;
    @Basic(optional = false)
    @Column(name = "id_area")
    private String idArea;

    public VwfPersonalActivoCalificado() {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCodigoFirma() {
        return codigoFirma;
    }

    public void setCodigoFirma(String codigoFirma) {
        this.codigoFirma = codigoFirma;
    }

    public String getFechadenacimiento() {
        return fechadenacimiento;
    }

    public void setFechadenacimiento(String fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
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

    public String getFechadeingreso() {
        return fechadeingreso;
    }

    public void setFechadeingreso(String fechadeingreso) {
        this.fechadeingreso = fechadeingreso;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getGruposanguineo() {
        return gruposanguineo;
    }

    public void setGruposanguineo(String gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getNiveleducativo() {
        return niveleducativo;
    }

    public void setNiveleducativo(String niveleducativo) {
        this.niveleducativo = niveleducativo;
    }

    public String getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(String idCargo) {
        this.idCargo = idCargo;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }
    
}
