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
import javax.persistence.Lob;
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
@Table(name = "bitacora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b")
    , @NamedQuery(name = "Bitacora.findByIdBitacora", query = "SELECT b FROM Bitacora b WHERE b.idBitacora = :idBitacora")
    , @NamedQuery(name = "Bitacora.findByTurno", query = "SELECT b FROM Bitacora b WHERE b.turno = :turno")
    , @NamedQuery(name = "Bitacora.findByFechaInicio", query = "SELECT b FROM Bitacora b WHERE b.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Bitacora.findByFechaFin", query = "SELECT b FROM Bitacora b WHERE b.fechaFin = :fechaFin")
    , @NamedQuery(name = "Bitacora.findByAdjunto", query = "SELECT b FROM Bitacora b WHERE b.adjunto = :adjunto")
    , @NamedQuery(name = "Bitacora.findByCantidadActividadesGeneral", query = "SELECT b FROM Bitacora b WHERE b.cantidadActividadesGeneral = :cantidadActividadesGeneral")
    , @NamedQuery(name = "Bitacora.findByCantidadActividadesReportadas", query = "SELECT b FROM Bitacora b WHERE b.cantidadActividadesReportadas = :cantidadActividadesReportadas")
    , @NamedQuery(name = "Bitacora.findByCantidadCasosSolucionados", query = "SELECT b FROM Bitacora b WHERE b.cantidadCasosSolucionados = :cantidadCasosSolucionados")
    , @NamedQuery(name = "Bitacora.findByCantidadCasosPendientes", query = "SELECT b FROM Bitacora b WHERE b.cantidadCasosPendientes = :cantidadCasosPendientes")
    , @NamedQuery(name = "Bitacora.findByCantidadPendientesSolucionados", query = "SELECT b FROM Bitacora b WHERE b.cantidadPendientesSolucionados = :cantidadPendientesSolucionados")
    , @NamedQuery(name = "Bitacora.findByCantidadPendientes", query = "SELECT b FROM Bitacora b WHERE b.cantidadPendientes = :cantidadPendientes")
    , @NamedQuery(name = "Bitacora.findByMovimientosEquipos", query = "SELECT b FROM Bitacora b WHERE b.movimientosEquipos = :movimientosEquipos")
    , @NamedQuery(name = "Bitacora.findByRevisado", query = "SELECT b FROM Bitacora b WHERE b.revisado = :revisado")
    , @NamedQuery(name = "Bitacora.findByFechaRevision", query = "SELECT b FROM Bitacora b WHERE b.fechaRevision = :fechaRevision")
    , @NamedQuery(name = "Bitacora.findByUsuarioRegistro", query = "SELECT b FROM Bitacora b WHERE b.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Bitacora.findByFechaRegistro", query = "SELECT b FROM Bitacora b WHERE b.fechaRegistro = :fechaRegistro")})
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bitacora")
    private Integer idBitacora;
    @Basic(optional = false)
    @Lob
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "turno")
    private String turno;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "adjunto")
    private String adjunto;
    @Basic(optional = false)
    @Column(name = "cantidad_actividades_general")
    private int cantidadActividadesGeneral;
    @Basic(optional = false)
    @Column(name = "cantidad_actividades_reportadas")
    private int cantidadActividadesReportadas;
    @Basic(optional = false)
    @Column(name = "cantidad_casos_solucionados")
    private int cantidadCasosSolucionados;
    @Basic(optional = false)
    @Column(name = "cantidad_casos_pendientes")
    private int cantidadCasosPendientes;
    @Basic(optional = false)
    @Column(name = "cantidad_pendientes_solucionados")
    private int cantidadPendientesSolucionados;
    @Basic(optional = false)
    @Column(name = "cantidad_pendientes")
    private int cantidadPendientes;
    @Column(name = "movimientos_equipos")
    private Integer movimientosEquipos;
    @Basic(optional = false)
    @Column(name = "revisado")
    private short revisado;
    @Basic(optional = false)
    @Column(name = "fecha_revision")
    private String fechaRevision;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private int usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Bitacora() {
    }

    public Bitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Bitacora(Integer idBitacora, String titulo, String turno, Date fechaInicio, Date fechaFin, String adjunto, int cantidadActividadesGeneral, int cantidadActividadesReportadas, int cantidadCasosSolucionados, int cantidadCasosPendientes, int cantidadPendientesSolucionados, int cantidadPendientes, short revisado, String fechaRevision, String observacion, int usuarioRegistro, Date fechaRegistro) {
        this.idBitacora = idBitacora;
        this.titulo = titulo;
        this.turno = turno;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.adjunto = adjunto;
        this.cantidadActividadesGeneral = cantidadActividadesGeneral;
        this.cantidadActividadesReportadas = cantidadActividadesReportadas;
        this.cantidadCasosSolucionados = cantidadCasosSolucionados;
        this.cantidadCasosPendientes = cantidadCasosPendientes;
        this.cantidadPendientesSolucionados = cantidadPendientesSolucionados;
        this.cantidadPendientes = cantidadPendientes;
        this.revisado = revisado;
        this.fechaRevision = fechaRevision;
        this.observacion = observacion;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public int getCantidadActividadesGeneral() {
        return cantidadActividadesGeneral;
    }

    public void setCantidadActividadesGeneral(int cantidadActividadesGeneral) {
        this.cantidadActividadesGeneral = cantidadActividadesGeneral;
    }

    public int getCantidadActividadesReportadas() {
        return cantidadActividadesReportadas;
    }

    public void setCantidadActividadesReportadas(int cantidadActividadesReportadas) {
        this.cantidadActividadesReportadas = cantidadActividadesReportadas;
    }

    public int getCantidadCasosSolucionados() {
        return cantidadCasosSolucionados;
    }

    public void setCantidadCasosSolucionados(int cantidadCasosSolucionados) {
        this.cantidadCasosSolucionados = cantidadCasosSolucionados;
    }

    public int getCantidadCasosPendientes() {
        return cantidadCasosPendientes;
    }

    public void setCantidadCasosPendientes(int cantidadCasosPendientes) {
        this.cantidadCasosPendientes = cantidadCasosPendientes;
    }

    public int getCantidadPendientesSolucionados() {
        return cantidadPendientesSolucionados;
    }

    public void setCantidadPendientesSolucionados(int cantidadPendientesSolucionados) {
        this.cantidadPendientesSolucionados = cantidadPendientesSolucionados;
    }

    public int getCantidadPendientes() {
        return cantidadPendientes;
    }

    public void setCantidadPendientes(int cantidadPendientes) {
        this.cantidadPendientes = cantidadPendientes;
    }

    public Integer getMovimientosEquipos() {
        return movimientosEquipos;
    }

    public void setMovimientosEquipos(Integer movimientosEquipos) {
        this.movimientosEquipos = movimientosEquipos;
    }

    public short getRevisado() {
        return revisado;
    }

    public void setRevisado(short revisado) {
        this.revisado = revisado;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(int usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Bitacora[ idBitacora=" + idBitacora + " ]";
    }
    
}
