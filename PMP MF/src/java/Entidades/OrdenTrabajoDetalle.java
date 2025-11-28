/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "orden_trabajo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajoDetalle.findAll", query = "SELECT o FROM OrdenTrabajoDetalle o"),
    @NamedQuery(name = "OrdenTrabajoDetalle.findByIdOrdenTrabajoDetalle", query = "SELECT o FROM OrdenTrabajoDetalle o WHERE o.idOrdenTrabajoDetalle = :idOrdenTrabajoDetalle"),
    @NamedQuery(name = "OrdenTrabajoDetalle.findByContProgramadas", query = "SELECT o FROM OrdenTrabajoDetalle o WHERE o.contProgramadas = :contProgramadas"),
    @NamedQuery(name = "OrdenTrabajoDetalle.findByContEjecutadas", query = "SELECT o FROM OrdenTrabajoDetalle o WHERE o.contEjecutadas = :contEjecutadas"),
    @NamedQuery(name = "OrdenTrabajoDetalle.findByNoPmp", query = "SELECT o FROM OrdenTrabajoDetalle o WHERE o.noPmp = :noPmp"),
    @NamedQuery(name = "OrdenTrabajoDetalle.findByFechaRealizado", query = "SELECT o FROM OrdenTrabajoDetalle o WHERE o.fechaRealizado = :fechaRealizado")})
public class OrdenTrabajoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_trabajo_detalle")
    private Integer idOrdenTrabajoDetalle;
    @Lob
    @Column(name = "actividades")
    private String actividades;
    @Basic(optional = false)
    @Column(name = "cont_programadas")
    private int contProgramadas;
    @Basic(optional = false)
    @Column(name = "cont_ejecutadas")
    private int contEjecutadas;
    @Lob
    @Column(name = "pendientes")
    private String pendientes;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "repuestos")
    private String repuestos;
    @Column(name = "no_pmp")
    private String noPmp;
    @Column(name = "fecha_realizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizado;
    @Lob
    @Column(name = "log_responsables")
    private String logResponsables;
    @JoinColumn(name = "id_orden_trabajo", referencedColumnName = "id_orden_trabajo")
    @ManyToOne
    private OrdenTrabajo idOrdenTrabajo;

    public OrdenTrabajoDetalle() {
    }

    public OrdenTrabajoDetalle(Integer idOrdenTrabajoDetalle) {
        this.idOrdenTrabajoDetalle = idOrdenTrabajoDetalle;
    }

    public OrdenTrabajoDetalle(Integer idOrdenTrabajoDetalle, int contProgramadas, int contEjecutadas) {
        this.idOrdenTrabajoDetalle = idOrdenTrabajoDetalle;
        this.contProgramadas = contProgramadas;
        this.contEjecutadas = contEjecutadas;
    }

    public Integer getIdOrdenTrabajoDetalle() {
        return idOrdenTrabajoDetalle;
    }

    public void setIdOrdenTrabajoDetalle(Integer idOrdenTrabajoDetalle) {
        this.idOrdenTrabajoDetalle = idOrdenTrabajoDetalle;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public int getContProgramadas() {
        return contProgramadas;
    }

    public void setContProgramadas(int contProgramadas) {
        this.contProgramadas = contProgramadas;
    }

    public int getContEjecutadas() {
        return contEjecutadas;
    }

    public void setContEjecutadas(int contEjecutadas) {
        this.contEjecutadas = contEjecutadas;
    }

    public String getPendientes() {
        return pendientes;
    }

    public void setPendientes(String pendientes) {
        this.pendientes = pendientes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(String repuestos) {
        this.repuestos = repuestos;
    }

    public String getNoPmp() {
        return noPmp;
    }

    public void setNoPmp(String noPmp) {
        this.noPmp = noPmp;
    }

    public Date getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(Date fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public String getLogResponsables() {
        return logResponsables;
    }

    public void setLogResponsables(String logResponsables) {
        this.logResponsables = logResponsables;
    }

    public OrdenTrabajo getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(OrdenTrabajo idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenTrabajoDetalle != null ? idOrdenTrabajoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenTrabajoDetalle)) {
            return false;
        }
        OrdenTrabajoDetalle other = (OrdenTrabajoDetalle) object;
        if ((this.idOrdenTrabajoDetalle == null && other.idOrdenTrabajoDetalle != null) || (this.idOrdenTrabajoDetalle != null && !this.idOrdenTrabajoDetalle.equals(other.idOrdenTrabajoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OrdenTrabajoDetalle[ idOrdenTrabajoDetalle=" + idOrdenTrabajoDetalle + " ]";
    }
    
}
