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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "capacitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capacitacion.findAll", query = "SELECT c FROM Capacitacion c"),
    @NamedQuery(name = "Capacitacion.findByIdCapacitacion", query = "SELECT c FROM Capacitacion c WHERE c.idCapacitacion = :idCapacitacion"),
    @NamedQuery(name = "Capacitacion.findByFecha", query = "SELECT c FROM Capacitacion c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Capacitacion.findByDuracion", query = "SELECT c FROM Capacitacion c WHERE c.duracion = :duracion"),
    @NamedQuery(name = "Capacitacion.findByFolio", query = "SELECT c FROM Capacitacion c WHERE c.folio = :folio"),
    @NamedQuery(name = "Capacitacion.findByEstado", query = "SELECT c FROM Capacitacion c WHERE c.estado = :estado"),
    @NamedQuery(name = "Capacitacion.findByFechaRegistro", query = "SELECT c FROM Capacitacion c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Capacitacion.findByUsuarioRegistro", query = "SELECT c FROM Capacitacion c WHERE c.usuarioRegistro = :usuarioRegistro")})
public class Capacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_capacitacion")
    private Integer idCapacitacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "entidad")
    private String entidad;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Lob
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "duracion")
    private String duracion;
    @Basic(optional = false)
    @Lob
    @Column(name = "capacitador")
    private String capacitador;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "folio")
    private String folio;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Capacitacion() {
    }

    public Capacitacion(Integer idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }

    public Capacitacion(Integer idCapacitacion, String entidad, Date fecha, String titulo, String duracion, String capacitador, String observaciones, String folio, int estado, Date fechaRegistro, String usuarioRegistro) {
        this.idCapacitacion = idCapacitacion;
        this.entidad = entidad;
        this.fecha = fecha;
        this.titulo = titulo;
        this.duracion = duracion;
        this.capacitador = capacitador;
        this.observaciones = observaciones;
        this.folio = folio;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdCapacitacion() {
        return idCapacitacion;
    }

    public void setIdCapacitacion(Integer idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(String capacitador) {
        this.capacitador = capacitador;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapacitacion != null ? idCapacitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capacitacion)) {
            return false;
        }
        Capacitacion other = (Capacitacion) object;
        if ((this.idCapacitacion == null && other.idCapacitacion != null) || (this.idCapacitacion != null && !this.idCapacitacion.equals(other.idCapacitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Capacitacion[ idCapacitacion=" + idCapacitacion + " ]";
    }
    
}
