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
@Table(name = "recipiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipiente.findAll", query = "SELECT r FROM Recipiente r")
    , @NamedQuery(name = "Recipiente.findByIdRecipiente", query = "SELECT r FROM Recipiente r WHERE r.idRecipiente = :idRecipiente")
    , @NamedQuery(name = "Recipiente.findByNombreRecipiente", query = "SELECT r FROM Recipiente r WHERE r.nombreRecipiente = :nombreRecipiente")
    , @NamedQuery(name = "Recipiente.findByPesoRecipiente", query = "SELECT r FROM Recipiente r WHERE r.pesoRecipiente = :pesoRecipiente")
    , @NamedQuery(name = "Recipiente.findByMedidaRecipiente", query = "SELECT r FROM Recipiente r WHERE r.medidaRecipiente = :medidaRecipiente")
    , @NamedQuery(name = "Recipiente.findByBolsa", query = "SELECT r FROM Recipiente r WHERE r.bolsa = :bolsa")
    , @NamedQuery(name = "Recipiente.findByPesoBolsa", query = "SELECT r FROM Recipiente r WHERE r.pesoBolsa = :pesoBolsa")
    , @NamedQuery(name = "Recipiente.findByMedidaBolsa", query = "SELECT r FROM Recipiente r WHERE r.medidaBolsa = :medidaBolsa")
    , @NamedQuery(name = "Recipiente.findByEstado", query = "SELECT r FROM Recipiente r WHERE r.estado = :estado")
    , @NamedQuery(name = "Recipiente.findByUsuarioRegistro", query = "SELECT r FROM Recipiente r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Recipiente.findByFechaRegistro", query = "SELECT r FROM Recipiente r WHERE r.fechaRegistro = :fechaRegistro")})
public class Recipiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_recipiente")
    private Integer idRecipiente;
    @Column(name = "nombre_recipiente")
    private String nombreRecipiente;
    @Column(name = "peso_recipiente")
    private String pesoRecipiente;
    @Column(name = "medida_recipiente")
    private String medidaRecipiente;
    @Column(name = "bolsa")
    private String bolsa;
    @Column(name = "peso_bolsa")
    private String pesoBolsa;
    @Column(name = "medida_bolsa")
    private String medidaBolsa;
    @Column(name = "Estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idRecipiente")
    private Collection<Registro> registroCollection;

    public Recipiente() {
    }

    public Recipiente(Integer idRecipiente) {
        this.idRecipiente = idRecipiente;
    }

    public Recipiente(Integer idRecipiente, Date fechaRegistro) {
        this.idRecipiente = idRecipiente;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRecipiente() {
        return idRecipiente;
    }

    public void setIdRecipiente(Integer idRecipiente) {
        this.idRecipiente = idRecipiente;
    }

    public String getNombreRecipiente() {
        return nombreRecipiente;
    }

    public void setNombreRecipiente(String nombreRecipiente) {
        this.nombreRecipiente = nombreRecipiente;
    }

    public String getPesoRecipiente() {
        return pesoRecipiente;
    }

    public void setPesoRecipiente(String pesoRecipiente) {
        this.pesoRecipiente = pesoRecipiente;
    }

    public String getMedidaRecipiente() {
        return medidaRecipiente;
    }

    public void setMedidaRecipiente(String medidaRecipiente) {
        this.medidaRecipiente = medidaRecipiente;
    }

    public String getBolsa() {
        return bolsa;
    }

    public void setBolsa(String bolsa) {
        this.bolsa = bolsa;
    }

    public String getPesoBolsa() {
        return pesoBolsa;
    }

    public void setPesoBolsa(String pesoBolsa) {
        this.pesoBolsa = pesoBolsa;
    }

    public String getMedidaBolsa() {
        return medidaBolsa;
    }

    public void setMedidaBolsa(String medidaBolsa) {
        this.medidaBolsa = medidaBolsa;
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
    public Collection<Registro> getRegistroCollection() {
        return registroCollection;
    }

    public void setRegistroCollection(Collection<Registro> registroCollection) {
        this.registroCollection = registroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecipiente != null ? idRecipiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipiente)) {
            return false;
        }
        Recipiente other = (Recipiente) object;
        if ((this.idRecipiente == null && other.idRecipiente != null) || (this.idRecipiente != null && !this.idRecipiente.equals(other.idRecipiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Recipiente[ idRecipiente=" + idRecipiente + " ]";
    }
    
}
