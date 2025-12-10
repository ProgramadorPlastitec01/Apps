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
@Table(name = "mc_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McCargo.findAll", query = "SELECT m FROM McCargo m"),
    @NamedQuery(name = "McCargo.findByIdMcCargo", query = "SELECT m FROM McCargo m WHERE m.idMcCargo = :idMcCargo"),
    @NamedQuery(name = "McCargo.findByIdCargo", query = "SELECT m FROM McCargo m WHERE m.idCargo = :idCargo"),
    @NamedQuery(name = "McCargo.findByCodigo", query = "SELECT m FROM McCargo m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "McCargo.findByVersion", query = "SELECT m FROM McCargo m WHERE m.version = :version"),
    @NamedQuery(name = "McCargo.findByTitulo", query = "SELECT m FROM McCargo m WHERE m.titulo = :titulo"),
    @NamedQuery(name = "McCargo.findByFrecuencia", query = "SELECT m FROM McCargo m WHERE m.frecuencia = :frecuencia"),
    @NamedQuery(name = "McCargo.findByPonderado", query = "SELECT m FROM McCargo m WHERE m.ponderado = :ponderado"),
    @NamedQuery(name = "McCargo.findByEstado", query = "SELECT m FROM McCargo m WHERE m.estado = :estado"),
    @NamedQuery(name = "McCargo.findByFechaRegistro", query = "SELECT m FROM McCargo m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "McCargo.findByUsuarioRegistro", query = "SELECT m FROM McCargo m WHERE m.usuarioRegistro = :usuarioRegistro")})
public class McCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_cargo")
    private Integer idMcCargo;
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private int idCargo;
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
    @Column(name = "frecuencia")
    private int frecuencia;
    @Basic(optional = false)
    @Column(name = "ponderado")
    private int ponderado;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMcCargo")
    private Collection<McDefinicion> mcDefinicionCollection;

    public McCargo() {
    }

    public McCargo(Integer idMcCargo) {
        this.idMcCargo = idMcCargo;
    }

    public McCargo(Integer idMcCargo, int idCargo, String codigo, int version, String titulo, int frecuencia, int ponderado, int estado, Date fechaRegistro, String usuarioRegistro) {
        this.idMcCargo = idMcCargo;
        this.idCargo = idCargo;
        this.codigo = codigo;
        this.version = version;
        this.titulo = titulo;
        this.frecuencia = frecuencia;
        this.ponderado = ponderado;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdMcCargo() {
        return idMcCargo;
    }

    public void setIdMcCargo(Integer idMcCargo) {
        this.idMcCargo = idMcCargo;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
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

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getPonderado() {
        return ponderado;
    }

    public void setPonderado(int ponderado) {
        this.ponderado = ponderado;
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
    public Collection<McDefinicion> getMcDefinicionCollection() {
        return mcDefinicionCollection;
    }

    public void setMcDefinicionCollection(Collection<McDefinicion> mcDefinicionCollection) {
        this.mcDefinicionCollection = mcDefinicionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcCargo != null ? idMcCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McCargo)) {
            return false;
        }
        McCargo other = (McCargo) object;
        if ((this.idMcCargo == null && other.idMcCargo != null) || (this.idMcCargo != null && !this.idMcCargo.equals(other.idMcCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McCargo[ idMcCargo=" + idMcCargo + " ]";
    }
    
}
