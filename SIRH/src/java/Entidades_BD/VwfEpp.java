/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
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
@Table(name = "vwf_epp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfEpp.findAll", query = "SELECT v FROM VwfEpp v"),
    @NamedQuery(name = "VwfEpp.findByDocumento", query = "SELECT v FROM VwfEpp v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfEpp.findByNombre", query = "SELECT v FROM VwfEpp v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfEpp.findByApellido", query = "SELECT v FROM VwfEpp v WHERE v.apellido = :apellido"),
    @NamedQuery(name = "VwfEpp.findByFecha", query = "SELECT v FROM VwfEpp v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfEpp.findByEstado", query = "SELECT v FROM VwfEpp v WHERE v.estado = :estado")})
public class VwfEpp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Documento")
    private long documento;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(name = "Epp")
    private String epp;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;
    @Column(name = "Estado")
    private String estado;

    public VwfEpp() {
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEpp() {
        return epp;
    }

    public void setEpp(String epp) {
        this.epp = epp;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
