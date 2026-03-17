/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s")
    , @NamedQuery(name = "Solicitud.findByIdSolicitud", query = "SELECT s FROM Solicitud s WHERE s.idSolicitud = :idSolicitud")
    , @NamedQuery(name = "Solicitud.findByFechaIngreso", query = "SELECT s FROM Solicitud s WHERE s.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "Solicitud.findByIdUsuarios", query = "SELECT s FROM Solicitud s WHERE s.idUsuarios = :idUsuarios")
    , @NamedQuery(name = "Solicitud.findByNumeroSolicitud", query = "SELECT s FROM Solicitud s WHERE s.numeroSolicitud = :numeroSolicitud")
    , @NamedQuery(name = "Solicitud.findByPrioridad", query = "SELECT s FROM Solicitud s WHERE s.prioridad = :prioridad")
    , @NamedQuery(name = "Solicitud.findByFicha", query = "SELECT s FROM Solicitud s WHERE s.ficha = :ficha")
    , @NamedQuery(name = "Solicitud.findByIdplano", query = "SELECT s FROM Solicitud s WHERE s.idplano = :idplano")
    , @NamedQuery(name = "Solicitud.findByPieza", query = "SELECT s FROM Solicitud s WHERE s.pieza = :pieza")
    , @NamedQuery(name = "Solicitud.findByCantidad", query = "SELECT s FROM Solicitud s WHERE s.cantidad = :cantidad")
    , @NamedQuery(name = "Solicitud.findByEstado", query = "SELECT s FROM Solicitud s WHERE s.estado = :estado")
    , @NamedQuery(name = "Solicitud.findByFechaFinSolicitud", query = "SELECT s FROM Solicitud s WHERE s.fechaFinSolicitud = :fechaFinSolicitud")
    , @NamedQuery(name = "Solicitud.findByIdPendiente", query = "SELECT s FROM Solicitud s WHERE s.idPendiente = :idPendiente")
    , @NamedQuery(name = "Solicitud.findByTipo", query = "SELECT s FROM Solicitud s WHERE s.tipo = :tipo")})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSolicitud")
    private Integer idSolicitud;
    @Basic(optional = false)
    @Column(name = "Fecha_Ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "idUsuarios")
    private int idUsuarios;
    @Basic(optional = false)
    @Column(name = "Numero_Solicitud")
    private String numeroSolicitud;
    @Basic(optional = false)
    @Column(name = "Prioridad")
    private String prioridad;
    @Basic(optional = false)
    @Column(name = "Ficha")
    private int ficha;
    @Basic(optional = false)
    @Column(name = "idplano")
    private int idplano;
    @Basic(optional = false)
    @Column(name = "Pieza")
    private String pieza;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private String cantidad;
    @Basic(optional = false)
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Fecha_Fin_Solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaFinSolicitud;
    @Lob
    @Column(name = "maquina_programada")
    private String maquinaProgramada;
    @Lob
    @Column(name = "cantidad_programada")
    private String cantidadProgramada;
    @Column(name = "id_pendiente")
    private Integer idPendiente;
    @Column(name = "Tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitud")
    private Collection<Seguimiento> seguimientoCollection;
    @OneToMany(mappedBy = "idSolicitud")
    private Collection<Movimientos> movimientosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitud")
    private Collection<Registro> registroCollection;

    public Solicitud() {
    }

    public Solicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Solicitud(Integer idSolicitud, Date fechaIngreso, int idUsuarios, String numeroSolicitud, String prioridad, int ficha, int idplano, String pieza, String cantidad, String descripcion, int estado) {
        this.idSolicitud = idSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.idUsuarios = idUsuarios;
        this.numeroSolicitud = numeroSolicitud;
        this.prioridad = prioridad;
        this.ficha = ficha;
        this.idplano = idplano;
        this.pieza = pieza;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getFicha() {
        return ficha;
    }

    public void setFicha(int ficha) {
        this.ficha = ficha;
    }

    public int getIdplano() {
        return idplano;
    }

    public void setIdplano(int idplano) {
        this.idplano = idplano;
    }

    public String getPieza() {
        return pieza;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaFinSolicitud() {
        return fechaFinSolicitud;
    }

    public void setFechaFinSolicitud(Date fechaFinSolicitud) {
        this.fechaFinSolicitud = fechaFinSolicitud;
    }

    public String getMaquinaProgramada() {
        return maquinaProgramada;
    }

    public void setMaquinaProgramada(String maquinaProgramada) {
        this.maquinaProgramada = maquinaProgramada;
    }

    public String getCantidadProgramada() {
        return cantidadProgramada;
    }

    public void setCantidadProgramada(String cantidadProgramada) {
        this.cantidadProgramada = cantidadProgramada;
    }

    public Integer getIdPendiente() {
        return idPendiente;
    }

    public void setIdPendiente(Integer idPendiente) {
        this.idPendiente = idPendiente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Seguimiento> getSeguimientoCollection() {
        return seguimientoCollection;
    }

    public void setSeguimientoCollection(Collection<Seguimiento> seguimientoCollection) {
        this.seguimientoCollection = seguimientoCollection;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }

    @XmlTransient
    public Collection<Registro> getRegistroCollection() {
        return registroCollection;
    }

    public void setRegistroCollection(Collection<Registro> registroCollection) {
        this.registroCollection = registroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Solicitud[ idSolicitud=" + idSolicitud + " ]";
    }
    
}
