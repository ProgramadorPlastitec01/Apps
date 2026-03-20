/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author prog.sistemas2
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByFecha", query = "SELECT c FROM Cliente c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApellido", query = "SELECT c FROM Cliente c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Cliente.findByDocumento", query = "SELECT c FROM Cliente c WHERE c.documento = :documento"),
    @NamedQuery(name = "Cliente.findByUsuario", query = "SELECT c FROM Cliente c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Cliente.findByPassword", query = "SELECT c FROM Cliente c WHERE c.password = :password"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "documento")
    private String documento;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(mappedBy = "idCliente")
    private Collection<ControlDmsC> controlDmsCCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<ControlDmsD> controlDmsDCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Dimensional> dimensionalCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Defecto> defectoCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Comentario> comentarioCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<FichaTecnica> fichaTecnicaCollection;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private Rol idRol;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Cuarentena> cuarentenaCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Visual> visualCollection;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, Date fecha) {
        this.idCliente = idCliente;
        this.fecha = fecha;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<ControlDmsC> getControlDmsCCollection() {
        return controlDmsCCollection;
    }

    public void setControlDmsCCollection(Collection<ControlDmsC> controlDmsCCollection) {
        this.controlDmsCCollection = controlDmsCCollection;
    }

    @XmlTransient
    public Collection<ControlDmsD> getControlDmsDCollection() {
        return controlDmsDCollection;
    }

    public void setControlDmsDCollection(Collection<ControlDmsD> controlDmsDCollection) {
        this.controlDmsDCollection = controlDmsDCollection;
    }

    @XmlTransient
    public Collection<Dimensional> getDimensionalCollection() {
        return dimensionalCollection;
    }

    public void setDimensionalCollection(Collection<Dimensional> dimensionalCollection) {
        this.dimensionalCollection = dimensionalCollection;
    }

    @XmlTransient
    public Collection<Defecto> getDefectoCollection() {
        return defectoCollection;
    }

    public void setDefectoCollection(Collection<Defecto> defectoCollection) {
        this.defectoCollection = defectoCollection;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    @XmlTransient
    public Collection<FichaTecnica> getFichaTecnicaCollection() {
        return fichaTecnicaCollection;
    }

    public void setFichaTecnicaCollection(Collection<FichaTecnica> fichaTecnicaCollection) {
        this.fichaTecnicaCollection = fichaTecnicaCollection;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @XmlTransient
    public Collection<Cuarentena> getCuarentenaCollection() {
        return cuarentenaCollection;
    }

    public void setCuarentenaCollection(Collection<Cuarentena> cuarentenaCollection) {
        this.cuarentenaCollection = cuarentenaCollection;
    }

    @XmlTransient
    public Collection<Visual> getVisualCollection() {
        return visualCollection;
    }

    public void setVisualCollection(Collection<Visual> visualCollection) {
        this.visualCollection = visualCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
