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
@Table(name = "actividad_reportada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadReportada.findAll", query = "SELECT a FROM ActividadReportada a")
    , @NamedQuery(name = "ActividadReportada.findByIdActividadReportada", query = "SELECT a FROM ActividadReportada a WHERE a.idActividadReportada = :idActividadReportada")
    , @NamedQuery(name = "ActividadReportada.findByReportante", query = "SELECT a FROM ActividadReportada a WHERE a.reportante = :reportante")
    , @NamedQuery(name = "ActividadReportada.findByIdEquipo", query = "SELECT a FROM ActividadReportada a WHERE a.idEquipo = :idEquipo")
    , @NamedQuery(name = "ActividadReportada.findByIdTipoSoporte", query = "SELECT a FROM ActividadReportada a WHERE a.idTipoSoporte = :idTipoSoporte")
    , @NamedQuery(name = "ActividadReportada.findByIdAplicativo", query = "SELECT a FROM ActividadReportada a WHERE a.idAplicativo = :idAplicativo")
    , @NamedQuery(name = "ActividadReportada.findByFechaReportante", query = "SELECT a FROM ActividadReportada a WHERE a.fechaReportante = :fechaReportante")
    , @NamedQuery(name = "ActividadReportada.findByFechaEjecucion", query = "SELECT a FROM ActividadReportada a WHERE a.fechaEjecucion = :fechaEjecucion")
    , @NamedQuery(name = "ActividadReportada.findByFechaTerminacion", query = "SELECT a FROM ActividadReportada a WHERE a.fechaTerminacion = :fechaTerminacion")
    , @NamedQuery(name = "ActividadReportada.findByBitacora", query = "SELECT a FROM ActividadReportada a WHERE a.bitacora = :bitacora")
    , @NamedQuery(name = "ActividadReportada.findByFechaRegistro", query = "SELECT a FROM ActividadReportada a WHERE a.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "ActividadReportada.findByTiempoParada", query = "SELECT a FROM ActividadReportada a WHERE a.tiempoParada = :tiempoParada")
    , @NamedQuery(name = "ActividadReportada.findByParadaProduccion", query = "SELECT a FROM ActividadReportada a WHERE a.paradaProduccion = :paradaProduccion")})
public class ActividadReportada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_reportada")
    private Integer idActividadReportada;
    @Column(name = "reportante")
    private String reportante;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "id_tipo_soporte")
    private Integer idTipoSoporte;
    @Column(name = "id_aplicativo")
    private Integer idAplicativo;
    @Column(name = "fecha_reportante")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReportante;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminacion;
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @Lob
    @Column(name = "solucion")
    private String solucion;
    @Column(name = "bitacora")
    private Short bitacora;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "Tiempo_Parada")
    private int tiempoParada;
    @Basic(optional = false)
    @Column(name = "Parada_Produccion")
    private int paradaProduccion;
    @JoinColumn(name = "usuario_registro", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioRegistro;

    public ActividadReportada() {
    }

    public ActividadReportada(Integer idActividadReportada) {
        this.idActividadReportada = idActividadReportada;
    }

    public ActividadReportada(Integer idActividadReportada, int tiempoParada, int paradaProduccion) {
        this.idActividadReportada = idActividadReportada;
        this.tiempoParada = tiempoParada;
        this.paradaProduccion = paradaProduccion;
    }

    public Integer getIdActividadReportada() {
        return idActividadReportada;
    }

    public void setIdActividadReportada(Integer idActividadReportada) {
        this.idActividadReportada = idActividadReportada;
    }

    public String getReportante() {
        return reportante;
    }

    public void setReportante(String reportante) {
        this.reportante = reportante;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getIdTipoSoporte() {
        return idTipoSoporte;
    }

    public void setIdTipoSoporte(Integer idTipoSoporte) {
        this.idTipoSoporte = idTipoSoporte;
    }

    public Integer getIdAplicativo() {
        return idAplicativo;
    }

    public void setIdAplicativo(Integer idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public Date getFechaReportante() {
        return fechaReportante;
    }

    public void setFechaReportante(Date fechaReportante) {
        this.fechaReportante = fechaReportante;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Short getBitacora() {
        return bitacora;
    }

    public void setBitacora(Short bitacora) {
        this.bitacora = bitacora;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getTiempoParada() {
        return tiempoParada;
    }

    public void setTiempoParada(int tiempoParada) {
        this.tiempoParada = tiempoParada;
    }

    public int getParadaProduccion() {
        return paradaProduccion;
    }

    public void setParadaProduccion(int paradaProduccion) {
        this.paradaProduccion = paradaProduccion;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadReportada != null ? idActividadReportada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadReportada)) {
            return false;
        }
        ActividadReportada other = (ActividadReportada) object;
        if ((this.idActividadReportada == null && other.idActividadReportada != null) || (this.idActividadReportada != null && !this.idActividadReportada.equals(other.idActividadReportada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ActividadReportada[ idActividadReportada=" + idActividadReportada + " ]";
    }
    
}
