/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "equipo")
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @NamedQuery(name = "Equipo.findByIdEquipo", query = "SELECT e FROM Equipo e WHERE e.idEquipo = :idEquipo"),
    @NamedQuery(name = "Equipo.findByEquipo", query = "SELECT e FROM Equipo e WHERE e.equipo = :equipo"),
    @NamedQuery(name = "Equipo.findByMarca", query = "SELECT e FROM Equipo e WHERE e.marca = :marca"),
    @NamedQuery(name = "Equipo.findByModelo", query = "SELECT e FROM Equipo e WHERE e.modelo = :modelo"),
    @NamedQuery(name = "Equipo.findBySerie", query = "SELECT e FROM Equipo e WHERE e.serie = :serie"),
    @NamedQuery(name = "Equipo.findByDescripcion", query = "SELECT e FROM Equipo e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Equipo.findByAnio", query = "SELECT e FROM Equipo e WHERE e.anio = :anio"),
    @NamedQuery(name = "Equipo.findByUbicacion", query = "SELECT e FROM Equipo e WHERE e.ubicacion = :ubicacion"),
    @NamedQuery(name = "Equipo.findByVoltaje", query = "SELECT e FROM Equipo e WHERE e.voltaje = :voltaje"),
    @NamedQuery(name = "Equipo.findByCapacidad", query = "SELECT e FROM Equipo e WHERE e.capacidad = :capacidad"),
    @NamedQuery(name = "Equipo.findByHorometroPmp", query = "SELECT e FROM Equipo e WHERE e.horometroPmp = :horometroPmp"),
    @NamedQuery(name = "Equipo.findByFechaPmp", query = "SELECT e FROM Equipo e WHERE e.fechaPmp = :fechaPmp"),
    @NamedQuery(name = "Equipo.findByHorometroActual", query = "SELECT e FROM Equipo e WHERE e.horometroActual = :horometroActual"),
    @NamedQuery(name = "Equipo.findByFechaActual", query = "SELECT e FROM Equipo e WHERE e.fechaActual = :fechaActual"),
    @NamedQuery(name = "Equipo.findByEstado", query = "SELECT e FROM Equipo e WHERE e.estado = :estado"),
    @NamedQuery(name = "Equipo.findByUsuarioRegistro", query = "SELECT e FROM Equipo e WHERE e.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Equipo.findByFechaRegistro", query = "SELECT e FROM Equipo e WHERE e.fechaRegistro = :fechaRegistro")})
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Basic(optional = false)
    @Column(name = "equipo")
    private String equipo;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "voltaje")
    private String voltaje;
    @Basic(optional = false)
    @Column(name = "capacidad")
    private String capacidad;
    @Basic(optional = false)
    @Column(name = "horometro_pmp")
    private int horometroPmp;
    @Basic(optional = false)
    @Column(name = "fecha_pmp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPmp;
    @Basic(optional = false)
    @Column(name = "horometro_actual")
    private int horometroActual;
    @Basic(optional = false)
    @Column(name = "fecha_actual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActual;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_tipo_equipo", referencedColumnName = "id_tipo_equipo")
    @ManyToOne(optional = false)
    private TipoEquipo tipoEquipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipo")
    private Collection<OrdenTrabajo> ordenTrabajoCollection;

    public Equipo() {
    }

    public Equipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Equipo(Integer idEquipo, String equipo, String marca, String modelo, String serie, String descripcion, int anio, String ubicacion, String voltaje, String capacidad, int horometroPmp, Date fechaPmp, int horometroActual, Date fechaActual, String estado, String usuarioRegistro, Date fechaRegistro) {
        this.idEquipo = idEquipo;
        this.equipo = equipo;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.descripcion = descripcion;
        this.anio = anio;
        this.ubicacion = ubicacion;
        this.voltaje = voltaje;
        this.capacidad = capacidad;
        this.horometroPmp = horometroPmp;
        this.fechaPmp = fechaPmp;
        this.horometroActual = horometroActual;
        this.fechaActual = fechaActual;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(String voltaje) {
        this.voltaje = voltaje;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public int getHorometroPmp() {
        return horometroPmp;
    }

    public void setHorometroPmp(int horometroPmp) {
        this.horometroPmp = horometroPmp;
    }

    public Date getFechaPmp() {
        return fechaPmp;
    }

    public void setFechaPmp(Date fechaPmp) {
        this.fechaPmp = fechaPmp;
    }

    public int getHorometroActual() {
        return horometroActual;
    }

    public void setHorometroActual(int horometroActual) {
        this.horometroActual = horometroActual;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Collection<OrdenTrabajo> getOrdenTrabajoCollection() {
        return ordenTrabajoCollection;
    }

    public void setOrdenTrabajoCollection(Collection<OrdenTrabajo> ordenTrabajoCollection) {
        this.ordenTrabajoCollection = ordenTrabajoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Equipo[idEquipo=" + idEquipo + "]";
    }

}
