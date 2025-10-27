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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aprendiz.sena1
 */
@Entity
@Table(name = "cargo")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findByIdCargo", query = "SELECT c FROM Cargo c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "Cargo.findByUsuRegistro", query = "SELECT c FROM Cargo c WHERE c.usuRegistro = :usuRegistro"),
    @NamedQuery(name = "Cargo.findByFchRegistro", query = "SELECT c FROM Cargo c WHERE c.fchRegistro = :fchRegistro"),
    @NamedQuery(name = "Cargo.findByNombreRegistro", query = "SELECT c FROM Cargo c WHERE c.nombreRegistro = :nombreRegistro"),
    @NamedQuery(name = "Cargo.findByCargo", query = "SELECT c FROM Cargo c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Cargo.findByCodigoRegistro", query = "SELECT c FROM Cargo c WHERE c.codigoRegistro = :codigoRegistro"),
    @NamedQuery(name = "Cargo.findByVersionCodigo", query = "SELECT c FROM Cargo c WHERE c.versionCodigo = :versionCodigo"),
    @NamedQuery(name = "Cargo.findByEstado", query = "SELECT c FROM Cargo c WHERE c.estado = :estado"),
    @NamedQuery(name = "Cargo.findByEnviarCorreos", query = "SELECT c FROM Cargo c WHERE c.enviarCorreos = :enviarCorreos")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "nombre_registro")
    private String nombreRegistro;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "codigo_registro")
    private Integer codigoRegistro;
    @Column(name = "version_codigo")
    private String versionCodigo;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "enviar_correos")
    private Integer enviarCorreos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private Collection<Formulario> formularioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area area;

    public Cargo() {
    }

    public Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(Integer codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getVersionCodigo() {
        return versionCodigo;
    }

    public void setVersionCodigo(String versionCodigo) {
        this.versionCodigo = versionCodigo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEnviarCorreos() {
        return enviarCorreos;
    }

    public void setEnviarCorreos(Integer enviarCorreos) {
        this.enviarCorreos = enviarCorreos;
    }

    public Collection<Formulario> getFormularioCollection() {
        return formularioCollection;
    }

    public void setFormularioCollection(Collection<Formulario> formularioCollection) {
        this.formularioCollection = formularioCollection;
    }

    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Servlets.Cargo[idCargo=" + idCargo + "]";
    }

}
