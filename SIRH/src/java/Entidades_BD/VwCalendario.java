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
@Table(name = "vw_calendario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwCalendario.findAll", query = "SELECT v FROM VwCalendario v"),
    @NamedQuery(name = "VwCalendario.findByFechaInicio", query = "SELECT v FROM VwCalendario v WHERE v.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "VwCalendario.findByActividad", query = "SELECT v FROM VwCalendario v WHERE v.actividad = :actividad"),
    @NamedQuery(name = "VwCalendario.findByFechaFin", query = "SELECT v FROM VwCalendario v WHERE v.fechaFin = :fechaFin")})
public class VwCalendario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Fecha Inicio")
    private String fechaInicio;
    @Column(name = "Actividad")
    private String actividad;
    @Lob
    @Column(name = "Detalle")
    private String detalle;
    @Column(name = "Fecha Fin")
    private String fechaFin;

    public VwCalendario() {
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
