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
@Table(name = "vwf_capacitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfCapacitacion.findAll", query = "SELECT v FROM VwfCapacitacion v"),
    @NamedQuery(name = "VwfCapacitacion.findByFecha", query = "SELECT v FROM VwfCapacitacion v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfCapacitacion.findByFolio", query = "SELECT v FROM VwfCapacitacion v WHERE v.folio = :folio"),
    @NamedQuery(name = "VwfCapacitacion.findByDuraci\u00f3n", query = "SELECT v FROM VwfCapacitacion v WHERE v.duraci\u00f3n = :duraci\u00f3n")})
public class VwfCapacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "Folio")
    private String folio;
    @Basic(optional = false)
    @Lob
    @Column(name = "Entidad")
    private String entidad;
    @Basic(optional = false)
    @Lob
    @Column(name = "Titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "Duraci\u00f3n")
    private String duración;
    @Basic(optional = false)
    @Lob
    @Column(name = "Capacitador")
    private String capacitador;
    @Lob
    @Column(name = "Observacciones")
    private String observacciones;

    public VwfCapacitacion() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuración() {
        return duración;
    }

    public void setDuración(String duración) {
        this.duración = duración;
    }

    public String getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(String capacitador) {
        this.capacitador = capacitador;
    }

    public String getObservacciones() {
        return observacciones;
    }

    public void setObservacciones(String observacciones) {
        this.observacciones = observacciones;
    }
    
}
