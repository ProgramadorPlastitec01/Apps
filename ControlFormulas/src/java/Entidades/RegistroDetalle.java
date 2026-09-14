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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "registro_detalle")
@NamedQueries({
    @NamedQuery(name = "RegistroDetalle.findAll", query = "SELECT r FROM RegistroDetalle r"),
    @NamedQuery(name = "RegistroDetalle.findByIdRegistroDetalle", query = "SELECT r FROM RegistroDetalle r WHERE r.idRegistroDetalle = :idRegistroDetalle"),
    @NamedQuery(name = "RegistroDetalle.findByDato", query = "SELECT r FROM RegistroDetalle r WHERE r.dato = :dato"),
    @NamedQuery(name = "RegistroDetalle.findByUsuarioRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroDetalle.findByFechaRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_detalle")
    private Integer idRegistroDetalle;
    @Column(name = "dato")
    private String dato;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria categoria;

    public RegistroDetalle() {
    }

    public RegistroDetalle(Integer idRegistroDetalle) {
        this.idRegistroDetalle = idRegistroDetalle;
    }

    public Integer getIdRegistroDetalle() {
        return idRegistroDetalle;
    }

    public void setIdRegistroDetalle(Integer idRegistroDetalle) {
        this.idRegistroDetalle = idRegistroDetalle;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
