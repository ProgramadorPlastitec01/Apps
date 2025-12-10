/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "mc_sst_rendicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McSstRendicion.findAll", query = "SELECT m FROM McSstRendicion m"),
    @NamedQuery(name = "McSstRendicion.findByIdMcSstRendicion", query = "SELECT m FROM McSstRendicion m WHERE m.idMcSstRendicion = :idMcSstRendicion"),
    @NamedQuery(name = "McSstRendicion.findByCodigo", query = "SELECT m FROM McSstRendicion m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "McSstRendicion.findByVersion", query = "SELECT m FROM McSstRendicion m WHERE m.version = :version"),
    @NamedQuery(name = "McSstRendicion.findByTitulo", query = "SELECT m FROM McSstRendicion m WHERE m.titulo = :titulo"),
    @NamedQuery(name = "McSstRendicion.findByEstado", query = "SELECT m FROM McSstRendicion m WHERE m.estado = :estado"),
    @NamedQuery(name = "McSstRendicion.findByFechaRegistro", query = "SELECT m FROM McSstRendicion m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "McSstRendicion.findByUsuarioRegistro", query = "SELECT m FROM McSstRendicion m WHERE m.usuarioRegistro = :usuarioRegistro")})
public class McSstRendicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_sst_rendicion")
    private Integer idMcSstRendicion;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMcSstRendicion")
    private Collection<McSstDefinicion> mcSstDefinicionCollection;

    public McSstRendicion() {
    }

    public McSstRendicion(Integer idMcSstRendicion) {
        this.idMcSstRendicion = idMcSstRendicion;
    }

    public McSstRendicion(Integer idMcSstRendicion, String codigo, int version, String titulo, int estado, Date fechaRegistro) {
        this.idMcSstRendicion = idMcSstRendicion;
        this.codigo = codigo;
        this.version = version;
        this.titulo = titulo;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdMcSstRendicion() {
        return idMcSstRendicion;
    }

    public void setIdMcSstRendicion(Integer idMcSstRendicion) {
        this.idMcSstRendicion = idMcSstRendicion;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
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

    @XmlTransient
    public Collection<McSstDefinicion> getMcSstDefinicionCollection() {
        return mcSstDefinicionCollection;
    }

    public void setMcSstDefinicionCollection(Collection<McSstDefinicion> mcSstDefinicionCollection) {
        this.mcSstDefinicionCollection = mcSstDefinicionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcSstRendicion != null ? idMcSstRendicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McSstRendicion)) {
            return false;
        }
        McSstRendicion other = (McSstRendicion) object;
        if ((this.idMcSstRendicion == null && other.idMcSstRendicion != null) || (this.idMcSstRendicion != null && !this.idMcSstRendicion.equals(other.idMcSstRendicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McSstRendicion[ idMcSstRendicion=" + idMcSstRendicion + " ]";
    }
    
}
