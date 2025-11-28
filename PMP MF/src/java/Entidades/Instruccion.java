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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "instruccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instruccion.findAll", query = "SELECT i FROM Instruccion i"),
    @NamedQuery(name = "Instruccion.findByIdInstruccion", query = "SELECT i FROM Instruccion i WHERE i.idInstruccion = :idInstruccion"),
    @NamedQuery(name = "Instruccion.findByTitulo", query = "SELECT i FROM Instruccion i WHERE i.titulo = :titulo"),
    @NamedQuery(name = "Instruccion.findByUsuarioRegistro", query = "SELECT i FROM Instruccion i WHERE i.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Instruccion.findByFechaRegistro", query = "SELECT i FROM Instruccion i WHERE i.fechaRegistro = :fechaRegistro")})
public class Instruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_instruccion")
    private Integer idInstruccion;
    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Instruccion() {
    }

    public Instruccion(Integer idInstruccion) {
        this.idInstruccion = idInstruccion;
    }

    public Integer getIdInstruccion() {
        return idInstruccion;
    }

    public void setIdInstruccion(Integer idInstruccion) {
        this.idInstruccion = idInstruccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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
        hash += (idInstruccion != null ? idInstruccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instruccion)) {
            return false;
        }
        Instruccion other = (Instruccion) object;
        if ((this.idInstruccion == null && other.idInstruccion != null) || (this.idInstruccion != null && !this.idInstruccion.equals(other.idInstruccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Instruccion[ idInstruccion=" + idInstruccion + " ]";
    }
    
}
