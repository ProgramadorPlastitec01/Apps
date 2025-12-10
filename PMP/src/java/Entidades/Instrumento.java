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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "instrumento")
@NamedQueries({
    @NamedQuery(name = "Instrumento.findAll", query = "SELECT i FROM Instrumento i"),
    @NamedQuery(name = "Instrumento.findByIdInstrumento", query = "SELECT i FROM Instrumento i WHERE i.idInstrumento = :idInstrumento"),
    @NamedQuery(name = "Instrumento.findByInstrumento", query = "SELECT i FROM Instrumento i WHERE i.instrumento = :instrumento"),
    @NamedQuery(name = "Instrumento.findByEstado", query = "SELECT i FROM Instrumento i WHERE i.estado = :estado"),
    @NamedQuery(name = "Instrumento.findByUsuarioRegistro", query = "SELECT i FROM Instrumento i WHERE i.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Instrumento.findByFechaRegistro", query = "SELECT i FROM Instrumento i WHERE i.fechaRegistro = :fechaRegistro")})
public class Instrumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_instrumento")
    private Integer idInstrumento;
    @Basic(optional = false)
    @Column(name = "instrumento")
    private String instrumento;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumento")
    private Collection<Parametro> parametroCollection;

    public Instrumento() {
    }

    public Instrumento(Integer idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public Instrumento(Integer idInstrumento, String instrumento, short estado, String usuarioRegistro, Date fechaRegistro) {
        this.idInstrumento = idInstrumento;
        this.instrumento = instrumento;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(Integer idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
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

    public Collection<Parametro> getParametroCollection() {
        return parametroCollection;
    }

    public void setParametroCollection(Collection<Parametro> parametroCollection) {
        this.parametroCollection = parametroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstrumento != null ? idInstrumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instrumento)) {
            return false;
        }
        Instrumento other = (Instrumento) object;
        if ((this.idInstrumento == null && other.idInstrumento != null) || (this.idInstrumento != null && !this.idInstrumento.equals(other.idInstrumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Instrumento[idInstrumento=" + idInstrumento + "]";
    }

}
