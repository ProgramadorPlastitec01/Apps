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
import javax.persistence.Lob;
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
@Table(name = "prueba")
@NamedQueries({
    @NamedQuery(name = "Prueba.findAll", query = "SELECT p FROM Prueba p"),
    @NamedQuery(name = "Prueba.findByIdPrueba", query = "SELECT p FROM Prueba p WHERE p.idPrueba = :idPrueba"),
    @NamedQuery(name = "Prueba.findByNombre", query = "SELECT p FROM Prueba p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Prueba.findByFrecuencia", query = "SELECT p FROM Prueba p WHERE p.frecuencia = :frecuencia"),
    @NamedQuery(name = "Prueba.findByEstado", query = "SELECT p FROM Prueba p WHERE p.estado = :estado"),
    @NamedQuery(name = "Prueba.findByUsuarioRegistro", query = "SELECT p FROM Prueba p WHERE p.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Prueba.findByFechaRegistro", query = "SELECT p FROM Prueba p WHERE p.fechaRegistro = :fechaRegistro")})
public class Prueba implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prueba")
    private Integer idPrueba;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "frecuencia")
    private int frecuencia;
    @Basic(optional = false)
    @Lob
    @Column(name = "aplica")
    private String aplica;
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
    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso")
    @ManyToOne(optional = false)
    private Proceso proceso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prueba")
    private Collection<RegistroDetalle> registroDetalleCollection;

    public Prueba() {
    }

    public Prueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    public Prueba(Integer idPrueba, String nombre, int frecuencia, String aplica, short estado, String usuarioRegistro, Date fechaRegistro) {
        this.idPrueba = idPrueba;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.aplica = aplica;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getAplica() {
        return aplica;
    }

    public void setAplica(String aplica) {
        this.aplica = aplica;
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

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Collection<RegistroDetalle> getRegistroDetalleCollection() {
        return registroDetalleCollection;
    }

    public void setRegistroDetalleCollection(Collection<RegistroDetalle> registroDetalleCollection) {
        this.registroDetalleCollection = registroDetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrueba != null ? idPrueba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prueba)) {
            return false;
        }
        Prueba other = (Prueba) object;
        if ((this.idPrueba == null && other.idPrueba != null) || (this.idPrueba != null && !this.idPrueba.equals(other.idPrueba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Prueba[idPrueba=" + idPrueba + "]";
    }

}
