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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "caso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caso.findAll", query = "SELECT c FROM Caso c")
    , @NamedQuery(name = "Caso.findByIdCaso", query = "SELECT c FROM Caso c WHERE c.idCaso = :idCaso")
    , @NamedQuery(name = "Caso.findByFechaEnvio", query = "SELECT c FROM Caso c WHERE c.fechaEnvio = :fechaEnvio")
    , @NamedQuery(name = "Caso.findByPrioridad", query = "SELECT c FROM Caso c WHERE c.prioridad = :prioridad")
    , @NamedQuery(name = "Caso.findByAdjuntoEnvio", query = "SELECT c FROM Caso c WHERE c.adjuntoEnvio = :adjuntoEnvio")
    , @NamedQuery(name = "Caso.findByFechaEjecucion", query = "SELECT c FROM Caso c WHERE c.fechaEjecucion = :fechaEjecucion")
    , @NamedQuery(name = "Caso.findByFechaSolucion", query = "SELECT c FROM Caso c WHERE c.fechaSolucion = :fechaSolucion")
    , @NamedQuery(name = "Caso.findBySolucion", query = "SELECT c FROM Caso c WHERE c.solucion = :solucion")
    , @NamedQuery(name = "Caso.findByIdEquipo", query = "SELECT c FROM Caso c WHERE c.idEquipo = :idEquipo")
    , @NamedQuery(name = "Caso.findByAdjuntoSolucion", query = "SELECT c FROM Caso c WHERE c.adjuntoSolucion = :adjuntoSolucion")
    , @NamedQuery(name = "Caso.findByBitacora", query = "SELECT c FROM Caso c WHERE c.bitacora = :bitacora")})
public class Caso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_caso")
    private Integer idCaso;
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Lob
    @Column(name = "solicitud")
    private String solicitud;
    @Column(name = "prioridad")
    private String prioridad;
    @Column(name = "adjunto_envio")
    private String adjuntoEnvio;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "fecha_solucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolucion;
    @Column(name = "solucion")
    private String solucion;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "adjunto_solucion")
    private String adjuntoSolucion;
    @Column(name = "bitacora")
    private Short bitacora;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area idArea;
    @JoinColumn(name = "id_reportante", referencedColumnName = "id_reportante")
    @ManyToOne
    private Reportante idReportante;
    @JoinColumn(name = "id_tipo_soporte", referencedColumnName = "id_tipo_soporte")
    @ManyToOne
    private TipoSoporte idTipoSoporte;
    @JoinColumn(name = "id_tecnico_asignado", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idTecnicoAsignado;
    @JoinColumn(name = "id_tecnico_solucion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idTecnicoSolucion;

    public Caso() {
    }

    public Caso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getAdjuntoEnvio() {
        return adjuntoEnvio;
    }

    public void setAdjuntoEnvio(String adjuntoEnvio) {
        this.adjuntoEnvio = adjuntoEnvio;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getAdjuntoSolucion() {
        return adjuntoSolucion;
    }

    public void setAdjuntoSolucion(String adjuntoSolucion) {
        this.adjuntoSolucion = adjuntoSolucion;
    }

    public Short getBitacora() {
        return bitacora;
    }

    public void setBitacora(Short bitacora) {
        this.bitacora = bitacora;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Reportante getIdReportante() {
        return idReportante;
    }

    public void setIdReportante(Reportante idReportante) {
        this.idReportante = idReportante;
    }

    public TipoSoporte getIdTipoSoporte() {
        return idTipoSoporte;
    }

    public void setIdTipoSoporte(TipoSoporte idTipoSoporte) {
        this.idTipoSoporte = idTipoSoporte;
    }

    public Usuario getIdTecnicoAsignado() {
        return idTecnicoAsignado;
    }

    public void setIdTecnicoAsignado(Usuario idTecnicoAsignado) {
        this.idTecnicoAsignado = idTecnicoAsignado;
    }

    public Usuario getIdTecnicoSolucion() {
        return idTecnicoSolucion;
    }

    public void setIdTecnicoSolucion(Usuario idTecnicoSolucion) {
        this.idTecnicoSolucion = idTecnicoSolucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaso != null ? idCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caso)) {
            return false;
        }
        Caso other = (Caso) object;
        if ((this.idCaso == null && other.idCaso != null) || (this.idCaso != null && !this.idCaso.equals(other.idCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Caso[ idCaso=" + idCaso + " ]";
    }
    
}
