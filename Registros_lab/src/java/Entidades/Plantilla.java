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
import javax.persistence.Lob;
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
@Table(name = "plantilla")
@NamedQueries({
    @NamedQuery(name = "Plantilla.findAll", query = "SELECT p FROM Plantilla p"),
    @NamedQuery(name = "Plantilla.findByIdPlantilla", query = "SELECT p FROM Plantilla p WHERE p.idPlantilla = :idPlantilla"),
    @NamedQuery(name = "Plantilla.findByIdTipoLinea", query = "SELECT p FROM Plantilla p WHERE p.idTipoLinea = :idTipoLinea"),
    @NamedQuery(name = "Plantilla.findByCodigo", query = "SELECT p FROM Plantilla p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Plantilla.findByVersion", query = "SELECT p FROM Plantilla p WHERE p.version = :version"),
    @NamedQuery(name = "Plantilla.findByEstado", query = "SELECT p FROM Plantilla p WHERE p.estado = :estado"),
    @NamedQuery(name = "Plantilla.findByFechaRegistro", query = "SELECT p FROM Plantilla p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Plantilla.findByUsuarioRegistro", query = "SELECT p FROM Plantilla p WHERE p.usuarioRegistro = :usuarioRegistro")})
public class Plantilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;
    @Basic(optional = false)
    @Column(name = "id_tipo_linea")
    private int idTipoLinea;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "version")
    private Integer version;
    @Lob
    @Column(name = "formato")
    private String formato;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Plantilla() {
    }

    public Plantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Plantilla(Integer idPlantilla, int idTipoLinea) {
        this.idPlantilla = idPlantilla;
        this.idTipoLinea = idTipoLinea;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdTipoLinea() {
        return idTipoLinea;
    }

    public void setIdTipoLinea(int idTipoLinea) {
        this.idTipoLinea = idTipoLinea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantilla != null ? idPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plantilla)) {
            return false;
        }
        Plantilla other = (Plantilla) object;
        if ((this.idPlantilla == null && other.idPlantilla != null) || (this.idPlantilla != null && !this.idPlantilla.equals(other.idPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Plantilla[idPlantilla=" + idPlantilla + "]";
    }

}
