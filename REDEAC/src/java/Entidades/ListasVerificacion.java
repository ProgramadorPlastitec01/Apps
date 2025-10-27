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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "listas_verificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListasVerificacion.findAll", query = "SELECT l FROM ListasVerificacion l")
    , @NamedQuery(name = "ListasVerificacion.findByIdVerificacion", query = "SELECT l FROM ListasVerificacion l WHERE l.idVerificacion = :idVerificacion")
    , @NamedQuery(name = "ListasVerificacion.findByNombreLista", query = "SELECT l FROM ListasVerificacion l WHERE l.nombreLista = :nombreLista")
    , @NamedQuery(name = "ListasVerificacion.findByEstado", query = "SELECT l FROM ListasVerificacion l WHERE l.estado = :estado")
    , @NamedQuery(name = "ListasVerificacion.findByFechaRegistro", query = "SELECT l FROM ListasVerificacion l WHERE l.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "ListasVerificacion.findByUsuarioRegistro", query = "SELECT l FROM ListasVerificacion l WHERE l.usuarioRegistro = :usuarioRegistro")})
public class ListasVerificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_verificacion")
    private Integer idVerificacion;
    @Column(name = "nombre_lista")
    private String nombreLista;
    @Lob
    @Column(name = "listado_equipo")
    private String listadoEquipo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public ListasVerificacion() {
    }

    public ListasVerificacion(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public Integer getIdVerificacion() {
        return idVerificacion;
    }

    public void setIdVerificacion(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getListadoEquipo() {
        return listadoEquipo;
    }

    public void setListadoEquipo(String listadoEquipo) {
        this.listadoEquipo = listadoEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idVerificacion != null ? idVerificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListasVerificacion)) {
            return false;
        }
        ListasVerificacion other = (ListasVerificacion) object;
        if ((this.idVerificacion == null && other.idVerificacion != null) || (this.idVerificacion != null && !this.idVerificacion.equals(other.idVerificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ListasVerificacion[ idVerificacion=" + idVerificacion + " ]";
    }
    
}
