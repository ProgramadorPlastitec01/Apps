/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

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

/**
 *
 * @author a.sistemas2
 */
@Entity
@Table(name = "analisis_por_area")
@NamedQueries({
    @NamedQuery(name = "AnalisisPorArea.findAll", query = "SELECT a FROM AnalisisPorArea a"),
    @NamedQuery(name = "AnalisisPorArea.findByIdAnalisisPorArea", query = "SELECT a FROM AnalisisPorArea a WHERE a.idAnalisisPorArea = :idAnalisisPorArea"),
    @NamedQuery(name = "AnalisisPorArea.findByAnalisis", query = "SELECT a FROM AnalisisPorArea a WHERE a.analisis = :analisis"),
    @NamedQuery(name = "AnalisisPorArea.findByVolumenMuestrado", query = "SELECT a FROM AnalisisPorArea a WHERE a.volumenMuestrado = :volumenMuestrado"),
    @NamedQuery(name = "AnalisisPorArea.findByLoteProducto", query = "SELECT a FROM AnalisisPorArea a WHERE a.loteProducto = :loteProducto"),
    @NamedQuery(name = "AnalisisPorArea.findByMicroOrgaam", query = "SELECT a FROM AnalisisPorArea a WHERE a.microOrgaam = :microOrgaam"),
    @NamedQuery(name = "AnalisisPorArea.findByMicroOrgaHongos", query = "SELECT a FROM AnalisisPorArea a WHERE a.microOrgaHongos = :microOrgaHongos"),
    @NamedQuery(name = "AnalisisPorArea.findByMicroOrgaLevad", query = "SELECT a FROM AnalisisPorArea a WHERE a.microOrgaLevad = :microOrgaLevad"),
    @NamedQuery(name = "AnalisisPorArea.findByConcepto", query = "SELECT a FROM AnalisisPorArea a WHERE a.concepto = :concepto"),
    @NamedQuery(name = "AnalisisPorArea.findByFechaIngreso", query = "SELECT a FROM AnalisisPorArea a WHERE a.fechaIngreso = :fechaIngreso")})
public class AnalisisPorArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnalisis_Por_Area")
    private Integer idAnalisisPorArea;
    @Basic(optional = false)
    @Column(name = "Analisis")
    private String analisis;
    @Basic(optional = false)
    @Column(name = "Volumen_Muestrado")
    private String volumenMuestrado;
    @Basic(optional = false)
    @Column(name = "Lote_Producto")
    private String loteProducto;
    @Basic(optional = false)
    @Column(name = "Micro_Orga_a_m")
    private int microOrgaam;
    @Basic(optional = false)
    @Column(name = "Micro_Orga_Hongos")
    private int microOrgaHongos;
    @Basic(optional = false)
    @Column(name = "Micro_Orga_Levad")
    private int microOrgaLevad;
    @Basic(optional = false)
    @Column(name = "Concepto")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "Fecha_Ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Lob
    @Column(name = "Observaciones")
    private String observaciones;
    @JoinColumn(name = "Tipo_Area_idTipo_Area", referencedColumnName = "idTipo_Area")
    @ManyToOne(optional = false)
    private TipoArea tipoArea;
    @JoinColumn(name = "Desinfectante_idDesinfectante", referencedColumnName = "idDesinfectante")
    @ManyToOne(optional = false)
    private Desinfectante desinfectante;
    @JoinColumn(name = "Cabecera_idCabecera", referencedColumnName = "idCabecera")
    @ManyToOne(optional = false)
    private Cabecera cabecera;
    @JoinColumn(name = "Area_Muestrada_idArea_Muestrada", referencedColumnName = "idArea_Muestrada")
    @ManyToOne(optional = false)
    private AreaMuestrada areaMuestrada;

    public AnalisisPorArea() {
    }

    public AnalisisPorArea(Integer idAnalisisPorArea) {
        this.idAnalisisPorArea = idAnalisisPorArea;
    }

    public AnalisisPorArea(Integer idAnalisisPorArea, String analisis, String volumenMuestrado, String loteProducto, int microOrgaam, int microOrgaHongos, int microOrgaLevad, String concepto, Date fechaIngreso, String observaciones) {
        this.idAnalisisPorArea = idAnalisisPorArea;
        this.analisis = analisis;
        this.volumenMuestrado = volumenMuestrado;
        this.loteProducto = loteProducto;
        this.microOrgaam = microOrgaam;
        this.microOrgaHongos = microOrgaHongos;
        this.microOrgaLevad = microOrgaLevad;
        this.concepto = concepto;
        this.fechaIngreso = fechaIngreso;
        this.observaciones = observaciones;
    }

    public Integer getIdAnalisisPorArea() {
        return idAnalisisPorArea;
    }

    public void setIdAnalisisPorArea(Integer idAnalisisPorArea) {
        this.idAnalisisPorArea = idAnalisisPorArea;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getVolumenMuestrado() {
        return volumenMuestrado;
    }

    public void setVolumenMuestrado(String volumenMuestrado) {
        this.volumenMuestrado = volumenMuestrado;
    }

    public String getLoteProducto() {
        return loteProducto;
    }

    public void setLoteProducto(String loteProducto) {
        this.loteProducto = loteProducto;
    }

    public int getMicroOrgaam() {
        return microOrgaam;
    }

    public void setMicroOrgaam(int microOrgaam) {
        this.microOrgaam = microOrgaam;
    }

    public int getMicroOrgaHongos() {
        return microOrgaHongos;
    }

    public void setMicroOrgaHongos(int microOrgaHongos) {
        this.microOrgaHongos = microOrgaHongos;
    }

    public int getMicroOrgaLevad() {
        return microOrgaLevad;
    }

    public void setMicroOrgaLevad(int microOrgaLevad) {
        this.microOrgaLevad = microOrgaLevad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TipoArea getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(TipoArea tipoArea) {
        this.tipoArea = tipoArea;
    }

    public Desinfectante getDesinfectante() {
        return desinfectante;
    }

    public void setDesinfectante(Desinfectante desinfectante) {
        this.desinfectante = desinfectante;
    }

    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    public AreaMuestrada getAreaMuestrada() {
        return areaMuestrada;
    }

    public void setAreaMuestrada(AreaMuestrada areaMuestrada) {
        this.areaMuestrada = areaMuestrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnalisisPorArea != null ? idAnalisisPorArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisPorArea)) {
            return false;
        }
        AnalisisPorArea other = (AnalisisPorArea) object;
        if ((this.idAnalisisPorArea == null && other.idAnalisisPorArea != null) || (this.idAnalisisPorArea != null && !this.idAnalisisPorArea.equals(other.idAnalisisPorArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.AnalisisPorArea[idAnalisisPorArea=" + idAnalisisPorArea + "]";
    }

}
