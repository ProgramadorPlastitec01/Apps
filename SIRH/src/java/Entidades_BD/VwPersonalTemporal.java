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
@Table(name = "vw_personal_temporal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwPersonalTemporal.findAll", query = "SELECT v FROM VwPersonalTemporal v"),
    @NamedQuery(name = "VwPersonalTemporal.findByDocumento", query = "SELECT v FROM VwPersonalTemporal v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwPersonalTemporal.findByApellidos", query = "SELECT v FROM VwPersonalTemporal v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwPersonalTemporal.findByNombre", query = "SELECT v FROM VwPersonalTemporal v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwPersonalTemporal.findByGenero", query = "SELECT v FROM VwPersonalTemporal v WHERE v.genero = :genero"),
    @NamedQuery(name = "VwPersonalTemporal.findByCodigoFirma", query = "SELECT v FROM VwPersonalTemporal v WHERE v.codigoFirma = :codigoFirma"),
    @NamedQuery(name = "VwPersonalTemporal.findByFechadenacimiento", query = "SELECT v FROM VwPersonalTemporal v WHERE v.fechadenacimiento = :fechadenacimiento"),
    @NamedQuery(name = "VwPersonalTemporal.findBy\u00c1rea", query = "SELECT v FROM VwPersonalTemporal v WHERE v.\u00e1rea = :\u00e1rea"),
    @NamedQuery(name = "VwPersonalTemporal.findByCargo", query = "SELECT v FROM VwPersonalTemporal v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwPersonalTemporal.findByFechadeingreso", query = "SELECT v FROM VwPersonalTemporal v WHERE v.fechadeingreso = :fechadeingreso"),
    @NamedQuery(name = "VwPersonalTemporal.findByContrato", query = "SELECT v FROM VwPersonalTemporal v WHERE v.contrato = :contrato"),
    @NamedQuery(name = "VwPersonalTemporal.findByGruposanguineo", query = "SELECT v FROM VwPersonalTemporal v WHERE v.gruposanguineo = :gruposanguineo"),
    @NamedQuery(name = "VwPersonalTemporal.findByNiveleducativo", query = "SELECT v FROM VwPersonalTemporal v WHERE v.niveleducativo = :niveleducativo"),
    @NamedQuery(name = "VwPersonalTemporal.findByIdCargo", query = "SELECT v FROM VwPersonalTemporal v WHERE v.idCargo = :idCargo")})
public class VwPersonalTemporal implements Serializable {

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
    @Column(name = "\u00c1rea")
    private String área;
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

    public VwPersonalTemporal() {
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

    public String getÁrea() {
        return área;
    }

    public void setÁrea(String área) {
        this.área = área;
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
    
}
