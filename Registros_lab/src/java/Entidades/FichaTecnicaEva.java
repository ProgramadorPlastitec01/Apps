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
@Table(name = "ficha_tecnica_eva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichaTecnicaEva.findAll", query = "SELECT f FROM FichaTecnicaEva f"),
    @NamedQuery(name = "FichaTecnicaEva.findByIdFichaTecnicaEva", query = "SELECT f FROM FichaTecnicaEva f WHERE f.idFichaTecnicaEva = :idFichaTecnicaEva"),
    @NamedQuery(name = "FichaTecnicaEva.findByProducto", query = "SELECT f FROM FichaTecnicaEva f WHERE f.producto = :producto"),
    @NamedQuery(name = "FichaTecnicaEva.findByCodigo", query = "SELECT f FROM FichaTecnicaEva f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "FichaTecnicaEva.findByVersion", query = "SELECT f FROM FichaTecnicaEva f WHERE f.version = :version"),
    @NamedQuery(name = "FichaTecnicaEva.findByUsuarioRegistro", query = "SELECT f FROM FichaTecnicaEva f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "FichaTecnicaEva.findByFechaRegistro", query = "SELECT f FROM FichaTecnicaEva f WHERE f.fechaRegistro = :fechaRegistro")})
public class FichaTecnicaEva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ficha_tecnica_eva")
    private Integer idFichaTecnicaEva;
    @Basic(optional = false)
    @Column(name = "producto")
    private String producto;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    @Basic(optional = false)
    @Lob
    @Column(name = "materiales")
    private String materiales;
    @Basic(optional = false)
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public FichaTecnicaEva() {
    }

    public FichaTecnicaEva(Integer idFichaTecnicaEva) {
        this.idFichaTecnicaEva = idFichaTecnicaEva;
    }

    public FichaTecnicaEva(Integer idFichaTecnicaEva, String producto, String codigo, int version, String materiales, String observaciones, String usuarioRegistro, Date fechaRegistro) {
        this.idFichaTecnicaEva = idFichaTecnicaEva;
        this.producto = producto;
        this.codigo = codigo;
        this.version = version;
        this.materiales = materiales;
        this.observaciones = observaciones;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdFichaTecnicaEva() {
        return idFichaTecnicaEva;
    }

    public void setIdFichaTecnicaEva(Integer idFichaTecnicaEva) {
        this.idFichaTecnicaEva = idFichaTecnicaEva;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
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

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        hash += (idFichaTecnicaEva != null ? idFichaTecnicaEva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaTecnicaEva)) {
            return false;
        }
        FichaTecnicaEva other = (FichaTecnicaEva) object;
        if ((this.idFichaTecnicaEva == null && other.idFichaTecnicaEva != null) || (this.idFichaTecnicaEva != null && !this.idFichaTecnicaEva.equals(other.idFichaTecnicaEva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FichaTecnicaEva[ idFichaTecnicaEva=" + idFichaTecnicaEva + " ]";
    }
    
}
