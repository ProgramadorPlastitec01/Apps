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
@Table(name = "algoritmo")
@NamedQueries({
    @NamedQuery(name = "Algoritmo.findAll", query = "SELECT a FROM Algoritmo a"),
    @NamedQuery(name = "Algoritmo.findByIdAlgoritmo", query = "SELECT a FROM Algoritmo a WHERE a.idAlgoritmo = :idAlgoritmo"),
    @NamedQuery(name = "Algoritmo.findByAlgoritmo", query = "SELECT a FROM Algoritmo a WHERE a.algoritmo = :algoritmo"),
    @NamedQuery(name = "Algoritmo.findByUsuarioRegistro", query = "SELECT a FROM Algoritmo a WHERE a.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Algoritmo.findByFechaRegistro", query = "SELECT a FROM Algoritmo a WHERE a.fechaRegistro = :fechaRegistro")})
public class Algoritmo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_algoritmo")
    private Integer idAlgoritmo;
    @Column(name = "algoritmo")
    private String algoritmo;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Algoritmo() {
    }

    public Algoritmo(Integer idAlgoritmo) {
        this.idAlgoritmo = idAlgoritmo;
    }

    public Integer getIdAlgoritmo() {
        return idAlgoritmo;
    }

    public void setIdAlgoritmo(Integer idAlgoritmo) {
        this.idAlgoritmo = idAlgoritmo;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
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
        hash += (idAlgoritmo != null ? idAlgoritmo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Algoritmo)) {
            return false;
        }
        Algoritmo other = (Algoritmo) object;
        if ((this.idAlgoritmo == null && other.idAlgoritmo != null) || (this.idAlgoritmo != null && !this.idAlgoritmo.equals(other.idAlgoritmo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Algoritmo[idAlgoritmo=" + idAlgoritmo + "]";
    }

}
