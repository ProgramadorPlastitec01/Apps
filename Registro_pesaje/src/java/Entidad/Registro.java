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
import javax.persistence.CascadeType;
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
@Table(name = "registro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r")
    , @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro")
    , @NamedQuery(name = "Registro.findByFechaDia", query = "SELECT r FROM Registro r WHERE r.fechaDia = :fechaDia")
    , @NamedQuery(name = "Registro.findByLote", query = "SELECT r FROM Registro r WHERE r.lote = :lote")
    , @NamedQuery(name = "Registro.findByEstiba", query = "SELECT r FROM Registro r WHERE r.estiba = :estiba")
    , @NamedQuery(name = "Registro.findByEstado", query = "SELECT r FROM Registro r WHERE r.estado = :estado")
    , @NamedQuery(name = "Registro.findByUsuarioRegistro", query = "SELECT r FROM Registro r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Registro.findByFechaRegistro", query = "SELECT r FROM Registro r WHERE r.fechaRegistro = :fechaRegistro")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "fecha_dia")
    @Temporal(TemporalType.DATE)
    private Date fechaDia;
    @Column(name = "lote")
    private String lote;
    @Column(name = "estiba")
    private Integer estiba;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRegistro")
    private Collection<RegistroDetalle> registroDetalleCollection;
    @JoinColumn(name = "id_recipiente", referencedColumnName = "id_recipiente")
    @ManyToOne
    private Recipiente idRecipiente;
    @JoinColumn(name = "id_orden", referencedColumnName = "id_orden")
    @ManyToOne
    private Orden idOrden;

    public Registro() {
    }

    public Registro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaDia() {
        return fechaDia;
    }

    public void setFechaDia(Date fechaDia) {
        this.fechaDia = fechaDia;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getEstiba() {
        return estiba;
    }

    public void setEstiba(Integer estiba) {
        this.estiba = estiba;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
    public Collection<RegistroDetalle> getRegistroDetalleCollection() {
        return registroDetalleCollection;
    }

    public void setRegistroDetalleCollection(Collection<RegistroDetalle> registroDetalleCollection) {
        this.registroDetalleCollection = registroDetalleCollection;
    }

    public Recipiente getIdRecipiente() {
        return idRecipiente;
    }

    public void setIdRecipiente(Recipiente idRecipiente) {
        this.idRecipiente = idRecipiente;
    }

    public Orden getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Orden idOrden) {
        this.idOrden = idOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Registro[ idRegistro=" + idRegistro + " ]";
    }
    
}
