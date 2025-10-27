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
@Table(name = "pendiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pendiente.findAll", query = "SELECT p FROM Pendiente p")
    , @NamedQuery(name = "Pendiente.findByIdPendiente", query = "SELECT p FROM Pendiente p WHERE p.idPendiente = :idPendiente")
    , @NamedQuery(name = "Pendiente.findByRevisado", query = "SELECT p FROM Pendiente p WHERE p.revisado = :revisado")
    , @NamedQuery(name = "Pendiente.findByFechaRegistro", query = "SELECT p FROM Pendiente p WHERE p.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Pendiente.findByFechaSolucion", query = "SELECT p FROM Pendiente p WHERE p.fechaSolucion = :fechaSolucion")
    , @NamedQuery(name = "Pendiente.findByBitacora", query = "SELECT p FROM Pendiente p WHERE p.bitacora = :bitacora")
    , @NamedQuery(name = "Pendiente.findByAsunto", query = "SELECT p FROM Pendiente p WHERE p.asunto = :asunto")})
public class Pendiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pendiente")
    private Integer idPendiente;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "solucion")
    private String solucion;
    @Column(name = "revisado")
    private Short revisado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_solucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolucion;
    @Column(name = "bitacora")
    private Short bitacora;
    @Column(name = "asunto")
    private String asunto;
    @JoinColumn(name = "id_usuario_envia", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuarioEnvia;
    @JoinColumn(name = "id_usuario_recibe", referencedColumnName = "id_rol")
    @ManyToOne
    private Rol idUsuarioRecibe;
    @JoinColumn(name = "id_usuario_solucion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuarioSolucion;
    @JoinColumn(name = "id_usuario_revisa", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuarioRevisa;

    public Pendiente() {
    }

    public Pendiente(Integer idPendiente) {
        this.idPendiente = idPendiente;
    }

    public Integer getIdPendiente() {
        return idPendiente;
    }

    public void setIdPendiente(Integer idPendiente) {
        this.idPendiente = idPendiente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Short getRevisado() {
        return revisado;
    }

    public void setRevisado(Short revisado) {
        this.revisado = revisado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public Short getBitacora() {
        return bitacora;
    }

    public void setBitacora(Short bitacora) {
        this.bitacora = bitacora;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Usuario getIdUsuarioEnvia() {
        return idUsuarioEnvia;
    }

    public void setIdUsuarioEnvia(Usuario idUsuarioEnvia) {
        this.idUsuarioEnvia = idUsuarioEnvia;
    }

    public Rol getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(Rol idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public Usuario getIdUsuarioSolucion() {
        return idUsuarioSolucion;
    }

    public void setIdUsuarioSolucion(Usuario idUsuarioSolucion) {
        this.idUsuarioSolucion = idUsuarioSolucion;
    }

    public Usuario getIdUsuarioRevisa() {
        return idUsuarioRevisa;
    }

    public void setIdUsuarioRevisa(Usuario idUsuarioRevisa) {
        this.idUsuarioRevisa = idUsuarioRevisa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPendiente != null ? idPendiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pendiente)) {
            return false;
        }
        Pendiente other = (Pendiente) object;
        if ((this.idPendiente == null && other.idPendiente != null) || (this.idPendiente != null && !this.idPendiente.equals(other.idPendiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pendiente[ idPendiente=" + idPendiente + " ]";
    }
    
}
