/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vwf_incapacidades_v2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfIncapacidadesV2.findAll", query = "SELECT v FROM VwfIncapacidadesV2 v"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByDocumento", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByApellido", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.apellido = :apellido"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByNombres", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.nombres = :nombres"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByFechaIncapacidad", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.fechaIncapacidad = :fechaIncapacidad"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByClasificaci\u00f3n", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.clasificaci\u00f3n = :clasificaci\u00f3n"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByTipoIncapacidad", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.tipoIncapacidad = :tipoIncapacidad"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByHoras", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.horas = :horas"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByDias", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.dias = :dias"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByCargo", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.cargo = :cargo"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByGenero", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.genero = :genero"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByTipoContrato", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.tipoContrato = :tipoContrato"),
    @NamedQuery(name = "VwfIncapacidadesV2.findByArea", query = "SELECT v FROM VwfIncapacidadesV2 v WHERE v.area = :area")})
public class VwfIncapacidadesV2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private BigInteger documento;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Nombres")
    private String nombres;
    @Column(name = "Fecha Incapacidad")
    @Temporal(TemporalType.DATE)
    private Date fechaIncapacidad;
    @Column(name = "Clasificaci\u00f3n")
    private String clasificación;
    @Column(name = "Tipo Incapacidad")
    private String tipoIncapacidad;
    @Column(name = "# Horas")
    private Integer horas;
    @Column(name = "# Dias")
    private Long dias;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Genero")
    private String genero;
    @Column(name = "Tipo Contrato")
    private String tipoContrato;
    @Column(name = "Area")
    private String area;

    public VwfIncapacidadesV2() {
    }

    public BigInteger getDocumento() {
        return documento;
    }

    public void setDocumento(BigInteger documento) {
        this.documento = documento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Date getFechaIncapacidad() {
        return fechaIncapacidad;
    }

    public void setFechaIncapacidad(Date fechaIncapacidad) {
        this.fechaIncapacidad = fechaIncapacidad;
    }

    public String getClasificación() {
        return clasificación;
    }

    public void setClasificación(String clasificación) {
        this.clasificación = clasificación;
    }

    public String getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(String tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Long getDias() {
        return dias;
    }

    public void setDias(Long dias) {
        this.dias = dias;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}
