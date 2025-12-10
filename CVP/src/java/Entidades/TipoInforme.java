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
@Table(name = "tipo_informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoInforme.findAll", query = "SELECT t FROM TipoInforme t"),
    @NamedQuery(name = "TipoInforme.findByIdTipoInforme", query = "SELECT t FROM TipoInforme t WHERE t.idTipoInforme = :idTipoInforme"),
    @NamedQuery(name = "TipoInforme.findByNombre", query = "SELECT t FROM TipoInforme t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoInforme.findByVigencia", query = "SELECT t FROM TipoInforme t WHERE t.vigencia = :vigencia"),
    @NamedQuery(name = "TipoInforme.findByEstado", query = "SELECT t FROM TipoInforme t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoInforme.findByFechaRegistro", query = "SELECT t FROM TipoInforme t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "TipoInforme.findByUsuarioRegistro", query = "SELECT t FROM TipoInforme t WHERE t.usuarioRegistro = :usuarioRegistro")})
public class TipoInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_informe")
    private Integer idTipoInforme;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "vigencia")
    private int vigencia;
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

    public TipoInforme() {
    }

    public TipoInforme(Integer idTipoInforme) {
        this.idTipoInforme = idTipoInforme;
    }

    public TipoInforme(Integer idTipoInforme, String nombre, int vigencia, int estado, Date fechaRegistro, String usuarioRegistro) {
        this.idTipoInforme = idTipoInforme;
        this.nombre = nombre;
        this.vigencia = vigencia;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdTipoInforme() {
        return idTipoInforme;
    }

    public void setIdTipoInforme(Integer idTipoInforme) {
        this.idTipoInforme = idTipoInforme;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoInforme != null ? idTipoInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoInforme)) {
            return false;
        }
        TipoInforme other = (TipoInforme) object;
        if ((this.idTipoInforme == null && other.idTipoInforme != null) || (this.idTipoInforme != null && !this.idTipoInforme.equals(other.idTipoInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoInforme[ idTipoInforme=" + idTipoInforme + " ]";
    }
    
}
