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
 * @author Programador.TI1
 */
@Entity
@Table(name = "orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o")
    , @NamedQuery(name = "Orden.findByIdOrden", query = "SELECT o FROM Orden o WHERE o.idOrden = :idOrden")
    , @NamedQuery(name = "Orden.findByNumeroOrden", query = "SELECT o FROM Orden o WHERE o.numeroOrden = :numeroOrden")
    , @NamedQuery(name = "Orden.findByCodigo", query = "SELECT o FROM Orden o WHERE o.codigo = :codigo")
    , @NamedQuery(name = "Orden.findByProducto", query = "SELECT o FROM Orden o WHERE o.producto = :producto")
    , @NamedQuery(name = "Orden.findByPlan", query = "SELECT o FROM Orden o WHERE o.plan = :plan")
    , @NamedQuery(name = "Orden.findByLote", query = "SELECT o FROM Orden o WHERE o.lote = :lote")
    , @NamedQuery(name = "Orden.findByCantidadProgramada", query = "SELECT o FROM Orden o WHERE o.cantidadProgramada = :cantidadProgramada")
    , @NamedQuery(name = "Orden.findByUnidad", query = "SELECT o FROM Orden o WHERE o.unidad = :unidad")
    , @NamedQuery(name = "Orden.findByCentroCosto", query = "SELECT o FROM Orden o WHERE o.centroCosto = :centroCosto")
    , @NamedQuery(name = "Orden.findByFechaInicio", query = "SELECT o FROM Orden o WHERE o.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Orden.findByFechaFin", query = "SELECT o FROM Orden o WHERE o.fechaFin = :fechaFin")
    , @NamedQuery(name = "Orden.findByEstado", query = "SELECT o FROM Orden o WHERE o.estado = :estado")
    , @NamedQuery(name = "Orden.findByPesoMeta", query = "SELECT o FROM Orden o WHERE o.pesoMeta = :pesoMeta")
    , @NamedQuery(name = "Orden.findByPesoxgramos", query = "SELECT o FROM Orden o WHERE o.pesoxgramos = :pesoxgramos")
    , @NamedQuery(name = "Orden.findByUsuarioRegistro", query = "SELECT o FROM Orden o WHERE o.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Orden.findByFechaRegistro", query = "SELECT o FROM Orden o WHERE o.fechaRegistro = :fechaRegistro")})
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden")
    private Integer idOrden;
    @Column(name = "numero_orden")
    private Integer numeroOrden;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "producto")
    private String producto;
    @Column(name = "plan")
    private String plan;
    @Column(name = "lote")
    private String lote;
    @Column(name = "cantidad_programada")
    private String cantidadProgramada;
    @Column(name = "unidad")
    private String unidad;
    @Column(name = "centro_costo")
    private String centroCosto;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "peso_meta")
    private Integer pesoMeta;
    @Column(name = "pesoxgramos")
    private Integer pesoxgramos;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_maquina", referencedColumnName = "id_maquina")
    @ManyToOne
    private Maquina idMaquina;
    @OneToMany(mappedBy = "idOrden")
    private Collection<Registro> registroCollection;

    public Orden() {
    }

    public Orden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCantidadProgramada() {
        return cantidadProgramada;
    }

    public void setCantidadProgramada(String cantidadProgramada) {
        this.cantidadProgramada = cantidadProgramada;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getPesoMeta() {
        return pesoMeta;
    }

    public void setPesoMeta(Integer pesoMeta) {
        this.pesoMeta = pesoMeta;
    }

    public Integer getPesoxgramos() {
        return pesoxgramos;
    }

    public void setPesoxgramos(Integer pesoxgramos) {
        this.pesoxgramos = pesoxgramos;
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

    public Maquina getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Maquina idMaquina) {
        this.idMaquina = idMaquina;
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
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Orden[ idOrden=" + idOrden + " ]";
    }
    
}
