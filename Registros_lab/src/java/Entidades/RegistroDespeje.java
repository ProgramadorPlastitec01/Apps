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
@Table(name = "registro_despeje")
@NamedQueries({
    @NamedQuery(name = "RegistroDespeje.findAll", query = "SELECT r FROM RegistroDespeje r"),
    @NamedQuery(name = "RegistroDespeje.findByIdRegistroDespeje", query = "SELECT r FROM RegistroDespeje r WHERE r.idRegistroDespeje = :idRegistroDespeje"),
    @NamedQuery(name = "RegistroDespeje.findByFechaRegistro", query = "SELECT r FROM RegistroDespeje r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RegistroDespeje.findByUsuarioRegistro", query = "SELECT r FROM RegistroDespeje r WHERE r.usuarioRegistro = :usuarioRegistro")})
public class RegistroDespeje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_despeje")
    private Integer idRegistroDespeje;
    @Basic(optional = false)
    @Lob
    @Column(name = "formato")
    private String formato;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro registro;

    public RegistroDespeje() {
    }

    public RegistroDespeje(Integer idRegistroDespeje) {
        this.idRegistroDespeje = idRegistroDespeje;
    }

    public RegistroDespeje(Integer idRegistroDespeje, String formato, Date fechaRegistro, String usuarioRegistro) {
        this.idRegistroDespeje = idRegistroDespeje;
        this.formato = formato;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdRegistroDespeje() {
        return idRegistroDespeje;
    }

    public void setIdRegistroDespeje(Integer idRegistroDespeje) {
        this.idRegistroDespeje = idRegistroDespeje;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroDespeje != null ? idRegistroDespeje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDespeje)) {
            return false;
        }
        RegistroDespeje other = (RegistroDespeje) object;
        if ((this.idRegistroDespeje == null && other.idRegistroDespeje != null) || (this.idRegistroDespeje != null && !this.idRegistroDespeje.equals(other.idRegistroDespeje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroDespeje[idRegistroDespeje=" + idRegistroDespeje + "]";
    }

}
