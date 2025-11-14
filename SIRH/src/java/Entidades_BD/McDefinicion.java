/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "mc_definicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McDefinicion.findAll", query = "SELECT m FROM McDefinicion m"),
    @NamedQuery(name = "McDefinicion.findByIdMcDefinicion", query = "SELECT m FROM McDefinicion m WHERE m.idMcDefinicion = :idMcDefinicion"),
    @NamedQuery(name = "McDefinicion.findByIdMcGrupo", query = "SELECT m FROM McDefinicion m WHERE m.idMcGrupo = :idMcGrupo"),
    @NamedQuery(name = "McDefinicion.findByValorGrupo", query = "SELECT m FROM McDefinicion m WHERE m.valorGrupo = :valorGrupo"),
    @NamedQuery(name = "McDefinicion.findByOrden", query = "SELECT m FROM McDefinicion m WHERE m.orden = :orden"),
    @NamedQuery(name = "McDefinicion.findByEstado", query = "SELECT m FROM McDefinicion m WHERE m.estado = :estado"),
    @NamedQuery(name = "McDefinicion.findByFechaRegistro", query = "SELECT m FROM McDefinicion m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "McDefinicion.findByUsuarioRegistro", query = "SELECT m FROM McDefinicion m WHERE m.usuarioRegistro = :usuarioRegistro")})
public class McDefinicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_definicion")
    private Integer idMcDefinicion;
    @Basic(optional = false)
    @Column(name = "id_mc_grupo")
    private int idMcGrupo;
    @Basic(optional = false)
    @Column(name = "valor_grupo")
    private int valorGrupo;
    @Basic(optional = false)
    @Lob
    @Column(name = "definicion")
    private String definicion;
    @Basic(optional = false)
    @Lob
    @Column(name = "conducta")
    private String conducta;
    @Basic(optional = false)
    @Column(name = "orden")
    private int orden;
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
    @JoinColumn(name = "id_mc_cargo", referencedColumnName = "id_mc_cargo")
    @ManyToOne(optional = false)
    private McCargo idMcCargo;

    public McDefinicion() {
    }

    public McDefinicion(Integer idMcDefinicion) {
        this.idMcDefinicion = idMcDefinicion;
    }

    public McDefinicion(Integer idMcDefinicion, int idMcGrupo, int valorGrupo, String definicion, String conducta, int orden, int estado, Date fechaRegistro, String usuarioRegistro) {
        this.idMcDefinicion = idMcDefinicion;
        this.idMcGrupo = idMcGrupo;
        this.valorGrupo = valorGrupo;
        this.definicion = definicion;
        this.conducta = conducta;
        this.orden = orden;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdMcDefinicion() {
        return idMcDefinicion;
    }

    public void setIdMcDefinicion(Integer idMcDefinicion) {
        this.idMcDefinicion = idMcDefinicion;
    }

    public int getIdMcGrupo() {
        return idMcGrupo;
    }

    public void setIdMcGrupo(int idMcGrupo) {
        this.idMcGrupo = idMcGrupo;
    }

    public int getValorGrupo() {
        return valorGrupo;
    }

    public void setValorGrupo(int valorGrupo) {
        this.valorGrupo = valorGrupo;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public String getConducta() {
        return conducta;
    }

    public void setConducta(String conducta) {
        this.conducta = conducta;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
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

    public McCargo getIdMcCargo() {
        return idMcCargo;
    }

    public void setIdMcCargo(McCargo idMcCargo) {
        this.idMcCargo = idMcCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcDefinicion != null ? idMcDefinicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McDefinicion)) {
            return false;
        }
        McDefinicion other = (McDefinicion) object;
        if ((this.idMcDefinicion == null && other.idMcDefinicion != null) || (this.idMcDefinicion != null && !this.idMcDefinicion.equals(other.idMcDefinicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McDefinicion[ idMcDefinicion=" + idMcDefinicion + " ]";
    }
    
}
