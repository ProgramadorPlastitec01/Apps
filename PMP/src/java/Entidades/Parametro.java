/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "parametro")
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
    @NamedQuery(name = "Parametro.findByIdParametro", query = "SELECT p FROM Parametro p WHERE p.idParametro = :idParametro"),
    @NamedQuery(name = "Parametro.findByParametro", query = "SELECT p FROM Parametro p WHERE p.parametro = :parametro"),
    @NamedQuery(name = "Parametro.findByEspecificacion", query = "SELECT p FROM Parametro p WHERE p.especificacion = :especificacion"),
    @NamedQuery(name = "Parametro.findByEspecificacionMax", query = "SELECT p FROM Parametro p WHERE p.especificacionMax = :especificacionMax"),
    @NamedQuery(name = "Parametro.findByEspecificacionMin", query = "SELECT p FROM Parametro p WHERE p.especificacionMin = :especificacionMin"),
    @NamedQuery(name = "Parametro.findByValidador", query = "SELECT p FROM Parametro p WHERE p.validador = :validador"),
    @NamedQuery(name = "Parametro.findByTomas", query = "SELECT p FROM Parametro p WHERE p.tomas = :tomas"),
    @NamedQuery(name = "Parametro.findByEstado", query = "SELECT p FROM Parametro p WHERE p.estado = :estado"),
    @NamedQuery(name = "Parametro.findByUsuarioRegistro", query = "SELECT p FROM Parametro p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Parametro.findByFechaRegistro", query = "SELECT p FROM Parametro p WHERE p.fechaRegistro = :fechaRegistro")})
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Basic(optional = false)
    @Column(name = "parametro")
    private String parametro;
    @Basic(optional = false)
    @Column(name = "especificacion")
    private double especificacion;
    @Basic(optional = false)
    @Column(name = "especificacion_max")
    private double especificacionMax;
    @Basic(optional = false)
    @Column(name = "especificacion_min")
    private double especificacionMin;
    @Basic(optional = false)
    @Column(name = "validador")
    private String validador;
    @Basic(optional = false)
    @Column(name = "tomas")
    private int tomas;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_unidad_medida", referencedColumnName = "id_unidad_medida")
    @ManyToOne(optional = false)
    private UnidadMedida unidadMedida;
    @JoinColumn(name = "id_tipo_equipo", referencedColumnName = "id_tipo_equipo")
    @ManyToOne(optional = false)
    private TipoEquipo tipoEquipo;
    @JoinColumn(name = "id_instrumento", referencedColumnName = "id_instrumento")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    @OneToMany(mappedBy = "parametro")
    private Collection<ParametroOrden> parametroOrdenCollection;

    public Parametro() {
    }

    public Parametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Parametro(Integer idParametro, String parametro, double especificacion, double especificacionMax, double especificacionMin, String validador, int tomas, short estado, String usuarioRegistro, Date fechaRegistro) {
        this.idParametro = idParametro;
        this.parametro = parametro;
        this.especificacion = especificacion;
        this.especificacionMax = especificacionMax;
        this.especificacionMin = especificacionMin;
        this.validador = validador;
        this.tomas = tomas;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public double getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(double especificacion) {
        this.especificacion = especificacion;
    }

    public double getEspecificacionMax() {
        return especificacionMax;
    }

    public void setEspecificacionMax(double especificacionMax) {
        this.especificacionMax = especificacionMax;
    }

    public double getEspecificacionMin() {
        return especificacionMin;
    }

    public void setEspecificacionMin(double especificacionMin) {
        this.especificacionMin = especificacionMin;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public int getTomas() {
        return tomas;
    }

    public void setTomas(int tomas) {
        this.tomas = tomas;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
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

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public Collection<ParametroOrden> getParametroOrdenCollection() {
        return parametroOrdenCollection;
    }

    public void setParametroOrdenCollection(Collection<ParametroOrden> parametroOrdenCollection) {
        this.parametroOrdenCollection = parametroOrdenCollection;
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
