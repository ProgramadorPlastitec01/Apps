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
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "ficha_tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichaTecnica.findAll", query = "SELECT f FROM FichaTecnica f")
    , @NamedQuery(name = "FichaTecnica.findByIdFichaTecnica", query = "SELECT f FROM FichaTecnica f WHERE f.idFichaTecnica = :idFichaTecnica")
    , @NamedQuery(name = "FichaTecnica.findByProducto", query = "SELECT f FROM FichaTecnica f WHERE f.producto = :producto")
    , @NamedQuery(name = "FichaTecnica.findByCodigo", query = "SELECT f FROM FichaTecnica f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "FichaTecnica.findByVersion", query = "SELECT f FROM FichaTecnica f WHERE f.version = :version")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExterior", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExterior = :diametroExterior")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExteriorMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExteriorMin = :diametroExteriorMin")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExteriorMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExteriorMax = :diametroExteriorMax")
    , @NamedQuery(name = "FichaTecnica.findByDiametroInterior", query = "SELECT f FROM FichaTecnica f WHERE f.diametroInterior = :diametroInterior")
    , @NamedQuery(name = "FichaTecnica.findByDiametroInteriorMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroInteriorMin = :diametroInteriorMin")
    , @NamedQuery(name = "FichaTecnica.findByDiametroInteriorMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroInteriorMax = :diametroInteriorMax")
    , @NamedQuery(name = "FichaTecnica.findByEspesorPared", query = "SELECT f FROM FichaTecnica f WHERE f.espesorPared = :espesorPared")
    , @NamedQuery(name = "FichaTecnica.findByEspesorParedMin", query = "SELECT f FROM FichaTecnica f WHERE f.espesorParedMin = :espesorParedMin")
    , @NamedQuery(name = "FichaTecnica.findByEspesorParedMax", query = "SELECT f FROM FichaTecnica f WHERE f.espesorParedMax = :espesorParedMax")
    , @NamedQuery(name = "FichaTecnica.findByRugosidad", query = "SELECT f FROM FichaTecnica f WHERE f.rugosidad = :rugosidad")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExteriorBobina", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExteriorBobina = :diametroExteriorBobina")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExteriorBobinaMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExteriorBobinaMin = :diametroExteriorBobinaMin")
    , @NamedQuery(name = "FichaTecnica.findByDiametroInteriorBobina", query = "SELECT f FROM FichaTecnica f WHERE f.diametroInteriorBobina = :diametroInteriorBobina")
    , @NamedQuery(name = "FichaTecnica.findByDiametroExteriorBobinaMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroExteriorBobinaMax = :diametroExteriorBobinaMax")
    , @NamedQuery(name = "FichaTecnica.findByAlturaBobina", query = "SELECT f FROM FichaTecnica f WHERE f.alturaBobina = :alturaBobina")
    , @NamedQuery(name = "FichaTecnica.findByInspeccionVisual", query = "SELECT f FROM FichaTecnica f WHERE f.inspeccionVisual = :inspeccionVisual")
    , @NamedQuery(name = "FichaTecnica.findByEstado", query = "SELECT f FROM FichaTecnica f WHERE f.estado = :estado")
    , @NamedQuery(name = "FichaTecnica.findByUsuarioRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "FichaTecnica.findByFechaRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.fechaRegistro = :fechaRegistro")})
public class FichaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ficha_tecnica")
    private Integer idFichaTecnica;
    @Column(name = "producto")
    private String producto;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "version")
    private Integer version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "diametro_exterior")
    private Double diametroExterior;
    @Column(name = "diametro_exterior_min")
    private Double diametroExteriorMin;
    @Column(name = "diametro_exterior_max")
    private Double diametroExteriorMax;
    @Column(name = "diametro_interior")
    private Double diametroInterior;
    @Column(name = "diametro_interior_min")
    private Double diametroInteriorMin;
    @Column(name = "diametro_interior_max")
    private Double diametroInteriorMax;
    @Column(name = "espesor_pared")
    private Double espesorPared;
    @Column(name = "espesor_pared_min")
    private Double espesorParedMin;
    @Column(name = "espesor_pared_max")
    private Double espesorParedMax;
    @Column(name = "rugosidad")
    private Double rugosidad;
    @Column(name = "diametro_exterior_bobina")
    private Double diametroExteriorBobina;
    @Column(name = "diametro_exterior_bobina_min")
    private Double diametroExteriorBobinaMin;
    @Column(name = "diametro_interior_bobina")
    private Double diametroInteriorBobina;
    @Column(name = "diametro_exterior_bobina_max")
    private Double diametroExteriorBobinaMax;
    @Column(name = "altura_bobina")
    private Double alturaBobina;
    @Column(name = "inspeccion_visual")
    private Integer inspeccionVisual;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idFichaTecnica")
    private Collection<OrdenProduccion> ordenProduccionCollection;
    @OneToMany(mappedBy = "idFichaTecnica")
    private Collection<Registro> registroCollection;

    public FichaTecnica() {
    }

    public FichaTecnica(Integer idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
    }

    public Integer getIdFichaTecnica() {
        return idFichaTecnica;
    }

    public void setIdFichaTecnica(Integer idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getDiametroExterior() {
        return diametroExterior;
    }

    public void setDiametroExterior(Double diametroExterior) {
        this.diametroExterior = diametroExterior;
    }

    public Double getDiametroExteriorMin() {
        return diametroExteriorMin;
    }

    public void setDiametroExteriorMin(Double diametroExteriorMin) {
        this.diametroExteriorMin = diametroExteriorMin;
    }

    public Double getDiametroExteriorMax() {
        return diametroExteriorMax;
    }

    public void setDiametroExteriorMax(Double diametroExteriorMax) {
        this.diametroExteriorMax = diametroExteriorMax;
    }

    public Double getDiametroInterior() {
        return diametroInterior;
    }

    public void setDiametroInterior(Double diametroInterior) {
        this.diametroInterior = diametroInterior;
    }

    public Double getDiametroInteriorMin() {
        return diametroInteriorMin;
    }

    public void setDiametroInteriorMin(Double diametroInteriorMin) {
        this.diametroInteriorMin = diametroInteriorMin;
    }

    public Double getDiametroInteriorMax() {
        return diametroInteriorMax;
    }

    public void setDiametroInteriorMax(Double diametroInteriorMax) {
        this.diametroInteriorMax = diametroInteriorMax;
    }

    public Double getEspesorPared() {
        return espesorPared;
    }

    public void setEspesorPared(Double espesorPared) {
        this.espesorPared = espesorPared;
    }

    public Double getEspesorParedMin() {
        return espesorParedMin;
    }

    public void setEspesorParedMin(Double espesorParedMin) {
        this.espesorParedMin = espesorParedMin;
    }

    public Double getEspesorParedMax() {
        return espesorParedMax;
    }

    public void setEspesorParedMax(Double espesorParedMax) {
        this.espesorParedMax = espesorParedMax;
    }

    public Double getRugosidad() {
        return rugosidad;
    }

    public void setRugosidad(Double rugosidad) {
        this.rugosidad = rugosidad;
    }

    public Double getDiametroExteriorBobina() {
        return diametroExteriorBobina;
    }

    public void setDiametroExteriorBobina(Double diametroExteriorBobina) {
        this.diametroExteriorBobina = diametroExteriorBobina;
    }

    public Double getDiametroExteriorBobinaMin() {
        return diametroExteriorBobinaMin;
    }

    public void setDiametroExteriorBobinaMin(Double diametroExteriorBobinaMin) {
        this.diametroExteriorBobinaMin = diametroExteriorBobinaMin;
    }

    public Double getDiametroInteriorBobina() {
        return diametroInteriorBobina;
    }

    public void setDiametroInteriorBobina(Double diametroInteriorBobina) {
        this.diametroInteriorBobina = diametroInteriorBobina;
    }

    public Double getDiametroExteriorBobinaMax() {
        return diametroExteriorBobinaMax;
    }

    public void setDiametroExteriorBobinaMax(Double diametroExteriorBobinaMax) {
        this.diametroExteriorBobinaMax = diametroExteriorBobinaMax;
    }

    public Double getAlturaBobina() {
        return alturaBobina;
    }

    public void setAlturaBobina(Double alturaBobina) {
        this.alturaBobina = alturaBobina;
    }

    public Integer getInspeccionVisual() {
        return inspeccionVisual;
    }

    public void setInspeccionVisual(Integer inspeccionVisual) {
        this.inspeccionVisual = inspeccionVisual;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
    public Collection<OrdenProduccion> getOrdenProduccionCollection() {
        return ordenProduccionCollection;
    }

    public void setOrdenProduccionCollection(Collection<OrdenProduccion> ordenProduccionCollection) {
        this.ordenProduccionCollection = ordenProduccionCollection;
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
        hash += (idFichaTecnica != null ? idFichaTecnica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaTecnica)) {
            return false;
        }
        FichaTecnica other = (FichaTecnica) object;
        if ((this.idFichaTecnica == null && other.idFichaTecnica != null) || (this.idFichaTecnica != null && !this.idFichaTecnica.equals(other.idFichaTecnica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FichaTecnica[ idFichaTecnica=" + idFichaTecnica + " ]";
    }
    
}
