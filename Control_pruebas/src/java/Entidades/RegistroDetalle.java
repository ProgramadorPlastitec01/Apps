/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro_detalle")
@NamedQueries({
    @NamedQuery(name = "RegistroDetalle.findAll", query = "SELECT r FROM RegistroDetalle r"),
    @NamedQuery(name = "RegistroDetalle.findByIdRegistroDetalle", query = "SELECT r FROM RegistroDetalle r WHERE r.idRegistroDetalle = :idRegistroDetalle"),
    @NamedQuery(name = "RegistroDetalle.findByProducto", query = "SELECT r FROM RegistroDetalle r WHERE r.producto = :producto"),
    @NamedQuery(name = "RegistroDetalle.findByLote", query = "SELECT r FROM RegistroDetalle r WHERE r.lote = :lote"),
    @NamedQuery(name = "RegistroDetalle.findByMuestra", query = "SELECT r FROM RegistroDetalle r WHERE r.muestra = :muestra"),
    @NamedQuery(name = "RegistroDetalle.findByHoraToma", query = "SELECT r FROM RegistroDetalle r WHERE r.horaToma = :horaToma"),
    @NamedQuery(name = "RegistroDetalle.findByHoraMuestra", query = "SELECT r FROM RegistroDetalle r WHERE r.horaMuestra = :horaMuestra"),
    @NamedQuery(name = "RegistroDetalle.findByHoraResultado", query = "SELECT r FROM RegistroDetalle r WHERE r.horaResultado = :horaResultado"),
    @NamedQuery(name = "RegistroDetalle.findByResultado", query = "SELECT r FROM RegistroDetalle r WHERE r.resultado = :resultado"),
    @NamedQuery(name = "RegistroDetalle.findByOtroResultado", query = "SELECT r FROM RegistroDetalle r WHERE r.otroResultado = :otroResultado"),
    @NamedQuery(name = "RegistroDetalle.findByResponsable", query = "SELECT r FROM RegistroDetalle r WHERE r.responsable = :responsable"),
    @NamedQuery(name = "RegistroDetalle.findByUsuarioRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroDetalle.findByFechaRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_detalle")
    private Integer idRegistroDetalle;
    @Basic(optional = false)
    @Column(name = "producto")
    private String producto;
    @Basic(optional = false)
    @Column(name = "lote")
    private String lote;
    @Basic(optional = false)
    @Column(name = "muestra")
    private int muestra;
    @Basic(optional = false)
    @Column(name = "hora_toma")
    @Temporal(TemporalType.TIME)
    private Date horaToma;
    @Basic(optional = false)
    @Column(name = "hora_muestra")
    @Temporal(TemporalType.TIME)
    private Date horaMuestra;
    @Basic(optional = false)
    @Column(name = "hora_resultado")
    @Temporal(TemporalType.TIME)
    private Date horaResultado;
    @Basic(optional = false)
    @Lob
    @Column(name = "electrodo_bocas")
    private String electrodoBocas;
    @Basic(optional = false)
    @Lob
    @Column(name = "electrodo_colas")
    private String electrodoColas;
    @Basic(optional = false)
    @Column(name = "resultado")
    private int resultado;
    @Basic(optional = false)
    @Column(name = "otro_resultado")
    private int otroResultado;
    @Basic(optional = false)
    @Column(name = "responsable")
    private String responsable;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro registro;
    @JoinColumn(name = "id_prueba", referencedColumnName = "id_prueba")
    @ManyToOne(optional = false)
    private Prueba prueba;
    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso")
    @ManyToOne(optional = false)
    private Proceso proceso;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea")
    @ManyToOne(optional = false)
    private Linea linea;

    public RegistroDetalle() {
    }

    public RegistroDetalle(Integer idRegistroDetalle) {
        this.idRegistroDetalle = idRegistroDetalle;
    }

    public RegistroDetalle(Integer idRegistroDetalle, String producto, String lote, int muestra, Date horaToma, Date horaMuestra, Date horaResultado, String electrodoBocas, String electrodoColas, int resultado, int otroResultado, String responsable, String observacion, String usuarioRegistro, Date fechaRegistro) {
        this.idRegistroDetalle = idRegistroDetalle;
        this.producto = producto;
        this.lote = lote;
        this.muestra = muestra;
        this.horaToma = horaToma;
        this.horaMuestra = horaMuestra;
        this.horaResultado = horaResultado;
        this.electrodoBocas = electrodoBocas;
        this.electrodoColas = electrodoColas;
        this.resultado = resultado;
        this.otroResultado = otroResultado;
        this.responsable = responsable;
        this.observacion = observacion;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRegistroDetalle() {
        return idRegistroDetalle;
    }

    public void setIdRegistroDetalle(Integer idRegistroDetalle) {
        this.idRegistroDetalle = idRegistroDetalle;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getMuestra() {
        return muestra;
    }

    public void setMuestra(int muestra) {
        this.muestra = muestra;
    }

    public Date getHoraToma() {
        return horaToma;
    }

    public void setHoraToma(Date horaToma) {
        this.horaToma = horaToma;
    }

    public Date getHoraMuestra() {
        return horaMuestra;
    }

    public void setHoraMuestra(Date horaMuestra) {
        this.horaMuestra = horaMuestra;
    }

    public Date getHoraResultado() {
        return horaResultado;
    }

    public void setHoraResultado(Date horaResultado) {
        this.horaResultado = horaResultado;
    }

    public String getElectrodoBocas() {
        return electrodoBocas;
    }

    public void setElectrodoBocas(String electrodoBocas) {
        this.electrodoBocas = electrodoBocas;
    }

    public String getElectrodoColas() {
        return electrodoColas;
    }

    public void setElectrodoColas(String electrodoColas) {
        this.electrodoColas = electrodoColas;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public int getOtroResultado() {
        return otroResultado;
    }

    public void setOtroResultado(int otroResultado) {
        this.otroResultado = otroResultado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroDetalle != null ? idRegistroDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDetalle)) {
            return false;
        }
        RegistroDetalle other = (RegistroDetalle) object;
        if ((this.idRegistroDetalle == null && other.idRegistroDetalle != null) || (this.idRegistroDetalle != null && !this.idRegistroDetalle.equals(other.idRegistroDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroDetalle[idRegistroDetalle=" + idRegistroDetalle + "]";
    }

}
