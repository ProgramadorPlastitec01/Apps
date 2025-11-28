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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @NamedQuery(name = "Equipo.findByIdEquipo", query = "SELECT e FROM Equipo e WHERE e.idEquipo = :idEquipo"),
    @NamedQuery(name = "Equipo.findByNombre", query = "SELECT e FROM Equipo e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Equipo.findByMarca", query = "SELECT e FROM Equipo e WHERE e.marca = :marca"),
    @NamedQuery(name = "Equipo.findByTipoModelo", query = "SELECT e FROM Equipo e WHERE e.tipoModelo = :tipoModelo"),
    @NamedQuery(name = "Equipo.findByAnioFabricacion", query = "SELECT e FROM Equipo e WHERE e.anioFabricacion = :anioFabricacion"),
    @NamedQuery(name = "Equipo.findBySerie", query = "SELECT e FROM Equipo e WHERE e.serie = :serie"),
    @NamedQuery(name = "Equipo.findByCodigoInterno", query = "SELECT e FROM Equipo e WHERE e.codigoInterno = :codigoInterno"),
    @NamedQuery(name = "Equipo.findByFechaInstalacion", query = "SELECT e FROM Equipo e WHERE e.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "Equipo.findByProveedor", query = "SELECT e FROM Equipo e WHERE e.proveedor = :proveedor"),
    @NamedQuery(name = "Equipo.findByNumeroContacto", query = "SELECT e FROM Equipo e WHERE e.numeroContacto = :numeroContacto"),
    @NamedQuery(name = "Equipo.findByPaisOrigen", query = "SELECT e FROM Equipo e WHERE e.paisOrigen = :paisOrigen"),
    @NamedQuery(name = "Equipo.findByNumeroMaquina", query = "SELECT e FROM Equipo e WHERE e.numeroMaquina = :numeroMaquina"),
    @NamedQuery(name = "Equipo.findByLongitud", query = "SELECT e FROM Equipo e WHERE e.longitud = :longitud"),
    @NamedQuery(name = "Equipo.findByAncho", query = "SELECT e FROM Equipo e WHERE e.ancho = :ancho"),
    @NamedQuery(name = "Equipo.findByAlto", query = "SELECT e FROM Equipo e WHERE e.alto = :alto"),
    @NamedQuery(name = "Equipo.findByPesoTotal", query = "SELECT e FROM Equipo e WHERE e.pesoTotal = :pesoTotal"),
    @NamedQuery(name = "Equipo.findByUnidadDimensiones", query = "SELECT e FROM Equipo e WHERE e.unidadDimensiones = :unidadDimensiones"),
    @NamedQuery(name = "Equipo.findByLinea", query = "SELECT e FROM Equipo e WHERE e.linea = :linea"),
    @NamedQuery(name = "Equipo.findByLocalizacion", query = "SELECT e FROM Equipo e WHERE e.localizacion = :localizacion"),
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
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "tipo_modelo")
    private String tipoModelo;
    @Basic(optional = false)
    @Column(name = "anio_fabricacion")
    private int anioFabricacion;
    @Basic(optional = false)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @Column(name = "codigo _interno")
    private String codigoInterno;
    @Basic(optional = false)
    @Column(name = "fecha_instalacion")
    @Temporal(TemporalType.DATE)
    private Date fechaInstalacion;
    @Basic(optional = false)
    @Column(name = "proveedor")
    private String proveedor;
    @Basic(optional = false)
    @Column(name = "numero_contacto")
    private int numeroContacto;
    @Basic(optional = false)
    @Column(name = "pais_origen")
    private String paisOrigen;
    @Basic(optional = false)
    @Column(name = "numero_maquina")
    private int numeroMaquina;
    @Basic(optional = false)
    @Column(name = "longitud")
    private long longitud;
    @Basic(optional = false)
    @Column(name = "ancho")
    private long ancho;
    @Basic(optional = false)
    @Column(name = "alto")
    private long alto;
    @Basic(optional = false)
    @Column(name = "peso_total")
    private long pesoTotal;
    @Basic(optional = false)
    @Column(name = "unidad_dimensiones")
    private String unidadDimensiones;
    @Basic(optional = false)
    @Column(name = "linea")
    private String linea;
    @Basic(optional = false)
    @Column(name = "localizacion")
    private String localizacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones_generales")
    private String observacionesGenerales;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Equipo() {
    }

    public Equipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Equipo(Integer idEquipo, String nombre, String marca, String tipoModelo, int anioFabricacion, String serie, String codigoInterno, Date fechaInstalacion, String proveedor, int numeroContacto, String paisOrigen, int numeroMaquina, long longitud, long ancho, long alto, long pesoTotal, String unidadDimensiones, String linea, String localizacion, String observaciones, String observacionesGenerales, int estado, String usuarioRegistro, Date fechaRegistro) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.marca = marca;
        this.tipoModelo = tipoModelo;
        this.anioFabricacion = anioFabricacion;
        this.serie = serie;
        this.codigoInterno = codigoInterno;
        this.fechaInstalacion = fechaInstalacion;
        this.proveedor = proveedor;
        this.numeroContacto = numeroContacto;
        this.paisOrigen = paisOrigen;
        this.numeroMaquina = numeroMaquina;
        this.longitud = longitud;
        this.ancho = ancho;
        this.alto = alto;
        this.pesoTotal = pesoTotal;
        this.unidadDimensiones = unidadDimensiones;
        this.linea = linea;
        this.localizacion = localizacion;
        this.observaciones = observaciones;
        this.observacionesGenerales = observacionesGenerales;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipoModelo() {
        return tipoModelo;
    }

    public void setTipoModelo(String tipoModelo) {
        this.tipoModelo = tipoModelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(int numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public int getNumeroMaquina() {
        return numeroMaquina;
    }

    public void setNumeroMaquina(int numeroMaquina) {
        this.numeroMaquina = numeroMaquina;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public long getAncho() {
        return ancho;
    }

    public void setAncho(long ancho) {
        this.ancho = ancho;
    }

    public long getAlto() {
        return alto;
    }

    public void setAlto(long alto) {
        this.alto = alto;
    }

    public long getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(long pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public String getUnidadDimensiones() {
        return unidadDimensiones;
    }

    public void setUnidadDimensiones(String unidadDimensiones) {
        this.unidadDimensiones = unidadDimensiones;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
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
        return "Entidades.Equipo[ idEquipo=" + idEquipo + " ]";
    }
    
}
