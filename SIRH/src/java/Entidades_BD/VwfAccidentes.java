/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
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
@Table(name = "vwf_accidentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfAccidentes.findAll", query = "SELECT v FROM VwfAccidentes v"),
    @NamedQuery(name = "VwfAccidentes.findByDocumento", query = "SELECT v FROM VwfAccidentes v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfAccidentes.findByApellidos", query = "SELECT v FROM VwfAccidentes v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfAccidentes.findByNombre", query = "SELECT v FROM VwfAccidentes v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfAccidentes.findByFecha", query = "SELECT v FROM VwfAccidentes v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfAccidentes.findByTipo", query = "SELECT v FROM VwfAccidentes v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "VwfAccidentes.findByDias", query = "SELECT v FROM VwfAccidentes v WHERE v.dias = :dias"),
    @NamedQuery(name = "VwfAccidentes.findByParte", query = "SELECT v FROM VwfAccidentes v WHERE v.parte = :parte"),
    @NamedQuery(name = "VwfAccidentes.findByAgente", query = "SELECT v FROM VwfAccidentes v WHERE v.agente = :agente")})
public class VwfAccidentes implements Serializable {

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
    @Column(name = "# Dias")
    private String dias;
    @Column(name = "Parte")
    private String parte;
    @Column(name = "Agente")
    private String agente;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;

    public VwfAccidentes() {
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

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }
    
}
