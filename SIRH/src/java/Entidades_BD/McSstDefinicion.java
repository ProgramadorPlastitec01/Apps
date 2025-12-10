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
@Table(name = "mc_sst_definicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "McSstDefinicion.findAll", query = "SELECT m FROM McSstDefinicion m"),
    @NamedQuery(name = "McSstDefinicion.findByIdMcSstDefinicion", query = "SELECT m FROM McSstDefinicion m WHERE m.idMcSstDefinicion = :idMcSstDefinicion"),
    @NamedQuery(name = "McSstDefinicion.findByValorGrupo", query = "SELECT m FROM McSstDefinicion m WHERE m.valorGrupo = :valorGrupo"),
    @NamedQuery(name = "McSstDefinicion.findByPosicion", query = "SELECT m FROM McSstDefinicion m WHERE m.posicion = :posicion"),
    @NamedQuery(name = "McSstDefinicion.findByFechaRegistro", query = "SELECT m FROM McSstDefinicion m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "McSstDefinicion.findByUsuarioRegistro", query = "SELECT m FROM McSstDefinicion m WHERE m.usuarioRegistro = :usuarioRegistro")})
public class McSstDefinicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mc_sst_definicion")
    private Integer idMcSstDefinicion;
    @Basic(optional = false)
    @Column(name = "valor_grupo")
    private int valorGrupo;
    @Basic(optional = false)
    @Lob
    @Column(name = "definicion")
    private String definicion;
    @Basic(optional = false)
    @Column(name = "posicion")
    private int posicion;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @JoinColumn(name = "id_mc_grupo", referencedColumnName = "id_mc_grupo")
    @ManyToOne(optional = false)
    private McGrupo idMcGrupo;
    @JoinColumn(name = "id_mc_sst_rendicion", referencedColumnName = "id_mc_sst_rendicion")
    @ManyToOne(optional = false)
    private McSstRendicion idMcSstRendicion;

    public McSstDefinicion() {
    }

    public McSstDefinicion(Integer idMcSstDefinicion) {
        this.idMcSstDefinicion = idMcSstDefinicion;
    }

    public McSstDefinicion(Integer idMcSstDefinicion, int valorGrupo, String definicion, int posicion, Date fechaRegistro, String usuarioRegistro) {
        this.idMcSstDefinicion = idMcSstDefinicion;
        this.valorGrupo = valorGrupo;
        this.definicion = definicion;
        this.posicion = posicion;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdMcSstDefinicion() {
        return idMcSstDefinicion;
    }

    public void setIdMcSstDefinicion(Integer idMcSstDefinicion) {
        this.idMcSstDefinicion = idMcSstDefinicion;
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

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
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

    public McGrupo getIdMcGrupo() {
        return idMcGrupo;
    }

    public void setIdMcGrupo(McGrupo idMcGrupo) {
        this.idMcGrupo = idMcGrupo;
    }

    public McSstRendicion getIdMcSstRendicion() {
        return idMcSstRendicion;
    }

    public void setIdMcSstRendicion(McSstRendicion idMcSstRendicion) {
        this.idMcSstRendicion = idMcSstRendicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMcSstDefinicion != null ? idMcSstDefinicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McSstDefinicion)) {
            return false;
        }
        McSstDefinicion other = (McSstDefinicion) object;
        if ((this.idMcSstDefinicion == null && other.idMcSstDefinicion != null) || (this.idMcSstDefinicion != null && !this.idMcSstDefinicion.equals(other.idMcSstDefinicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.McSstDefinicion[ idMcSstDefinicion=" + idMcSstDefinicion + " ]";
    }
    
}
