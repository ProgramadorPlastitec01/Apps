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
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "formula_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormulaC.findAll", query = "SELECT f FROM FormulaC f")
    , @NamedQuery(name = "FormulaC.findByIdFormulaC", query = "SELECT f FROM FormulaC f WHERE f.idFormulaC = :idFormulaC")
    , @NamedQuery(name = "FormulaC.findByFchRegistro", query = "SELECT f FROM FormulaC f WHERE f.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "FormulaC.findByUsuRegistro", query = "SELECT f FROM FormulaC f WHERE f.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "FormulaC.findByProducto", query = "SELECT f FROM FormulaC f WHERE f.producto = :producto")
    , @NamedQuery(name = "FormulaC.findByCodigo", query = "SELECT f FROM FormulaC f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "FormulaC.findByFchSolicitud", query = "SELECT f FROM FormulaC f WHERE f.fchSolicitud = :fchSolicitud")
    , @NamedQuery(name = "FormulaC.findByTExtrusion", query = "SELECT f FROM FormulaC f WHERE f.tExtrusion = :tExtrusion")
    , @NamedQuery(name = "FormulaC.findByTMaterial", query = "SELECT f FROM FormulaC f WHERE f.tMaterial = :tMaterial")
    , @NamedQuery(name = "FormulaC.findByExtructura", query = "SELECT f FROM FormulaC f WHERE f.extructura = :extructura")
    , @NamedQuery(name = "FormulaC.findByCapa", query = "SELECT f FROM FormulaC f WHERE f.capa = :capa")
    , @NamedQuery(name = "FormulaC.findByCtdCapa", query = "SELECT f FROM FormulaC f WHERE f.ctdCapa = :ctdCapa")
    , @NamedQuery(name = "FormulaC.findByEstado", query = "SELECT f FROM FormulaC f WHERE f.estado = :estado")})
public class FormulaC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formula_c")
    private Integer idFormulaC;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "producto")
    private String producto;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fch_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fchSolicitud;
    @Column(name = "t_extrusion")
    private String tExtrusion;
    @Column(name = "t_material")
    private String tMaterial;
    @Column(name = "extructura")
    private String extructura;
    @Column(name = "capa")
    private String capa;
    @Column(name = "ctd_capa")
    private Integer ctdCapa;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "fk_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto fkProyecto;

    public FormulaC() {
    }

    public FormulaC(Integer idFormulaC) {
        this.idFormulaC = idFormulaC;
    }

    public FormulaC(Integer idFormulaC, Date fchRegistro) {
        this.idFormulaC = idFormulaC;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdFormulaC() {
        return idFormulaC;
    }

    public void setIdFormulaC(Integer idFormulaC) {
        this.idFormulaC = idFormulaC;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
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

    public Date getFchSolicitud() {
        return fchSolicitud;
    }

    public void setFchSolicitud(Date fchSolicitud) {
        this.fchSolicitud = fchSolicitud;
    }

    public String getTExtrusion() {
        return tExtrusion;
    }

    public void setTExtrusion(String tExtrusion) {
        this.tExtrusion = tExtrusion;
    }

    public String getTMaterial() {
        return tMaterial;
    }

    public void setTMaterial(String tMaterial) {
        this.tMaterial = tMaterial;
    }

    public String getExtructura() {
        return extructura;
    }

    public void setExtructura(String extructura) {
        this.extructura = extructura;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Integer getCtdCapa() {
        return ctdCapa;
    }

    public void setCtdCapa(Integer ctdCapa) {
        this.ctdCapa = ctdCapa;
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

    public Proyecto getFkProyecto() {
        return fkProyecto;
    }

    public void setFkProyecto(Proyecto fkProyecto) {
        this.fkProyecto = fkProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulaC != null ? idFormulaC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaC)) {
            return false;
        }
        FormulaC other = (FormulaC) object;
        if ((this.idFormulaC == null && other.idFormulaC != null) || (this.idFormulaC != null && !this.idFormulaC.equals(other.idFormulaC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FormulaC[ idFormulaC=" + idFormulaC + " ]";
    }
    
}
