package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "parametro")
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
    @NamedQuery(name = "Parametro.findByIdParametro", query = "SELECT p FROM Parametro p WHERE p.idParametro = :idParametro"),
    @NamedQuery(name = "Parametro.findByNombre", query = "SELECT p FROM Parametro p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Parametro.findByFrecuencia", query = "SELECT p FROM Parametro p WHERE p.frecuencia = :frecuencia"),
    @NamedQuery(name = "Parametro.findByTipoDato", query = "SELECT p FROM Parametro p WHERE p.tipoDato = :tipoDato"),
    @NamedQuery(name = "Parametro.findByComparador", query = "SELECT p FROM Parametro p WHERE p.comparador = :comparador"),
    @NamedQuery(name = "Parametro.findByResponsable", query = "SELECT p FROM Parametro p WHERE p.responsable = :responsable"),
    @NamedQuery(name = "Parametro.findByEstado", query = "SELECT p FROM Parametro p WHERE p.estado = :estado"),
    @NamedQuery(name = "Parametro.findByPosicion", query = "SELECT p FROM Parametro p WHERE p.posicion = :posicion"),
    @NamedQuery(name = "Parametro.findByUsuarioRegistro", query = "SELECT p FROM Parametro p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Parametro.findByFechaRegistro", query = "SELECT p FROM Parametro p WHERE p.fechaRegistro = :fechaRegistro")})
public class Parametro implements Serializable {

    @OneToMany(mappedBy = "idParametro")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @Column(name = "tipo_dato")
    private String tipoDato;
    @Column(name = "comparador")
    private String comparador;
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "posicion")
    private Integer posicion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "parametro")
    private Collection<RegistroFrecuenciaMediaHora> registroFrecuenciaMediaHoraCollection;
    @JoinColumn(name = "id_tipo_parametro", referencedColumnName = "id_tipo_parametro")
    @ManyToOne
    private TipoParametro tipoParametro;
    @JoinColumn(name = "id_tipo_linea", referencedColumnName = "id_tipo_linea")
    @ManyToOne
    private TipoLinea tipoLinea;

    public Parametro() {
    }

    public Parametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getComparador() {
        return comparador;
    }

    public void setComparador(String comparador) {
        this.comparador = comparador;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
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

    public Collection<RegistroFrecuenciaMediaHora> getRegistroFrecuenciaMediaHoraCollection() {
        return registroFrecuenciaMediaHoraCollection;
    }

    public void setRegistroFrecuenciaMediaHoraCollection(Collection<RegistroFrecuenciaMediaHora> registroFrecuenciaMediaHoraCollection) {
        this.registroFrecuenciaMediaHoraCollection = registroFrecuenciaMediaHoraCollection;
    }

    public TipoParametro getTipoParametro() {
        return tipoParametro;
    }

    public void setTipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    public TipoLinea getTipoLinea() {
        return tipoLinea;
    }

    public void setTipoLinea(TipoLinea tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametro[idParametro=" + idParametro + "]";
    }

}
