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
 * @author Programador.TI1
 */
@Entity
@Table(name = "plantilla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plantilla.findAll", query = "SELECT p FROM Plantilla p")
    , @NamedQuery(name = "Plantilla.findByIdPlantilla", query = "SELECT p FROM Plantilla p WHERE p.idPlantilla = :idPlantilla")
    , @NamedQuery(name = "Plantilla.findByCodigo", query = "SELECT p FROM Plantilla p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Plantilla.findByVersion", query = "SELECT p FROM Plantilla p WHERE p.version = :version")
    , @NamedQuery(name = "Plantilla.findByEstado", query = "SELECT p FROM Plantilla p WHERE p.estado = :estado")
    , @NamedQuery(name = "Plantilla.findByUsuarioRegistro", query = "SELECT p FROM Plantilla p WHERE p.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Plantilla.findByFechaRegistro", query = "SELECT p FROM Plantilla p WHERE p.fechaRegistro = :fechaRegistro")})
public class Plantilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    @Basic(optional = false)
    @Lob
    @Column(name = "formato")
    private String formato;
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

    public Plantilla() {
    }

    public Plantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Plantilla(Integer idPlantilla, String codigo, int version, String formato, int estado, String usuarioRegistro, Date fechaRegistro) {
        this.idPlantilla = idPlantilla;
        this.codigo = codigo;
        this.version = version;
        this.formato = formato;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
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
        return "Entidades.Plantilla[ idPlantilla=" + idPlantilla + " ]";
    }
    
}
