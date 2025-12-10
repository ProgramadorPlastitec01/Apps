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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "entrada_otro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntradaOtro.findAll", query = "SELECT e FROM EntradaOtro e")
    , @NamedQuery(name = "EntradaOtro.findByIdEntradaOtro", query = "SELECT e FROM EntradaOtro e WHERE e.idEntradaOtro = :idEntradaOtro")
    , @NamedQuery(name = "EntradaOtro.findByIdProyecto", query = "SELECT e FROM EntradaOtro e WHERE e.idProyecto = :idProyecto")
    , @NamedQuery(name = "EntradaOtro.findByFechaRegistro", query = "SELECT e FROM EntradaOtro e WHERE e.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "EntradaOtro.findByAsunto", query = "SELECT e FROM EntradaOtro e WHERE e.asunto = :asunto")
    , @NamedQuery(name = "EntradaOtro.findByUsuarioRegistro", query = "SELECT e FROM EntradaOtro e WHERE e.usuarioRegistro = :usuarioRegistro")})
public class EntradaOtro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_entrada_otro")
    private Integer idEntradaOtro;
    @Basic(optional = false)
    @Column(name = "id_proyecto")
    private int idProyecto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "asunto")
    private String asunto;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public EntradaOtro() {
    }

    public EntradaOtro(Integer idEntradaOtro) {
        this.idEntradaOtro = idEntradaOtro;
    }

    public EntradaOtro(Integer idEntradaOtro, int idProyecto) {
        this.idEntradaOtro = idEntradaOtro;
        this.idProyecto = idProyecto;
    }

    public Integer getIdEntradaOtro() {
        return idEntradaOtro;
    }

    public void setIdEntradaOtro(Integer idEntradaOtro) {
        this.idEntradaOtro = idEntradaOtro;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntradaOtro != null ? idEntradaOtro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradaOtro)) {
            return false;
        }
        EntradaOtro other = (EntradaOtro) object;
        if ((this.idEntradaOtro == null && other.idEntradaOtro != null) || (this.idEntradaOtro != null && !this.idEntradaOtro.equals(other.idEntradaOtro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EntradaOtro[ idEntradaOtro=" + idEntradaOtro + " ]";
    }
    
}
