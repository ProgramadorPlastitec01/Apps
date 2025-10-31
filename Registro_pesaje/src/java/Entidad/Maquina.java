/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
 * @author Programador.TI1
 */
@Entity
@Table(name = "maquina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maquina.findAll", query = "SELECT m FROM Maquina m")
    , @NamedQuery(name = "Maquina.findByIdMaquina", query = "SELECT m FROM Maquina m WHERE m.idMaquina = :idMaquina")
    , @NamedQuery(name = "Maquina.findByNombreMaquina", query = "SELECT m FROM Maquina m WHERE m.nombreMaquina = :nombreMaquina")
    , @NamedQuery(name = "Maquina.findByCodProducto", query = "SELECT m FROM Maquina m WHERE m.codProducto = :codProducto")
    , @NamedQuery(name = "Maquina.findByProducto", query = "SELECT m FROM Maquina m WHERE m.producto = :producto")
    , @NamedQuery(name = "Maquina.findByMolde", query = "SELECT m FROM Maquina m WHERE m.molde = :molde")
    , @NamedQuery(name = "Maquina.findByTara", query = "SELECT m FROM Maquina m WHERE m.tara = :tara")
    , @NamedQuery(name = "Maquina.findByUnidadMedida", query = "SELECT m FROM Maquina m WHERE m.unidadMedida = :unidadMedida")
    , @NamedQuery(name = "Maquina.findByEstado", query = "SELECT m FROM Maquina m WHERE m.estado = :estado")
    , @NamedQuery(name = "Maquina.findByUsuarioRegistro", query = "SELECT m FROM Maquina m WHERE m.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Maquina.findByFechaRegistro", query = "SELECT m FROM Maquina m WHERE m.fechaRegistro = :fechaRegistro")})
public class Maquina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_maquina")
    private Integer idMaquina;
    @Column(name = "nombre_maquina")
    private String nombreMaquina;
    @Column(name = "cod_producto")
    private String codProducto;
    @Column(name = "producto")
    private String producto;
    @Column(name = "molde")
    private String molde;
    @Column(name = "tara")
    private String tara;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    @Column(name = "Estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idMaquina")
    private Collection<Orden> ordenCollection;

    public Maquina() {
    }

    public Maquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public Maquina(Integer idMaquina, Date fechaRegistro) {
        this.idMaquina = idMaquina;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getMolde() {
        return molde;
    }

    public void setMolde(String molde) {
        this.molde = molde;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
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

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaquina != null ? idMaquina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maquina)) {
            return false;
        }
        Maquina other = (Maquina) object;
        if ((this.idMaquina == null && other.idMaquina != null) || (this.idMaquina != null && !this.idMaquina.equals(other.idMaquina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Maquina[ idMaquina=" + idMaquina + " ]";
    }
    
}
